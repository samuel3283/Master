// Decompiled by DJ v3.7.7.81 Copyright 2004 Atanas Neshkov  Date: 16/07/2008 05:01:29 p.m.
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Lista.java

package pe.com.nextel.provisioning.framework.util.ajax;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

// Referenced classes of package com.mdp.framework.util.ajax:
//            Comparador

public class Lista
{

    public Lista()
    {
    }

    public static Collection ordenarLista(HttpServletRequest request, String nombrelista, String columna)
        throws Exception
    {
        Collection lista = null;
        Object objLista = request.getSession().getAttribute(nombrelista);
        Object objColumnas = request.getSession().getAttribute("lista.columnas");
        boolean ascendente = true;
        Hashtable hashColumnas = null;
        if(objColumnas == null)
        {
            hashColumnas = new Hashtable();
            hashColumnas.put(columna, "false");
        } else
        {
            hashColumnas = (Hashtable)objColumnas;
            Object objColumna = hashColumnas.get(columna);
            if(objColumna == null)
            {
                hashColumnas.put(columna, "false");
            } else
            {
                String valor = objColumna.toString();
                if("false".equals(valor))
                {
                    hashColumnas.put(columna, "true");
                    ascendente = false;
                } else
                {
                    hashColumnas.put(columna, "false");
                    ascendente = true;
                }
            }
        }
        request.getSession().setAttribute("lista.columnas", hashColumnas);
        if(objLista != null)
        {
            lista = (Collection)objLista;
            Collections.sort((ArrayList)lista, new Comparador(columna, ascendente));
            request.getSession().setAttribute(nombrelista, lista);
        }
        return lista;
    }
}