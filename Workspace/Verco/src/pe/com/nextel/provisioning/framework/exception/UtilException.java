// Decompiled by DJ v3.7.7.81 Copyright 2004 Atanas Neshkov  Date: 16/07/2008 04:55:55 p.m.
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   UtilException.java

package pe.com.nextel.provisioning.framework.exception;


// Referenced classes of package com.mdp.framework.exception:
//            BaseException

public class UtilException extends BaseException
{

    public UtilException(String message, Exception exception)
    {
        super(message, exception);
    }

    public UtilException(String message, String params[], Exception exception)
    {
        super(message, params, exception);
    }

    public UtilException(String message, String params[])
    {
        super(message, params);
    }

    public UtilException(Exception exception)
    {
        super(exception);
    }

    public UtilException(Class source, String propertiesFileName, Throwable cause)
    {
        super(source, propertiesFileName, cause);
    }

    public UtilException(String message)
    {
        super(message);
    }
}