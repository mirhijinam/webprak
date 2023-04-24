drop schema public cascade;
create schema public;

create table if not exists city (
    city_id             serial primary key,
    city_name           text not null,
    delivery_days       interval not null
);

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
    address_id          integer unique,
    mail                text,
    phone               varchar(11) not null
);

create table if not exists book (
    book_id             serial primary key,
    book_name           text not null,
    publication_info    jsonb not null,
    price               money not null,
    is_available        boolean not null,
    num_of_copies       integer not null
);

create type order_status as enum('ready', 'received', 'on_the_way', 'cancelled');
create table if not exists "order" (
    order_id            serial primary key,
    creation_data       date not null,
    delivery_data       date not null,
    delivery_terms      jsonb not null,
    status              order_status not null,
    price               money not null
);

create table if not exists client_city_rel (
    id                  serial primary key,
    client_id          integer references client(client_id),
    city_id             integer references city(city_id),
    street_name         text not null
);

create table if not exists ordering_book_rel (
    id                  serial primary key,
    order_id            integer references "order"(order_id),
    book_id             integer references book(book_id),
    amount              integer not null,
    total_cost          integer not null
);

create table if not exists book_genre_rel (
    id                  serial primary key,
    book_id             integer references book(book_id),
    genre_id            integer references genre(genre_id)
);

create table if not exists book_author_rel (
    id                  serial primary key,
    book_id             integer references book(book_id),
    author_id           integer references author(author_id)
);

create table if not exists order_history_rel (
    id                  serial primary key,
    client_id           integer references client(client_id),
    order_id            integer references "order"(order_id)
);