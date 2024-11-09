package ee.taltech.iti0202.builder;

import ee.taltech.iti0202.person.Person;

/**
 * The PersonBuilder class is responsible for constructing
 * instances of the Person class with various attributes.
 * It provides a fluent interface for setting attributes
 * and constructing Person objects.
 */
public class PersonBuilder {
    private final String idCode;
    private String name;
    private Integer age;
    private Boolean isMale;

    /**
     * Constructs a new PersonBuilder with the given ID code.
     *
     * @param idCode The ID code for the person.
     */
    public PersonBuilder(String idCode) {
        this.idCode = idCode;
    }

    /**
     * Sets the name of the person.
     *
     * @param name The name of the person.
     * @return This PersonBuilder instance.
     */
    public PersonBuilder withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the age of the person.
     *
     * @param age The age of the person.
     * @return This PersonBuilder instance.
     * @throws IllegalArgumentException if the age is negative.
     */
    public PersonBuilder withAge(Integer age) {
        if (age <= 0) {
            throw new IllegalArgumentException();
        }
        this.age = age;
        return this;
    }

    /**
     * Sets the gender of the person.
     *
     * @param isMale A boolean indicating whether the person is male.
     * @return This PersonBuilder instance.
     */
    public PersonBuilder isMale(Boolean isMale) {
        this.isMale = isMale;
        return this;
    }

    /**
     * Constructs and returns a new Person object with the
     * attributes set in this builder.
     *
     * @return A new Person object.
     */
    public Person build() {
        return new Person(idCode, name, age, isMale);
    }
}
