truncate table client cascade;

truncate table ordering_book_rel;

truncate table order_history_rel;

truncate table "order" cascade;


--//-------------------------------Always Need---------------------------------------------------------//--


insert into city values
    (1, 'ДС1', 1),
    (2, 'ДС2', 2),
    (3, 'ДС3', 3);

insert into author values
    (1, 'Автор1'),
    (2, 'Автор2'),
    (3, 'Автор3');

insert into genre values
    (1, 'Жанр1'),
    (2, 'Жанр2'),
    (3, 'Жанр3');

insert into book values
    (1, 'Книга1', 100, 'yes', 10),
    (2, 'Книга2', 200, 'yes', 20),
    (3, 'Книга3', 300, 'yes', 30);

insert into book_author_rel values
    (1, 1, 1),
    (2, 2, 2),
    (3, 3, 3),
    (4, 1, 2);

insert into book_genre_rel values
    (1, 1, 1),
    (2, 2, 2),
    (3, 3, 3),
    (4, 2, 3);


--//----------------------------------------------------------------------------------------//--


insert into client values
    (1, 'Иван Иванов', 'ivanov@mail.ru', '89999999991'),
    (2, 'Петр Петров', 'petrov@mail.ru', '89999999992'),
    (3, 'Павел Павлов', 'pavlov@mail.ru', '89999999993');

insert into client_city_rel values
    (1, 1, 1, 'Улица1'),
    (2, 2, 2, 'Улица2'),
    (3, 3, 3, 'Улица3');

insert into "order" values
    (1, '2022-01-01', '2022-01-02', 'FINISHED', 500),
    (2, '2022-02-02', '2022-02-04', 'IN_PROGRESS', 1000),
    (3, '2022-03-03', '2022-03-07', 'NEW', 1500);

insert into order_history_rel values
    (1, 1, 1),
    (2, 2, 2),
    (3, 3, 3);

insert into ordering_book_rel values
(1, 1, 1, 5, 500),
(2, 2, 2, 5, 1000),
(3, 3, 3, 5, 1500);
