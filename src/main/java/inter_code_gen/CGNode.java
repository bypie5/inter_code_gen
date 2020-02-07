package inter_code_gen;

public class CGNode {
    public String name;

    public CGNode(String name) {
        this.name = name;
    }

    public boolean equals(String lhs) {
        return this.name == lhs;
    }
}
