-- commento rispetto ad una recensione
create table comment (
    id serial primary key,
    id_review integer not null,
    description varchar(1000) not null,

    foreign key (id_review)
        references review(id)
);