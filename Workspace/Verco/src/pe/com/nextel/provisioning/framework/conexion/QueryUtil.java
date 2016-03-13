package pe.com.nextel.provisioning.framework.conexion;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

// Referenced classes of package
//            QueryException, CommandUtil, ParameterCollection, Parameter

public class QueryUtil
{
    private static final Logger logger;

    static 
    {
        logger = Logger.getLogger(QueryUtil.class.getName());
    }
    
    public QueryUtil()
    {
    }

    public static void execute(Connection conn, String storeProcedure, ParameterCollection paramsInputCollection)
        throws QueryException

    {
        CommandUtil.execute(conn, storeProcedure, paramsInputCollection);
    }

    public static void execute(Connection conn, String storeProcedure, String paramsInputCollection[])
        throws QueryException

    {
        ParameterCollection parameterCollection = new ParameterCollection();
        for(int i = 0; i < paramsInputCollection.length; i++)
            parameterCollection.addInput(paramsInputCollection[i]);


        CommandUtil.execute(conn, storeProcedure, parameterCollection);
    }

    public static ArrayList executeProcedure(Connection conn, String storeProcedure)
        throws QueryException
    {
        CallableStatement cstmt = null;
        ResultSet rs = null;
        int numParamsInput = 0;
        ArrayList outPutList = new ArrayList();
        try

        {
            cstmt = conn.prepareCall(CommandUtil.getProcedureName(storeProcedure, 1));
            cstmt.registerOutParameter(1, -10);
            cstmt.execute();
            rs = (ResultSet)cstmt.getObject(numParamsInput + 1);
            outPutList = CommandUtil.getVOList(rs);
        }
        catch(SQLException e)
        {
            logger.error("error SQLException, storeprocedure:" + storeProcedure);
            throw new QueryException(e.getMessage(), e);
        }
        catch(Exception e)

        {
            logger.error("error Exception, storeprocedure:" + storeProcedure);
            throw new QueryException(e.getMessage(), e);
        }

        finally
        {
            try
            {
                if(rs != null)

                    rs.close();
            }
            catch(SQLException e1)
            {

                logger.error("Error en Execute procedure:" + storeProcedure + ", al cerrar Resultset");
            }
            try
            {
                if(cstmt != null)

                    cstmt.close();
            }
            catch(SQLException e2)
            {
                logger.error("Error en Execute procedure:" + storeProcedure + ", al cerrar CallableStatement");
            }
        }
        return outPutList;
    }


    public static ArrayList executeProcedure(Connection conn, String storeProcedure, ParameterCollection paramsInputCollection)
        throws QueryException
    {

        CallableStatement cstmt = null;
        ResultSet rs = null;
        int numParamsInput = 0;

        ArrayList outPutList = new ArrayList();
        try
        {
            numParamsInput = paramsInputCollection.size();
            cstmt = conn.prepareCall(CommandUtil.getProcedureName(storeProcedure, numParamsInput + 1));
            for(int i = 1; i <= numParamsInput; i++)
            {
                Parameter parameter = (Parameter)paramsInputCollection.get(i - 1);
                CommandUtil.setParameter(cstmt, i, parameter);
            }

            cstmt.registerOutParameter(numParamsInput + 1, -10);
            cstmt.execute();
            rs = (ResultSet)cstmt.getObject(numParamsInput + 1);
            outPutList = CommandUtil.getVOList(rs);
        }
        catch(SQLException e)
        {
            logger.error("error SQLException, storeprocedure:" + storeProcedure);
            throw new QueryException(e.getMessage(), e);
        }
        catch(Exception e)
        {
            logger.error("error Exception, storeprocedure:" + storeProcedure);
            throw new QueryException(e.getMessage(), e);
        }
        finally
        {
            try
            {
                if(rs != null)
                    rs.close();
            }
            catch(SQLException e1)
            {
                logger.error("Error en Execute procedure:" + storeProcedure + ", al cerrar Resultset");
            }
            try
            {
                if(cstmt != null)
                    cstmt.close();
            }
            catch(SQLException e2)
            {
                logger.error("Error en Execute procedure:" + storeProcedure + ", al cerrar CallableStatement");
            }
        }
        return outPutList;
    }

