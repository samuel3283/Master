// Decompiled by DJ v3.7.7.81 Copyright 2004 Atanas Neshkov  Date: 16/07/2008 05:04:44 p.m.
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   NumberFormatter.java

package pe.com.nextel.provisioning.framework.util.format;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import org.apache.log4j.Logger;

import pe.com.nextel.provisioning.framework.exception.FormatException;
import pe.com.nextel.provisioning.framework.util.common.Utilities;

public class NumberFormatter
{

    private static Logger logger;
    private static final String decimalMask = "###,###.00";
    private static final DecimalFormatSymbols symbols;

    static 
    {
        logger = Logger.getLogger(pe.com.nextel.provisioning.framework.util.format.NumberFormatter.class.getName());
        symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        symbols.setGroupingSeparator(',');
    }
    public NumberFormatter()
    {
    }

    public static String decimalformatter(double number, String mask, int numberDecimals)
        throws FormatException
    {
        DecimalFormat decimalFormat = new DecimalFormat(mask, symbols);
        if(numberDecimals > -1)
        {
            decimalFormat.setMaximumFractionDigits(numberDecimals);
            decimalFormat.setMinimumFractionDigits(numberDecimals);
        }
        return decimalFormat.format(number);
    }

    public static String decimalformatter(double number, int numberDecimals)
        throws FormatException
    {
        return decimalformatter(number, "###,###.00", numberDecimals);
    }

    public static String decimalformatter(double number)
        throws FormatException
    {
        return decimalformatter(number, -1);
    }

    public static String decimalformatter(double number, String mask)
        throws FormatException
    {
        return decimalformatter(number, mask, -1);
    }

    public static String decimalformatter(BigDecimal bigDecimal, String mask, int numberDecimals)
        throws FormatException
    {
        String result = "0.00";
        if(!Utilities.isNull(bigDecimal))
        {
            DecimalFormat decimalFormat = new DecimalFormat(mask, symbols);
            if(numberDecimals > -1)
            {
                decimalFormat.setMaximumFractionDigits(numberDecimals);
                decimalFormat.setMinimumFractionDigits(numberDecimals);
            }
            result = decimalFormat.format(bigDecimal);
        }
        return result;
    }

    public static String decimalformatter(BigDecimal bigDecimal, int numberDecimals)
        throws FormatException
    {
        return decimalformatter(bigDecimal, "###,###.00", 0);
    }

    public static String decimalformatter(BigDecimal bigDecimal)
        throws FormatException
    {
        return decimalformatter(bigDecimal, "###,###.00", 0);
    }

    public static double toPrimitiveZero(Double wrapper)
    {
        if(wrapper == null)
            return 0.0D;
        else
            return wrapper.doubleValue();
    }

    public static int toPrimitiveZero(Integer wrapper)
    {
        if(wrapper == null)
            return 0;
        else
            return wrapper.intValue();
    }

    public static long toPrimitiveZero(Long wrapper)
    {
        if(wrapper == null)
            return 0L;
        else
            return wrapper.longValue();
    }

    public static boolean isInteger(String str)
        throws FormatException
    {
        boolean ret = true;
        String val = "";
        String maskValid = "0123456789";
        int sw = 0;
        for(int i = 0; i < str.length(); i++)
        {
            val = str.substring(i, i + 1);
            if(maskValid.indexOf(val) >= 0)
                continue;
            ret = false;
            break;
        }

        return ret;
    }

    public static boolean isDouble(String number)
        throws FormatException
    {
        try
        {
            Double.parseDouble(number);
            return true;
        }
        catch(NumberFormatException e)
        {
            logger.debug("EL n\372mero ingresado no es double");
            throw new FormatException(e.getMessage());
        }
    }

}