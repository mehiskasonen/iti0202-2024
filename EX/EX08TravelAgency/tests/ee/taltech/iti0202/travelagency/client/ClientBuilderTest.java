package ee.taltech.iti0202.travelagency.client;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ClientBuilderTest {
    @org.junit.jupiter.api.Test
    void setId() throws IOException {
        UUID id = UUID.randomUUID();
        ClientBuilder clientBuilder = new ClientBuilder("John", "john@example.com", 30);
        clientBuilder.setId(id);
        assertEquals(id, clientBuilder.id);
    }

    @org.junit.jupiter.api.Test
    void testSetName() throws IOException {
        String name = "John";
        ClientBuilder clientBuilder = new ClientBuilder("Jane", "jane@example.com", 25);
        clientBuilder.setName(name);
        assertEquals(name, name);
    }

    @org.junit.jupiter.api.Test
    void testSetEmail() throws IOException {
        String email = "john@example.com";
        ClientBuilder clientBuilder = new ClientBuilder("John", "jane@example.com", 25);
        clientBuilder.setEmail(email);
        assertEquals(email, clientBuilder.email);
    }

    @org.junit.jupiter.api.Test
    void testSetAge() throws IOException {
        int age = 35;
        ClientBuilder clientBuilder = new ClientBuilder("John", "john@example.com", 25);
        clientBuilder.setAge(age);
        assertEquals(age, clientBuilder.age);
    }

    @org.junit.jupiter.api.Test
    void testSetPhoneNr() throws IOException {
        Long phoneNr = 123456789L;
        ClientBuilder clientBuilder = new ClientBuilder("John", "john@example.com", 25);
        clientBuilder.setPhoneNr(phoneNr);
        assertEquals(phoneNr, clientBuilder.phoneNr);
    }

    @org.junit.jupiter.api.Test
    void testSetAddress() throws IOException {
        String address = "123 Main St";
        ClientBuilder clientBuilder = new ClientBuilder("John", "john@example.com", 25);
        clientBuilder.setAddress(address);
        assertEquals(address, clientBuilder.address);
    }

    @org.junit.jupiter.api.Test
    void testCreateClient() throws IOException {
        String name = "Client6";
        String email = "client6@mail.com";
        int age = 25;
        ClientBuilder client = new ClientBuilder(name, email, age);
        assertNotNull(client);
        assertEquals(name, client.getName());
        assertEquals(email, client.getEmail());
        assertEquals(age, client.getAge());
    }
}
