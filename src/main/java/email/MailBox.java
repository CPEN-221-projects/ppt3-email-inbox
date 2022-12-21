package email;

import java.sql.Time;
import java.util.*;

/**
 * A datatype that represents a mailbox or collection of email.
 */
public class MailBox {
    Map<Email, String> emailMap = new HashMap();

    /**
     * Add a new message to the mailbox
     *
     * @param msg the message to add
     * @return true if the message was added to the mailbox,
     * and false if it was not added to the mailbox (because a duplicate exists
     * or msg was null)
     */
    public boolean addMsg(Email msg) {
        if (emailMap.get(msg) != null || msg == null) {
            return false;
        }
        emailMap.put(msg, "unread");
        return true;
    }


    /**
     * Return the email with the provided id
     * @param msgID the id of the email to retrieve, is not null
     * @return the email with the provided id
     * and null if such an email does not exist in this mailbox
     */
    public Email getMsg(UUID msgID) {
        for (Email email: emailMap.keySet()) {
            if (email.getId() == msgID) {
                return email;
            }
        }
        return null;
    }

    /**
     * Delete a message from the mailbox
     *
     * @param msgId the id of the message to delete
     * @return true if the message existed in the mailbox and it was removed,
     * else return false
     */
    public boolean delMsg(UUID msgId) {
        Email email = getMsg(msgId);
        if (email == null) {
            return false;
        }
        emailMap.remove(email);
        return true;
    }

    /**
     * Obtain the number of messages in the mailbox
     *
     * @return the number of messages in the mailbox
     */
    public int getMsgCount() {
        return emailMap.size();
    }

    /**
     * Mark the message with the given id as read
     *
     * @param msgID the id of the message to mark as read, is not null
     * @return true if the message exists in the mailbox and false otherwise
     */
    public boolean markRead(UUID msgID) {
        Email email = getMsg(msgID);
        if (email == null) {
            return false;
        }
        emailMap.put(email, "read");
        return true;
    }

    /**
     * Mark the message with the given id as unread
     *
     * @param msgID the id of the message to mark as unread, is not null
     * @return true if the message exists in the mailbox and false otherwise
     */
    public boolean markUnread(UUID msgID) {
        Email email = getMsg(msgID);
        if (email == null) {
            return false;
        }
        emailMap.put(email, "unread");
        return true;
    }

    /**
     * Determine if the specified message has been read or not
     *
     * @param msgID the id of the message to check, is not null
     * @return true if the message has been read and false otherwise
     * @throws IllegalArgumentException if the message does not exist in the mailbox
     */
    public boolean isRead(UUID msgID) {
        Email email = getMsg(msgID);
        if (email == null) {
            throw new IllegalArgumentException();
        }
        if (emailMap.get(email) == "read") {
            return true;
        }
        return false;
    }

    /**
     * Obtain the number of unread messages in this mailbox
     * @return the number of unread messages in this mailbox
     */
    public int getUnreadMsgCount() {
        int unreadCount = 0;
        for (Email email: emailMap.keySet()) {
            if (emailMap.get(email).equals("unread")) {
                unreadCount++;
            }
        }
        return unreadCount;
    }

    /**
     * Obtain a list of messages in the mailbox, sorted by timestamp,
     * with most recent message first
     *
     * @return a list that represents a view of the mailbox with messages sorted
     * by timestamp, with most recent message first. If multiple messages have
     * the same timestamp, the ordering among those messages is arbitrary.
     */
    public List<Email> getTimestampView() {
        List<Email> timestampList = new ArrayList<>();
        for (Email email: emailMap.keySet()) {
            timestampList.add(email);
        }
        TimestampComparator playerComparator = new TimestampComparator();
        Collections.sort(timestampList, playerComparator.ASCENDING.reversed());
        return timestampList;
    }

    /**
     * Obtain all the messages with timestamps such that
     * startTime <= timestamp <= endTime,
     * sorted with the earliest message first and breaking ties arbitrarily
     *
     * @param startTime the start of the time range, >= 0
     * @param endTime   the end of the time range, >= startTime
     * @return all the messages with timestamps such that
     * startTime <= timestamp <= endTime,
     * sorted with the earliest message first and breaking ties arbitrarily
     */
    public List<Email> getMsgsInRange(int startTime, int endTime) {
        List<Email> emailList = getTimestampView();
        List<Email> emailRange = new ArrayList<>();
        for (int i = emailList.size() - 1; i >= 0; i--) {
            Email email = emailList.get(i);
            if (email.getTimestamp() >= startTime && email.getTimestamp() <= endTime) {
                emailRange.add(email);
            }
        }
        return emailRange;
    }


