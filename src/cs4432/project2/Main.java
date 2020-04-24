package cs4432.project2;

import cs4432.project2.disk.Disk;
import cs4432.project2.disk.DiskFactory;
import cs4432.project2.manager.Manager;
import cs4432.project2.query.Query;
import cs4432.project2.query.QueryFactory;
import cs4432.project2.utils.InputManager;

public class Main {

    public static void main(String[] args) {
        // Read all files in dataset directory
//        int totalBlocks = Disk.INSTANCE.getTables().get(DiskFactory.DATASET_DIR).size();
//        System.out.println();
//        for (int j=1; j<=totalBlocks; j++) {
//            System.out.println("J: " + j);
//            String block4K = Disk.INSTANCE.readBlock(DiskFactory.DATASET_DIR, j);
//            System.out.println(block4K);
//            System.out.println();
//            for(int i=1; i<=DiskFactory.RECORDS_PER_BLOCK; i++) {
//                int start = 40 * (i - 1);
//                int end = start + 40; // the first 40 bytes
//                assert block4K != null;
//                String recordContent = block4K.substring(start, end);
//                System.out.println(recordContent);
//                extractRandomV(recordContent);
//                System.out.println();
//            }
//        }

        // Instantiate a new database manager
        Manager dbManager = new Manager();
        while (true) {
            System.out.println("Please enter a query: ");
            String query = InputManager.readQuery();
            QueryFactory queryFactory = new QueryFactory();
            Query queryType = queryFactory.getQuery(query);
            if (queryType == Query.BUILD_INDEX) {
                // Build index
                dbManager.runCreateIndex();
            } else if (queryType == Query.EQUALITY) {

            } else if (queryType == Query.INEQUALITY) {

            } else if (queryType == Query.RANGE) {

            } else {
                System.out.println("Invalid Command. Please try again.");
            }
        }

    }
}
