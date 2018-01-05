package util;

import common.BaseType;
import common.CodingMethod;
import common.Visibility;
import mib.tree.DataType;

public class NewDataTypeCreator {
    static public DataType getNewDataType(String[] arrayText) {
        Visibility tempVis;
        BaseType tempBase;
        CodingMethod tempCod;
        long minSize, maxSize;

        tempVis = Visibility.valueOf(arrayText[1]);
        tempCod = CodingMethod.valueOf(arrayText[3]);
        tempBase = BaseType.valueOf(arrayText[4]);

        if (arrayText.length == 7) {
            maxSize = Long.parseLong(arrayText[6]);
            minSize = Long.parseLong(arrayText[5]);
            return new DataType(arrayText[0], tempVis, tempCod, tempBase, Integer.parseInt(arrayText[2]), minSize, maxSize);
        } else if (arrayText.length == 6) {
            maxSize = Integer.parseInt(arrayText[5]);
            return new DataType(arrayText[0], tempVis, tempCod, tempBase, Integer.parseInt(arrayText[2]), maxSize);
        }

        return new DataType(arrayText[0], tempVis, tempCod, tempBase, Integer.parseInt(arrayText[2]));
    }
}
