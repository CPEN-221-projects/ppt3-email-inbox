package email;

import java.util.Comparator;

public class TimestampComparator {

    public static final Comparator<TimestampedObject> ASCENDING = new Comparator<TimestampedObject>() {
        public int compare(TimestampedObject ts1, TimestampedObject ts2) {
            return ts1.getTimestamp() - ts2.getTimestamp();
        }
    };

}
