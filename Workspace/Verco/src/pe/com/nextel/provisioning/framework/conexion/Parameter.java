package pe.com.nextel.provisioning.framework.conexion;


import java.util.ArrayList;

public final class Parameter
{

    public Parameter()
    {
        params = new ArrayList();
        datatype = 12;
    }

    public int getDatatype()
    {
        return datatype;
    }

    public Object getDataValue()
    {
        return dataValue;
    }

    public void setDatatype(int dataType)
    {
        datatype = dataType;
    }

    public void setDataValue(Object object)
    {
        dataValue = object;
    }

    public Class getType()
    {
        return type;
    }

    public void setType(Class class1)
    {
        type = class1;
    }

    private ArrayList params;
    private int datatype;
    private Class type;
    private Object dataValue;
}