    /**
     * Mark all the messages in the same thread as the message
     * with the given id as read
     * @param msgID the id of a message whose entire thread is to be marked as read
     * @return true if a message with that id is in this mailbox
     * and false otherwise
     */
    public boolean markThreadAsRead(UUID msgID) {
        if (getMsg(msgID) == null) {
            return false;
        }
        Set<Email> emailThread = getThread(msgID);
        for (Email email: emailThread) {
            emailMap.put(email, "read");
        }
        return true;
    }

    private Set<Email> getThread(UUID msgID) {

        //get first email
        Email currentEmail = getMsg(msgID);
        while (currentEmail.getResponseTo() != Email.NO_PARENT_ID) {
            Email previousEmail = getMsg(currentEmail.getResponseTo());
            currentEmail = previousEmail;
        }
        Email firstEmail = currentEmail;

        Set<Email> threadSet = recurseEmails(firstEmail);

//        //go back in time
//        Email currentEmail = getMsg(msgID);
//        threadSet.add(currentEmail);
//        while (currentEmail.getResponseTo() != Email.NO_PARENT_ID) {
//            Email previousEmail = getMsg(currentEmail.getResponseTo());
//            threadSet.add(previousEmail);
//            currentEmail = previousEmail;
//        }
        return threadSet;
    }

    private Set<Email> recurseEmails(Email firstEmail) {
        Set<Email> threadSet = new HashSet<>();
        threadSet.add(firstEmail);
        for (Email email: emailMap.keySet()) {
            if (email.getResponseTo() == firstEmail.getId()) {
                threadSet.addAll(recurseEmails(email));
            }
        }
        return threadSet;
    }

    /**
     * Mark all the messages in the same thread as the message
     * with the given id as unread
     * @param msgID the id of a message whose entire thread is to be marked as unread
     * @return true if a message with that id is in this mailbox
     * and false otherwise
     */
    public boolean markThreadAsUnread(UUID msgID) {
        if (getMsg(msgID) == null) {
            return false;
        }
        Set<Email> emailThread = getThread(msgID);
        for (Email email: emailThread) {
            emailMap.put(email, "unread");
        }
        return true;
    }

    /**
     * Obtain a list of messages, organized by message threads.
     * <p>
     * The message thread view organizes messages by starting with the thread
     * that has the most recent activity (based on timestamps of messages in the
     * thread) first, and within a thread more recent messages appear first.
     * If multiple emails within a thread have the same timestamp then the
     * ordering among those messages is arbitrary. Similarly, if more than one
     * thread can be considered "most recent", those threads can be ordered
     * arbitrarily.
     * <p>
     * A thread is identified by using information in an email that indicates
     * whether an email was in response to another email. The group of emails
     * that can be traced back to a common parent email message form a thread.
     *
     * @return a list that represents a thread-based view of the mailbox.
     */
    public List<Email> getThreadedView() {
        List<List<Email>> threadList = new ArrayList<>();
        for (Email email: emailMap.keySet()) {
            boolean alreadyInThread = false;
            for (List<Email> thread: threadList) {
                if (thread.contains(email)) {
                    alreadyInThread = true;
                }
            }
            if (alreadyInThread == true) {
                continue;
            }
            List<Email> currentThread = new ArrayList<>(getThread(email.getId()));
            threadList.add(currentThread);
        }

        //sort, then get most recent email list
        List<Email> recentEmails = new ArrayList<>();
        TimestampComparator playerComparator = new TimestampComparator();
        for (List<Email> thread: threadList) {
            Collections.sort(thread, playerComparator.ASCENDING.reversed());
            recentEmails.add(thread.get(0));
        }

        Collections.sort(recentEmails, playerComparator.ASCENDING.reversed());

        List<Email> finalList = new ArrayList<>();
        for (Email email: recentEmails) {
            for (List<Email> thread: threadList) {
                if (thread.get(0) == email) {
                    for (Email thisEmail: thread) {
                        finalList.add(thisEmail);
                    }
                }
            }
        }
        return finalList;
    }


}