    public static Object[] executeProcedure(Connection conn, String storeProcedure, ParameterCollection paramsInputCollection, ParameterCollection parameterOutputCollection)
        throws QueryException
    {
        CallableStatement cstmt = null;
        ResultSet rs = null;
        int numParamsInput = 0;
        int numParamsOutput = 0;
        int numParamsTotal = 0;
        Object outPutList[] = (Object[])null;
        try
        {
            numParamsInput = paramsInputCollection.size();
            numParamsOutput = parameterOutputCollection.size();
            numParamsTotal = numParamsInput + numParamsOutput;
            outPutList = new Object[numParamsTotal];
            cstmt = conn.prepareCall(CommandUtil.getProcedureName(storeProcedure, numParamsTotal));
            for(int i = 1; i <= numParamsInput; i++)
            {
                Parameter parameter = (Parameter)paramsInputCollection.get(i - 1);
                CommandUtil.setParameter(cstmt, i, parameter);
            }

            for(int i = 1; i <= numParamsOutput; i++)
                cstmt.registerOutParameter(numParamsInput + i, parameterOutputCollection.getDataType(i - 1));

            cstmt.execute();
            outPutList = CommandUtil.getArrayResult(cstmt, parameterOutputCollection, numParamsInput);
        }
        catch(SQLException e)
        {
            logger.error("error SQLException, storeprocedure:" + storeProcedure);
            throw new QueryException(e.getMessage(), e);

        }
        catch(Exception e)
        {
            e.printStackTrace();
            logger.error("error Exception, storeprocedure:" + storeProcedure);
            throw new QueryException(e.getMessage(), e);
        }
        finally
        {
            try
            {
                if(rs != null)
                    rs.close();
            }
            catch(SQLException e1)
            {
                logger.error("Error en Execute procedure:" + storeProcedure + ", al cerrar Resultset");
            }
            try
            {
                if(cstmt != null)
                    cstmt.close();
            }
            catch(SQLException e2)
            {
                logger.error("Error en Execute procedure:" + storeProcedure + ", al cerrar CallableStatement");
            }
        }
        return outPutList;
    }

    public static ArrayList executeProcedure(Connection conn, String storeProcedure, String paramsInputCollection[])
        throws QueryException
    {
        CallableStatement cstmt = null;
        ResultSet rs = null;
        int numParamsInput = 0;
        int numParamsOutput = 0;
        int numParamsTotal = 0;

        ArrayList outPutList = new ArrayList();
        try
        {
            numParamsInput = paramsInputCollection.length;
            cstmt = conn.prepareCall(CommandUtil.getProcedureName(storeProcedure, numParamsInput + 1));
            for(int i = 1; i <= numParamsInput; i++)
                cstmt.setString(i, paramsInputCollection[i - 1]);

            cstmt.registerOutParameter(numParamsInput + 1, -10);
            cstmt.execute();
            rs = (ResultSet)cstmt.getObject(numParamsInput + 1);
            outPutList = CommandUtil.getVOList(rs);
        }
        catch(SQLException e)

        {
            logger.error("error SQLException, storeprocedure:" + storeProcedure + " " + e.getMessage(), e);
            throw new QueryException(e.getMessage(), e);
        }

        catch(Exception e)
        {
            logger.error("error Exception, storeprocedure:" + storeProcedure);

            throw new QueryException(e.getMessage(), e);
        }
        finally
        {
            try
            {
                if(rs != null)
                    rs.close();
            }
            catch(SQLException e1)
            {
                logger.error("Error en Execute procedure:" + storeProcedure + ", al cerrar Resultset");
            }
            try
            {
                if(cstmt != null)
                    cstmt.close();
            }
            catch(SQLException e2)

            {

                logger.error("Error en Execute procedure:" + storeProcedure + ", al cerrar CallableStatement");
            }
        }
        return outPutList;
    }

