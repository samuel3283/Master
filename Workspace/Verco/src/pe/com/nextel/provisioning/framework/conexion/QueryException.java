package pe.com.nextel.provisioning.framework.conexion;

import pe.com.nextel.provisioning.framework.exception.BaseException;


public class QueryException extends BaseException
{

    public QueryException(String message, Exception exception)
    {
        super(message, exception);
    }

    public QueryException(String message)
    {
        this(message, null);
    }

    public QueryException(Exception exception)
    {
        this(null, exception);
    }
}
