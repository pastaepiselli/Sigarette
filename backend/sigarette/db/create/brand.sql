create table brand (
    id bigserial primary key,
    name varchar(100) not null,
    country varchar(100),
    description TEXT,
    logo_url varchar(500)
);