// Decompiled by DJ v3.7.7.81 Copyright 2004 Atanas Neshkov  Date: 16/07/2008 04:58:52 p.m.
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   EmailException.java

package pe.com.nextel.provisioning.framework.exception;


// Referenced classes of package com.mdp.framework.exception:
//            BaseException

public class EmailException extends BaseException
{

    public EmailException(String message, Exception exception)
    {
        super(message, exception);
    }

    public EmailException(String message, String params[], Exception exception)
    {
        super(message, params, exception);
    }

    public EmailException(String message, String params[])
    {
        super(message, params);
    }

    public EmailException(Exception exception)
    {
        super(exception);
    }

    public EmailException(Class source, String propertiesFileName, Throwable cause)
    {
        super(source, propertiesFileName, cause);
    }

    public EmailException(String message)
    {
        super(message);
    }
}