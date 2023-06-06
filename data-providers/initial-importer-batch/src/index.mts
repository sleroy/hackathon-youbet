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
    const db = new Database('/home/sleroy/git/database.sqlite');


    //await importationCountries(db, eventClient);
    //await importationLeagues(db, eventClient);
    //await importationTeams(db, eventClient);
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



/**
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
 */

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
    taway .team_long_name as taway,
    home_team_goal, away_team_goal, home_player_X1, home_player_X2, home_player_X3, home_player_X4, home_player_X5, home_player_X6, home_player_X7, home_player_X8, home_player_X9, home_player_X10, home_player_X11, away_player_X1, away_player_X2, away_player_X3, away_player_X4, away_player_X5, away_player_X6, away_player_X7, away_player_X8, away_player_X9, away_player_X10, away_player_X11, home_player_Y1, home_player_Y2, home_player_Y3, home_player_Y4, home_player_Y5, home_player_Y6, home_player_Y7, home_player_Y8, home_player_Y9, home_player_Y10, home_player_Y11, away_player_Y1, away_player_Y2, away_player_Y3, away_player_Y4, away_player_Y5, away_player_Y6, away_player_Y7, away_player_Y8, away_player_Y9, away_player_Y10, away_player_Y11, home_player_1, home_player_2, home_player_3, home_player_4, home_player_5, home_player_6, home_player_7, home_player_8, home_player_9, home_player_10, home_player_11, away_player_1, away_player_2, away_player_3, away_player_4, away_player_5, away_player_6, away_player_7, away_player_8, away_player_9, away_player_10, away_player_11, goal, shoton, shotoff, foulcommit, card, "cross", corner, possession
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
    const eachCb: RecordCallback = async (res: any) => {
        // Row from Match
        console.log(res);

        const fields = []
        for (let i = 1; i <= 11; ++i) {
            fields.push("home_player_" + i);
            fields.push("away_player_" + i);            
            fields.push("away_player_X" + i);
            fields.push("away_player_Y" + i);
            fields.push("home_player_X" + i);
            fields.push("home_player_Y" + i);
        }
        const promises : Promise<any>[] = fields.map(field => {
            return new Promise<any>((resolve, reject) => {
                db.get("SELECT * FROM PLAYER WHERE id=${playerId}", (err, row: any) => {
                    if (err) reject(err);
                    res[field] = row.id;
                    return resolve(res);
                });
            })
        });

        return Promise.all(promises)
    }
        
    const completeCb: CompleteCallback = promises => { logger.info("Importation of the matches => FINISHED", { records : promises.length }); return Promise.all(promises); }
    await executeApplyForAllQuery(db, sql, eachCb, completeCb)        

    /**
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

 */

}

