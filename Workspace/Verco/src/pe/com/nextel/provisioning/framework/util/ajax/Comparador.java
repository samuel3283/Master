// Decompiled by DJ v3.7.7.81 Copyright 2004 Atanas Neshkov  Date: 16/07/2008 05:01:06 p.m.
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Comparador.java

package pe.com.nextel.provisioning.framework.util.ajax;

import java.math.BigDecimal;
import java.util.Comparator;

import org.apache.commons.beanutils.PropertyUtils;

public class Comparador
    implements Comparator
{

    public Comparador(String columna, boolean ascendente)
    {
        this.ascendente = ascendente;
        this.columna = columna;
    }

    public int compare(Object o1, Object o2)
    {
        int valor = 0;
        Object valor1 = null;
        Object valor2 = null;
        try
        {
            valor1 = PropertyUtils.getSimpleProperty(o1, columna);
            valor2 = PropertyUtils.getSimpleProperty(o2, columna);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        if(valor1 instanceof BigDecimal)
        {
            BigDecimal valorDecimal1 = (BigDecimal)valor1;
            if(valor2 != null)
            {
                BigDecimal valorDecimal2 = new BigDecimal(valor2.toString());
                valor = valorDecimal1.compareTo(valorDecimal2);
            } else
            {
                valor = valorDecimal1.compareTo(new BigDecimal(0.0D));
            }
        } else
        {
            valor = valor1.toString().compareTo(valor2.toString());
        }
        if(ascendente)
            return valor;
        else
            return -1 * valor;
    }

    public boolean equals(Object o)
    {
        return this == o;
    }

    private boolean ascendente;
    private String columna;
}