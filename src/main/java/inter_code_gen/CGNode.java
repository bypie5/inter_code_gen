package inter_code_gen;

public class CGNode {
    public String name;

    // For topological sort
    public boolean tempMark;
    public boolean permMark;

    public CGNode(String name) {
        this.name = name;

        tempMark = false;
        permMark = false;
    }

    public boolean equals(String lhs) {
        return this.name == lhs;
    }

    public void giveTempMark() {
        tempMark = true;
    }

    public void removeTempMark() {
        tempMark = false;
    }

    public void givePermMark() {
        permMark = true;
    }
}
