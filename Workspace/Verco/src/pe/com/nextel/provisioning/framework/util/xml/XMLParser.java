// Decompiled by DJ v3.7.7.81 Copyright 2004 Atanas Neshkov  Date: 16/07/2008 05:07:26 p.m.
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   XMLParser.java

package pe.com.nextel.provisioning.framework.util.xml;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class XMLParser
{

    public XMLParser()
    {
    }

    public static String parseaResulSet(ResultSet result)
    {
        StringBuffer bufferXML = new StringBuffer();
        try
        {
            ResultSetMetaData rmd = result.getMetaData();
            String columns[] = new String[rmd.getColumnCount()];
            for(int k = 1; k < rmd.getColumnCount(); k++)
            {
                columns[k] = rmd.getColumnName(k);
                System.out.println("Columna:" + columns[k]);
            }

            bufferXML.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            bufferXML.append("<ROWSET>");
            int iCountRows = 1;
            for(; result.next(); bufferXML.append("</ROW>"))
            {
                bufferXML.append("<ROW num=\"").append(iCountRows).append("\">");
                for(int i = 1; i < columns.length; i++)
                {
                    bufferXML.append("<").append(columns[i]).append(">");
                    bufferXML.append(result.getString(i));
                    bufferXML.append("</").append(columns[i]).append(">");
                }

                iCountRows++;
            }

            bufferXML.append("</ROWSET>");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        System.out.println(bufferXML.toString());
        return bufferXML.toString();
    }
}