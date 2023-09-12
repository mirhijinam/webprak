drop schema public cascade;
create schema public;

create table if not exists genre (
    genre_id            serial primary key,
    genre_name          text not null
);

create table if not exists author (
    author_id           serial primary key,
    author_name         text not null
);

create table if not exists client (
    client_id           serial primary key,
    client_name         text not null,
    mail                text,
    phone               varchar(11) not null
);

create table if not exists city (
    city_id             serial primary key,
    city_name           text not null,
    delivery_days       integer not null
);

create table if not exists client_city_rel (
    id                  serial primary key,
    client_id           integer not null references client(client_id) on delete cascade,
    city_id             integer not null references city(city_id) on delete cascade,
    street_name         text not null
);

create table if not exists book (
    book_id             serial primary key,
    book_name           text not null,
    price               integer not null,
    is_available        text not null,
    num_of_copies       integer not null
);

create table if not exists "order" (
    order_id            serial primary key,
    creation_data       date not null,
    delivery_data       date not null,
    status              varchar(11) not null,
    price               integer not null
);



create table if not exists ordering_book_rel (
    id                  serial primary key,
    order_id            integer not null references "order"(order_id) on delete cascade,
    book_id             integer not null references book(book_id) on delete cascade,
    amount              integer not null,
    total_cost          integer not null
);

create table if not exists book_genre_rel (
    id                  serial primary key,
    book_id             integer not null references book(book_id) on delete cascade,
    genre_id            integer not null references genre(genre_id) on delete cascade
);

create table if not exists book_author_rel (
    id                  serial primary key,
    book_id             integer not null references book(book_id) on delete cascade,
    author_id           integer not null references author(author_id) on delete cascade
);

create table if not exists order_history_rel (
    id                  serial primary key,
    client_id           integer not null references client(client_id) on delete cascade,
    order_id            integer not null references "order"(order_id) on delete cascade
);