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

const REGION = "us-east-1";
const { Database } = pkg;


try {
    // a client can be shared by different commands.
    const client = new EventBridgeClient({ region: REGION });
    const eventClient = new YoubetEventBusClient(client);

    // Open a SQLite database, stored in the file db.sqlite
    const db = new Database('/home/sleroy/database.sqlite');


    importationCountries(db, eventClient);
    importationLeagues(db, eventClient);


} catch (err) {
    logger.error("Got some error while fetching the data", err);
}

async function importationCountries(db: pkg.Database, eventClient: YoubetEventBusClient) {
    logger.info("Importation of the countries")
    await db.each(
        'SELECT * from COUNTRY',
        (err, res) => {
            eventClient.send(createCountryCommand(res));
        }
    );
}

async function importationLeagues(db: pkg.Database, eventClient: YoubetEventBusClient) {
    await db.each(
        'SELECT * from LEAGUE',
        (err, res) => {
            eventClient.send(createLeagueCommand(res));
        }
    );
}

