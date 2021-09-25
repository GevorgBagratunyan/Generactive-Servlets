CREATE sequence item_sequence
    start with 1
    increment by 1;

CREATE sequence group_sequence
    start with 1
    increment by 1;


CREATE sequence user_sequence
    start with 1
    increment by 1;

CREATE TABLE groups
(
    id          bigint primary key default nextval('group_sequence'),
    name        varchar not null,
    parent_id   bigint references groups(id)

);

CREATE TABLE group_parent
(
    group_id bigint,
    parent_id bigint,
    PRIMARY KEY(group_id, parent_id)
);

CREATE TABLE item
(
    id         bigint primary key default nextval('item_sequence'),
    name       varchar not null,
    url        varchar,
    base_price numeric             default 0.0,
    group_id   bigint references groups (id)
);

CREATE TABLE stock_item
(
    id         bigint primary key references item(id) ON DELETE CASCADE
);

CREATE TABLE generative_item
(
    id         bigint primary key references item(id) ON DELETE CASCADE ,
    complexity varchar check ( complexity in ('ONE', 'TWO') )
);

CREATE TABLE "user"
(
    id       bigint primary key default nextval('user_sequence'),
    username varchar not null,
    password varchar not null,
    role     varchar check ( role in ('ADMIN', 'USER', 'GUEST') )
);


