package util;

import mib.tree.interfaces.HaveChildren;
import mib.tree.interfaces.HaveUID;
import mib.tree.BaseNode;

import java.util.Iterator;
import java.util.List;

public class NodeExtractor {
    static public BaseNode findNode(List<Integer> OIDList, BaseNode root) {
        List<BaseNode> childrenList = (root instanceof HaveChildren) ? ((HaveChildren) root).getChildren() : null;
        if (childrenList != null && (!(root instanceof HaveUID)) || ((HaveUID) root).getUID() != OIDList.get(0)) {
            return null;
        }
        for (Iterator<Integer> integerIterator = OIDList.subList(1, OIDList.size()).iterator(); integerIterator.hasNext(); ) {
            int OIDPart = integerIterator.next();
            for (BaseNode node : childrenList) {
                if (node instanceof HaveUID) {
                    if (((HaveUID) node).getUID() == OIDPart) {
                        if (integerIterator.hasNext()) {
                            childrenList = ((HaveChildren) node).getChildren();
                            break;
                        } else {
                            return node;
                        }
                    }
                }
            }
        }
        return null;
    }
}
