package cs4432.project2.disk;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Disk {
    INSTANCE;

    /**
     * This strucure maps a table to a list of files
     */
    Map<String, List<String>> tables = new HashMap<>();

    /**
     * Is called by class loader so before any other method
     */
    Disk() {
        this.initialize();
    }

    /**
     * This method initializes our enum by calling discoverTables
     */
    private void initialize() {
        this.discoverTables();
    }

    private void discoverTables() {
        File tablePath = new File(DiskFactory.DB_PATH);
        File[] tables = tablePath.listFiles();
        for (int i=0; i<tables.length; i++) {
            if (tables[i].isDirectory()) {
                String name = tables[i].getName();
                if (!DiskFactory.EXCLUDED_FILES.contains(name)) {
                    this.discoverDatablocks(name);
                }
            }
        }
    }

    private void discoverDatablocks(String path) {
        File dataset = new File(DiskFactory.DB_PATH + File.separator + path);
        File[] datablocks = dataset.listFiles();
        List<String> datablocksList = new ArrayList<>();
        for (int i=0; i<datablocks.length; i++) {
            if (datablocks[i].isFile()) {
                String name = datablocks[i].getName();
                datablocksList.add(name);
            }
        }
        this.tables.put(path, datablocksList);
    }

    public Map<String, List<String>> getTables() {
        return tables;
    }

    public String readBlock(String tableName, int blockId) {
        String fileName = "F" + blockId + ".txt";
        List<String> dataBlocks = this.tables.get(tableName);
        if (dataBlocks.contains(fileName)) {
            File block = new File(tableName + File.separator + fileName);
            try (BufferedReader br = new BufferedReader(new FileReader(block))) {
                String line;
                while ((line = br.readLine()) != null) {
                    // The line represents one Frame
                    return line;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // In case we haven't returned anything
        return null;
    }

    @Override
    public String toString() {
        return "Disk{" +
                "tables=" + tables.toString() +
                '}';
    }
}
