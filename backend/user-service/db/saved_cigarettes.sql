CREATE TABLE saved_cigarettes (
    id bigserial primary key,
    id_user uuid not null references users(id),
    id_cigarette bigint NOT NULL,
    status varchar(20) not null
        CHECK (status IN ('LIKED', 'WANT_TO_TRY')),
    created_at timestamp not null,
    unique (id_user, id_cigarette)
);
