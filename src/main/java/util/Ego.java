package util;

import mib.tree.BaseNode;
import mib.tree.DataType;
import mib.tree.Leaf;
import mib.tree.interfaces.HaveChildren;
import mib.tree.interfaces.LeafData;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
// TODO: Find proper name for Class
public final class Ego {
    static public List<DataType> detectDataType(BaseNode node, List<DataType> dataTypes) {
        List<DataType> list = new ArrayList<>();

        // Co tu sie odj*****
        if (node instanceof HaveChildren) {
            for (BaseNode n : ((HaveChildren) node).getChildren()) {
                if (n instanceof Leaf) {
                    if (((Leaf) n).getNodeName().equals("SYNTAX")) {
                        final String dataName = recognizeData(((Leaf) n).getData(), dataTypes);
                        if (dataName.equals("SEQUENCE OF")) {
                            list.add(dataTypes.stream().filter(o -> o.getNewTypeName().equalsIgnoreCase(dataName)).findAny().get());
                            final String sequenceName = ((Leaf) n).getData().replaceAll(dataName, "").replaceAll("^\\s+|\\s+$|\\s+(?=\\s)", "");
                            final BaseNode bs = ((HaveChildren) node).getChildren().stream().filter(o -> o.getNodeName().equalsIgnoreCase(sequenceName)).findAny().get();
                            if (bs instanceof HaveChildren) {
                                for (BaseNode seqNode : ((HaveChildren) bs).getChildren().stream().filter(o -> !(o instanceof LeafData)).collect(Collectors.toList())) {
                                    for (BaseNode seqNodeChild : ((HaveChildren) seqNode).getChildren()) {
                                        if (seqNodeChild instanceof Leaf) {
                                            if (((Leaf) seqNodeChild).getNodeName().equals("SYNTAX")) {
                                                final String seqDataName = recognizeData(((Leaf) seqNodeChild).getData(), dataTypes);
                                                list.add(correctDataSize(((Leaf) seqNodeChild).getData(), seqDataName, dataTypes));
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            list.add(correctDataSize(((Leaf) n).getData(), dataName, dataTypes));
                        }
                    }
                }
            }
        }

        return list;
    }

    static private DataType correctDataSize(String s, String dataTypeName, List<DataType> dataTypes) {
        Matcher matcher = Pattern.compile("[\\(|\\.](\\d+)[\\)|\\.]").matcher(s);

        List<Integer> newSize = new ArrayList<>();
        int newMinSize, newMaxSize;

        while (matcher.find()) {
            newSize.add(Integer.parseInt(matcher.group(1)));
        }

        newMaxSize = newSize.stream().max(Integer::compare).orElse(0);

        newMinSize = newSize.stream().min(Integer::compare).orElse(0);


        return newSize.isEmpty() ? dataTypes.stream().filter(dT -> dT.getNewTypeName().equals(dataTypeName)).findAny().orElse(null) :
                new DataType(dataTypes.stream().filter(dT -> dT.getNewTypeName().equals(dataTypeName)).findAny().orElse(null), newMinSize, newMaxSize);
    }

    static private String recognizeData(String s, List<DataType> dataTypes) {
        String dataTypeName = "";
        List<String> newDataTypeNames = dataTypes.stream().map(DataType::getNewTypeName).collect(Collectors.toList());

        for (String name : newDataTypeNames) {
            if (s.contains(name)) {
                dataTypeName = name;
            }
        }

        return dataTypeName;
    }
}
