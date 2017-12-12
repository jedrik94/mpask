package util.suppliers;

import mib.tree.BaseNode;
import mib.tree.MyNode;
import mib.tree.interfaces.HaveChildren;
import util.Parser;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ObjectIdentifierSupplier {
    public static void createObjectIdentifierList(List<String> listObjectIdentifierElement, List<String> filesText) {
        filesText.forEach(text -> Parser.collectMatches(Parser.objectTypeParser(text, Parser.OBJECT_IDENTIFIER_REGEX))
                .forEach(s -> {
                    final Pattern oid = Pattern.compile("([a-zA-Z0-9-]+)\\s+.*\\s+([a-zA-Z0-9-]+)[0-9()]*\\s+(\\d+)");
                    final Matcher matcher = oid.matcher(s);
                    if (matcher.find()) {
                        listObjectIdentifierElement.add(matcher.group(1) + " " + matcher.group(2) + " " + matcher.group(3));
                    }
                }));
    }

    public static void putObjectIdentifierToTree(List<BaseNode> everyNode, List<String> listObjectIdentifierElement) {
        listObjectIdentifierElement.forEach(oi -> {
            String[] temp = oi.split("\\s");
            BaseNode tempParent = everyNode.stream().filter(n -> n.getNodeName().equals(temp[1])).findAny().orElse(null);

            {
                MyNode tempChildren = new MyNode(temp[0], Integer.parseInt(temp[2]), tempParent);
                everyNode.add(tempChildren);
                if (((HaveChildren) tempParent) != null) {
                    ((HaveChildren) tempParent).addChild(tempChildren);
                }
            }
        });
    }
}