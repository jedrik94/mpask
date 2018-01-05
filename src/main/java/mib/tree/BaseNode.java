package mib.tree;

public abstract class BaseNode {
    protected String nodeName = "";

    public BaseNode(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeName() {
        return nodeName;
    }

    @Override
    public String toString() {
        return "Node name: " + nodeName;
    }
}
