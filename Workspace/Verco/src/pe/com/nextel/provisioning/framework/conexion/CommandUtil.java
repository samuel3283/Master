package pe.com.nextel.provisioning.framework.conexion;


import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import oracle.jdbc.OracleCallableStatement;
import oracle.sql.ARRAY;
import oracle.sql.BFILE;
import oracle.sql.BLOB;
import oracle.sql.CHAR;
import oracle.sql.CLOB;
import oracle.sql.REF;
import oracle.sql.ROWID;
import oracle.sql.STRUCT;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

// Referenced classes of package 
//            QueryException, Parameter, ParameterCollection

public class CommandUtil
{
    private static Logger logger;

    static 
    {
        logger = Logger.getLogger(pe.com.nextel.provisioning.framework.conexion.CommandUtil.class.getName());
    }
    private CommandUtil()
    {
    }

    public static void execute(Connection conn, String storeProcedure, ParameterCollection paramsInputCollection)
        throws QueryException
    {
        CallableStatement cstmt = null;
        ResultSet rs = null;
        int numParamsInput = 0;
        Object outPut = null;
        try
        {
            numParamsInput = paramsInputCollection.size();
            outPut = new Object();
            cstmt = conn.prepareCall(getProcedureName(storeProcedure, numParamsInput));
            for(int i = 1; i <= numParamsInput; i++)
            {
                Parameter parameter = (Parameter)paramsInputCollection.get(i - 1);
                setParameter(cstmt, i, parameter);
            }

            cstmt.execute();
        }
        catch(SQLException e)
        {
            logger.error("Error SQLException en execute:" + storeProcedure, e);
            throw new QueryException(e.getMessage(), e);
        }
        catch(Exception e)
        {
            logger.error("Error Exception en execute:" + storeProcedure, e);
            throw new QueryException(e.getMessage(), e);
        }
        finally
        {
            try
            {
                if(rs != null)
                    rs.close();
                if(cstmt != null)
                    cstmt.close();
            }
            catch(SQLException e1)
            {
                logger.error("Error en execute:" + storeProcedure + " al cerrar rs o cstmt", e1);
            }
        }
    }

    public static int executeUpdate(Connection conn, String query)
        throws QueryException
    {
        PreparedStatement cstmt = null;
        int numParamsInput = 0;
        try
        {
            cstmt = conn.prepareStatement(query);
            int i = cstmt.executeUpdate();
            return i;
        }
        catch(SQLException e)
        {
            logger.error("Error SQLException en executeUpdate:" + query, e);
            throw new QueryException(e.getMessage(), e);
        }
        catch(Exception e)
        {
            logger.error("Error Exception en executeUpdate:" + query, e);
            throw new QueryException(e.getMessage(), e);
        }
        finally
        {
            try
            {
                if(cstmt != null)
                    cstmt.close();
            }
            catch(SQLException e1)
            {
                logger.error("Error en executeUpdate:" + query + " al cerrar cstmt", e1);
            }
        }
    }

    public static ArrayList executeQuery(Connection conn, String query)
        throws QueryException
    {
        PreparedStatement cstmt = null;
        ResultSet rs = null;
        try
        {
            cstmt = conn.prepareStatement(query);
            rs = cstmt.executeQuery();
            ArrayList arraylist = getVOList(rs);
            return arraylist;
        }
        catch(SQLException e)
        {
            logger.error("Error SQLException en executeQuery:" + query, e);
            throw new QueryException(e.getMessage(), e);
        }
        catch(Exception e)
        {
            logger.error("Error Exception en executeQuery:" + query, e);
            throw new QueryException(e.getMessage(), e);
        }
        finally
        {
            try
            {
                if(rs != null)
                    rs.close();
                if(cstmt != null)
                    cstmt.close();
            }
            catch(SQLException e1)
            {
                logger.error("Error en executeQuery:" + query + " al cerrar rs o cstmt", e1);
            }
        }
    }

