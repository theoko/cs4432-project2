package cs4432.project2.disk;

public class DataFrame {
    int blockID;
    int recID;
    int randomV;
    String content;

    public DataFrame(int blockID, int recID, int randomV, String content) {
        this.blockID = blockID;
        this.recID = recID;
        this.randomV = randomV;
        this.content = content;
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

    public String getContent() {
        return content;
    }
}
