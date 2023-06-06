-- Your SQL scripts for initialization goes here...
-- Country definition
USE matchsystem;

SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS `Country`;
DROP TABLE IF EXISTS `Team`;
DROP TABLE IF EXISTS `League`;
DROP TABLE IF EXISTS `Match`;
DROP TABLE IF EXISTS `Player`;
DROP TABLE IF EXISTS `MatchRecord`;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE matchsystem.`Country`
(
    `id`   int          NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `Country_UN` (`name`)
);
INSERT INTO Country (name)
VALUES ('Belgium'),
       ('England'),
       ('France'),
       ('Germany'),
       ('Italy'),
       ('Netherlands'),
       ('Poland'),
       ('Portugal'),
       ('Scotland'),
       ('Spain'),
       ('Switzerland');

-- matchsystem.Player definition	

CREATE TABLE `Player`
(
    `id`            int          NOT NULL AUTO_INCREMENT,
    `player_api_id` varchar(255) NOT NULL,
    `player_name`   TEXT,
    `birthday`      TEXT,
    `height`        INTEGER,
    `weight`        INTEGER,
    PRIMARY KEY (`id`)
);


-- matchsystem.Team definition

CREATE TABLE matchsystem.`Team`
(
    `id`          int          NOT NULL AUTO_INCREMENT,
    `team_api_id` varchar(255) NOT NULL,
    `name`        varchar(1024) DEFAULT NULL,
    `short_name`  varchar(255)  DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `Team_UN` (`team_api_id`)
);


-- matchsystem.League definition