    public static ArrayList executeQuery(Connection conn, String query, String params[])
        throws QueryException
    {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try
        {
            pstmt = conn.prepareStatement(query);
            for(int i = 0; i < params.length; i++)
            {
                String string = params[i];
                pstmt.setString(i + 1, string);
            }

            rs = pstmt.executeQuery();
            ArrayList arraylist = getVOList(rs);
            return arraylist;
        }
        catch(SQLException e)
        {
            logger.error("Error SQLException en executeQuery:" + query, e);
            throw new QueryException(e.getMessage(), e);
        }
        catch(Exception e)
        {
            logger.error("Error Exception en executeQuery:" + query, e);
            throw new QueryException(e.getMessage(), e);
        }
        finally
        {
            try
            {
                if(rs != null)
                    rs.close();
                if(pstmt != null)
                    pstmt.close();
            }
            catch(SQLException e1)
            {
                logger.error("Error en executeQuery:" + query + " al cerrar rs o cstmt", e1);
            }
        }
    }

    public static Object executeScalar(Connection conn, String storeProcedure, ParameterCollection paramsInputCollection, ParameterCollection parameterOutputCollection)
        throws QueryException
    {
        CallableStatement cstmt = null;
        ResultSet rs = null;
        int numParamsInput = 0;
        int numParamsOutput = 0;
        int numParamsTotal = 0;
        Object outPut = null;
        try
        {
            numParamsInput = paramsInputCollection.size();
            numParamsOutput = parameterOutputCollection.size();
            numParamsTotal = numParamsInput + numParamsOutput;
            outPut = new Object();
            cstmt = conn.prepareCall(getProcedureName(storeProcedure, numParamsTotal));
            for(int i = 1; i <= numParamsInput; i++)
            {
                Parameter parameter = (Parameter)paramsInputCollection.get(i - 1);
                setParameter(cstmt, i, parameter);
            }

            for(int i = 1; i <= numParamsOutput; i++)
                cstmt.registerOutParameter(numParamsInput + i, parameterOutputCollection.getDataType(i - 1));

            cstmt.execute();
            outPut = getScalarResult(cstmt, parameterOutputCollection, numParamsInput);
        }
        catch(SQLException e)
        {
            logger.error("Error SQLException en executeScalar:" + storeProcedure, e);
            throw new QueryException(e.getMessage(), e);
        }
        catch(Exception e)
        {
            logger.error("Error Exception en executeScalar:" + storeProcedure, e);
            throw new QueryException(e.getMessage(), e);
        }
        finally
        {
            try
            {

                if(rs != null)
                    rs.close();
                if(cstmt != null)
                    cstmt.close();
            }
            catch(SQLException e1)
            {
                logger.error("Error en executeScalar:" + storeProcedure + " al cerrar rs o cstmt", e1);
            }
        }
        return outPut;
    }
    public static String getProcedureName(String procedureName, int paramsQty)
    {

        String prepareCall = "";
        prepareCall = "{call " + procedureName + "(";

        for(int i = 0; i < paramsQty; i++)

            prepareCall = i >= paramsQty - 1 ? prepareCall + "?" : prepareCall + "?,";


        prepareCall = prepareCall + ")}";
        return prepareCall;

    }

    public static String getFunctionName(String functionName, int paramsQty)
    {
        String prepareCall = "";
        prepareCall = "{?=call " + functionName + "(";
        for(int i = 0; i < paramsQty; i++)
            prepareCall = i >= paramsQty - 1 ? prepareCall + "?" : prepareCall + "?,";

        prepareCall = prepareCall + ")}";
        return prepareCall;
    }

