package cs4432.project2.index;

public class Pointer {
    int blockID;
    int recID;

    public Pointer(int blockID, int recID) {
        this.blockID = blockID;
        this.recID = recID;
    }

    public int getBlockID() {
        return blockID;
    }

    public int getRecID() {
        return recID;
    }
}