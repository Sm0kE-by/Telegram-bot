create table attributes
(
    id         serial
        constraint country_pk
            primary key,
    type_name  varchar not null,
    attribute1 varchar not null,
    attribute2 varchar not null,
    attribute3 varchar not null,
    attribute4 varchar not null,
    attribute5 varchar not null
);

alter table attributes
    owner to postgres;

