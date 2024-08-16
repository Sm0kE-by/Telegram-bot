create table games_links
(
    id        serial
        constraint games_links_pk
            primary key,
    name      varchar not null,
    link      varchar not null,
    clan_link varchar not null
);

alter table games_links
    owner to postgres;

