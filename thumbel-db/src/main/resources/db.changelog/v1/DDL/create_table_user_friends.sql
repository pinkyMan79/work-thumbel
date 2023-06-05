CREATE TABLE t_user
(
    id UUID NOT NULL,
    create_date TIMESTAMP WITHOUT TIME ZONE,
    login VARCHAR(255),
    password VARCHAR(255),
    email VARCHAR(255),
    photo OID,
    bio VARCHAR(255),
    position VARCHAR(255),
    state VARCHAR(255),
    role VARCHAR(255),
    CONSTRAINT pk_t_user PRIMARY KEY (id)
);

CREATE TABLE user_to_subs
(
    subscriber_id UUID NOT NULL,
    user_id UUID NOT NULL
);

ALTER TABLE t_user
    ADD CONSTRAINT hashPassword UNIQUE (password);

ALTER TABLE t_user
    ADD CONSTRAINT login UNIQUE (login);

ALTER TABLE user_to_subs
    ADD CONSTRAINT f_setosu_on_subscriber FOREIGN KEY (subscriber_id) REFERENCES t_user (id);

ALTER TABLE user_to_subs
    ADD CONSTRAINT f_setosu_on_user FOREIGN KEY (user_id) REFERENCES t_user (id);