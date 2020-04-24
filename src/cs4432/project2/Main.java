package cs4432.project2;

import cs4432.project2.benchmark.Benchmark;
import cs4432.project2.disk.DataFrame;
import cs4432.project2.disk.Disk;
import cs4432.project2.disk.DiskFactory;
import cs4432.project2.index.TableIndex;
import cs4432.project2.manager.Manager;
import cs4432.project2.query.Query;
import cs4432.project2.query.QueryFactory;
import cs4432.project2.utils.InputManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Read all files in dataset directory
        /*int totalBlocks = Disk.INSTANCE.getTables().get(DiskFactory.DATASET_DIR).size();
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
        }*/

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
                System.out.println("The Hash-Based and Array-Based indexes have been set up.");
            } else {
                try {
                    String where = query.substring(query.toLowerCase().indexOf("where") + 6);
                    if (queryType == Query.EQUALITY) {
                        try {
                            System.out.println("EQUALITY QUERY");
                            String equality = where.toLowerCase();
                            int randomV = Integer.parseInt(equality.toLowerCase().replace("randomv =", "").replace("randomv=", "").replaceAll("\\s+", ""));
                            // Instantiate a benchmark to measure performance
                            Benchmark benchmark = new Benchmark();

                            if (dbManager.indexesCreated()) {
                                // Array-based index
                                List<DataFrame> res = dbManager.getArrayBasedIndex().searchIndex(randomV);
                                long timeElapsedInMS = benchmark.getTimeElapsed();
                                System.out.println("----");
                                System.out.println("Index: Array-based");
                                System.out.println("Time in MS: " + timeElapsedInMS);
                                System.out.println("Blocks read: " + dbManager.getArrayBasedIndex().getLastBlocksRead());
                                System.out.println("-- Total: " + res.size() + " rows --");
                                for (DataFrame dataFrame : res) {
                                    System.out.println(dataFrame.getContent());
                                }
                                System.out.println("----");
                            } else {
                                // Table index (scanning all records)
                                TableIndex tableIndex = new TableIndex();
                                List<DataFrame> res = tableIndex.searchForRandomV(randomV);
                                long timeElapsedInMS = benchmark.getTimeElapsed();
                                System.out.println("----");
                                System.out.println("Index: full table scan");
                                System.out.println("Time in MS: " + timeElapsedInMS);
                                System.out.println("Blocks read: " + Disk.INSTANCE.getTables().get(DiskFactory.DATASET_DIR).size());
                                System.out.println("-- Total: " + res.size() + " rows --");
                                for (DataFrame dataFrame : res) {
                                    System.out.println(dataFrame.getContent());
                                }
                                System.out.println("----");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid Command. Please try again.");
                        }
                    } else if (queryType == Query.INEQUALITY) {
                        try {
                            System.out.println("INEQUALITY QUERY");
                            String inequality = where.toLowerCase();
                            System.out.println(inequality);
                            int randomV = Integer.parseInt(inequality.toLowerCase().replace("randomv !=", "").replace("randomv!=", "").replaceAll("\\s+", ""));
                            System.out.println(randomV);
                            // Instantiate a benchmark to measure performance
                            Benchmark benchmark = new Benchmark();

                            if (dbManager.indexesCreated()) {
                                // Array-based index
                                List<DataFrame> res = dbManager.getArrayBasedIndex().inequality(randomV);
                                long timeElapsedInMS = benchmark.getTimeElapsed();
                                System.out.println("----");
                                System.out.println("Index: Array-based");
                                System.out.println("Time in MS: " + timeElapsedInMS);
                                System.out.println("Blocks read: " + dbManager.getArrayBasedIndex().getLastBlocksRead());
                                System.out.println("-- Total: " + res.size() + " rows --");
                                for (DataFrame dataFrame : res) {
                                    System.out.println(dataFrame.getContent());
                                }
                                System.out.println("----");
                            } else {
                                // Table index (scanning all records)
                                TableIndex tableIndex = new TableIndex();
                                List<DataFrame> res = tableIndex.inequality(randomV);
                                long timeElapsedInMS = benchmark.getTimeElapsed();
                                System.out.println("----");
                                System.out.println("Index: full table scan");
                                System.out.println("Time in MS: " + timeElapsedInMS);
                                System.out.println("Blocks read: " + Disk.INSTANCE.getTables().get(DiskFactory.DATASET_DIR).size());
                                System.out.println("-- Total: " + res.size() + " rows --");
                                for (DataFrame dataFrame : res) {
                                    System.out.println(dataFrame.getContent());
                                }
                                System.out.println("----");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid Command. Please try again.");
                        }
                    } else if (queryType == Query.RANGE) {
                        System.out.println("RANGE QUERY");
                        String range = where.toLowerCase();
                        String[] rangeSplit = range.split(" and ");
                        System.out.println(Arrays.toString(range.split(" and ")));
                        // Instantiate a benchmark to measure performance
                        Benchmark benchmark = new Benchmark();

                        int start = Integer.parseInt(rangeSplit[0].replace("randomv >", "").replaceAll(" ", ""));
                        int end = Integer.parseInt(rangeSplit[1].replace("randomv <", "").replaceAll(" ", ""));
                        if (dbManager.indexesCreated()) {
                            // Hash-based index
                            List<DataFrame> res = dbManager.getHashBasedIndex().range(start, end);
                            long timeElapsedInMS = benchmark.getTimeElapsed();
                            System.out.println("----");
                            System.out.println("Index: Hash-based");
                            System.out.println("Time in MS: " + timeElapsedInMS);
                            System.out.println("Blocks read: " + dbManager.getHashBasedIndex().getLastBlocksRead());
                            System.out.println("-- Total: " + res.size() + " rows --");
                            for (DataFrame dataFrame : res) {
                                System.out.println(dataFrame.getContent());
                            }
                            System.out.println("----");
                        } else {
                            try {
                                // Table index (scanning all records)
                                TableIndex tableIndex = new TableIndex();
                                System.out.println("start: " + start);
                                System.out.println("end: " + end);
                                List<DataFrame> res = tableIndex.range(start, end);
                                long timeElapsedInMS = benchmark.getTimeElapsed();
                                System.out.println("----");
                                System.out.println("Index: full table scan");
                                System.out.println("Time in MS: " + timeElapsedInMS);
                                System.out.println("Blocks read: " + Disk.INSTANCE.getTables().get(DiskFactory.DATASET_DIR).size());
                                System.out.println("-- Total: " + res.size() + " rows --");
                                for (DataFrame dataFrame : res) {
                                    System.out.println(dataFrame.getContent());
                                }
                                System.out.println("----");
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid Command. Please try again.");
                            }
                        }
                    } else {
                        System.out.println("Invalid Command. Please try again.");
                    }
                } catch (StringIndexOutOfBoundsException e) {
                    System.out.println("Invalid command. Please try again.");
                }
            }
        }

    }
}
