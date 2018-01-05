package mib.tree.interfaces;

import mib.tree.BaseNode;

public interface HaveParent {
    void setParent(BaseNode parent);
    boolean hasParent();
}
