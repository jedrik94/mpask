package mib.tree;

import common.BaseType;
import common.CodingMethod;
import common.Visibility;

import java.util.List;


public class DataType extends Object{

    private String newTypeName = "";
    private Visibility visibility;
    private CodingMethod codingMethod;
    private BaseType baseType;
    private int ID = 0;
    private long minSize = 0;
    private long maxSize = 0;

    public String getNewTypeName() {
        return newTypeName;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public CodingMethod getCodingMethod() {
        return codingMethod;
    }

    public BaseType getBaseType() {
        return baseType;
    }

    public int getID() {
        return ID;
    }

    public long getMinSize() {
        return minSize;
    }

    public long getMaxSize() {
        return maxSize;
    }

    public DataType(String newTypeName, Visibility visibility, CodingMethod codingMethod, BaseType baseType, int ID) {
        this.newTypeName = newTypeName;
        this.visibility = visibility;
        this.codingMethod = codingMethod;
        this.baseType = baseType;
        this.ID = ID;
    }

    public DataType(DataType newType, long minSize, long maxSize) {
        this.newTypeName = newType.newTypeName;
        this.visibility = newType.visibility;
        this.codingMethod = newType.codingMethod;
        this.baseType = newType.baseType;
        this.ID = newType.ID;
        this.minSize = minSize;
        this.maxSize = maxSize;
    }

    public DataType(String newTypeName, Visibility visibility, CodingMethod codingMethod, BaseType baseType, int ID, long maxSize) {
        this.newTypeName = newTypeName;
        this.visibility = visibility;
        this.codingMethod = codingMethod;
        this.baseType = baseType;
        this.ID = ID;
        this.maxSize = maxSize;
    }

    public DataType(String newTypeName, Visibility visibility, CodingMethod codingMethod, BaseType baseType, int ID, long minSize, long maxSize) {
        this.newTypeName = newTypeName;
        this.visibility = visibility;
        this.codingMethod = codingMethod;
        this.baseType = baseType;
        this.ID = ID;
        this.minSize = minSize;
        this.maxSize = maxSize;
    }

    @Override
    public String toString() {
        String tempTextSize = maxSize != 0 ? "from " + minSize + " to " + maxSize + ";" : "SIZE OF BASE TYPE;";
        return "[DataType] Name: " + newTypeName + ", " +
                "Visibility: " + visibility + ", " +
                "ID: " + ID + ", " +
                "Coding Method: " + codingMethod + ", " +
                "Base Type: " + baseType + ", " +
                "Size: " + tempTextSize;
    }

    public static DataType getBaseType(List<DataType> typeList, BaseType bT) {

        return typeList.stream().filter(dataType ->
                bT.toString().replaceAll("_", " ").equals(dataType.getNewTypeName())
        ).findAny().get();
    }
}
