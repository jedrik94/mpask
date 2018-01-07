package util.coders;

import common.BaseType;
import common.CodingMethod;
import mib.tree.DataType;
import util.suppliers.ProperSizeValidator;

import java.util.List;

public class BERCoder {
    final private int offSet;
    final private List<DataType> dataTypes;
    final private List<DataType> detectedData;
    final private List<String> valuesList;
    final private String expectedDataNames;

    public BERCoder(List<DataType> dataTypes, List<DataType> detectedData, List<String> valuesList) {
        this.offSet = setSequenceOffSet(detectedData);
        this.dataTypes = dataTypes;
        this.detectedData = detectedData;
        this.valuesList = valuesList;
        this.expectedDataNames = createExpectedDataString();
    }

    private boolean checkNumberOfPassedArguments() {
        if ((detectedData.size() == 0 || detectedData.size() == 1) && valuesList.size() == 0) {
            throw new IllegalArgumentException("Not passed any argument. Arguments: " + valuesList.size() + ".");
        } else if (!(detectedData.size() == valuesList.size() || detectedData.size() - 1 == valuesList.size())) {
            throw new IllegalArgumentException("Not valid number of arguments. Arguments: " + valuesList.size()
                    + "; Data types: " + (detectedData.size() - offSet) + ". "
                    + "Expected data: " + expectedDataNames);
        }

        return true;
    }

    public String createDataFrame() {
        if (checkNumberOfPassedArguments())
            return resultCreator();
        else
            return "Corrupted data.";
    }

    private String createExpectedDataString() {
        StringBuilder expectedDataNames = new StringBuilder("");
        for (int i = offSet; i < detectedData.size(); i++) {
            expectedDataNames.append(detectedData.get(i).getNewTypeName());
            if (i != detectedData.size() - 1) {
                expectedDataNames.append(", ");
            }
        }

        return expectedDataNames.toString();
    }

    private String resultCreator() {
        StringBuilder sb = new StringBuilder("");

        if (isPassedDataValid()) {
            for (int i = 0; i < detectedData.size() - offSet; i++) {
                DataType dataType = detectedData.get(i + offSet);
                String value = valuesList.get(i);
                StringBuilder innerBuilder = new StringBuilder("");

                if (dataType.getCodingMethod().equals(CodingMethod.EXPLICIT)) {
                    innerBuilder.append(DataTypeCoder.code(dataType));
                    DataType baseType = new DataType(DataType.getBaseType(dataTypes, dataType.getBaseType()), dataType.getMinSize(), dataType.getMaxSize());
                    String baseTypeCodedExplicitArgument = ImplicitTypeCoder.code(baseType, value);
                    innerBuilder.append(SizeCoder.getCodedArgumentLength(baseTypeCodedExplicitArgument));
                    innerBuilder.append(baseTypeCodedExplicitArgument);
                } else {
                    innerBuilder.append(DataTypeCoder.code(dataType));
                    innerBuilder.append(ImplicitTypeCoder.code(dataType, value));
                }
                sb.append(innerBuilder);
            }

            if (offSet == 1) {
                DataType sequenceDataType = detectedData.get(0);
                String dataTypeCoded = DataTypeCoder.code(sequenceDataType);
                sb.insert(0, SizeCoder.getCodedArgumentLength(sb.toString()));
                sb.insert(0, dataTypeCoded);
            }
        } else {
            throw new IllegalArgumentException("Invalid data passed to parse.");
        }

        return sb.toString();
    }

    private boolean isPassedDataValid() {
        boolean valid = true;

        ProperSizeValidator validator = ProperSizeValidator.valueOf(dataTypes);
        for (int i = 0; i < detectedData.size() - offSet; i++) {
            DataType dataType = detectedData.get(i + offSet);
            String value = valuesList.get(i);

            switch (dataType.getBaseType()) {
                case OCTET_STRING:
                    if (OctetStringCoder.isValidOctetString(value)) {
                        valid = valid & validator.isValidSize(value, dataType);
                    } else {
                        valid = false;
                    }
                    break;
                case INTEGER:
                    valid = valid & validator.isValidSize(value, dataType);
                    break;
                case OBJECT_IDENTIFIER:
                    valid = valid & validator.isValidSize(value, dataType);
                    break;
                default:
                    valid = false;
                    break;
            }
        }
        return valid;
    }

    private int setSequenceOffSet(List<DataType> detectedData) {
        return detectedData.size() != 0 && detectedData.get(0).getBaseType() == BaseType.SEQUENCE_OF ? 1 : 0;
    }
}
