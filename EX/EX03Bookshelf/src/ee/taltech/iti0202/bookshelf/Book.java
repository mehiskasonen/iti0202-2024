package ee.taltech.iti0202.bookshelf;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Book {

    private final String title;
    private final String author;
    private final int year;
    private final int price;
    private Person person;
    private static int nextId = 0;
    private final int id;
    private static List<Book> books = new LinkedList<>();
    private static Map<String, List<Book>> allBooks = new LinkedHashMap<>();
    private static Deque<Book> removedBook = new ArrayDeque<>();

    /**
     * Static method that returns id of next book and then increases ID value.
     * @return next id value.
     */
    public static int getAndIncrementNextId() {
        int current = nextId;
        nextId = nextId + 1;
        return current;
    }

    /**
     * Book constructor with attributes.
     * @param title of Book.
     * @param author of Book.
     * @param yearOfPublishing of Book.
     * @param price of Book.
     */
    public Book(String title, String author, int yearOfPublishing, int price) {
        this.title = title;
        this.author = author;
        this.year = yearOfPublishing;
        this.price = price;
        this.id = getAndIncrementNextId();
    }

    /**
     * Check if list of books contains given author as a key.
     * If it does return all books from that key.
     * @param author of book.
     * @return list of book objects or null.
     */
    public static List<Book> containsBook(String author) {
        if (allBooks.containsKey(author.toLowerCase())) {
            return allBooks.get(author.toLowerCase());
        }
        return null;
    }

    /**
     * Create new book instance, if there is no book with given name, author and year.
     * Method has to know what books have already been created. If there is no such instance, returns
     * new book instance.
     * Take into account only books created with "of" method.
     * @param title of book to check or create.
     * @param author of book to check or create.
     * @param yearOfPublishing of book to check or create.
     * @param price of book to check or create.
     * @return new instance of Book or already existing Book.
     */
    public static Book of(String title, String author, int yearOfPublishing, int price) {
        String key = author.toLowerCase();
        if (allBooks.containsKey(key)) {
            List<Book> books = allBooks.get(key);
            for (Book book : books) {
                if (book.title.equals(title) && book.author.equalsIgnoreCase(author) && book.year == yearOfPublishing) {
                    return book;
                }
            }
            Book neoBook = new Book(title, author, yearOfPublishing, price);
            books.add(neoBook);
            return neoBook;
        } else {
            List<Book> books = new ArrayList<>();
            Book neoBook = new Book(title, author, yearOfPublishing, price);
            books.add(neoBook);
            allBooks.put(key, books);
            return neoBook;
        }
    }

    /**
     * Remove book from all book lists.
     * If the book has an owner, money is returned.
     * If given book is null or does not exist, return false, else returns true.
     * @param book object.
     * @return boolean.
     */
    public static boolean removeBook(Book book) {
        if (book == null || containsBook(book.getAuthor().toLowerCase()) == null) {
            return false;
        }
        List<Book> tot = containsBook(book.getAuthor().toLowerCase());
        if (tot == null) {
            return false;
        }
        if (tot.size() == 1) {
            Book removed = tot.get(0);
            removedBook.add(removed);
        }

        if (book.getOwner() != null) {
            book.getOwner().sellBook(book);
        }
        allBooks.get(book.getAuthor().toLowerCase()).remove(book);
        return true;
    }

    /**
     * Method to make adding books easier. Author and year are taken from previously entered book.
     * If this method is called out first, meaning that previous book has not been entered, it returns null;
     * @param title of book.
     * @param price of book.
     * @return null or Book object.
     */
    public static Book of(String title, int price) {
        if (allBooks.isEmpty()) {
            return null;
        }

        String lastAuthorName = allBooks.keySet().toArray(new String[0])[allBooks.size() - 1];
        List<Book> lastAuthorBooks = allBooks.get(lastAuthorName);
        if (lastAuthorBooks.isEmpty()) {
            String mockString = removedBook.getLast().author;
            int mockYear = removedBook.getLast().year;
            Book toAdd = new Book(title, mockString, mockYear, price);
            lastAuthorBooks.add(toAdd);
        } else {
            for (int i = 0; i < lastAuthorBooks.size(); i++) {
                Book book = lastAuthorBooks.get(i);
                if (book.title.equals(title) && book.year == lastAuthorBooks.get(i).year) {
                    return book;
                }
            }
        }
        Book lastAuthorBook = lastAuthorBooks.get(lastAuthorBooks.size() - 1);
        Book neoBook = new Book(title, lastAuthorBook.author, lastAuthorBook.year, price);
        allBooks.get(lastAuthorName).add(neoBook);
        return neoBook;
    }

    /**
     * Return all books belonging to given owner.
     * @param owner object.
     * @return list.
     */
    public static List<Book> getBooksByOwner(Person owner) {
        return owner.getBooks().stream()
                .filter(book -> book.getOwner() == owner)
                .toList();
    }

    /**
     * Return all books which author matches input author.
     * Search is not case sensitive. If no books are found, returns an empty list.
     * @param author object.
     * @return list of books.
     */
    public static List<Book> getBooksByAuthor(String author) {
        List<Book> tot = containsBook(author);
        if (tot == null) {
            return new ArrayList<>();
        }
        return tot.stream()
                .filter(book -> Objects.equals(book.getAuthor().toLowerCase(), author.toLowerCase()))
                .toList();
    }

    /**
     * Return title of Book.
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Return author of Book.
     * @return author.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Return year of publishing.
     * @return year.
     */
    public int getYearOfPublishing() {
        return year;
    }

    /**
     * Setter method for setting new owner to book.
     * @param person object.
     */
    public void setOwner(Person person) {
        this.person = person;
    }

    /**
     * Return owners' Person object.
     * @return person.
     */
    public Person getOwner() {
        return person;
    }

    /**
     * Return price of Book.
     * @return price.
     */
    public int getPrice() {
        return price;
    }

    /**
     * Return unique ID of Book. First ID is 0, next is 1, and so on.
     * @return id.
     */
    public int getId() {
        return id;
    }

    /**
     * A book is bought by a given person.
     * Return true if transaction is successful, else return false.
     * The book can not be bought by its current owner.
     * The book can not be bought if the new owner does not have enought money.
     * The old owner gets money according to the price of the book.
     * This method has to use Person class methods sellBook and buyBook: if buyer is null, the seller
     * will get his money back.
     * @param buyer of book.
     * @return true or false.
     */
    public boolean buy(Person buyer) {
        if (buyer == null && person != null) {
            person.sellBook(this);
            return true;
        }
        if (Objects.equals(person, buyer)) {
            return false;
        }
        if (buyer.getMoney() < this.getPrice()) {
            return false;
        }
        if (this.getOwner() == null) {
            buyer.buyBook(this);
            return true;
        }
        person.sellBook(this);
        buyer.buyBook(this);
        return true;
    }


}
