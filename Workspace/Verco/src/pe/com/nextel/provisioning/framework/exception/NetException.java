// Decompiled by DJ v3.7.7.81 Copyright 2004 Atanas Neshkov  Date: 16/07/2008 04:56:48 p.m.
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   NetException.java

package pe.com.nextel.provisioning.framework.exception;


// Referenced classes of package com.mdp.framework.exception:
//            BaseException

public class NetException extends BaseException
{

    public NetException(String message, Exception exception)
    {
        super(message, exception);
    }

    public NetException(String message, String params[], Exception exception)
    {
        super(message, params, exception);
    }

    public NetException(String message, String params[])
    {
        super(message, params);
    }

    public NetException(Exception exception)
    {
        super(exception);
    }

    public NetException(Class source, String propertiesFileName, Throwable cause)
    {
        super(source, propertiesFileName, cause);
    }

    public NetException(String message)
    {
        super(message);
    }
}