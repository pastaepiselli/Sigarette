create table cigarette (
    id bigserial primary key ,
    brand_id bigint not null references brand(id),
    name varchar(150) not null,
    description text,
    image_url varchar(500),
    nicotine_mg numeric(3,1),
    tar_mg numeric(3,1),
    co_mg numeric(3,1),
    format numeric(30),
    sticks_per_pack INT,
    is_menthol boolean not null default false,
    avg_rating numeric(3,2) not null default 0,
    ratings_count integer not null default 0,
    created_at timestamp not null default now(),
    UNIQUE (brand_id, name)
);

