create table message_photo
(
    id               serial
        constraint message_photo_pk
            primary key,
    telegram_file_id varchar not null,
    file_size        integer not null,
    message_id       integer
        references message_user
);

alter table message_photo
    owner to postgres;

