/**
 * Feed provider that query a external provider to read data about Sport events 
 **/

// We take source from https://www.thesportsdb.com/api.php
import * as fs from 'fs';
import * as path from 'path';
import { setTimeout } from "timers/promises";
import pkg from 'sqlite3';
const { Database } = pkg;

try {

    // Open a SQLite database, stored in the file db.sqlite
    const db = new Database('/home/sleroy/database.sqlite');

    // Fetch a random integer between -99 and +99
    db.each(
        'SELECT * from COUNTRY',
        (err, res) => console.log(res)
    );

} catch (err) {
    console.error("Got some error while fetching the data", err);
}

