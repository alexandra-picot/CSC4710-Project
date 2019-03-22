CREATE TABLE `authors` (
	`email` varchar(255) NOT NULL,
	`name` varchar(150) NOT NULL,
	`affiliation` varchar(100) NOT NULL,
	PRIMARY KEY (`email`)
);

CREATE TABLE `papers` (
	`paperid` int NOT NULL AUTO_INCREMENT,
	`title` varchar(255) NOT NULL,
	`abstract` TEXT NOT NULL,
	`pdf` varchar(255) NOT NULL,
	PRIMARY KEY (`paperid`)
);

CREATE TABLE `paper_authors` (
	`paper_id` int NOT NULL,
	`author_id` varchar(255) NOT NULL,
	`contribution_significance` int NOT NULL,
	PRIMARY KEY (`paper_id`,`author_id`),
	FOREIGN KEY (`paper_id`) REFERENCES `papers`(`paperid`)
	ON UPDATE CASCADE
	ON DELETE CASCADE,
	FOREIGN KEY (`author_id`) REFERENCES `authors`(`email`)
	ON UPDATE CASCADE
	ON DELETE CASCADE
);

CREATE TABLE `pc_members` (
	`email` varchar(255) NOT NULL,
	`name` varchar(150) NOT NULL,
	PRIMARY KEY (`email`)
);

CREATE TABLE `reports` (
	`reportid` int NOT NULL AUTO_INCREMENT,
	`comment` TEXT NOT NULL DEFAULT NULL,
	`recommendation` char(1) NOT NULL DEFAULT 'P',
	`submission_date` DATE NOT NULL DEFAULT NULL,
	`paper_id` int NOT NULL,
	`pc_member_id` varchar(255) NOT NULL,
	PRIMARY KEY (`reportid`),
	FOREIGN KEY (`paper_id`) REFERENCES `papers`(`paperid`)
	ON UPDATE CASCADE
	ON DELETE CASCADE,
	FOREIGN KEY (`pc_member_id`) REFERENCES `pc_members`(`email`)
	ON UPDATE CASCADE
	ON DELETE CASCADE,
	CHECK ( recommendation IN ('A', 'R', 'P'))
);