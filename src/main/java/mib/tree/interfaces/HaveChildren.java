package mib.tree.interfaces;

import mib.tree.BaseNode;

import java.util.List;

public interface HaveChildren {
    void addChild(BaseNode child);
    List<BaseNode> getChildren();
    List<BaseNode> removeChild();
    boolean hasAnyChild();
}
