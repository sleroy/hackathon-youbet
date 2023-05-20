/**
 * Feed provider that query a external provider to read data about Sport events 
 **/

// We take source from https://www.thesportsdb.com/api.php
import pkg from 'sqlite3';

import { createCountryCommand } from './domain/country.mjs'


import { EventBridgeClient } from "@aws-sdk/client-eventbridge";
import { YoubetEventBusClient } from './utils/YoutubeEventClient.mjs';
import { logger } from './utils/logger.mjs';
import { createLeagueCommand } from './domain/league.mjs';
import { createTeamCommand } from './domain/team.mjs';
import { registerMatchCommand, updateMatchDetailsCommand } from './domain/match.mjs';

const REGION = "us-east-1";
const { Database } = pkg;


try {
    // a client can be shared by different commands.
    const client = new EventBridgeClient({ region: REGION });
    const eventClient = new YoubetEventBusClient(client);

    // Open a SQLite database, stored in the file db.sqlite
    const db = new Database('/home/sleroy/database.sqlite');


    await importationCountries(db, eventClient);
    await importationLeagues(db, eventClient);
    await importationTeams(db, eventClient);
    await importationMatches(db, eventClient)


} catch (err) {
    logger.error("Got some error while fetching the data", err);
}


interface RecordCallback {
    (res: unknown): Promise<any>
}

interface CompleteCallback {
    (res: Promise<any>[]): Promise<any>
}

/**
 * Execute a each() on a query
 * @param db the database
 * @param query  the query
 * @param cb the callback to apply
 */
function executeApplyForAllQuery(db: pkg.Database, query: string, cb: RecordCallback, completeCb: CompleteCallback) {
    const promises: Promise<any>[] = [];
    const p = new Promise<Promise<any>[]>((resolve, reject) => {
        db.each(
            query,
            (err, res) => {
                if (err) reject(err);
                else {
                    const p = cb(res);
                    promises.push(p);
                }
            }
            , (err, count) => {
                if (err) {
                    reject(err); // optional: again, you might choose to swallow this error.
                } else {
                    resolve(promises); // resolve the promise
                }
            });
    });
    return p.then((ajax) => completeCb(ajax))
}

async function importationCountries(db: pkg.Database, eventClient: YoubetEventBusClient) {
    logger.info("Importation of the countries")
    const eachCb: RecordCallback = res => eventClient.send(createCountryCommand(res))
    const completeCb: CompleteCallback = promises => { logger.info("Importation of the countries => FINISHED", { records : promises.length }); return Promise.all(promises); }
    return await executeApplyForAllQuery(db, 'SELECT * from COUNTRY', eachCb, completeCb)

}

async function importationTeams(db: pkg.Database, eventClient: YoubetEventBusClient) {
    logger.info("Importation of the teams")
    const eachCb: RecordCallback = res => eventClient.send(createTeamCommand(res))
    const completeCb: CompleteCallback = promises => { logger.info("Importation of the teams => FINISHED", { records : promises.length }); return Promise.all(promises); }
    return await executeApplyForAllQuery(db, 'SELECT * from TEAM', eachCb, completeCb)
}


async function importationLeagues(db: pkg.Database, eventClient: YoubetEventBusClient) {
    logger.info("Importation of the leagues")
    const eachCb: RecordCallback = res => eventClient.send(createLeagueCommand(res))
    const completeCb: CompleteCallback = promises => { logger.info("Importation of the leagues => FINISHED", { records : promises.length }); return Promise.all(promises); }
    return await executeApplyForAllQuery(db, 'SELECT * from LEAGUE', eachCb, completeCb)        
}

async function importationMatches(db: pkg.Database, eventClient: YoubetEventBusClient) {
    logger.info("Importation of the matches")
    const sql = `SELECT
    m.id,
    c.name ,
    l.name,
    season,
    stage,
    date,
    thome.team_long_name as thome,
    taway .team_long_name as taway
    FROM
        "Match" m
    LEFT JOIN Team thome ON
        m.home_team_api_id = thome.team_api_id
    LEFT JOIN Team taway ON
        m.away_team_api_id = taway.team_api_id
    LEFT JOIN Country c ON
        m.country_id = c.id
    LEFT JOIN League l ON
        m.league_id = l.id;
;`
    const eachCb: RecordCallback = res => eventClient.send(registerMatchCommand(res))
    const completeCb: CompleteCallback = promises => { logger.info("Importation of the matches => FINISHED", { records : promises.length }); return Promise.all(promises); }
    await executeApplyForAllQuery(db, sql, eachCb, completeCb)        


    // For each match
    // We send an update event about the match is running
    const eachCb2: RecordCallback = res => eventClient.send(updateMatchDetailsCommand('running', res))
    const completeCb2: CompleteCallback = promises => { logger.info("Matched marked has running", { records : promises.length }); return Promise.all(promises); }
    await executeApplyForAllQuery(db, sql, eachCb2, completeCb2)        

        // For each match
    // We send an update event about the match is ended
    const eachCb3: RecordCallback = res => eventClient.send(updateMatchDetailsCommand('ended', res))
    const completeCb3: CompleteCallback = promises => { logger.info("Matched marked has ended", { records : promises.length }); return Promise.all(promises); }
    await executeApplyForAllQuery(db, sql, eachCb3, completeCb3);        



}

