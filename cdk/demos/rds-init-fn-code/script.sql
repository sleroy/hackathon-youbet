-- Your SQL scripts for initialization goes here...
-- Country definition
USE matchsystem;

SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS `Country`;
DROP TABLE IF EXISTS `Team`;
DROP TABLE IF EXISTS `League`;
DROP TABLE IF EXISTS `Match`;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE  matchsystem.`Country` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Country_UN` (`name`)
);
INSERT INTO Country (name) VALUES
	 ('Belgium'),
	 ('England'),
	 ('France'),
	 ('Germany'),
	 ('Italy'),
	 ('Netherlands'),
	 ('Poland'),
	 ('Portugal'),
	 ('Scotland'),
	 ('Spain');
INSERT INTO Country (name) VALUES
	 ('Switzerland');


-- matchsystem.Team definition

CREATE TABLE matchsystem.`Team` (
  `id` int NOT NULL AUTO_INCREMENT,
  `team_api_id` varchar(255) NOT NULL,
  `name` varchar(1024) DEFAULT NULL,
  `short_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Team_UN` (`team_api_id`)
);


-- matchsystem.League definition

CREATE TABLE matchsystem.`League` (
  `id` int NOT NULL AUTO_INCREMENT,
  `country_id` int DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `league_api_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UC_League_Name` (`name`),
  UNIQUE KEY `UC_League_Api_id` (`league_api_id`),
  KEY `country_id` (`country_id`),
  CONSTRAINT `League_ibfk_1` FOREIGN KEY (`country_id`) REFERENCES `Country` (`id`)
);

-- matchsystem.`Match` definition

CREATE TABLE matchsystem.`Match` (
  `id` int NOT NULL,
  `country_id` int NOT NULL,
  `league_id` int NOT NULL,
  `match_api_id` varchar(255) NOT NULL,
  `season` varchar(255) DEFAULT NULL,
  `state` enum('planned','running','cancelled','ended') NOT NULL DEFAULT 'planned',
  `date` datetime NOT NULL,
  `home_team_api_id` int NOT NULL,
  `away_team_api_id` int NOT NULL,
  `score_team_home` INT NULL,
  `score_team_away` INT NULL,
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