    public static ArrayList executeProcedure(Connection conn, String storeProcedure, ParameterCollection paramsInputCollection, Class objectVO)
        throws QueryException
    {
        CallableStatement cstmt = null;
        ResultSet rs = null;
        int numParamsInput = 0;
        int numParamsOutput = 0;
        int numParamsTotal = 0;
        ArrayList outPutList = new ArrayList();
        try
        {
            numParamsInput = paramsInputCollection.size();
            cstmt = conn.prepareCall(CommandUtil.getProcedureName(storeProcedure, numParamsInput + 1));
            for(int i = 1; i <= numParamsInput; i++)
            {
                Parameter parameter = (Parameter)paramsInputCollection.get(i - 1);
                CommandUtil.setParameter(cstmt, i, parameter);
            }

            cstmt.registerOutParameter(numParamsInput + 1, -10);

            cstmt.execute();
            rs = (ResultSet)cstmt.getObject(numParamsInput + 1);
            outPutList = CommandUtil.getVOList(rs, objectVO);
        }
        catch(SQLException e)
        {
            logger.error("error SQLException, storeprocedure:" + storeProcedure + " " + e.getMessage(), e);
            throw new QueryException(e.getMessage(), e);
        }
        catch(Exception e)

        {
            logger.error("error Exception, storeprocedure:" + storeProcedure);
            throw new QueryException(e.getMessage(), e);
        }

        finally
        {
            try
            {
                if(rs != null)
                    rs.close();
            }
            catch(SQLException e1)

            {
                logger.error("Error en Execute procedure:" + storeProcedure + ", al cerrar Resultset");
            }
            try
            {
                if(cstmt != null)
                    cstmt.close();

            }
            catch(SQLException e2)
            {
                logger.error("Error en Execute procedure:" + storeProcedure + ", al cerrar CallableStatement");
            }
        }
        return outPutList;
    }

    public static ArrayList executeProcedure(Connection conn, String storeProcedure, String paramsInputCollection[], Class objectVO)
        throws QueryException
    {
        CallableStatement cstmt = null;
        ResultSet rs = null;
        int numParamsInput = 0;
        int numParamsOutput = 0;
        int numParamsTotal = 0;
        ArrayList outPutList = new ArrayList();
        try
        {
            numParamsInput = paramsInputCollection.length;
            cstmt = conn.prepareCall(CommandUtil.getProcedureName(storeProcedure, numParamsInput + 1));
            for(int i = 1; i <= numParamsInput; i++)

                cstmt.setString(i, paramsInputCollection[i - 1]);

            cstmt.registerOutParameter(numParamsInput + 1, -10);
            cstmt.execute();
            rs = (ResultSet)cstmt.getObject(numParamsInput + 1);
            outPutList = CommandUtil.getVOList(rs, objectVO);
        }

        catch(SQLException e)

        {
            logger.error("error SQLException, storeprocedure:" + storeProcedure + " " + e.getMessage(), e);
            throw new QueryException(e.getMessage(), e);
        }

        catch(Exception e)
        {
            logger.error("error Exception, storeprocedure:" + storeProcedure);

            throw new QueryException(e.getMessage(), e);
        }
        finally
        {
            try
            {
                if(rs != null)

                    rs.close();
            }
            catch(SQLException e1)
            {
                logger.error("Error en Execute procedure:" + storeProcedure + ", al cerrar Resultset");
            }
            try
            {
                if(cstmt != null)
                    cstmt.close();
            }
            catch(SQLException e2)
            {
                logger.error("Error en Execute procedure:" + storeProcedure + ", al cerrar CallableStatement");
            }
        }
        return outPutList;
    }

    public static Object executeScalar(Connection conn, String storeProcedure, String paramsInputCollection[], Class classVO)
        throws QueryException
    {
        ArrayList list = executeProcedure(conn, storeProcedure, paramsInputCollection, classVO);
        if(list != null && list.size() > 0)
            return list.get(0);
        else
            return null;
    }

