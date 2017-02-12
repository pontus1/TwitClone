DROP DATABASE twitter_clone_db;
CREATE DATABASE twitter_clone_db;
USE twitter_clone_db;

CREATE TABLE `User`
(
	user_id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    enabled TINYINT NOT NULL DEFAULT 1,
    PRIMARY KEY (user_id)
);

CREATE TABLE User_role 
(
	user_role_id int(10) NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    role VARCHAR(10),
	PRIMARY KEY (user_role_id),
	FOREIGN KEY (user_id) REFERENCES `User`(user_id) ON DELETE CASCADE,
	CONSTRAINT unique_user_role UNIQUE (user_id, role)	
);

CREATE TABLE Follow
(
	follower_id INT NOT NULL,
    followee_id INT NOT NULL,
    PRIMARY KEY (follower_id, followee_id),
    FOREIGN KEY (follower_id) REFERENCES `User`(user_id) ON DELETE CASCADE, 
    FOREIGN KEY (followee_id) REFERENCES `User`(user_id) ON DELETE CASCADE
);

CREATE TABLE Tweet
(
	message_id INT NOT NULL AUTO_INCREMENT,
    author_id INT NOT NULL,
    content VARCHAR(160) NOT NULL,
    pub_date TIMESTAMP,
    PRIMARY KEY (message_id),
	FOREIGN KEY (author_id) REFERENCES `User`(user_id) ON DELETE CASCADE
);


CREATE USER 'user'@'localhost' IDENTIFIED BY 'password';

GRANT EXECUTE, SELECT, INSERT, UPDATE, DELETE
ON twitter_clone_db.*
TO 'user'@'localhost';