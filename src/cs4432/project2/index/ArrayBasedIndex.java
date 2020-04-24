package cs4432.project2.index;

import cs4432.project2.disk.DataFrame;
import cs4432.project2.disk.Disk;
import cs4432.project2.disk.DiskFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cs4432.project2.utils.Helpers.extractRandomV;

public class ArrayBasedIndex {
    private List<Pointer>[] fixedArrayOfLists;
    private int lastBlocksRead;

    public ArrayBasedIndex() {
        this.fixedArrayOfLists = new ArrayList[5000];
        this.lastBlocksRead = 0;
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
            for (int i = 1; i <= DiskFactory.RECORDS_PER_BLOCK; i++) {
                int start = 40 * (i - 1);
                int end = start + 40; // the first 40 bytes
                assert block4K != null;
                String recordContent = block4K.substring(start, end);
//                System.out.println(recordContent);
//                System.out.println();
                // Extract randomV from record content
                int randomV = extractRandomV(recordContent);
                // Search index for randomV
                if (this.fixedArrayOfLists[randomV - 1] == null) {
                    this.fixedArrayOfLists[randomV - 1] = new ArrayList<>();
                }
                this.fixedArrayOfLists[randomV - 1].add(new Pointer(j, i));
            }
        }
    }

    public int getLastBlocksRead() {
        return lastBlocksRead;
    }

    public List<DataFrame> searchIndex(int search) {
        this.lastBlocksRead = 0;
        if (this.fixedArrayOfLists[search - 1] == null) return new ArrayList<>();
        List<DataFrame> res = new ArrayList<>();
        for (Pointer pointer : this.fixedArrayOfLists[search - 1]) {
            String block4K = Disk.INSTANCE.readBlock(DiskFactory.DATASET_DIR, pointer.getBlockID());
            this.lastBlocksRead++;
            int start = 40 * (pointer.getRecID() - 1);
            int end = start + 40; // the first 40 bytes
            assert block4K != null;
            DataFrame dataFrame = new DataFrame(pointer.getBlockID(), pointer.getRecID(), extractRandomV(block4K), block4K.substring(start, end));
            res.add(dataFrame);
        }
        return res;
    }

    public List<DataFrame> inequality(int search) {
        this.lastBlocksRead = 0;
        Map<Integer, String> cache = new HashMap<>();
        List<DataFrame> res = new ArrayList<>();
        for (int i=0; i<this.fixedArrayOfLists.length; i++) {
            if (search - 1 != i) {
                List<Pointer> pointers = this.fixedArrayOfLists[i];
                if (pointers != null) {
                    for (Pointer pointer : pointers) {
                        // Add to last blocks read by checking if the block id has previously been visited
                        if (cache.get(pointer.getBlockID()) == null) {
                            String block4K = Disk.INSTANCE.readBlock(DiskFactory.DATASET_DIR, pointer.getBlockID());
                            cache.put(pointer.blockID, block4K);
                            this.lastBlocksRead++;
                            int start = 40 * (pointer.getRecID() - 1);
                            int end = start + 40; // the first 40 bytes
                            assert block4K != null;
                            DataFrame dataFrame = new DataFrame(pointer.getBlockID(), pointer.getRecID(), extractRandomV(block4K), block4K.substring(start, end));
                            res.add(dataFrame);
                        } else {
                            String block4K = cache.get(pointer.getBlockID());
                            int start = 40 * (pointer.getRecID() - 1);
                            int end = start + 40; // the first 40 bytes
                            assert block4K != null;
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
