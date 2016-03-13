package pe.com.nextel.provisioning.framework.exception;

public abstract class BaseException extends Exception
{

    public BaseException(String message, Exception exception)
    {
        super(message);
        params = null;
        parametros = null;
        this.exception = exception;
        this.message = message;
    }

    public BaseException(String message, String params[], Exception exception)
    {
        super(message);
        this.params = null;
        parametros = null;
        this.exception = exception;
        this.message = message;
        this.params = params;
    }

    public BaseException(String message, Object parametros[], Exception exception)
    {
        super(message);
        params = null;
        this.parametros = null;
        this.exception = exception;
        this.message = message;
        this.parametros = parametros;
    }

    public BaseException(String message, String params[])
    {
        super(message);
        this.params = null;
        parametros = null;
        this.message = message;
        this.params = params;
    }

    public BaseException(String message, Object parametros[])
    {
        super(message);
        params = null;
        this.parametros = null;
        this.message = message;
        this.parametros = parametros;
    }

    public BaseException(Exception exception)
    {
        params = null;
        parametros = null;
        this.exception = exception;
        message = exception.getMessage();
    }

    public BaseException(Class source, String propertiesFileName, Throwable cause)
    {
        params = null;
        parametros = null;
    }

    public BaseException(String message)
    {
        super(message);
        params = null;
        parametros = null;
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String[] getParams()
    {
        return params;
    }

    public Object[] getParametros()
    {
        return params;
    }

    public Exception getException()
    {
        return exception;
    }

    public void setException(Exception exception)
    {
        this.exception = exception;
    }

    private Exception exception;
    private String message;
    private String params[];
    private Object parametros[];
    private String prueba;
    private String error11;
}