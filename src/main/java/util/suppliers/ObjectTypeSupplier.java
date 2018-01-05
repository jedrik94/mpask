package util.suppliers;

import common.Constants;
import mib.tree.BaseNode;
import mib.tree.Leaf;
import mib.tree.MyNode;
import mib.tree.interfaces.HaveChildren;
import util.Parser;
import util.StringValueExtractor;

import java.util.List;

public class ObjectTypeSupplier {
    public static void createObjectTypeList(List<String> listObjectTypeElement, List<String> filesText) {
        filesText.forEach(text -> listObjectTypeElement.addAll(Parser.collectMatches(Parser.objectTypeParser(text, Parser.OBJECT_TYPE_REGEX))));
    }

    public static void putObjectTypeToTree(List<BaseNode> everyNode, List<String> listObjectTypeElement) {
        listObjectTypeElement.forEach(s -> {
            String tempParentName = StringValueExtractor.extract(s, StringValueExtractor.EnabledTokens.PARENT);
            BaseNode tempParent = everyNode.stream().filter(n -> n.getNodeName().equals(tempParentName)).findAny().orElse(null);

            String tempNodeName = StringValueExtractor.extract(s, StringValueExtractor.EnabledTokens.NODE_NAME);
            int tempUID = Integer.parseInt(StringValueExtractor.extract(s, StringValueExtractor.EnabledTokens.UID));


            MyNode tempChildren = new MyNode(tempNodeName, tempUID, tempParent);
            everyNode.add(tempChildren);
            if (((HaveChildren) tempParent) != null) {
                ((HaveChildren) tempParent).addChild(tempChildren);
            }

            for (String name : Constants.MIB_VARIABLES_NAMES) {
                Leaf tempLeaf = new Leaf(name, tempChildren);
                tempLeaf.setData(StringValueExtractor.extractValue(s, name, Constants.MIB_VARIABLES_NAMES));
                everyNode.add(tempLeaf);
                tempChildren.addChild(tempLeaf);
            }
        });
    }
}