CREATE TABLE matchsystem.`League`
(
    `id`            int          NOT NULL AUTO_INCREMENT,
    `country_id`    int DEFAULT NULL,
    `name`          varchar(255) NOT NULL,
    `league_api_id` varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UC_League_Name` (`name`),
    UNIQUE KEY `UC_League_Api_id` (`league_api_id`),
    KEY `country_id` (`country_id`),
    CONSTRAINT `League_ibfk_1` FOREIGN KEY (`country_id`) REFERENCES `Country` (`id`)
);

-- matchsystem.`Match` definition

CREATE TABLE matchsystem.`Match`
(
    `id`               int                                            NOT NULL AUTO_INCREMENT,
    `country_id`       int                                            NOT NULL,
    `league_id`        int                                            NOT NULL,
    `match_api_id`     varchar(255)                                   NOT NULL,
    `season`           varchar(255)                                            DEFAULT NULL,
    `state`            enum ('planned','running','cancelled','ended') NOT NULL DEFAULT 'planned',
    `date`             datetime                                       NOT NULL,
    `home_team_api_id` int                                            NOT NULL,
    `away_team_api_id` int                                            NOT NULL,
    `score_team_home`  INT                                            NULL,
    `score_team_away`  INT                                            NULL,
    `home_player_X1`   INTEGER,
    `home_player_X2`   INTEGER,
    `home_player_X3`   INTEGER,
    `home_player_X4`   INTEGER,
    `home_player_X5`   INTEGER,
    `home_player_X6`   INTEGER,
    `home_player_X7`   INTEGER,
    `home_player_X8`   INTEGER,
    `home_player_X9`   INTEGER,
    `home_player_X10`  INTEGER,
    `home_player_X11`  INTEGER,
    `away_player_X1`   INTEGER,
    `away_player_X2`   INTEGER,
    `away_player_X3`   INTEGER,
    `away_player_X4`   INTEGER,
    `away_player_X5`   INTEGER,
    `away_player_X6`   INTEGER,
    `away_player_X7`   INTEGER,
    `away_player_X8`   INTEGER,
    `away_player_X9`   INTEGER,
    `away_player_X10`  INTEGER,
    `away_player_X11`  INTEGER,
    `home_player_Y1`   INTEGER,
    `home_player_Y2`   INTEGER,
    `home_player_Y3`   INTEGER,
    `home_player_Y4`   INTEGER,
    `home_player_Y5`   INTEGER,
    `home_player_Y6`   INTEGER,
    `home_player_Y7`   INTEGER,
    `home_player_Y8`   INTEGER,
    `home_player_Y9`   INTEGER,
    `home_player_Y10`  INTEGER,
    `home_player_Y11`  INTEGER,
    `away_player_Y1`   INTEGER,
    `away_player_Y2`   INTEGER,
    `away_player_Y3`   INTEGER,
    `away_player_Y4`   INTEGER,
    `away_player_Y5`   INTEGER,
    `away_player_Y6`   INTEGER,
    `away_player_Y7`   INTEGER,
    `away_player_Y8`   INTEGER,
    `away_player_Y9`   INTEGER,
    `away_player_Y10`  INTEGER,
    `away_player_Y11`  INTEGER,
    `home_player_1`    INTEGER,
    `home_player_2`    INTEGER,
    `home_player_3`    INTEGER,
    `home_player_4`    INTEGER,
    `home_player_5`    INTEGER,
    `home_player_6`    INTEGER,
    `home_player_7`    INTEGER,
    `home_player_8`    INTEGER,
    `home_player_9`    INTEGER,
    `home_player_10`   INTEGER,
    `home_player_11`   INTEGER,
    `away_player_1`    INTEGER,
    `away_player_2`    INTEGER,
    `away_player_3`    INTEGER,
    `away_player_4`    INTEGER,
    `away_player_5`    INTEGER,
    `away_player_6`    INTEGER,
    `away_player_7`    INTEGER,
    `away_player_8`    INTEGER,
    `away_player_9`    INTEGER,
    `away_player_10`   INTEGER,
    `away_player_11`   INTEGER,
    `goal`             TEXT DEFAULT NULL,
    `shoton`           TEXT DEFAULT NULL,
    `shotoff`          TEXT DEFAULT NULL,
    `foulcommit`       TEXT DEFAULT NULL,
    `card`             TEXT DEFAULT NULL,
    `cross`            TEXT DEFAULT NULL,
    `corner`           TEXT DEFAULT NULL,
    `possession`       TEXT DEFAULT NULL,
    `homewin`          FLOAT                                                   DEFAULT 0.0,
    `awaywin`          FLOAT                                                   DEFAULT 0.0,
    `stalemate`        FLOAT                                                   DEFAULT 0.0,
    PRIMARY KEY (`id`),
    KEY `match_country_id` (`country_id`),
    KEY `match_league_id` (`league_id`),
    KEY `match_home_team_api_id` (`home_team_api_id`),
    KEY `match_away_team_api_id` (`away_team_api_id`),
    UNIQUE KEY `UC_Match_Api_id` (`match_api_id`),
    CONSTRAINT `Match_ibfk_1` FOREIGN KEY (`country_id`) REFERENCES `Country` (`id`),
    CONSTRAINT `Match_ibfk_2` FOREIGN KEY (`league_id`) REFERENCES `League` (`id`),
    CONSTRAINT `Match_ibfk_3` FOREIGN KEY (`home_team_api_id`) REFERENCES `Team` (`id`),
    CONSTRAINT `Match_ibfk_4` FOREIGN KEY (`away_team_api_id`) REFERENCES `Team` (`id`)
);

CREATE TABLE matchsystem.`MatchRecord`
(
    `id`               int      NOT NULL AUTO_INCREMENT,
    `country_id`       int      NOT NULL,
    `league_id`        int      NOT NULL,
    `season`           varchar(255) DEFAULT NULL,
    `date`             datetime NOT NULL,
    `home_team_api_id` int      NOT NULL,
    `away_team_api_id` int      NOT NULL,
    `score_team_home`  INT      NULL,
    `score_team_away`  INT      NULL,
    `home_player_X1`   INTEGER,
    `home_player_X2`   INTEGER,
    `home_player_X3`   INTEGER,
    `home_player_X4`   INTEGER,
    `home_player_X5`   INTEGER,
    `home_player_X6`   INTEGER,
    `home_player_X7`   INTEGER,
    `home_player_X8`   INTEGER,
    `home_player_X9`   INTEGER,
    `home_player_X10`  INTEGER,
    `home_player_X11`  INTEGER,
    `away_player_X1`   INTEGER,
    `away_player_X2`   INTEGER,
    `away_player_X3`   INTEGER,
    `away_player_X4`   INTEGER,
    `away_player_X5`   INTEGER,
    `away_player_X6`   INTEGER,
    `away_player_X7`   INTEGER,
    `away_player_X8`   INTEGER,
    `away_player_X9`   INTEGER,
    `away_player_X10`  INTEGER,
    `away_player_X11`  INTEGER,
    `home_player_Y1`   INTEGER,
    `home_player_Y2`   INTEGER,
    `home_player_Y3`   INTEGER,
    `home_player_Y4`   INTEGER,
    `home_player_Y5`   INTEGER,
    `home_player_Y6`   INTEGER,
    `home_player_Y7`   INTEGER,
    `home_player_Y8`   INTEGER,
    `home_player_Y9`   INTEGER,
    `home_player_Y10`  INTEGER,
    `home_player_Y11`  INTEGER,
    `away_player_Y1`   INTEGER,
    `away_player_Y2`   INTEGER,
    `away_player_Y3`   INTEGER,
    `away_player_Y4`   INTEGER,
    `away_player_Y5`   INTEGER,
    `away_player_Y6`   INTEGER,
    `away_player_Y7`   INTEGER,
    `away_player_Y8`   INTEGER,
    `away_player_Y9`   INTEGER,
    `away_player_Y10`  INTEGER,
    `away_player_Y11`  INTEGER,
    `home_player_1`    INTEGER,
    `home_player_2`    INTEGER,
    `home_player_3`    INTEGER,
    `home_player_4`    INTEGER,
    `home_player_5`    INTEGER,
    `home_player_6`    INTEGER,
    `home_player_7`    INTEGER,
    `home_player_8`    INTEGER,
    `home_player_9`    INTEGER,
    `home_player_10`   INTEGER,
    `home_player_11`   INTEGER,
    `away_player_1`    INTEGER,
    `away_player_2`    INTEGER,
    `away_player_3`    INTEGER,
    `away_player_4`    INTEGER,
    `away_player_5`    INTEGER,
    `away_player_6`    INTEGER,
    `away_player_7`    INTEGER,
    `away_player_8`    INTEGER,
    `away_player_9`    INTEGER,
    `away_player_10`   INTEGER,
    `away_player_11`   INTEGER,
    PRIMARY KEY (`id`),
    KEY `prediction_country_id` (`country_id`),
    KEY `prediction_league_id` (`league_id`),
    KEY `prediction_home_team_api_id` (`home_team_api_id`),
    KEY `prediction_away_team_api_id` (`away_team_api_id`),
    CONSTRAINT `Prediction_ibfk_1` FOREIGN KEY (`country_id`) REFERENCES `Country` (`id`),
    CONSTRAINT `Prediction_ibfk_2` FOREIGN KEY (`league_id`) REFERENCES `League` (`id`),
    CONSTRAINT `Prediction_ibfk_3` FOREIGN KEY (`home_team_api_id`) REFERENCES `Team` (`id`),
    CONSTRAINT `Prediction_ibfk_4` FOREIGN KEY (`away_team_api_id`) REFERENCES `Team` (`id`)
);



