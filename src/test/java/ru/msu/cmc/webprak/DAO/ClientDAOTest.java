package ru.msu.cmc.webprak.DAO;


import static org.junit.jupiter.api.Assertions.*;
import static ru.msu.cmc.webprak.models.Order.OrderStatus.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.test.context.TestPropertySource;

import ru.msu.cmc.webprak.models.*;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityManager;
import ru.msu.cmc.webprak.models.Order;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations = "classpath:application.properties")
public class ClientDAOTest {

    @Autowired
    private AuthorDAO authorDAO;
    @Autowired
    private GenreDAO genreDAO;
    @Autowired
    private BookDAO bookDAO;
    @Autowired
    private ClientDAO clientDAO;
    @Autowired
    private CityDAO cityDAO;
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private ClientCityRelDAO clientCityRelDAO;
    @Autowired
    private BookAuthorRelDAO bookAuthorRelDAO;
    @Autowired
    private BookGenreRelDAO bookGenreRelDAO;
    @Autowired
    private OrderingBookRelDAO orderingBookRelDAO;
    @Autowired
    private OrderHistoryDAO orderHistoryDAO;
    @Autowired
    private EntityManagerFactory entityManagerFactory;


    @Test
    void testUpdateClient() {
        Client client = clientDAO.getById(1L);
        assertNotNull(client);
        client.setClientName("Бебра Бебров");
        clientDAO.update(client);
        client = clientDAO.getById(1L);
        assertEquals("Бебра Бебров", client.getClientName());
    }

    @Test
    void testDeleteClient() {
        Client client = clientDAO.getById(3L);
        assertNotNull(client);
        clientDAO.delete(client);
        client = clientDAO.getById(3L);
        assertNull(client);
    }

    @Test
    void testDeleteById() {
        Client client = clientDAO.getById(2L);
        assertNotNull(client);
        clientDAO.deleteById(2L);
        client = clientDAO.getById(2L);
        assertNull(client);
    }

    @Test
    void testGetBuilderFilter() {
        ClientDAO.Filter filter = ClientDAO.getFilterBuilder().build();
        assertNotNull(filter);
    }

    @Test
    void testSearchClients() {
        ClientDAO.Filter.FilterBuilder filterBuilder1 = new ClientDAO.Filter.FilterBuilder();
        List<Client> clientList1 = clientDAO.searchClients(filterBuilder1.build());
        assertEquals(3, clientList1.size());

        ClientDAO.Filter.FilterBuilder filterBuilder2 = new ClientDAO.Filter.FilterBuilder();
        filterBuilder2.name("Петр");
        filterBuilder2.surname("Петров");
        filterBuilder2.id(2L);
        filterBuilder2.city("ДС2");
        filterBuilder2.phone("89999999992");
        filterBuilder2.email("petrov@mail.ru");
        List<Client> clientList2 = clientDAO.searchClients(filterBuilder2.build());
        assertEquals(1, clientList2.size());

        ClientDAO.Filter.FilterBuilder filterBuilder3 = new ClientDAO.Filter.FilterBuilder();
        filterBuilder3.name("Петр");
        List<Client> clientList3 = clientDAO.searchClients(filterBuilder3.build());
        assertEquals(1, clientList3.size());

        ClientDAO.Filter.FilterBuilder filterBuilder4 = new ClientDAO.Filter.FilterBuilder();
        filterBuilder4.surname("Иванов");
        List<Client> clientList4 = clientDAO.searchClients(filterBuilder4.build());
        assertEquals(1, clientList4.size());

        ClientDAO.Filter.FilterBuilder filterBuilder5 = new ClientDAO.Filter.FilterBuilder();
        filterBuilder5.city("WrongCity");
        List<Client> clientList5 = clientDAO.searchClients(filterBuilder5.build());
        assertEquals(0, clientList5.size());

        ClientDAO.Filter.FilterBuilder filterBuilder6 = new ClientDAO.Filter.FilterBuilder();
        filterBuilder6.id(2L);
        filterBuilder6.name("Иван");
        List<Client> clientList6 = clientDAO.searchClients(filterBuilder6.build());
        assertEquals(0, clientList6.size());

        ClientDAO.Filter.FilterBuilder filterBuilder7 = new ClientDAO.Filter.FilterBuilder();
        filterBuilder7.email("ivanov@mail.ru");
        List<Client> clientList7 = clientDAO.searchClients(filterBuilder7.build());
        assertEquals(1, clientList7.size());
    }

