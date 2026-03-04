import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PhoneBookTest {

    private PhoneBook phoneBook;

    @BeforeEach
    void setUp() {
        phoneBook = new PhoneBook();
    }

    @Test
    void testAdd() {
        assertEquals(1, phoneBook.add("Alice", "111"));
        assertEquals(2, phoneBook.add("Bob", "222"));
    }

    @Test
    void testFindByNumber() {
        phoneBook.add("Alice", "111");
        assertEquals("Alice", phoneBook.findByNumber("111"));
        assertNull(phoneBook.findByNumber("999"));
    }

    @Test
    void testFindByName() {
        phoneBook.add("Alice", "111");
        assertEquals("111", phoneBook.findByName("Alice"));
        assertNull(phoneBook.findByName("Bob"));
    }

    @Test
    void testPrintAllNames() {
        phoneBook.add("Charlie", "333");
        phoneBook.add("Alice", "111");
        phoneBook.add("Bob", "222");

        List<String> names = phoneBook.printAllNames();

        assertEquals(List.of("Alice", "Bob", "Charlie"), names);
    }
}