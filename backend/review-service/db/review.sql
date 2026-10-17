create table review (
    id bigserial primary key,
    id_user uuid not null,
    id_cigarette bigint not null,
    rating real check ( rating >= 0.5 and rating <= 5.0 ) not null,
    description varchar(1000),
    updated_at timestamp not null
    -- magari altra roba
);