    @Test
    void getOrderHistory() {
        Client client = clientDAO.getById(1L);
        assertNotNull(client);
        List<OrderHistory> orderHistoryList = clientDAO.getOrderHistory(client.getId());
        assertEquals(1, orderHistoryList.size());
    }

    @Test
    void getClientCityAndStreet() {
        Client client = clientDAO.getById(1L);
        assertNotNull(client);
        List<ClientCityRel> clientCityRelList = clientDAO.getClientCityRel(client.getId());
        assertEquals(1, clientCityRelList.size());
        List<Pair<City, String>> cityAndStreet = clientDAO.getClientCityAndStreet(client.getId());
        assertEquals(1, cityAndStreet.size());
    }

    @BeforeEach
    void beforeEach() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        List<Client> clientList = new ArrayList<>();
        clientList.add(new Client(1L, "Иван Иванов", "ivanov@mail.ru", "89999999991"));
        clientList.add(new Client(2L, "Петр Петров", "petrov@mail.ru", "89999999992"));
        clientList.add(new Client(3L, "Павел Павлов", "pavlov@mail.ru", "89999999993"));
        clientDAO.saveCollection(clientList);

        List<City> cityList = new ArrayList<>();
        cityList.add(new City(1L, "ДС1", 1));
        cityList.add(new City(2L, "ДС2", 2));
        cityList.add(new City(3L, "ДС3", 3));
        cityDAO.saveCollection(cityList);

        List<ClientCityRel> clientCityRelList = new ArrayList<>();
        clientCityRelList.add(new ClientCityRel(1L, clientDAO.getById(1L), cityDAO.getById(1L), "Улица1"));
        clientCityRelList.add(new ClientCityRel(2L, clientDAO.getById(2L), cityDAO.getById(2L), "Улица2"));
        clientCityRelList.add(new ClientCityRel(3L, clientDAO.getById(3L), cityDAO.getById(3L), "Улица3"));
        clientCityRelDAO.saveCollection(clientCityRelList);

        List<Order> orderList = new ArrayList<>();
        orderList.add(new Order(1L, dateFormat.parse("2022-01-01"), dateFormat.parse("2022-01-02"), FINISHED, 1000));
        orderList.add(new Order(2L, dateFormat.parse("2022-02-02"), dateFormat.parse("2022-02-04"), IN_PROGRESS, 2000));
        orderList.add(new Order(3L, dateFormat.parse("2022-03-03"), dateFormat.parse("2022-03-07"), NEW, 3000));
        orderDAO.saveCollection(orderList);

        List<OrderHistory> orderHistoryList = new ArrayList<>();
        orderHistoryList.add(new OrderHistory(1L, clientDAO.getById(1L), orderDAO.getById(1L)));
        orderHistoryList.add(new OrderHistory(2L, clientDAO.getById(2L), orderDAO.getById(2L)));
        orderHistoryList.add(new OrderHistory(3L, clientDAO.getById(3L), orderDAO.getById(3L)));
        orderHistoryDAO.saveCollection(orderHistoryList);

        List<Author> authorList = new ArrayList<>();
        authorList.add(new Author(1L, "Автор1"));
        authorList.add(new Author(2L, "Автор2"));
        authorList.add(new Author(3L, "Автор3"));
        authorDAO.saveCollection(authorList);

