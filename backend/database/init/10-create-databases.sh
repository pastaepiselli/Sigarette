#!/bin/sh
set -eu

createdb -U "$POSTGRES_USER" reviews
createdb -U "$POSTGRES_USER" sigarette

psql -v ON_ERROR_STOP=1 -U "$POSTGRES_USER" -d users -f /schema/user.sql
psql -v ON_ERROR_STOP=1 -U "$POSTGRES_USER" -d users -f /schema/saved_cigarettes.sql

psql -v ON_ERROR_STOP=1 -U "$POSTGRES_USER" -d reviews -f /schema/review.sql
psql -v ON_ERROR_STOP=1 -U "$POSTGRES_USER" -d reviews -f /schema/comment.sql

psql -v ON_ERROR_STOP=1 -U "$POSTGRES_USER" -d sigarette -f /schema/brand.sql
psql -v ON_ERROR_STOP=1 -U "$POSTGRES_USER" -d sigarette -f /schema/cigarette.sql
psql -v ON_ERROR_STOP=1 -U "$POSTGRES_USER" -d sigarette -f /schema/insert.sql
