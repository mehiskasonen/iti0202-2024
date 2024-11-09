package ee.taltech.iti0202.transportation.person;

public class Person {

    private final String name;
    private final int age;

    /**
     * Person class constructor.
     * @param name of person.
     * @param age of person.
     */
    public Person(String name, int age) {

        this.name = name;
        this.age = age;
    }

    /**
     * @return age of person.
     */
    public int getAge() {
        return age;
    }

    /**
     * @return name of person.
     */
    public String getName() {
        return name;
    }

    /**
     * @return string representation of a person.
     */
    @Override
    public String toString() {
        return "[" + name + '\'' +
                ", age=" + age +
                ']';
    }
}
