package email;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class PublicT3 {

    private static Email    thread1_0, thread1_1, thread1_2, thread1_3, thread1_4,
            thread2_0, thread2_1, thread2_2;

    private static String user1 = "Student 1";
    private static String user2 = "Student 2";
    private static String user3 = "Mom 1";

    @BeforeAll
    public static void setup() {

        thread1_0 = new Email(1, user1, user2, "How was the PPT?", "OK?");
        UUID tracker1 = thread1_0.getId();

        thread2_0 = new Email(2, user3, user2, "Dinner", "Should I order from JamJar?");
        UUID tracker2 = thread2_0.getId();

        thread1_1 = new Email(3, user2, user1, "Re How was the PPT?", "I hate Sathish!", tracker1);
        tracker1 = thread1_1.getId();

        thread2_1 = new Email(4, user2, user3, "Re Dinner", "Yes, please!", tracker2);
        tracker2 = thread2_1.getId();

        thread1_2 = new Email(5, user1, user2, "Re Re How was the PPT?", "Oh well. Dinner?", tracker1);
        tracker1 = thread1_2.getId();

        thread1_3 = new Email(6, user2, user1, "Re Re Re How was the PPT?", "Can't. Meeting my mom for dinner.", tracker1);
        tracker1 = thread1_3.getId();

        thread1_4 = new Email(7, user2, user1, "Was: How was the PPT?", "Let me see if you can join us.", tracker1);

        thread2_2 = new Email(8, user2, user3, "Re Re Dinner", "Can a friend join us for dinner?", tracker2);

    }

    private void setupMailBox(MailBox inbox) {
        inbox.addMsg(thread2_2);
        inbox.addMsg(thread2_1);
        inbox.addMsg(thread1_2);
        inbox.addMsg(thread1_3);
        inbox.addMsg(thread2_1);
        inbox.addMsg(thread1_1);
        inbox.addMsg(thread2_0);
        inbox.addMsg(thread1_0);
        inbox.addMsg(thread1_4);
    }

    @Test
    public void testMarkThreadAsRead() {
        MailBox inbox = new MailBox();
        setupMailBox(inbox);

        inbox.markThreadAsRead(thread2_1.getId());
        assertEquals(5, inbox.getUnreadMsgCount());

        List<Email> thread2 = Arrays.asList(thread2_2, thread2_1, thread2_0);
        for (Email email: thread2) {
            assertTrue(inbox.isRead(email.getId()));
        }
    }

    @Test
    public void testMarkThreadAsUnread() {
        MailBox inbox = new MailBox();
        setupMailBox(inbox);

        List<Email> allMails = Arrays.asList(thread1_4, thread1_3, thread1_2, thread1_1, thread1_0, thread2_2, thread2_1, thread2_0);

        for (Email email: allMails) {
            inbox.markRead(email.getId());
        }

        inbox.markThreadAsUnread(thread1_3.getId());
        assertEquals(5, inbox.getUnreadMsgCount());
        List<Email> thread1 = Arrays.asList(thread1_4, thread1_3, thread1_2, thread1_1, thread1_0);
        for (Email email: thread1) {
            assertFalse(inbox.isRead(email.getId()));
        }
    }
}
