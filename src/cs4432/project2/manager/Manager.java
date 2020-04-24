package cs4432.project2.manager;

import cs4432.project2.index.ArrayBasedIndex;
import cs4432.project2.index.HashBasedIndex;

public class Manager {
    private ArrayBasedIndex arrayBasedIndex;
    private HashBasedIndex hashBasedIndex;
    private boolean created;

    public void runCreateIndex() {
        this.arrayBasedIndex = new ArrayBasedIndex();
        this.hashBasedIndex = new HashBasedIndex();
        this.created = true;

        // flush() ??
    }


}
