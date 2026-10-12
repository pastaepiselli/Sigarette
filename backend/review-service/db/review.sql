create table review (
    id number generated as identity primary key,
    id_user number not null,
    id_sigaretta number not null,
    voto number(1, 5) not null,
    descrizione varchar(1000),
    -- magari altra roba
);