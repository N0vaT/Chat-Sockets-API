DROP TABLE IF EXISTS message_table;
DROP TABLE IF EXISTS user_table;

CREATE TABLE IF NOT EXISTS user_table(
    user_id serial PRIMARY KEY NOT NULL,
    user_name varchar(10),
    user_password varchar(255)
);

CREATE TABLE IF NOT EXISTS message_table(
    message_id serial PRIMARY KEY NOT NULL,
    message_text varchar NOT NULL,
    message_time timestamp,
    message_sender integer NOT NULL,
    FOREIGN KEY (message_sender)
        REFERENCES user_table(user_id) ON DELETE CASCADE
);
