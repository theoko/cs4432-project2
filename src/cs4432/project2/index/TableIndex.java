package cs4432.project2.index;

import cs4432.project2.disk.DataFrame;
import cs4432.project2.disk.Disk;
import cs4432.project2.disk.DiskFactory;

import java.util.ArrayList;
import java.util.List;

import static cs4432.project2.utils.Helpers.extractRandomV;

public class TableIndex {

    public List<DataFrame> searchForRandomV(int search) {
        List<DataFrame> res = new ArrayList<>();

        // Read all files in dataset directory
        int totalBlocks = Disk.INSTANCE.getTables().get(DiskFactory.DATASET_DIR).size();
        for (int j=1; j<=totalBlocks; j++) {
            String block4K = Disk.INSTANCE.readBlock(DiskFactory.DATASET_DIR, j);
            for (int i=1; i<=DiskFactory.RECORDS_PER_BLOCK; i++) {
                int start = 40 * (i - 1);
                int end = start + 40; // the first 40 bytes
                assert block4K != null;
                String recordContent = block4K.substring(start, end);
                // Extract randomV from record content
                int randomV = extractRandomV(recordContent);
                if (search == randomV) {
                    res.add(new DataFrame(j, i, randomV, recordContent));
                }
            }
        }

        return res;
    }

    public List<DataFrame> inequality(int search) {
        List<DataFrame> res = new ArrayList<>();

        // Read all files in dataset directory
        int totalBlocks = Disk.INSTANCE.getTables().get(DiskFactory.DATASET_DIR).size();
        for (int j=1; j<=totalBlocks; j++) {
            String block4K = Disk.INSTANCE.readBlock(DiskFactory.DATASET_DIR, j);
            for (int i=1; i<=DiskFactory.RECORDS_PER_BLOCK; i++) {
                int start = 40 * (i - 1);
                int end = start + 40; // the first 40 bytes
                assert block4K != null;
                String recordContent = block4K.substring(start, end);
                // Extract randomV from record content
                int randomV = extractRandomV(recordContent);
                if (search != randomV) {
                    res.add(new DataFrame(j, i, randomV, recordContent));
                }
            }
        }

        return res;
    }

    public List<DataFrame> range(int rangeFrom, int rangeTo) {
        List<DataFrame> res = new ArrayList<>();

        // Read all files in dataset directory
        int totalBlocks = Disk.INSTANCE.getTables().get(DiskFactory.DATASET_DIR).size();
        for (int j=1; j<=totalBlocks; j++) {
            String block4K = Disk.INSTANCE.readBlock(DiskFactory.DATASET_DIR, j);
            for (int i=1; i<=DiskFactory.RECORDS_PER_BLOCK; i++) {
                int start = 40 * (i - 1);
                int end = start + 40; // the first 40 bytes
                assert block4K != null;
                String recordContent = block4K.substring(start, end);
                // Extract randomV from record content
                int randomV = extractRandomV(recordContent);
                if (randomV > rangeFrom && randomV < rangeTo) {
                    res.add(new DataFrame(j, i, randomV, recordContent));
                }
            }
        }

        return res;
    }
}
