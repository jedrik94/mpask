package util.coders;

import common.Constants;
import mib.tree.DataType;

import java.util.List;

@Deprecated
public class PDUFrameCreator {
    public static String init(List<DataType> dataTypes, String combinationOIDValue) {
        StringBuilder sB = new StringBuilder("");

        sB.append(combinationOIDValue);
        sB.insert(0, SizeCoder.getCodedArgumentLength(combinationOIDValue));
        sB.insert(0, DataTypeCoder.code(dataTypes.stream().filter(dT -> dT.getNewTypeName().equals("SEQUENCE")).findFirst().get()));
        sB.insert(0, SizeCoder.getCodedArgumentLength(sB.toString()));
        sB.insert(0, DataTypeCoder.code(dataTypes.stream().filter(dT -> dT.getNewTypeName().equals("SEQUENCE OF")).findFirst().get()));

        String error = DataTypeCoder.code(dataTypes.stream().filter(dT -> dT.getNewTypeName().equals("INTEGER")).findFirst().get()) +
                SizeCoder.getCodedArgumentLength(IntegerCoder.code(0)) + IntegerCoder.code(0);

        sB.insert(0, error + error);

        String ID = DataTypeCoder.code(dataTypes.stream().filter(dT -> dT.getNewTypeName().equals("INTEGER")).findFirst().get()) +
                SizeCoder.getCodedArgumentLength(IntegerCoder.code(1)) + IntegerCoder.code(1);

        sB.insert(0, ID);

        sB.insert(0, SizeCoder.getCodedArgumentLength(sB.toString()));
        sB.insert(0, DataTypeCoder.code(dataTypes.stream().filter(dT -> dT.getNewTypeName().equals("GetResponsePDU")).findFirst().get()));

        String commString = DataTypeCoder.code(dataTypes.stream().filter(dT -> dT.getNewTypeName().equals("OCTET STRING")).findFirst().get()) +
                SizeCoder.getCodedArgumentLength(OctetStringCoder.code(Constants.COMMUNITY_STRING)) + OctetStringCoder.code(Constants.COMMUNITY_STRING);

        sB.insert(0, commString);

        String SNMPv = DataTypeCoder.code(dataTypes.stream().filter(dT -> dT.getNewTypeName().equals("INTEGER")).findFirst().get()) +
                SizeCoder.getCodedArgumentLength(IntegerCoder.code(0)) + IntegerCoder.code(0);

        sB.insert(0, SNMPv);

        sB.insert(0, SizeCoder.getCodedArgumentLength(sB.toString()));
        sB.insert(0, DataTypeCoder.code(dataTypes.stream().filter(dT -> dT.getNewTypeName().equals("SEQUENCE OF")).findFirst().get()));

        return sB.toString();
    }
}