    public static Object executeScalar(Connection conn, String storeProcedure, String paramsInputCollection[], int dataType)
        throws QueryException
    {
        ParameterCollection parameterCollection = new ParameterCollection();
        for(int i = 0; i < paramsInputCollection.length; i++)
            parameterCollection.addInput(paramsInputCollection[i]);

        ParameterCollection parameterOutputCollection = new ParameterCollection();
        parameterOutputCollection.addOutput(dataType);
        return CommandUtil.executeScalar(conn, storeProcedure, parameterCollection, parameterOutputCollection);
    }

    public static String executeScalar(Connection conn, String storeProcedure, String paramsInputCollection[])
        throws QueryException
    {
        ParameterCollection parameterCollection = new ParameterCollection();
        for(int i = 0; i < paramsInputCollection.length; i++)
            parameterCollection.addInput(paramsInputCollection[i]);

        ParameterCollection parameterOutputCollection = new ParameterCollection();
        parameterOutputCollection.addOutput(12);
        return (String)CommandUtil.executeScalar(conn, storeProcedure, parameterCollection, parameterOutputCollection);
    }

    public static Object executeScalar(Connection conn, String storeProcedure, ParameterCollection paramsInputCollection, int dataType)
        throws QueryException

    {
        ParameterCollection parameterOutputCollection = new ParameterCollection();
        parameterOutputCollection.addOutput(dataType);
        return CommandUtil.executeScalar(conn, storeProcedure, paramsInputCollection, parameterOutputCollection);
    }

    public static String executeScalar(Connection conn, String storeProcedure, ParameterCollection paramsInputCollection)

        throws QueryException
    {
        ParameterCollection parameterOutputCollection = new ParameterCollection();
        parameterOutputCollection.addOutput(12);
        return (String)CommandUtil.executeScalar(conn, storeProcedure, paramsInputCollection, parameterOutputCollection);
    }

    public static int executeUpdate(Connection conn, String query)

        throws QueryException
    {
        return CommandUtil.executeUpdate(conn, query);
    }

    public static ArrayList executeQuery(Connection conn, String query)
        throws QueryException
    {
        return CommandUtil.executeQuery(conn, query);
    }

    public static ArrayList executeQuery(Connection conn, String query, String params[])
        throws QueryException
    {
        return CommandUtil.executeQuery(conn, query, params);
    }

    public static ArrayList getArrayList(Connection conn, String functionName, ParameterCollection paramsInputCollection)
        throws QueryException

    {
        CallableStatement cstmt = null;

        ResultSet rs = null;
        int numParamsInput = paramsInputCollection.size();

        ArrayList outPutList = new ArrayList();
        try

        {
            cstmt = conn.prepareCall(CommandUtil.getFunctionName(functionName, numParamsInput));
            cstmt.registerOutParameter(1, -10);
            for(int i = 1; i <= numParamsInput; i++)
            {
                Parameter parameter = (Parameter)paramsInputCollection.get(i - 1);
                CommandUtil.setParameter(cstmt, i + 1, parameter);
            }

            cstmt.execute();
            rs = (ResultSet)cstmt.getObject(1);
            outPutList = CommandUtil.getVOList(rs);
        }
        catch(SQLException e)
        {
            logger.error(e.getMessage(), e);
            throw new QueryException(e.getMessage(), e);

        }
        catch(Exception e)
        {
            logger.error(e.getMessage(), e);
            throw new QueryException(e.getMessage(), e);
        }
        finally
        {
            try
            {
                if(rs != null)
                    rs.close();
            }
            catch(SQLException e1)
            {
                logger.error("Error en ejecutar funci\363n:" + functionName + ", al cerrar Resultset");
            }
            try
            {
                if(cstmt != null)
                    cstmt.close();
            }
            catch(SQLException e2)
            {
                logger.error("Error en ejecutar funci\363n:" + functionName + ", al cerrar CallableStatement");
            }
        }
        return outPutList;
    }

