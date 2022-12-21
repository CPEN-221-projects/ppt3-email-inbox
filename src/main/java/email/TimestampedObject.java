package email;

import java.util.UUID;

/**
 * A timestamped object has an integer timestamp.
 */
public interface TimestampedObject {

    /**
     * Obtain the identifier for the message
     * @return the identifier for the message
     */
    UUID getId();

    /**
     * Obtain the timestamp of the object
     * @return the timestamp of the object
     */
    int getTimestamp();
}
