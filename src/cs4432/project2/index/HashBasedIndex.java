package cs4432.project2.index;

import cs4432.project2.disk.DataFrame;
import cs4432.project2.disk.Disk;
import cs4432.project2.disk.DiskFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cs4432.project2.utils.Helpers.extractRandomV;

public class HashBasedIndex {
    private Map<Integer, List<Pointer>> map;
    private int lastBlocksRead;

    public HashBasedIndex() {
        this.map = new HashMap<>();
        this.initialize();
    }

    private void initialize() {
        // Read all files in dataset directory
        int totalBlocks = Disk.INSTANCE.getTables().get(DiskFactory.DATASET_DIR).size();
        for (int j=1; j<=totalBlocks; j++) {
//            System.out.println("J: " + j);
            String block4K = Disk.INSTANCE.readBlock(DiskFactory.DATASET_DIR, j);
//            System.out.println(block4K);
//            System.out.println();
            for(int i=1; i<=DiskFactory.RECORDS_PER_BLOCK; i++) {
                int start = 40 * (i - 1);
                int end = start + 40; // the first 40 bytes
                assert block4K != null;
                String recordContent = block4K.substring(start, end);
//                System.out.println(recordContent);
//                System.out.println();
                // Extract randomV from record content
                int randomV = extractRandomV(recordContent);
                // Search index for randomV
                List<Pointer> pointers = this.map.get(randomV);
                if (pointers == null) {
                    pointers = new ArrayList<>();
                } else {
                    pointers.add(new Pointer(j, i));
                }
                this.map.put(randomV, pointers);
            }
        }
    }

    public int getLastBlocksRead() {
        return lastBlocksRead;
    }

    public List<DataFrame> range(int from, int to) {
        this.lastBlocksRead = 0;
        List<DataFrame> res = new ArrayList<>();
        Map<Integer, String> cache = new HashMap<>();
        for (int i=from+1; i<to; i++) {
            List<Pointer> pointers = this.map.get(i);
            if (pointers != null) {
                for (Pointer pointer : pointers) {
                    if (pointer != null) {
                        if (cache.get(pointer.getBlockID()) == null) {
                            String block4K = Disk.INSTANCE.readBlock(DiskFactory.DATASET_DIR, pointer.getBlockID());
                            int start = 40 * (pointer.getRecID() - 1);
                            int end = start + 40; // the first 40 bytes
                            assert block4K != null;
                            DataFrame dataFrame = new DataFrame(pointer.getBlockID(), pointer.getRecID(), extractRandomV(block4K), block4K.substring(start, end));
                            res.add(dataFrame);
                            cache.put(pointer.getBlockID(), block4K);
                            this.lastBlocksRead++;
                        } else {
                            String block4K = cache.get(pointer.getBlockID());
                            int start = 40 * (pointer.getRecID() - 1);
                            int end = start + 40; // the first 40 bytes
                            DataFrame dataFrame = new DataFrame(pointer.getBlockID(), pointer.getRecID(), extractRandomV(block4K), block4K.substring(start, end));
                            res.add(dataFrame);
                        }
                    }
                }
            }
        }
        return res;
    }
}
