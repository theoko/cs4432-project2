package cs4432.project2;

import cs4432.project2.disk.Disk;
import cs4432.project2.disk.DiskFactory;
import cs4432.project2.query.Query;
import cs4432.project2.utils.InputManager;

public class Main {

    private static int extractRandomV(String record) {
        return Integer.parseInt(record.substring(33, 37));
    }

    public static void main(String[] args) {
        // Read all files in dataset directory
        int totalBlocks = Disk.INSTANCE.getTables().get(DiskFactory.DATASET_DIR).size();
//        System.out.println(Disk.INSTANCE.getTables());
//        for (int i=1; i<=totalBlocks; i++) {
//            System.out.println(Disk.INSTANCE.readBlock(DiskFactory.DATASET_DIR, i));
//        }
        System.out.println();
        for (int j=1; j<=totalBlocks; j++) {
            System.out.println("J: " + j);
            String block4K = Disk.INSTANCE.readBlock(DiskFactory.DATASET_DIR, j);
            System.out.println(block4K);
            System.out.println();
            for(int i=1; i<=DiskFactory.RECORDS_PER_BLOCK; i++) {
                int start = 40 * (i - 1);
                int end = start + 40; // the first 40 bytes
                assert block4K != null;
                String recordContent = block4K.substring(start, end);
                System.out.println(recordContent);
                extractRandomV(recordContent);
                System.out.println();
            }
        }

        while (true) {
            System.out.println("Please enter a query: ");
            String query = InputManager.readQuery();
            Query parsedQuery = Query.parseQuery(query);
        }

    }
}
