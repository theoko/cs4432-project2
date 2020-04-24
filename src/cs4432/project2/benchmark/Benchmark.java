package cs4432.project2.benchmark;

import java.time.Duration;
import java.time.Instant;

public class Benchmark {
    Instant start;

    public Benchmark() {
      start = Instant.now();
    }

    public long getTimeElapsed() {
        Instant finish = Instant.now();

        long timeElapsed = Duration.between(start, finish).toMillis();  //in millis
        return timeElapsed;
    }
}
