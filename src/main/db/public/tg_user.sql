create table tg_user
(
    id         integer not null
        primary key,
    first_name varchar not null,
    last_name  varchar not null,
    user_name  varchar not null
);

alter table tg_user
    owner to postgres;

