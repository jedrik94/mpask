package util.suppliers;

import common.BaseType;
import mib.tree.DataType;

import java.util.List;

public final class ProperSizeValidator {
    private enum Size {
        MIN, MAX
    }

    private final List<DataType> listTypes;

    private ProperSizeValidator(List<DataType> listTypes) {
        this.listTypes = listTypes;
    }

    public static ProperSizeValidator valueOf(List<DataType> listTypes) {
        return new ProperSizeValidator(listTypes);
    }

    public boolean isValidSize(String value, DataType dataType) {
        final long maxSize, minSize;
        boolean valid = false;

        if (dataType.getMaxSize() > 0) {
            maxSize = dataType.getMaxSize();
            minSize = dataType.getMinSize();
        } else {
            maxSize = getBaseTypeSize(dataType.getBaseType(), Size.MAX);
            minSize = getBaseTypeSize(dataType.getBaseType(), Size.MIN);
        }

        switch (dataType.getBaseType()) {
            case OCTET_STRING:
                int octalLength = value.length() / 2;
                valid = octalLength <= maxSize && octalLength >= minSize;
                break;
            case INTEGER:
                int intValue = Integer.parseInt(value);
                valid = intValue <= maxSize && intValue >= minSize;
                break;
            case OBJECT_IDENTIFIER:
                if (maxSize == 0) {
                    valid = true;
                } else {
                    int OIDLength = value.split("\\.").length;
                    valid = OIDLength <= maxSize && OIDLength >= minSize;
                }
                break;
        }

        return valid;
    }

    private long getBaseTypeSize(BaseType bT, Size size) {
        DataType dT = listTypes.stream().filter(dataType ->
                bT.toString().replaceAll("_", " ").equals(dataType.getNewTypeName())
        ).findAny().get();

        return size.equals(Size.MAX) ? dT.getMaxSize() : dT.getMinSize();
    }

}
