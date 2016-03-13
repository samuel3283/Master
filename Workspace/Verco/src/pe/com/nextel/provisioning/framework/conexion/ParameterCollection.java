package pe.com.nextel.provisioning.framework.conexion;


import java.util.ArrayList;

// Referenced classes of package 
//            Parameter, ParameterOutput

public class ParameterCollection extends ArrayList
{

    public ParameterCollection()
    {
    }

    public void addParameterInput(Parameter parameter)
    {
        add(parameter);
    }

    public void addParameterOutput(ParameterOutput parameter)
    {
        add(parameter);
    }

    public void addInput(Object object)
    {
        Parameter parameter = new Parameter();
        parameter.setDataValue(object);
        add(parameter);
    }

    public void addInput(Object object, int dataType)
    {
        Parameter parameter = new Parameter();
        parameter.setDataValue(object);
        parameter.setDatatype(dataType);
        add(parameter);
    }

    public void addOutput(int dataType)
    {
        ParameterOutput parameterOutput = new ParameterOutput(size(), dataType);
        add(parameterOutput);
    }

    public int getDataType(int index)
    {
        ParameterOutput parameter = (ParameterOutput)get(index);
        return parameter.getDataType();
    }
}
