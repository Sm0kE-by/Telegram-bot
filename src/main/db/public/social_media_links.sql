create table social_media_links
(
    id   serial
        constraint social_media_links_pk
            primary key,
    name varchar not null,
    link varchar not null
);

alter table social_media_links
    owner to postgres;

