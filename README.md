# Messages, Email and Mailboxes

## Problem Description
An email message can be represented as a datatype that encapsulates information about the email:

* message id: a **unique** identifier for the message
* sender: text that represents the sender of the email
* receiver: text that represents receiver of the email
* timestamp: one positive integer that indicates the time at which the email was sent and received; used to sequence/order emails.
* subject: text representing the email's subject
* body: text that forms the email body
* response-to id: the message id of the email that this email is written is response to, or a special message id (see NO_PARENT_ID in Email.java) if this email is not in response to any other email.

You have been provided with the implementation of the email datatype (in `email.Email`). `Email` is an immutable type. You should **not change** the provided implementation of `Email`. Also, two instances of `Email`, `m1` and `m2` are duplicates of each other if `m1.equals(m2)` is true.

You have to implement a `MailBox` datatype that is a mutable collection of email with the following operations:

* `addMsg`: to add a new message to the `MailBox`. A new message is added to the `MailBox` in an "unread" state until it is marked as "read".
* `delMsg`: to delete/remove a message from the `MailBox`.
* `getMsg`: to obtain a message with a given id from the `MailBox`.
* `getMsgCount`: to obtain the number of messages in the `MailBox`.
* `markRead`: to mark a message in the `MailBox` as "read".
* `markUnread`: to mark a message in the `MailBox` as "unread".
* `isRead`: to determine if a message has been read or not.
* `getUnreadMsgCount`: to obtain the number of unread messages in the `MailBox`.
* `getTimestampView`: to obtain a list of messages in the `MailBox` sorted using the timestamps, with newer messages appearing before older messages.
* `getMsgsInRange`: to obtain a list of messages within a given timestamp range (limits inclusive), sorted from earliest to latest
* `getThreadedView`: to obtain a list of messages in the `MailBox` in a **threaded** view that we will discuss in more detail next.
* `markThreadAsRead`: to mark all the messages in a thread as "read".
* `markThreadAsUnread`: to mark all the messages in a thread as "unread".

The skeleton code in `email.MailBox` provides specs for the methods that you need to implement.

### Email Threads

We define two emails, `m1` and `m2`, to be part of the same email thread if:

* `m2` is a direct response to `m1`, 
* or `m2` was written in response to any message that is part of the same thread as `m1`.

Clearly the definition above holds if we swap `m1` and `m2`. A message created fresh (not a response to another email) is considered to be a separate thread that may or may not grow. New messages (not in response to another message) have their `responseTo` field set to a special value (see the provided source code).

**Once a thread has been created/identified, deleting some messages from the thread should not invalidate the thread relationship between the other messages of the thread.**

**Examples of Email Threads**

* `m1` is a new email message and it gets an id of `id1`. `m1` is in an email thread that contains only itself.
* `m2` is an email message with id `id2` created in response to `m1`. `m2` will have a response-to id of `id1`. Now `m1` and `m2` are part of one email thread.
* An email `m3` created in response to `m2` will be part of the thread that contains `m1`, `m2` and `m3`.
* An email `m4`, different from `m2`, could be created in response to `m1` and then `m1`, `m2`, `m3` and `m4` are part of the same thread.

### Threaded Email View

A `MailBox` may consist of many email threads. The most recent email thread will be defined as the one that has the most recent email (i.e., the email with the highest timestamp). In the threaded email view, the messages are sequenced by threads with the most recent thread appearing first in the list. Within a thread, emails are also sorted by timestamps with the most recent email appearing first.

Suppose a `MailBox` has messages `m1`, `m11`, `m17` belonging to one thread and messages `n2`, `n3`, `n11` belonging to a different thread. Here we use the numbers to denote the timestamps of the messages. The threaded email view of this `MailBox` is the list `m17, m11, m1, n11, n3, n2`. (The timestamp view of this mailbox can be either `m17, m11, n11, n3, n2, m1` or `m17, n11, m11, n3, n2, m1`.)

### Adding Messages to `MailBox`

