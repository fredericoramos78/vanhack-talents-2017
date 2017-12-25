
# --- !Ups


CREATE TABLE users (
    id             INTEGER NOT NULL AUTO_INCREMENT,
    full_name      VARCHAR(255) NOT NULL,
    email_address  VARCHAR(255) NOT NULL,
    created_at     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT users_pk PRIMARY KEY (id)
);

CREATE UNIQUE INDEX users_email_uk ON users (email_address);

CREATE TABLE topics (
    id                INTEGER NOT NULL AUTO_INCREMENT,
    created_at        DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id           INTEGER NOT NULL,
    title             VARCHAR(255) NOT NULL,
    comments_count    INTEGER NOT NULL DEFAULT 0,
    last_commented_at DATETIME,
    CONSTRAINT topics_pk PRIMARY KEY (id)
);

CREATE INDEX topics_user_idx ON topics (user_id, last_commented_at);


CREATE VIEW v_topics_and_users (id, created_at, title, user_id, full_name, email_address, last_commented_at, comments_count)
AS 
    SELECT t.id, t.created_at, t.title, u.id, u.full_name, u.email_address, t.last_commented_at, t.comments_count
    FROM   topics t
           JOIN users u ON t.user_id=u.id;

         
# --- !Downs

DROP VIEW v_topics_and_users;

DROP TABLE topics;
DROP TABLE users;
