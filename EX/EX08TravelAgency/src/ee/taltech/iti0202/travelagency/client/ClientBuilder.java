package ee.taltech.iti0202.travelagency.client;

import java.io.IOException;
import java.util.UUID;

public class ClientBuilder extends Client {
    UUID id;
    private static String name;
    String email;
    int age;
    Long phoneNr;
    String address;

    /**
     * ClientBuilder class constructor.
     * @param name of client to be built.
     * @param email of client to be built.
     * @param age of client to be built.
     * @throws IOException for logger file.
     */
    public ClientBuilder(String name, String email, int age) throws IOException {
        super(name, email, age);
    }

    /**
     * @param id of client for builder.
     * @return id of client.
     */
    public ClientBuilder setId(UUID id) {
        this.id = id;
        return this;
    }

    /**
     * @param name of client for builder.
     * @return name of client.
     */
    public ClientBuilder setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * @param email of client for builder.
     * @return email of client.
     */
    public ClientBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * @param age of client for builder.
     * @return age of client.
     */
    public ClientBuilder setAge(int age) {
        this.age = age;
        return this;
    }

    /**
     * @param phoneNr of client for builder.
     * @return phone nr of client.
     */
    public ClientBuilder setPhoneNr(Long phoneNr) {
        this.phoneNr = phoneNr;
        return this;
    }

    /**
     * @param address of client for builder.
     * @return address of client.
     */
    public ClientBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    /**
     * @return new client.
     * @throws IOException for logger file.
     */
    public Client createClient() throws IOException {
        return new Client(name, email, age);
    }
}
