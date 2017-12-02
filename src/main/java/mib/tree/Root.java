package mib.tree;

import mib.tree.interfaces.HaveChildren;
import mib.tree.interfaces.HaveUID;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Root extends BaseNode implements HaveChildren, HaveUID {
    private List<BaseNode> children = new ArrayList<>();
    private int UID;

    public Root(String nodeName, int UID) {
        super(nodeName);
        this.UID = UID;
    }

    @Override
    public void addChild(BaseNode child) {
        this.children.add(child);
    }

    @Override
    public List<BaseNode> getChildren() {
        return children;
    }

    @Override
    public List<BaseNode> removeChild() {
        //TODO:
        return null;
    }

    @Override
    public boolean hasAnyChild() {
        return !children.isEmpty();
    }

    @Override
    public int getUID() {
        return UID;
    }

    @Override
    public String toString() {
        return "<Root> " +
                super.toString() + ", " +
                "UID: " + UID + ", " +
                "Children: " + children.stream().map(BaseNode::getNodeName).collect(Collectors.toList()) + "\n";
    }
}
