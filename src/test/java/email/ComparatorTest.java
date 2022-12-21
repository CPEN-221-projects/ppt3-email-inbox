package email;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ComparatorTest {

    @Test
    public void comparatorTest1() {
        Email email1 = new Email(1, "user 1", "user 2", "test 1", "blah");
        Email email2 = new Email(2, "user 2", "user 1", "test 2", "blah");
        Email email3 = new Email(40, "user 2", "user 1", "test 2", "blah");
        List<Email> actual = Arrays.asList(email2, email3, email1);
        Collections.sort(actual, TimestampComparator.ASCENDING);
        List<Email> expect = Arrays.asList(email1, email2, email3);
        assertEquals(expect, actual);
    }
}