        List<Genre> genreList = new ArrayList<>();
        genreList.add(new Genre(1L, "Жанр1"));
        genreList.add(new Genre(2L, "Жанр2"));
        genreList.add(new Genre(3L, "Жанр3"));
        genreDAO.saveCollection(genreList);

        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(1L, "Книга1", 100, "yes", 10));
        bookList.add(new Book(2L, "Книга2", 200, "no", 20));
        bookList.add(new Book(3L, "Книга3", 300, "no", 30));
        bookDAO.saveCollection(bookList);

        List<BookAuthorRel> bookAuthorRelList = new ArrayList<>();
        bookAuthorRelList.add(new BookAuthorRel(1L,  bookDAO.getById(1L), authorDAO.getById(1L)));
        bookAuthorRelList.add(new BookAuthorRel(2L,  bookDAO.getById(2L), authorDAO.getById(2L)));
        bookAuthorRelList.add(new BookAuthorRel(3L,  bookDAO.getById(3L), authorDAO.getById(3L)));
        bookAuthorRelDAO.saveCollection(bookAuthorRelList);

        List<BookGenreRel> bookGenreRelList = new ArrayList<>();
        bookGenreRelList.add(new BookGenreRel(1L,  bookDAO.getById(1L), genreDAO.getById(1L)));
        bookGenreRelList.add(new BookGenreRel(2L,  bookDAO.getById(2L), genreDAO.getById(2L)));
        bookGenreRelList.add(new BookGenreRel(3L,  bookDAO.getById(3L), genreDAO.getById(3L)));
        bookGenreRelDAO.saveCollection(bookGenreRelList);

        List<OrderingBookRel> orderingBookRelList = new ArrayList<>();
        orderingBookRelList.add(new OrderingBookRel(1L, orderDAO.getById(1L), bookDAO.getById(1L), 5, 500));
        orderingBookRelList.add(new OrderingBookRel(2L, orderDAO.getById(2L), bookDAO.getById(2L), 5, 1000));
        orderingBookRelList.add(new OrderingBookRel(3L, orderDAO.getById(3L), bookDAO.getById(3L), 5, 1500));
        orderingBookRelDAO.saveCollection(orderingBookRelList);
    }

    @BeforeAll
    @AfterEach
    void annihilation() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();

            entityManager.createNativeQuery("TRUNCATE client RESTART IDENTITY CASCADE;").executeUpdate();
            entityManager.createNativeQuery("ALTER SEQUENCE client_client_id_seq RESTART WITH 1;").executeUpdate();

            entityManager.createNativeQuery("TRUNCATE city RESTART IDENTITY CASCADE;").executeUpdate();
            entityManager.createNativeQuery("ALTER SEQUENCE city_city_id_seq RESTART WITH 1;").executeUpdate();

            entityManager.createNativeQuery("TRUNCATE ordering_book_rel RESTART IDENTITY CASCADE;").executeUpdate();
            entityManager.createNativeQuery("ALTER SEQUENCE ordering_book_rel_id_seq RESTART WITH 1;").executeUpdate();

            entityManager.createNativeQuery("TRUNCATE \"order\" RESTART IDENTITY CASCADE;").executeUpdate();
            entityManager.createNativeQuery("ALTER SEQUENCE order_order_id_seq RESTART WITH 1;").executeUpdate();

            entityManager.createNativeQuery("TRUNCATE client_city_rel RESTART IDENTITY CASCADE;").executeUpdate();
            entityManager.createNativeQuery("ALTER SEQUENCE client_city_rel_id_seq RESTART WITH 1;").executeUpdate();

            entityManager.createNativeQuery("TRUNCATE genre RESTART IDENTITY CASCADE;").executeUpdate();
            entityManager.createNativeQuery("ALTER SEQUENCE city_city_id_seq RESTART WITH 1;").executeUpdate();

            entityManager.createNativeQuery("TRUNCATE ordering_book_rel RESTART IDENTITY CASCADE;").executeUpdate();
            entityManager.createNativeQuery("ALTER SEQUENCE ordering_book_rel_id_seq RESTART WITH 1;").executeUpdate();

            entityManager.createNativeQuery("TRUNCATE book_author_rel RESTART IDENTITY CASCADE;").executeUpdate();
            entityManager.createNativeQuery("ALTER SEQUENCE book_author_rel_id_seq RESTART WITH 1;").executeUpdate();

            entityManager.createNativeQuery("TRUNCATE book_genre_rel RESTART IDENTITY CASCADE;").executeUpdate();
            entityManager.createNativeQuery("ALTER SEQUENCE book_genre_rel_id_seq RESTART WITH 1;").executeUpdate();

            entityManager.createNativeQuery("TRUNCATE book RESTART IDENTITY CASCADE;").executeUpdate();
            entityManager.createNativeQuery("ALTER SEQUENCE book_book_id_seq RESTART WITH 1;").executeUpdate();

            entityManager.getTransaction().commit();
        }
    }
}
