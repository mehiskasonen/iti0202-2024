package ee.taltech.iti0202.bookshelf;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Person {

    private final String name;
    private int money;
    private List<Book> ownersBooks = new LinkedList<>();

    /**
     * Person constructor.
     *
     * @param name  of person.
     * @param money person has.
     */
    public Person(String name, int money) {
        this.name = name;
        this.money = money;
    }

    /**
     * Return the amount of money a person has.
     *
     * @return money.
     */
    public int getMoney() {
        return money;
    }

    /**
     * Return name of Person.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Check if book is null or not. Return true if book is not null, false if book is null.
     *
     * @param book object.
     * @return boolean.
     */
    public boolean notNull(Book book) {
        return book != null;
    }

    /**
     * A person becomes the owner of a book. If transaction is successful, return true, else return false.
     * It is impossible to buy null book. If a person does not have enough money, transaction can no be done.
     * If the book already has an owner, the book can not be bought.After buying the book person's money diminishes
     * by the cost of the book.
     *
     * @param book object.
     * @return boolean.
     */
    public boolean buyBook(Book book) {
        if (notNull(book)) {
            if (getMoney() < money) {
                return false;
            }
            if (book.getOwner() != null) {
                return false;
            }
            if (money - book.getPrice() < 0) {
                return false;
            } else {
                money = money - book.getPrice();
                book.setOwner(this);
                this.ownersBooks.add(book);
                return true;
            }
        }
        return false;
    }


    /**
     * Method for person to sell a book. If transaction is successful, return true, else return false.
     *
     * @param book object.
     * @return boolean.
     */
    public boolean sellBook(Book book) {
        if (notNull(book)) {
            if (book.getOwner() != null) {
                if (Objects.equals(book.getOwner().getName(), getName())) {
                    money = money + book.getPrice();
                    ownersBooks.remove(book);
                    book.setOwner(null);
                    return true;
                }
            }
            if (!notNull(book)) {
                return false;
            }
        }
        return false;
    }

    /**
     * Return list of owners all books.
     * If there are no books, return an empty list.
     * At first, the person has no books. Collection increases by buying and decreases by selling.
     * @return list.
     */
    public List<Book> getBooks() {
        return ownersBooks;
    }
}
