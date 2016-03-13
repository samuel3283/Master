// Decompiled by DJ v3.7.7.81 Copyright 2004 Atanas Neshkov  Date: 16/07/2008 05:04:17 p.m.
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   DateFormatter.java

package pe.com.nextel.provisioning.framework.util.format;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.log4j.Logger;

import pe.com.nextel.provisioning.framework.exception.FormatException;

public class DateFormatter
{
	public static String dateFormat2 = "yyyyMMddhh24miss";
	public static String dateFormat1 = "yyyyMMdd";
	public static String dateDataBasee = "dd/MM/yyyy HH:mm:ss";
	public static String dateFormat = "dd/MM/yyyy";
    private static String dateDataBase = "yyyy'-'MM'-'dd HH:mm:ss";
    private static String timeFormat = "hh:mm aaa";
    private static final Locale locale = new Locale("PE", "es");
    private static Logger logger;

    static 
    {
        logger = Logger.getLogger(pe.com.nextel.provisioning.framework.util.format.DateFormatter.class.getName());
    }
    
    private DateFormatter()
    {
    }

    public static String getDateTime()
    {
        Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());
        return ts.toString();
    }

    public static String getDate()
    {
        return getDateTime().substring(0, 10);
    }

    public static String formatDate(Date date)
    {
        String ret = "";
        if(date != null)
            ret = (new SimpleDateFormat(dateFormat)).format(date);
        else
            logger.debug("The param date is null");
        return ret;
    }

    public static String formatDate(Date date, String mask)
        throws FormatException
    {
        String ret = "";
        if(date != null)
            ret = (new SimpleDateFormat(mask)).format(date);
        else
            logger.debug("The param date is null");
        return ret;
    }

    public static String formatTime(Date date)
    {
        String ret = "";
        if(date != null)
            ret = (new SimpleDateFormat(timeFormat)).format(date);
        else
            logger.debug("The param date is null");
        return ret;
    }

    public static String formatTime(Date date, String mask)
    {
        String ret = "";
        if(date != null)
            ret = (new SimpleDateFormat(mask)).format(date);
        else
            logger.debug("The param date is null");
        return ret;
    }

    public static Date parseDate(String date)
        throws FormatException
    {
        Date result = null;
        try
        {
            result = (new SimpleDateFormat(dateFormat)).parse(date);
            return result;
        }
        catch(ParseException e)
        {
            logger.error(e.getMessage(), e);
            throw new FormatException(e.getMessage());
        }
    }

    public static Date parseDate(String date, String format)
        throws FormatException
    {
        Date result = null;
        try
        {
            result = (new SimpleDateFormat(format)).parse(date);
            return result;
        }
        catch(ParseException e)
        {
            logger.error(e.getMessage(), e);
            throw new FormatException(e.getMessage());
        }
    }

    public static String getYear()
    {
        return getDate().substring(0, 4);
    }

    public static String getMonth()
    {
        return getDate().substring(5, 7);
    }

    public static String getDay()
    {
        return getDate().substring(8, 10);
    }

    public static String getYear(String date)
    {
        String res = "";
        if(date != null && date.length() >= 10)
            res = date.substring(6, 10);
        else
            logger.debug("The param date is null or its length not is >= 10");
        return res;
    }

    public static String getMonth(String date)
    {
        String res = "";
        if(date != null && date.length() >= 10)
            res = date.substring(3, 5);
        else
            logger.debug("The param date is null or its length not is >= 10");
        return res;
    }

    public static String getDay(String date)
        throws FormatException
    {
        String res = "";
        if(date != null && date.length() >= 10)
            res = date.substring(0, 2);
        else
            logger.debug("The param date is null or its length not is >= 10");
        return res;
    }

    public static String getLetterHeaderSpa(String city)
    {
        StringBuffer res = new StringBuffer();
        res.append(city + ", ");
        res.append((new SimpleDateFormat("EEEEE, d MMMM 'del 'yyyy", locale)).format(new Date()));
        return res.toString();
    }

    public static String getLetterHeaderEng(String city)
    {
        StringBuffer res = new StringBuffer();
        res.append(city + ", ");
        res.append((new SimpleDateFormat("EEEEE, MMMM d, yyyyy ", Locale.US)).format(new Date()));
        return res.toString();
    }

    public static String getLetterHeaderSpa(String city, Date date)
        throws FormatException
    {
        StringBuffer res = new StringBuffer();
        res.append(city + ", ");
        res.append((new SimpleDateFormat("EEEEE, d MMMM 'del 'yyyy", locale)).format(date));
        return res.toString();
    }

    public static String getLetterHeaderEng(String city, Date date)
        throws FormatException
    {
        StringBuffer res = new StringBuffer();
        res.append(city + ", ");
        res.append((new SimpleDateFormat("EEEEE, MMMM d, yyyyy ", Locale.US)).format(date));
        return res.toString();
    }

    public static Timestamp getTimestamp()
    {
        return new Timestamp((new Date()).getTime());
    }

    private static GregorianCalendar getCalendar()
    {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setLenient(false);
        return calendar;
    }

    public static Date addMonths(Date date, int months)
        throws FormatException
    {
        GregorianCalendar gCalendar = getCalendar();
        gCalendar.setTime(date);
        gCalendar.add(2, months);
        return gCalendar.getTime();
    }

    public static Date addDays(Date date, int days)
        throws FormatException
    {
        GregorianCalendar gCalendar = getCalendar();
        gCalendar.setTime(date);
        gCalendar.add(5, days);
        return gCalendar.getTime();
    }

    public static Date addYears(Date date, int years)
        throws FormatException
    {
        GregorianCalendar gCalendar = getCalendar();
        gCalendar.setTime(date);
        gCalendar.add(1, years);
        return gCalendar.getTime();
    }

    public static long changeDateToLong(Date date)
        throws FormatException
    {
        String ans = "";
        GregorianCalendar cal = new GregorianCalendar();
        try
        {
			cal.setTime(date);
			ans = "" + cal.get(1);
			if(("" + (1 + cal.get(2))).length() < 2)
				ans = "" + Integer.parseInt(ans + "0" + (1 + cal.get(2)));
			else
				ans = ans + (1 + cal.get(2));
			if(("" + cal.get(5)).length() < 2)
				ans = "" + Integer.parseInt(ans + "0" + cal.get(5));
			else
				ans = ans + cal.get(5);
        }
        catch(Throwable e)
        {
            ans = "0";
            throw new FormatException(e.getMessage());
        }
        return Long.parseLong(ans);
    }
    
    public static long changeTimeToLong(Time time)
        throws FormatException
    {
        String hour = time.toString();
        long ans = 0L;
        hour = hour.substring(0, 2) + hour.substring(3, 5) + hour.substring(6);
        ans = Long.parseLong(hour);
        return ans;
    }

    /*
    public static boolean isDateEqualOrGreater(Date startDate, Date endDate)
        throws FormatException
    {
        boolean resul = false;
        try
        {
            long date1 = changeDateToLong(startDate);
            long date2 = changeDateToLong(endDate);
            if(date1 >= date2)
                resul = true;
            return resul;
        }
        catch(Exception e)
        {
            throw new FormatException(e.getMessage());
        }
    }
*/
    public static boolean isDateGreater(Date startDate, Date endDate)
        throws FormatException
    {
		try {
			long date1 = changeDateToLong(startDate);
			long date2 = changeDateToLong(endDate);
			return date1 > date2;
		}catch(Exception e){
			throw new FormatException(e.getMessage());
		}
    }
    public static int difDays(Date startDate, Date endDate)
    {
        if(startDate != null && endDate != null)
        {
            long dateInitial = startDate.getTime();
            long dateFinal = endDate.getTime();
            long diffMillis = dateInitial - dateFinal;
            long diffDays = diffMillis / 0x5265c00L;
            return (int)diffDays;
        } else
        {
            logger.debug("Dates received: " + startDate + "-" + endDate);
            return -1;
        }
    }

    public static Date getEndDay(Date date)
        throws FormatException
    {
        Date result = null;
        if(date != null)
        {
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.set(11, calendar.getMaximum(11));
            calendar.set(12, calendar.getMaximum(12));
            calendar.set(13, calendar.getMaximum(13));
            calendar.set(14, calendar.getMaximum(14));
            result = calendar.getTime();
        }
        return result;
    }

    public static Date getStartDay(Date date)
        throws FormatException
    {
        Date result = null;
        if(date != null)
        {
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.set(11, calendar.getMinimum(11));
            calendar.set(12, calendar.getMinimum(12));
            calendar.set(13, calendar.getMinimum(13));
            calendar.set(14, calendar.getMinimum(14));
            result = calendar.getTime();
        }
        return result;
    }

    public static int getYearsOld(Date dateBirthday)
        throws FormatException
    {
        int yearsOld = 0;
        try
        {
            GregorianCalendar calendarDateBirthday = new GregorianCalendar();
            calendarDateBirthday.setTime(dateBirthday);
            GregorianCalendar calendarActualDate = new GregorianCalendar();
            int actualYear = calendarActualDate.get(1);
            int birthdayYear = calendarDateBirthday.get(1);
            int actualMonth = calendarActualDate.get(2);
            int birthdayMonth = calendarDateBirthday.get(2);
            int actualDay = calendarActualDate.get(5);
            int birthdayDay = calendarDateBirthday.get(5);
            yearsOld = actualYear - 1 - birthdayYear;
            if(actualMonth == birthdayMonth && actualDay >= birthdayDay)
                yearsOld++;
            if(actualMonth > birthdayMonth)
                yearsOld++;
        }
        catch(Exception e)
        {
            logger.debug(dateBirthday.toString(), e);
            return -1;
        }
        return yearsOld;
    }

    public static boolean validateDate(String strDate, String mask)
        throws FormatException
    {
        SimpleDateFormat sdf = new SimpleDateFormat(mask);
        sdf.setLenient(false);
        try
        {
            Date date = sdf.parse(strDate);
            return true;
        }
        catch(Exception ex)
        {
            throw new FormatException(ex.getMessage());
        }
    }

    public static String convertTimestampToString(Timestamp time)
    {
        String tOld = time.toString();
        if(tOld.length() < 21)
        {
            return "";
        } else
        {
            String tNew = tOld.substring(8, 10) + "/" + tOld.substring(5, 7) + "/" + tOld.substring(0, 4);
            return tNew;
        }
    }

    /*
    public static void main(String args[])
    {
        try
        {
            System.out.println("changeDateToLong:09/01/2004 " + changeDateToLong(parseDate("09/01/2004")));
            System.out.println("changeDateToLong:28/01/2004 " + changeDateToLong(parseDate("28/01/2004")));
        }
        catch(Exception e)
        {
            logger.debug("Hubo errores", e);
        }
    }
*/

}