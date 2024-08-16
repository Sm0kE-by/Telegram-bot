create table exchange_links
(
    id   serial
        constraint exchange_links_pk
            primary key,
    name varchar not null,
    link varchar not null
);

alter table exchange_links
    owner to postgres;

