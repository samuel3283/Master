package pe.com.nextel.provisioning.framework.exception;

public class DAOException extends BaseException
{

    public DAOException(String message, Exception exception)
    {
        super(message, exception);
    }

    public DAOException(Class source, String propertiesFileName, Throwable cause)
    {
        super(source, propertiesFileName, cause);
    }

    public DAOException(String message)
    {
        super(message);
    }

    public DAOException(String message, Object parametros[])
    {
        super(message, parametros);
    }
}