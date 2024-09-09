create table message_user
(
    user_id      integer not null
        primary key,
    title        varchar not null,
    text         varchar not null,
    link         varchar not null,
    hash_tage    varchar not null,
    social_link  varchar not null,
    from_handler varchar not null
);

alter table message_user
    owner to postgres;

