package util;

import mib.tree.BaseNode;
import mib.tree.interfaces.HaveChildren;
import mib.tree.interfaces.HaveUID;
import mib.tree.interfaces.LeafData;

public class Drawer {
    public static void drawTree(BaseNode node, String place) {
        System.out.print(place + node.getNodeName());

        if (node instanceof HaveUID) {
            System.out.print("; UID: " + ((HaveUID) node).getUID());
        }

        if (node instanceof LeafData) {
            System.out.println(": " + ((LeafData) node).getData());
        } else {
            System.out.println();
        }

        if (node instanceof HaveChildren) {
            ((HaveChildren) node).getChildren().forEach(ch -> drawTree(ch, "  " + place));
        }
    }
}