
# --- !Ups

ALTER TABLE topics ADD COLUMN question VARCHAR(2048) NOT NULL;

CREATE OR REPLACE VIEW v_topics_and_users
    (id, created_at, title, question, user_id, full_name, email_address, last_commented_at, comments_count)
AS
    SELECT t.id, t.created_at, t.title, t.question, u.id, u.full_name,
           u.email_address, t.last_commented_at, t.comments_count
    FROM   topics t
           JOIN users u ON t.user_id=u.id;
         
# --- !Downs

ALTER TABLE topics DROP COLUMN question;

CREATE OR REPLACE VIEW v_topics_and_users
    (id, created_at, title, user_id, full_name, email_address, last_commented_at, comments_count)
AS
    SELECT t.id, t.created_at, t.title, u.id, u.full_name, u.email_address, t.last_commented_at, t.comments_count
    FROM   topics t
           JOIN users u ON t.user_id=u.id;