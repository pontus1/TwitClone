USE twitter_clone_db;

INSERT INTO `User` (username, email, `password`)
VALUES ('John', 'johndoe@somemail.com', 'hasse123'); 

INSERT INTO `User` (username, email, `password`)
VALUES ('Jane', 'janedoe@somemail.com', 'frasse123'); 

INSERT INTO User_role (user_id, role) VALUES (1, 'ROLE_USER');
INSERT INTO User_role (user_id, role) VALUES (2, 'ROLE_ADMIN');