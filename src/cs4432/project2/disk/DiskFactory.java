package cs4432.project2.disk;

import java.util.ArrayList;
import java.util.List;

public class DiskFactory {
    public static final String DB_PATH = System.getProperty("user.dir");
    public static final String DATASET_DIR = "Project2Dataset";
    public static final List<String> EXCLUDED_FILES = new ArrayList<>() {{
        // Exclude directories from being considered tables
        add("out");
        add(".idea");
        add("src");
    }};
    public static final int RECORDS_PER_BLOCK = 100;
}
