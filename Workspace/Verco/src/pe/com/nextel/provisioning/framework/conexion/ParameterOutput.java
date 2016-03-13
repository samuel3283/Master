package pe.com.nextel.provisioning.framework.conexion;


public final class ParameterOutput
{

    public ParameterOutput(int index, int dataType)
    {
        this.index = index;
        this.dataType = dataType;
    }

    public int getDataType()
    {
        return dataType;
    }

    public int getIndex()
    {
        return index;
    }

    public void setDataType(int i)
    {
        dataType = i;
    }

    public void setIndex(int i)
    {
        index = i;
    }

    private int index;
    private int dataType;
}

