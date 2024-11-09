package ee.taltech.iti0202.bookshelf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class BookTest {

    public static final int MONEY_200 = 200;
    public static final int MONEY_300 = 300;
    public static final int MONEY_100 = 100;

    public static final int PRICE_100 = 100;
    public static final int PRICE_200 = 200;
    public static final int PRICE_300 = 300;
    public static final int YEAR_OF_PUBLISHING_1926 = 1926;
    public static final int YEAR_OF_PUBLISHING_1976 = 1976;
    public static final int YEAR_OF_PUBLISHING_2023 = 2023;
    public static final int YEAR_OF_PUBLISHING_1997 = 1997;
    public static final int PRICE_1000 = 1000;
    public static final int YEAR_OF_PUBLISHING_1998 = 1998;
    public static final int YEAR_OF_PUBLISHING_2001 = 2001;
    private static List<Book> allBooks = new LinkedList<>();

    private Person mati = new Person("Mati", MONEY_200);
    private Person kati = new Person("Kati", MONEY_300);
    private final Book tammsaare = new Book("Truth and Justice", "Tammsaare", YEAR_OF_PUBLISHING_1926,
            PRICE_100);
    private final Book meri = new Book("Silverwhite", "Meri", YEAR_OF_PUBLISHING_1976, PRICE_200);
    private final Book java = new Book("Java", "Luberg", YEAR_OF_PUBLISHING_2023, PRICE_300);

    private final Book harry1 = Book.of("Harry Potter: The Philosopher's Stone", "J. K. rowling",
            YEAR_OF_PUBLISHING_1997, PRICE_1000
    );
    private final Book harry2 = Book.of("Harry Potter: The Chamber of Secrets", "J. K. Rowling",
            YEAR_OF_PUBLISHING_1998, PRICE_1000);

    /**
     * Test basic removeBook function.
     */
    @org.junit.Test
    public void removeBook() {
        Person bonusPerson = new Person("Joonas Boonus", MONEY_100);
        Book b1 = Book.of("testOfLastAdded1", "testOfLastAddedAuthor1",
                YEAR_OF_PUBLISHING_2001, PRICE_100 + 1);
        Book b2 = Book.of("testOfLastAdded2", "testOfLastAddedAuthor2",
                YEAR_OF_PUBLISHING_2001 + 1, PRICE_100 + 2);
        Book b3 = Book.of("testOfLastAdded1", "testOfLastAddedAuthor1",
                YEAR_OF_PUBLISHING_2001, PRICE_100 + 1);
        Book b4 = Book.of("testOfLastAdded3", PRICE_100 + 3);

        b1.buy(bonusPerson);
        b2.buy(bonusPerson);
        assert b4 != null;
        b4.buy(bonusPerson);
        List<Book> personBooks = Book.getBooksByOwner(bonusPerson);
        Integer actual = personBooks.size();
        Integer expected = 3;
        assertEquals(expected, actual);

        Book.removeBook(b2);
        List<Book> personBooksAfterRemoval = Book.getBooksByOwner(bonusPerson);
        Integer actual2 = personBooksAfterRemoval.size();
        Integer expected2 = 2;
        assertEquals(expected2, actual2);
    }

    /**
     * Test basic removeBook functionality,
     * including removing book that was created without Book.of method.
     * and removing null book.
     */
    @org.junit.Test
    public void testRemoveBook2() {
        Book b1 = Book.of("testRemove1", "testRemove Author1",
                YEAR_OF_PUBLISHING_2001, PRICE_100 + 1);
        Book b2 = Book.of("testRemove2", "testRemove Author2",
                YEAR_OF_PUBLISHING_2001 + 1, PRICE_100 + 2);
        Book b3 = new Book("testRemove3", "testRemove Author3",
                YEAR_OF_PUBLISHING_2001 + 2, PRICE_100 + 3);
        Person p = new Person("Mati", MONEY_100 * 10);
        p.buyBook(b1);
        p.buyBook(b3);
        assertTrue(Book.removeBook(b1));
        assertFalse(Book.removeBook(b3));
        assertFalse(Book.removeBook(null));
        assertEqualsBookList(p.getBooks(), List.of(b3));
    }

    /**
     * Test if adding third book with Book of method after removing a book assigns the previous book author as
     * new author. Check if all other attributes like title, author, year of publishing and price are correct.
     */
    @org.junit.Test
    public void testOfLastAfterRemove() {
        Book b1 = Book.of("testOfLastAfterRemove", "testOfLastAfterRemoveAuthor",
                YEAR_OF_PUBLISHING_2001 - 1, PRICE_100);
        Book b2 = Book.of("testOfLastAfterRemove2", "testOfLastAfterRemoveAuthor2",
                YEAR_OF_PUBLISHING_2001 + 1, PRICE_100 + 2);
        Book.removeBook(b2);
        Book b3 = Book.of("testOfLastAfterRemove3", PRICE_100 + 1);
        assert b3 != null;
        assertEquals(b3.getTitle(), "testOfLastAfterRemove3");
        assertEquals(b3.getAuthor(), "testOfLastAfterRemoveAuthor2");
        assertEquals(b3.getYearOfPublishing(), YEAR_OF_PUBLISHING_2001 + 1);
        assertEquals(b3.getPrice(), PRICE_100 + 1);
    }

    /**
     * Test if authors have the correct books.
     * Uses assertEqualsBookList helper method.
     * @throws Exception none.
     */
    @org.junit.Test
    public void testBookGetBookByAuthor() throws Exception {
        Book b1 = Book.of("testBookGetBookByAuthor1", "testBookGetBookByAuthor Author1",
                YEAR_OF_PUBLISHING_2001, PRICE_100 + 1);
        Book b2 = Book.of("testBookGetBookByAuthor2", "testBookGetBookByAuthor Author2",
                YEAR_OF_PUBLISHING_2001 + 1, PRICE_100 + 1);
        Book b3 = Book.of("testBookGetBookByAuthor3", "testBookGetBookByAuthor Author3",
                YEAR_OF_PUBLISHING_2001 + 2, PRICE_100 + 1);
        Book b4 = Book.of("testBookGetBookByAuthor4", "testBookGetBookByAuthor Author1",
                YEAR_OF_PUBLISHING_2001 + 3, PRICE_100 + 1);
        Book b5 = Book.of("testBookGetBookByAuthor5", "testBookGetBookByAuthor Author3",
                YEAR_OF_PUBLISHING_2001 + 4, PRICE_100 + 1);
        Book b6 = Book.of("testBookGetBookByAuthor6", "testBookGetBookByAuthor Author1",
                YEAR_OF_PUBLISHING_2001 + 5, PRICE_100 + 1);

        assertEqualsBookList(Book.getBooksByAuthor("testBookGetBookByAuthor Author1"), Arrays.asList(b1, b4, b6));
        assertEqualsBookList(Book.getBooksByAuthor("testBookGetBookByAuthor Author2"), List.of(b2));
        assertEqualsBookList(Book.getBooksByAuthor("testBookGetBookByAuthor Author3"), Arrays.asList(b3, b5));
        assertEqualsBookList(Book.getBooksByAuthor("nonexistingauthor123"), new ArrayList<>());

    }

    /**
     * Helper method for testBookGetBookByAuthor.
     * @param actual list of books.
     * @param expected list of books.
     */
    private void assertEqualsBookList(List<Book> actual, List<Book> expected) {
        if (actual.size() != expected.size()) {
            fail("Expected list size: " + expected.size() + ", actual list size: " + actual.size());
        }
        for (Book book : actual) {
            if (!expected.contains(book)) {
                fail("Expected list to contain book: " + book + ", actual list: " + actual);
            }
        }
        for (Book book : expected) {
            if (!actual.contains(book)) {
                fail("Expected book: " + book + " not found in actual list: " + actual);
            }
        }
    }

    /**
     * Test if different instances with same attributes are equal.
     * Test if the same book (b3) can be added. It should not.
     * Test if book added with Book of short has correct author and attributes.
     */
    @org.junit.Test
    public void testOfLastAdded() {
        Book b1 = Book.of("testOfLastAdded1", "testOfLastAddedAuthor1",
                YEAR_OF_PUBLISHING_2001, PRICE_100 + 1);
        Book b2 = Book.of("testOfLastAdded2", "testOfLastAddedAuthor2",
                YEAR_OF_PUBLISHING_2001 + 1, PRICE_100 + 2);
        Book b3 = Book.of("testOfLastAdded1", "testOfLastAddedAuthor1",
                YEAR_OF_PUBLISHING_2001, PRICE_100 + 1);
        assertEquals(b1, b3);
        Book b4 = Book.of("testOfLastAdded3", PRICE_100 + 3);
        assert b4 != null;
        assertEquals(b4.getTitle(), "testOfLastAdded3");
        assertEquals(b4.getAuthor(), "testOfLastAddedAuthor2");
        assertEquals(b4.getYearOfPublishing(), YEAR_OF_PUBLISHING_2001 + 1);
        assertEquals(b4.getPrice(), PRICE_100 + 2);
    }

    /**
     * Test if getBooksByAuthor method uses correct letter case.
     * Test if different case author names are called out together.
     */
    @org.junit.Test
    public void testBookGetBooksByAuthorIgnoreCase() {
        Book harry1 = Book.of("Harry Potter: The Philosopher's Stone", "J. K. rowling",
                YEAR_OF_PUBLISHING_1997, PRICE_1000);
        Book harry2 = Book.of("Harry Potter: The Chamber of Secrets", "J. K. Rowling",
                YEAR_OF_PUBLISHING_1998, PRICE_1000);
        List<Book> rowlingBooks = Book.getBooksByAuthor("J. K. ROWLING");
        Integer actual = rowlingBooks.size(); // 2
        Integer expected = 2;
        assertEquals(expected, actual);
    }

    /**
     * Test if after removing book, book is removed from HashMap allBooks and from owner.
     */
    @org.junit.Test
    public void removeBookAffectsOtherPlaces() {
        Person extraPerson = new Person("extra", MONEY_100 * 10);
        extraPerson.buyBook(harry1);
        Book.removeBook(harry1);
        assertFalse(Book.getBooksByAuthor("j. k. rowling").contains(harry1));
        assertNotSame(harry1.getOwner(), extraPerson);
    }

    /**
     * Test if getAndIncrementNextId() function returns correct id, if there are 5 books.
     */
    @org.junit.Test
    public void getAndIncrementNextId() {
        assertEquals(0, tammsaare.getId());
        assertEquals(3, harry1.getId());
        assertEquals(5, Book.getAndIncrementNextId());
    }

    /**
     * Test if person can use buyBook function and getBooks function works.
     */
    @org.junit.Test
    public void getBookByOwner() {
        mati.buyBook(tammsaare);
        Object actual = mati.getBooks();
        assertEquals(List.of(tammsaare), actual);
    }

    /**
     * Test weather the behavior of the {@code getTitle()} method returns correct String.
     */
    @org.junit.Test
    public void getTitle() {
        Object actual = tammsaare.getTitle();
        String expected = "Truth and Justice";
        assertEquals(expected, actual);
    }

    /**
     * Test weather the behavior of the {@code getAuthor()} method returns correct String.
     */
    @org.junit.Test
    public void getAuthor() {
        Object actual = meri.getAuthor();
        String expected = "Meri";
        assertEquals(expected, actual);
    }

    /**
     * Test weather the behavior of the {@code getYearOfPublishing()} method returns correct integer.
     */
    @org.junit.Test
    public void getYearOfPublishing() {
        Object actual = meri.getYearOfPublishing();
        Integer expected = YEAR_OF_PUBLISHING_1976;
        assertEquals(expected, actual);
    }

    /**
     * Test weather the {@code setOwner()} method sets the correct owner.
     */
    @org.junit.Test
    public void setOwner() {
        assertNull(meri.getOwner());
        meri.setOwner(kati);
        Person actual = meri.getOwner();
        assertEquals(kati, actual);
    }

    /**
     * Test weather the {@code getOwner()} method returns the correct owner of a book after it is set.
     */
    @org.junit.Test
    public void getOwner() {
        meri.setOwner(kati);
        Object actual = meri.getOwner();
        Object expected = kati;
        assertEquals(expected, actual);
    }

    /**
     * Test weather the {@code getPrice()} method returns correct book price.
     */
    @org.junit.Test
    public void getPrice() {
        Integer actual = meri.getPrice();
        Integer expected = PRICE_200;
        assertEquals(expected, actual);
    }

    /**
     * Test weather the {@code getIdFirst()} method returns correct book id for first book.
     */
    @org.junit.Test
    public void getIdFirst() {
        Integer actual = tammsaare.getId();
        Integer expected = 0;
        assertEquals(expected, actual);
    }

    /**
     * Test weather id of second book is correct if there are two books.
     */
    @org.junit.Test
    public void getIdTwoBooks() {
        Integer actual = meri.getId();
        Integer expected = 1;
        assertEquals(expected, actual);
    }

    /**
     * Test weather owner can not buy a book that he/she already owns.
     */
    @org.junit.Test
    public void buyBuyerCurrentOwner() {
        meri.setOwner(kati);
        Boolean actual = meri.buy(kati);
        Boolean expected = false;
        assertEquals(expected, actual);
    }

    /**
     * Test that a new owner can not buy a book if he/she does not have enough money.
     */
    @org.junit.Test
    public void buyNewOwnerNotEnoughMoney() {
        meri.setOwner(kati);
        tammsaare.setOwner(kati);
        meri.buy(mati);
        Boolean actual = tammsaare.buy(mati);
        Boolean expected = false;
        assertEquals(expected, actual);
    }

    /**
     * Test weather old owner gets money after another person buys the book.
     */
    @org.junit.Test
    public void buyOldOwnerGetsMoney() {
        meri.setOwner(kati);
        meri.buy(mati);
        Integer actual = kati.getMoney();
        Integer expected = MONEY_300 + MONEY_200;
        assertEquals(expected, actual);
    }

    /**
     * Test weather old owner gets money if {@code buy(Person person)} method is used with null as person.
     * Also test weather new owner is null.
     */
    @org.junit.Test
    public void buyBuyerNull() {
        meri.setOwner(kati);
        meri.buy(null);
        Integer actual = kati.getMoney();
        Integer expected = MONEY_300 + MONEY_200;
        assertEquals(expected, actual);

        Object actual2 = meri.getOwner();
        assertNull(actual2);
    }

    /**
     * Test weather {@code buyBook()} method works with null.
     */
    @org.junit.Test
    public void buyBookNull2() {
        kati.buyBook(null);
        kati.sellBook(meri);
        Integer actual = kati.getMoney();
        Integer expected = MONEY_300;
        assertEquals(expected, actual);
    }

    /**
     * Test weather sellBook() function works if the owner is correct.
     */
    @org.junit.Test
    public void bookSellRightOwnerBefore() {
        meri.setOwner(kati);
        Boolean actual = kati.sellBook(meri);
        Boolean expected = true;
        assertEquals(expected, actual);
    }

    /**
     * Test that sellBook() function fails if book owner is incorrect.
     */
    @org.junit.Test
    public void bookSellWrongOwnerBefore() {
        meri.setOwner(kati);
        Boolean actual = mati.sellBook(meri);
        Boolean expected = false;
        assertEquals(expected, actual);
    }

    /**
     * Test that after using sellBook() the book has a correct owner.
     */
    @org.junit.Test
    public void bookSellCorrectOwnerAfter() {
        meri.setOwner(kati);
        kati.sellBook(meri);
        Object actual = meri.getOwner();
        assertNull(actual);
    }

    /**
     * Test if after using successfully using sellBook() function the previous owner has correct money.
     */
    @org.junit.Test
    public void bookSellSuccessCorrectMoney() {
        tammsaare.setOwner(mati);
        mati.sellBook(tammsaare);
        Integer actual = mati.getMoney();
        Integer expected = MONEY_300;
        assertEquals(expected, actual);
    }

    /**
     * Test weather the previous owner has correct money if sellBook() function fails.
     */
    @org.junit.Test
    public void bookSellFailureCorrectMoney() {
        meri.setOwner(kati);
        mati.sellBook(meri);
        Integer actual = mati.getMoney();
        Integer expected = MONEY_200;
        assertEquals(expected, actual);
    }

    /**
     * Test if trying to use sellBook() function with null fails.
     */
    @org.junit.Test
    public void bookSellNull() {
        Boolean actual = kati.sellBook(null);
        Boolean expected = false;
        assertEquals(expected, actual);
    }

    /**
     * Test if buyBook() function with null input fails.
     */
    @org.junit.Test
    public void bookBuyNull() {
        Boolean actual = kati.buyBook(null);
        Boolean expected = false;
        assertEquals(expected, actual);
    }

    /**
     * Test if person can buy another book if they do not have enough money left after first purchase.
     */
    @org.junit.Test
    public void testBookBuyBuyerHasNoMoney() {
        mati.buyBook(meri);
        Boolean actual = mati.buyBook(tammsaare);
        Boolean expected = false;
        assertEquals(expected, actual);
    }

    /**
     * Test if book has correct owner and getName() returns correct String after person has used buyBook() function.
     */
    @org.junit.Test
    public void testBookBuyCorrectOwnerAfter() {
        mati.buyBook(meri);
        String actual = meri.getOwner().getName();
        String expected = "Mati";
        assertEquals(expected, actual);
    }

    /**
     * Test that third person can not use boyBook() function to buy a book already owned.
     */
    @org.junit.Test
    public void testBookBuyAlreadyOwned() {
        meri.setOwner(kati);
        mati.buyBook(meri);
        String actual = meri.getOwner().getName();
        String expected = "Kati";
        assertEquals(expected, actual);
    }

    /**
     * Test if person has correct money after using buyBook() function.
     */
    @org.junit.Test
    public void testBookBuyCorrectMoneyAfter() {
        mati.buyBook(meri);
        Integer actual = mati.getMoney();
        Integer expected = 0;
        assertEquals(expected, actual);
    }
}
