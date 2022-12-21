package email;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PublicT1 {

    private static Email m1, m2, m3, m4, m5;
    private static String user1 = "Student 1";
    private static String user2 = "Student 2";
    private static String user3 = "Mom 1";

    @BeforeAll
    public static void setup() {
        m1 = new Email(0, user1, user2,
            "Dinner?", "I am starving.");
        m2 = new Email(1, user2, user1,
            "Re: Dinner?", "Sure. Pizza?", m1.getId());
        m3 = new Email(2, user1, user2,
            "Re: Dinner?", "See you at 7", m2.getId());
        m4 = new Email(3, user1, user3,
            "Re: How are you?", "Done with exam. Heading to have dinner with a friend.");
        m5 = new Email(4, user3, user1,
            "Re: How are you?", "OK. Take care.", m4.getId());
    }

    @Test
    public void testAdd1() {
        MailBox mb = new MailBox();
        mb.addMsg(m1);
        mb.addMsg(m2);
        mb.addMsg(m3);
        mb.addMsg(m4);
        mb.addMsg(m5);
        assertEquals(5, mb.getMsgCount());
    }

    @Test
    public void testAddDuplicate() {
        MailBox mb = new MailBox();
        mb.addMsg(m1);
        assertFalse(mb.addMsg(m1));
    }

    @Test
    public void testAddNull() {
        MailBox mb = new MailBox();
        assertFalse(mb.addMsg(null));
    }

    @Test
    public void testGetMsg1() {
        MailBox mb = new MailBox();
        mb.addMsg(m1);
        mb.addMsg(m2);
        assertEquals(m1, mb.getMsg(m1.getId()));
    }

    @Test
    public void testGetMsg2() {
        MailBox mb = new MailBox();
        mb.addMsg(m1);
        assertTrue(mb.getMsg(m2.getId()) == null);
    }


    @Test
    public void testAdd2() {
        MailBox mb = new MailBox();
        mb.addMsg(m1);
        mb.addMsg(m2);
        mb.addMsg(m3);
        assertEquals(3, mb.getMsgCount());
        mb.markRead(m1.getId());
        mb.markRead(m2.getId());
        assertEquals(3, mb.getMsgCount());
        assertEquals(1, mb.getUnreadMsgCount());
    }

    @Test
    public void testDel1() {
        MailBox mb = new MailBox();
        mb.addMsg(m1);
        mb.addMsg(m2);
        mb.addMsg(m3);
        mb.delMsg(m1.getId());
        assertEquals(2, mb.getMsgCount());
        assertEquals(2, mb.getUnreadMsgCount());
    }

    @Test
    public void testDel2() {
        MailBox mb = new MailBox();
        mb.addMsg(m1);
        mb.addMsg(m2);
        mb.addMsg(m3);
        assertFalse(mb.delMsg(m4.getId()));
        mb.delMsg(m1.getId());
        assertFalse(mb.isRead(m2.getId()));
        assertFalse(mb.markRead(m1.getId()));
    }

    @Test
    public void testUnreadCount1() {
        MailBox mb = new MailBox();
        mb.addMsg(m1);
        mb.addMsg(m2);
        mb.addMsg(m3);
        assertEquals(3, mb.getUnreadMsgCount());
        mb.markRead(m1.getId());
        mb.markRead(m3.getId());
        assertEquals(1, mb.getUnreadMsgCount());
        mb.markUnread(m1.getId());
        assertEquals(2, mb.getUnreadMsgCount());
    }

    @Test
    public void testUnreadCount2() {
        MailBox mb = new MailBox();
        mb.addMsg(m1);
        mb.addMsg(m2);
        mb.addMsg(m3);
        assertEquals(3, mb.getUnreadMsgCount());
        mb.markRead(m1.getId());
        mb.markRead(m3.getId());
        assertEquals(1, mb.getUnreadMsgCount());
        mb.markUnread(m1.getId());
        assertEquals(2, mb.getUnreadMsgCount());
        assertEquals(3, mb.getMsgCount());
    }
}
