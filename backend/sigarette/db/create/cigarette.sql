create table cigarette (
    id bigserial primary key ,
    brand_id bigint not null references brand(id),
    name varchar(150) not null,
    price real not null,
    description text,
    image_url varchar(500),
    nicotine_mg real,
    tar_mg real,
    co_mg real,
    format varchar(30),
    sticks_per_pack INT,
    is_menthol boolean not null default false,
    avg_rating float not null default 0,
    ratings_count integer not null default 0,
    created_at timestamp not null default now(),
    unique (brand_id, name)
);

