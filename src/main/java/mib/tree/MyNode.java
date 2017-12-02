package mib.tree;

import mib.tree.interfaces.HaveChildren;
import mib.tree.interfaces.HaveParent;
import mib.tree.interfaces.HaveUID;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MyNode extends BaseNode implements HaveChildren, HaveParent, HaveUID {
    private List<BaseNode> children = new ArrayList<>();
    private BaseNode parent = null;
    private int UID;

    public MyNode(String nodeName, int UID) {
        super(nodeName);
        this.UID = UID;
    }

    public MyNode(String nodeName, int UID, BaseNode parent) {
        super(nodeName);
        this.UID = UID;
        this.parent = parent;
    }

    public void setParent(BaseNode parent) {
        ((HaveChildren) parent).addChild(this);
        this.parent = parent;
    }

    public void addChild(BaseNode child) {
        if (!((HaveParent) child).hasParent())
            ((HaveParent) child).setParent(this);
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
    public boolean hasParent() {
        return parent != null;
    }

    @Override
    public int getUID() {
        return UID;
    }

    @Override
    public String toString() {
        return "<MyNode> " +
                super.toString() + ", " +
                "UID: " + UID + ", " +
                "Parent: [" + parent.getNodeName() + "], " +
                "Children: " + children.stream().map(BaseNode::getNodeName).collect(Collectors.toList()) + "\n";
    }
}
