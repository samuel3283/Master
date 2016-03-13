// Decompiled by DJ v3.7.7.81 Copyright 2004 Atanas Neshkov  Date: 16/07/2008 05:05:02 p.m.
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   StringFormatter.java

package pe.com.nextel.provisioning.framework.util.format;

import org.apache.log4j.Logger;

import pe.com.nextel.provisioning.framework.exception.FormatException;
import pe.com.nextel.provisioning.framework.util.common.Utilities;

public final class StringFormatter
{
    private static Logger logger;

    static 
    {
        logger = Logger.getLogger(pe.com.nextel.provisioning.framework.util.format.StringFormatter.class.getName());
    }
    private StringFormatter()
    {
    }

    public static String replaceQuotes(String str)
    {
        if(Utilities.isNull(str))
            return "";
        String result = "";
        for(int k = 0; k < str.length(); k++)
        {
            String aux = str.substring(k, k + 1);
            if(aux.equals("'"))
                result = result + "''";
            else
            if(aux.equals("\""))
                result = result + "\\\"";
            else
                result = result + aux;
        }

        return result;
    }

    public static String trimString(String str)
    {
        return str != null ? str.trim() : null;
    }

    public static String nullXEmptyString(String str)
    {
        return str != null ? str : "";
    }

    public static String completeString(String initialString, String elementFill, int finalLong, char sideFill)
        throws FormatException
    {
        if(initialString == null)
            return "";
        if(initialString.trim().length() > finalLong)
            return initialString.trim().substring(0, finalLong);
        StringBuffer wCadena = new StringBuffer();
        if(sideFill == 'D')
        {
            wCadena.append(initialString.trim());
            wCadena.append(repeatStr(elementFill, finalLong - initialString.trim().length()));
            return wCadena.toString().substring(0, finalLong);
        } else
        {
            wCadena.append(repeatStr(elementFill, finalLong - initialString.trim().length()));
            wCadena.append(initialString.trim());
            return wCadena.toString().substring(0, finalLong);
        }
    }

    public static String repeatStr(String fill, int numberRepeat)
    {
        StringBuffer strBuffer = new StringBuffer();
        for(int k = 0; k < numberRepeat; k++)
            strBuffer.append(fill);

        return strBuffer.toString();
    }

    public static String UpperInitialLetter(String str)
        throws FormatException
    {
        if(Utilities.isNull(str))
            return "";
        String maskJumpLine = "\n ,;.";
        str = str.trim();
        StringBuffer result = new StringBuffer();
        String aux = "";
        String lastCaracter = "";
        for(int k = 0; k < str.length(); k++)
        {
            aux = str.substring(k, k + 1);
            if(maskJumpLine.indexOf(lastCaracter) > -1 || k == 0)
                aux = aux.toUpperCase();
            result.append(aux);
            lastCaracter = aux;
        }

        return result.toString();
    }


}