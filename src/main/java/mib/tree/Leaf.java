package mib.tree;

import mib.tree.interfaces.HaveParent;
import mib.tree.interfaces.LeafData;

public class Leaf extends BaseNode implements HaveParent, LeafData<String> {
    private BaseNode parent = null;

    private String data;

    public Leaf(String nodeName, BaseNode parent) {
        super(nodeName);
        this.parent = parent;
    }

    public Leaf(String nodeName) {
        super(nodeName);
    }

    @Override
    public String getData() {
        return this.data;
    }

    @Override
    public void setData(String data) {
        this.data = data;
    }

    @Override
    public void setParent(BaseNode parent) {
        this.parent = parent;
    }

    @Override
    public boolean hasParent() {
        return parent != null;
    }

    @Override
    public String toString() {
        return "<Leaf> " +
                super.toString() + ", " +
                "Parent: [" + parent.getNodeName() + "], " +
                "Data: [" + getData() + "]\n";
    }
}