Messages need not be added to the `MailBox` in timestamp order. This models the (realistic) situation of messages being delivered out-of-order.

### Is `MailBox` Mutable?

`MailBox` is mutable, but should be mutated using the mutator operations that are indicated by the public interface (and not otherwise).

### Sorting Email

You will need to sort instances of `Email` to achieve the objectives of this PPT. To help you with this effort, an `email.TimestampComparator` class has been provided along with a test case in `email.ComparatorTest` to illustrate how such comparators can be used. 

You can add to `email.TimestampComparator` to complete your work. The provided test case in `email.ComparatorTest` should not be changed and should work even after you complete your work.

You may want to take note of the fact that `Email` implements the `TimestampedObject` interface.

## Tasks and Grading

### Task 1
* `addMsg`
* `delMsg`
* `getMsg`
* `getMsgCount`
* `markRead`
* `markUnread`
* `isRead`
* `getUnreadMsgCount`
### Task 2
* `getTimestampView`
* `getMsgsInRange`
### Task 3
* `markThreadAsRead`
* `markThreadAsUnread`
### Task 4
* `getThreadedView`

### Grading

| Work Accomplished | Grade |
| ----------------- | ----- |
| Task 1 does not pass all hidden tests | F |
| Task 1 passes all hidden tests | C |
| Tasks 1, 2 pass all hidden tests | B |
| Tasks 1, 2 and one of Tasks 3, 4 pass all hidden tests | A |
| Tasks 1 through 4 pass all hidden tests | A+ |

## Logistics

**Duration**

You have 75 minutes to complete this task.

**Submission Instructions**

+ Submit your work to the Github classroom repository that was created for you.
+ **Do not alter the directory/folder structure. You should retain the structure as in this repository.**
+ Do not wait until the last minute to push your work to Github. It is a good idea to push your work at intermediate points as well. _I would recommend that you get your Git and Github workflow set up at the start._

**What Should You Implement / Guidelines**

+ You should implement all the methods that are indicated with `TODO`.
+ Passing the provided tests is the minimum requirement. Use the tests to identify cases that need to be handled. Passing the provided tests is *not sufficient* to infer that your implementation is complete and that you will get full credit. Additional tests will be used to evaluate your work. The provided tests are to guide you.
+ You can implement additional helper methods if you need to but you should keep these methods `private` to the appropriate classes.
+ You do not need to implement new classes.
+ You can use additional standard Java libraries by importing them.
+ Do not throw new exceptions unless the specification for the method permits exceptions.


## Honour Code

By submitting your work to Github you agree to the following:

+ You did not consult with any other person for the purpose of completing this activity.
+ You did not aid any other person in the class in completing their activity.
+ If you consulted any external sources, such as resources available on the World Wide Web, in completing the examination then you have cited the source. (You do not need to cite class notes or Sun/Oracle Java documentation.)
+ You are not aware of any infractions of the honour code for this examination.

## Answers to FAQs

* **Can I consult Java documentation and other Internet-based sources?**

    Yes, you can. The point of this test is not to demonstrate mastery over syntax but that you can solve a problem in a    reasonable amount of time with reasonable resources.

    *If you find useful information online outside the official Java documentation and the course material, you must cite the source. You should do so by adding comments in your source code.*

    Naturally you are expected to adhere to all of the course and UBC policies on academic integrity.

* **Isn't 75 minutes too short to produce a working implementation?**

    The questions are straightforward, and these are not very different from what one might sometimes encounter on a job interview (for example). The difference is that you get less time during an interview (10-15 minutes) with no access to additional resources. So the time allotted is reasonable in that regard and I am expecting that everyone will be able to clear this bar. The goal is that it is possible to say, at a minimal level, what everyone who completes this course can achieve.

* **Why am I not guaranteed full credit if my implementation passes all the provided tests?**

    It is easy to develop an implementation that passes the provided tests and not much else. A good-faith implementation that passes all the provided tests is very likely to pass other tests too.
