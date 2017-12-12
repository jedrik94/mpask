package util.coders;

import mib.tree.DataType;

class ImplicitTypeCoder {
    static String code(DataType dataType, String value) {
        StringBuilder innerBuilder = new StringBuilder();
        innerBuilder.append(SizeCoder.getCodedArgumentLength(value));
        switch (dataType.getBaseType()) {
            case OCTET_STRING:
                innerBuilder.append(value);
                break;
            case INTEGER:
                innerBuilder.append(IntegerCoder.code(Integer.valueOf(value)));
                break;
            case OBJECT_IDENTIFIER:
                innerBuilder.append(ObjectIdentifierCoder.code(value));
                break;
        }
        return innerBuilder.toString();
    }
}