    public static void setParameter(CallableStatement cstmt, int i, Parameter parameter)
        throws SQLException
    {
        Object value = parameter.getDataValue();
        int dataType = parameter.getDatatype();
        parameter.setType(java.lang.String.class.getClass());
        if(value == null)

            cstmt.setNull(i, dataType);
        else
            switch(dataType)
            {
            case 2003: 
                cstmt.setArray(i, (ARRAY)value);
                break;

            case -4: 
            case -3: 
            case -2: 

                cstmt.setBytes(i, (byte[])value);

                break;

            case -5: 
                cstmt.setLong(i, Long.parseLong(value.toString()));
                break;

            case 2004: 
                cstmt.setBlob(i, (BLOB)value);
                break;

            case -7: 
                cstmt.setBoolean(i, (new Boolean((String)value)).booleanValue());
                break;


            case -13: 

                ((OracleCallableStatement)cstmt).setBfile(i, (BFILE)value);
                break;

            case 2005: 
                cstmt.setClob(i, (CLOB)value);
                break;

            case 1: // '\001'
                ((OracleCallableStatement)cstmt).setCHAR(i, (CHAR)value);
                break;


            case 91: // '['
                cstmt.setDate(i, (Date)value);
                break;

            case 8: // '\b'
                cstmt.setDouble(i, Double.parseDouble(value.toString()));
                break;


            case 6: // '\006'
            case 7: // '\007'
                cstmt.setFloat(i, Float.parseFloat(value.toString()));
                break;

            case 4: // '\004'
                cstmt.setInt(i, Integer.parseInt(value.toString()));
                break;


            case 2: // '\002'
                cstmt.setBigDecimal(i, new BigDecimal(value.toString()));

                break;


            case 0: // '\0'
                cstmt.setNull(i, dataType);
                break;

            case 2006: 
                cstmt.setRef(i, (REF)value);
                break;

            case -8: 
                ((OracleCallableStatement)cstmt).setROWID(i, (ROWID)value);
                break;

            case 5: // '\005'
                cstmt.setShort(i, Short.parseShort(value.toString()));
                break;

            case 2002: 
                ((OracleCallableStatement)cstmt).setSTRUCT(i, (STRUCT)value);
                break;

            case -6: 
                cstmt.setByte(i, Byte.parseByte(value.toString()));
                break;


            case 92: // '\\'
                cstmt.setTime(i, (Time)value);
                break;

            case 93: // ']'
                cstmt.setTimestamp(i, (Timestamp)value);
                break;

            case -1: 
            case 12: // '\f'
                cstmt.setString(i, value.toString());
                break;
            }
    }

    public static Object[] getArrayResult(CallableStatement cstmt, ParameterCollection parameterOutputCollection, int numParamsInput)
        throws SQLException, Exception
    {
        Object outList[] = new Object[parameterOutputCollection.size()];
        for(int i = 0; i < parameterOutputCollection.size(); i++)
        {
            int index = numParamsInput + i + 1;
            if(parameterOutputCollection.getDataType(i) == -10)
                outList[i] = getVOList((ResultSet)cstmt.getObject(index));
            else
                outList[i] = cstmt.getObject(index);
        }

        return outList;
    }

    public static Object getScalarResult(CallableStatement cstmt, ParameterCollection parameterOutputCollection, int numParamsInput)
        throws SQLException, Exception
    {
        Object out = new Object();
        int index = numParamsInput + 1;
        out = cstmt.getObject(index);
        return out;
    }

    public static ArrayList getVOList(ResultSet rs)
        throws Exception

    {
        String dynaBeanName = "ValueObject";
        ArrayList VOList = new ArrayList();
        HashMap map = new HashMap();
        ArrayList list = new ArrayList();
        int columnsNum = rs.getMetaData().getColumnCount();
        String columnName[] = new String[columnsNum];
        Class type = java.lang.String.class;
        try
        {
            for(int i = 0; i < columnsNum; i++)
            {
                columnName[i] = rs.getMetaData().getColumnName(i + 1);
                DynaProperty prop = new DynaProperty(columnName[i], type);
                list.add(prop);
            }

            DynaProperty properties[] = (DynaProperty[])list.toArray(new DynaProperty[0]);
            org.apache.commons.beanutils.DynaBean dynaBean;
            for(; rs.next(); VOList.add(dynaBean))
            {
                dynaBean = null;
                for(int i = 0; i < columnsNum; i++)
                {
                    String name = columnName[i];
                    String value = rs.getString(name);
                    map.put(name, value);
                    DynaClass dynaClass = new BasicDynaClass(dynaBeanName, null, properties);
                    dynaBean = dynaClass.newInstance();
                    BeanUtils.populate(dynaBean, map);
                }

            }

        }
        catch(SQLException e)
        {
            logger.error(e.getMessage(), e);
            new QueryException(e.getMessage(), e);
        }
        return VOList;
    }

