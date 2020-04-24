package cs4432.project2.disk;

public class DataFrame {
    int blockID;
    int recID;
    int randomV;

    public DataFrame(int blockID, int recID, int randomV) {
        this.blockID = blockID;
        this.recID = recID;
        this.randomV = randomV;
    }

    public int getBlockID() {
        return blockID;
    }

    public int getRecID() {
        return recID;
    }

    public int getRandomV() {
        return randomV;
    }
}