    public static ArrayList getArrayList(Connection conn, String functionName)
        throws QueryException
    {
        CallableStatement cstmt = null;
        ResultSet rs = null;
        ArrayList outPutList = new ArrayList();
        try
        {
            cstmt = conn.prepareCall(CommandUtil.getFunctionName(functionName, 0));
            cstmt.registerOutParameter(1, -10);
            cstmt.execute();
            rs = (ResultSet)cstmt.getObject(1);
            outPutList = CommandUtil.getVOList(rs);
        }
        catch(SQLException e)
        {
            logger.error(e.getMessage(), e);
            throw new QueryException(e.getMessage(), e);
        }
        catch(Exception e)
        {
            logger.error(e.getMessage(), e);
            throw new QueryException(e.getMessage(), e);
        }
        finally
        {
            try
            {
                if(rs != null)
                    rs.close();
            }
            catch(SQLException e1)
            {
                logger.error("Error en ejecutar funci\363n:" + functionName + ", al cerrar Resultset");
            }
            try
            {
                if(cstmt != null)
                    cstmt.close();
            }
            catch(SQLException e2)
            {
                logger.error("Error en ejecutar funci\363n:" + functionName + ", al cerrar CallableStatement");
            }
        }
        return outPutList;
    }

    public static ArrayList getArrayList(Connection conn, String functionName, String paramsInputCollection[])
        throws QueryException
    {
        CallableStatement cstmt = null;
        ResultSet rs = null;
        int numParamsInput = paramsInputCollection.length;
        ArrayList outPutList = new ArrayList();
        ParameterCollection parameterCollection = new ParameterCollection();
        for(int i = 0; i < paramsInputCollection.length; i++)
            parameterCollection.addInput(paramsInputCollection[i]);

        try
        {
            cstmt = conn.prepareCall(CommandUtil.getFunctionName(functionName, numParamsInput));
            cstmt.registerOutParameter(1, -10);
            for(int i = 1; i <= numParamsInput; i++)
            {
                Parameter parameter = (Parameter)parameterCollection.get(i - 1);
                CommandUtil.setParameter(cstmt, i + 1, parameter);

            }

            cstmt.execute();
            rs = (ResultSet)cstmt.getObject(1);
            outPutList = CommandUtil.getVOList(rs);
        }
        catch(SQLException e)
        {
            logger.error(e.getMessage(), e);
            throw new QueryException(e.getMessage(), e);
        }
        catch(Exception e)
        {
            logger.error(e.getMessage(), e);
            throw new QueryException(e.getMessage(), e);
        }
        finally
        {
            try
            {
                if(rs != null)
                    rs.close();
            }
            catch(SQLException e1)
            {
                logger.error("Error en ejecutar funci\363n:" + functionName + ", al cerrar Resultset");
            }
            try
            {
                if(cstmt != null)
                    cstmt.close();
            }
            catch(SQLException e2)
            {
                logger.error("Error en ejecutar funci\363n:" + functionName + ", al cerrar CallableStatement");
            }
        }
        return outPutList;
    }

    public static String getString(Connection conn, String functionName, ParameterCollection paramsInputCollection)
        throws QueryException
    {
        return (String)CommandUtil.executeFunction(conn, functionName, paramsInputCollection, 12);
    }

    public static String getString(Connection conn, String functionName, String paramsInputCollection[])
        throws QueryException
    {
        ParameterCollection parameterCollection = new ParameterCollection();
        for(int i = 0; i < paramsInputCollection.length; i++)
            parameterCollection.addInput(paramsInputCollection[i]);


        return (String)CommandUtil.executeFunction(conn, functionName, parameterCollection, 12);
    }

    public static Object getObject(Connection conn, String functionName, String paramsInputCollection[], int dataType)
        throws QueryException
    {
        ParameterCollection parameterCollection = new ParameterCollection();
        for(int i = 0; i < paramsInputCollection.length; i++)
            parameterCollection.addInput(paramsInputCollection[i]);

        return CommandUtil.executeFunction(conn, functionName, parameterCollection, dataType);
    }

    public static Object getObject(Connection conn, String functionName, String paramsInputCollection[])
        throws QueryException
    {
        ParameterCollection parameterCollection = new ParameterCollection();
        for(int i = 0; i < paramsInputCollection.length; i++)
            parameterCollection.addInput(paramsInputCollection[i]);

        return CommandUtil.executeFunction(conn, functionName, parameterCollection, 12);
    }


}