    public static ArrayList getVOList(ResultSet rs, Class objectVO)
        throws Exception
    {
        String dynaBeanName = "ValueObject";
        ArrayList VOList = new ArrayList();
        HashMap map = new HashMap();
        ArrayList list = new ArrayList();
        int columnsNum = rs.getMetaData().getColumnCount();
        String columnName[] = new String[columnsNum];
        Class columnType[] = new Class[columnsNum];
        Converter converter[] = new Converter[columnsNum];
        Class type = null;
        String name = null;
        Object object = null;
        Object valueObject = null;
        PropertyDescriptor descriptor = null;
        try
        {
            object = objectVO.newInstance();
            for(int i = 0; i < columnsNum; i++)
            {
                name = rs.getMetaData().getColumnName(i + 1);
                logger.debug("i=" + i + " name->" + name);
                columnName[i] = name;
                descriptor = PropertyUtils.getPropertyDescriptor(object, name);
                if(descriptor != null)
                {
                    type = descriptor.getPropertyType();
                    logger.debug("name->" + name + " type->" + type.getName());
                    columnType[i] = type;
                    converter[i] = ConvertUtils.lookup(type);
                } else
                {
                    columnType[i] = null;
                    converter[i] = null;
                }
            }

            for(; rs.next(); VOList.add(object))
            {
                object = objectVO.newInstance();
                for(int i = 0; i < columnsNum; i++)
                {
                    name = columnName[i];
                    Object value = rs.getObject(name);
                    if(converter[i] != null)
                    {
                        logger.debug("value->" + value);
                        if(value != null)
                            valueObject = converter[i].convert(columnType[i], value);
                        else
                            valueObject = null;
                        PropertyUtils.setSimpleProperty(object, name, valueObject);

                    }
                }

            }

        }
        catch(SQLException e)
        {
            logger.error(e.getMessage(), e);
            new QueryException(e.getMessage(), e);

        }

        return VOList;
    }

    public static Object executeFunction(Connection conn, String functionName, ParameterCollection paramsInputCollection, int dataType)
        throws QueryException
    {
        CallableStatement cstmt = null;
        ResultSet rs = null;
        int numParamsInput = 0;
        int numParamsOutput = 0;
        Object outPut = null;
        try
        {

            numParamsInput = paramsInputCollection.size();
            outPut = new Object();
            cstmt = conn.prepareCall(getFunctionName(functionName, numParamsInput));
            cstmt.registerOutParameter(1, dataType);
            for(int i = 1; i <= numParamsInput; i++)

            {
                Parameter parameter = (Parameter)paramsInputCollection.get(i - 1);
                setParameter(cstmt, i + 1, parameter);

            }

            cstmt.execute();

            outPut = getScalarResult(cstmt, paramsInputCollection, -paramsInputCollection.size());
        }
        catch(SQLException e)

        {
            logger.error("Error SQLException en executeScalar:" + functionName, e);
            throw new QueryException(e.getMessage(), e);
        }
        catch(Exception e)
        {
            logger.error("Error Exception en executeScalar:" + functionName, e);
            throw new QueryException(e.getMessage(), e);
        }
        finally
        {
            try
            {
                if(rs != null)
                    rs.close();
                if(cstmt != null)
                    cstmt.close();
            }
            catch(SQLException e1)
            {
                logger.error("Error en executeScalar:" + functionName + " al cerrar rs o cstmt", e1);
            }
        }
        return outPut;
    }


}
