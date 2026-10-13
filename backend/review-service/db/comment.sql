-- commento rispetto ad una recensione
create table comment (
    id bigserial primary key,
    id_review bigint not null,
    description varchar(1000) not null,
    dt_inserimento timestamp not null,
    foreign key (id_review)
        references review(id)
);