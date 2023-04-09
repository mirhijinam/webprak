# Книжный магазин

<img width="1120" alt="Снимок экрана 2023-04-10 в 12 06 19 AM" src="https://user-images.githubusercontent.com/59884652/230796506-8410f064-3f58-4fbd-ac6b-61d1b1e824c1.png">



## Описание страниц
С любой страницы можно перейти на главную, нажав соответвующую кнопку.

### ***Главная страница***
* Ссылка на страницу с полным списком книг
* Ссылка на страницу с полным списком клиентов
* Ссылка на страницу с полным списком заказов всех клиентов
* Ссылка на страницу с формой оформления заказа

### ***Cтраница с полным списком книг***
* Список книг, добавленных в базу данных с возможностью фильтровать по жанрам, авторам и др. характеристикам.
* Переход на страницу книги с дополнительной информацией при нажатии на ее ID
* Форма для поиска книги по названию, перенаправляющая на страницу книги с дополнительной информацией
* Ссылка на форму для добавления книги
    #### _Страница книги_
    * Содержащаяся информация: ID, название, автор, жанр, количество экземпляров, стоимость
    * Ссылка на форму для редактирования данных о книге
    * Кнопка для удаления книги
        ##### _Страница с формой редактирования данных о книге_
        - Строка ввода названия
        - Строка ввода жанров
        - Строка ввода авторов
        - Строка ввода издательства
        - Строка ввода года издания
        - Строка ввода количества страниц
        - Строка ввода вида обложки
        - Кнопка для сохранения изменений информации о книге в БД

### ***Cтраница с полным списком клиентов***
Клиент считается таковым после оформления им первого заказа.
* Список клиентов, добавленных в базу данных.
* Переход на страницу клиента с информацией о его заказах при нажатии на его ID
* Форма для поиска клиента по ФИО, перенаправляющая на страницу клиента с информацией о его заказах
    #### _Страница клиента_
    * Содержащаяся информация: ID, ФИО, список оформленных заказов с их статусом:  
         – Заказ ожидает получения с датой прибытия в город  
         – Заказ получен с датой доставки  
         – Заказ в пути с датой ожидаемой доставки  
         – Заказ отменен без даты  
      Дата доставки высчитывается, учитывая город клиента и дату оформления заказа
    * Ссылка на форму для редактирования данных о клиенте
    * Кнопка для удаления клиента
        ##### _Страница с формой редактирования данных о клиенте_
        * Строка ввода ФИО
        * Строка ввода адреса
        * Строка ввода эл.почты
        * Строка ввода номера телефона
        * Кнопка для сохранения изменений информации о клиенте в БД
        
### ***Страница с полным списком заказов***
* Список заказов, добавленных в базу данных.
* Переход на страницу заказа с дополнительной информации при нажатии на его ID
    #### _Страница заказа_
    * Содержащаяся информация: ID заказа, ID клиента, IDs книг, статус, стоимость
    * Форма для редактирования статуса заказа

### ***Страница с формой оформления заказа***
* Строка ввода информации о клиенте (при оформлении заказа на уже присутствующего в базе клиента, необходимо указать его ID)
* Строка ввода информации о книгах
* Кнопка для сохранения заказа в БД

![Bookstore  Диаграмма работы сайта](https://user-images.githubusercontent.com/59884652/228244677-08e2ce8e-445b-48a1-8bf1-1bca4a56191e.png)




## Сценарии использования

### ***Получение списка книг по жанрам, авторам и др. характеристикам***
* Перейти на главную страницу
* Перейти на страницу с полным списком книг
* Использовать параметры фильтрации формы для поиска при необходимости

### ***Оформление заказа***
* Перейти на главную страницу
* Перейти по страницу с формой оформления заказа
* Заполнить необходимые поля
* Нажать кнопку "Сохранить"

### ***Проверка статуса заказа***
* Перейти на главную страницу
* Перейти на страницу с полным списком заказов всех клиентов
* Нажать на ID интересующего заказа, чтобы перейти на страницу заказа с необходимой информацией

### ***Изменение статуса заказа***
* Перейти на главную страницу
* Перейти на страницу с полным списком заказов всех клиентов
* Нажать на ID интересующего заказа, чтобы перейти на страницу заказа
* Внести необходимые изменения в форму
* Нажать кнопку "Сохранить"

### ***Добавление клиента***
* Перейти на главную страницу
* Перейти на страницу с полным списком клиентов
* Перейти на страницу с формой регистрации клиента
* Заполнить необходимые поля
* Нажать кнопку "Сохранить"

### ***Удаление клиента***
* Перейти на главную страницу
* Перейти на страницу с полным списком клиентов
* Найти клиента в списке или используя форму для поиска
* Нажать на ID клиента, чтобы перейти на страницу клиента
* Нажать кнопку "Удалить"

### ***Чтение данных о клиенте***
* Перейти на главную страницу
* Перейти на страницу с полным списком клиентов
* Найти клиента в списке или используя форму для поиска
* Нажать на ID клиента, чтобы перейти на страницу клиента с необходимой информацией

### ***Редактирование данных о клиенте***
* Перейти на главную страницу
* Перейти на страницу с полным списком клиентов
* Найти клиента в списке или используя форму для поиска
* Нажать на ID клиента, чтобы перейти на страницу клиента
* Перейти на страницу с формой редактирования данных о клиенте
* Внести необходимые изменения
* Нажать кнопку "Сохранить"

### ***Добавление книги***
* Перейти на главную страницу
* Перейти на страницу с полным списком книг
* Перейти на страницу с формой добавления книги
* Заполнить необходимые поля
* Нажать кнопку "Сохранить"

### ***Удаление книги***
* Перейти на главную страницу
* Перейти на страницу с полным списком книг
* Найти книги в списке, используя параметры фильтрации формы для поиска
* Нажать на ID книги, чтобы перейти на страницу книги
* Нажать кнопку "Удалить"

### ***Чтение данных о книге***
* Перейти на главную страницу
* Перейти на страницу с полным списком книг
* Найти книги в списке, используя параметры фильтрации формы для поиска
* Нажать на ID книги, чтобы перейти на страницу книги с необходимой информацией

### ***Редактирование данных о книге***
* Перейти на главную страницу
* Перейти на страницу с полным списком книг
* Найти книги в списке, используя параметры фильтрации формы для поиска
* Нажать на ID книги, чтобы перейти на страницу книги
* Перейти на страницу с формой редактирования данных о книге
* Внести необходимые изменения
* Нажать кнопку "Сохранить"
