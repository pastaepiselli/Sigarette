create table users (
    id uuid primary key,
    username varchar(50) not null,
    image_url varchar(1000),
    bio varchar(2000),
    updated_at timestamp not null,
    unique (username)
);