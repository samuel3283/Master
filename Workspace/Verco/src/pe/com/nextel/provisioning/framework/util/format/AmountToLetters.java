// Decompiled by DJ v3.7.7.81 Copyright 2004 Atanas Neshkov  Date: 16/07/2008 05:03:48 p.m.
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   AmountToLetters.java

package pe.com.nextel.provisioning.framework.util.format;


public class AmountToLetters
{

    public AmountToLetters()
    {
    }

    public static void setRenglones(int xrenglones[])
    {
        renglones = xrenglones;
    }

    public static void setGenero_unidad(int xgenero_unidad)
    {
        genero_unidad = xgenero_unidad;
    }

    public static void setCantidad_decimales(int xcant_decimales)
    {
        cant_decimales = xcant_decimales;
    }

    public static void setPrefijo_inicio(String xprefijo_inicio)
    {
        prefijo_inicio = xprefijo_inicio;
    }

    public static void setSufijo_final(String xsufijo_final)
    {
        sufijo_final = xsufijo_final;
    }

    public static void setSufijo_enteros(String xsufijo_enteros)
    {
        sufijo_enteros = xsufijo_enteros;
    }

    public static void setPrefijo_decimales(String xprefijo_decimales)
    {
        prefijo_decimales = xprefijo_decimales;
    }

    public static void setSufijo_decimales(String xsufijo_decimales)
    {
        sufijo_decimales = xsufijo_decimales;
    }

    public static void setCaracter_proteccion(char xcaracter_proteccion)
    {
        caracter_proteccion = xcaracter_proteccion;
    }

    public static void setTraduce_decimales(boolean xtraduce_decimales)
    {
        traduce_decimales = xtraduce_decimales;
    }

    public static void setPalabra_cero(String xpalabra_cero)
    {
        palabra_cero = xpalabra_cero;
    }

    
    private static String[] getTexto(double importe, int qdecimales)
    {
        decimales = qdecimales;
        divisor = 1000000000000D;
        haymillones = 0;
        if(cant_decimales > 3)
            traduce_decimales = false;
        ajuste = 10;
        for(int i = 1; i < cant_decimales; i++)
            ajuste *= 10;

        if(cant_decimales == 0)
            ajuste = 0;
        datos = new char[renglones.length][renglones[0]];
        for(int x = 0; x < renglones.length; x++)
        {
            datos[x] = new char[renglones[x]];
            for(int y = 0; y < renglones[x]; y++)
                datos[x][y] = caracter_proteccion;

        }

        xDos[0] = "U|NO";
        xDiez[0] = "VEIN|TI|UNO";
        if(genero_unidad == 2)
        {
            xDos[0] = "UN";
            xDiez[0] = "VEIN|TI|UN";
        }
        if(genero_unidad == 1)
        {
            xDos[0] = "U|NA";
            xDiez[0] = "VEIN|TI|U|NA";
        }
        renglon = posicion = 0;
        for(paso = 0; paso < 5; paso++)
        {
            xtres = importe / divisor;
            tres = (int)xtres;
            importe -= (double)tres * divisor;
            divisor /= 1000D;
            if(tres > 0)
                traducir(tres);
        }

        if(palabra_cero.length() > 0 && renglon == 0 && posicion == 0)
        {
            if(prefijo_inicio.length() > 0)
                pasarTexto(prefijo_inicio);
            pasarTexto(palabra_cero);
        }
        if(sufijo_enteros.length() > 0 && (renglon > 0 || posicion > prefijo_inicio.length()))
            pasarTexto(sufijo_enteros);
        tres = (int)(importe * (double)ajuste);
        if(decimales > 0)
            tres = decimales;
        if(tres > 0 && prefijo_decimales.length() > 0 && (renglon != 0 || posicion != 0))
            pasarTexto(prefijo_decimales);
        if(tres > 0 && traduce_decimales)
        {
            paso = 5;
            traducir(tres);
        }
        /*if(tres > 0 && !traduce_decimales)
            pasarTexto(tres);*/
        if(tres > 0 && sufijo_decimales.length() > 0)
            pasarTexto(sufijo_decimales);
        if(sufijo_final.length() > 0)
            pasarTexto(sufijo_final);
        String texto[] = new String[datos.length];
        for(int i = 0; i < datos.length; i++)
            texto[i] = new String(datos[i]);

        return texto;
    }

    private static void iniciarSilaba()
    {
        posicion_corte = posicion;
        silaba = 0;
    }

    private static void paseCaracter(char caracter)
    {
        datos[renglon][posicion] = caracter;
        silaba++;
        posicion++;
    }

    private static void sumeRenglon()
    {
        renglon++;
        posicion = 0;
        iniciarSilaba();
    }

    private static void pasarTexto(String palabra)
    {
        char desglose[] = palabra.toCharArray();
        if(posicion > 0 && posicion < renglones[renglon] - 1 && desglose[0] != '|')
        {
            datos[renglon][posicion] = ' ';
            posicion++;
        } else
        if(posicion > 0 && posicion == renglones[renglon] - 1 && desglose[0] != '|')
        {
            datos[renglon][posicion] = ' ';
            sumeRenglon();
        }
        iniciarSilaba();
        for(int i = 0; i < desglose.length; i++)
            if(desglose[i] == '|' && i > 0 && posicion == renglones[renglon] - 1)
            {
                datos[renglon][posicion] = '-';
                sumeRenglon();
            } else
            if(desglose[i] == '|' && i > 0 && posicion < renglones[renglon] - 1)
                iniciarSilaba();
            else
            if(desglose[i] != '|' && posicion < renglones[renglon] - 1)
                paseCaracter(desglose[i]);
            else
            if(desglose[i] != '|' && posicion == renglones[renglon] - 1 && i == desglose.length - 1)
            {
                paseCaracter(desglose[i]);
                sumeRenglon();
            } else
            if(desglose[i] != '|' && posicion == renglones[renglon] - 1 && i < desglose.length - 1)
            {
                posicion = posicion_corte;
                datos[renglon][posicion] = '-';
                posicion++;
                if(posicion < renglones[renglon])
                    for(; posicion < renglones[renglon]; posicion++)
                        datos[renglon][posicion] = '-';

                int xsilaba = silaba;
                sumeRenglon();
                for(int z = i - xsilaba; z < i + 1; z++)
                {
                    datos[renglon][posicion] = desglose[z];
                    posicion++;
                }

            }

    }

    private static void traducir(int mil)
    {
        if(renglon == 0 && posicion == 0 && prefijo_inicio.length() > 0)
            pasarTexto(prefijo_inicio);
        uno = mil / 100;
        dos = mil - uno * 100;
        decena = dos / 10;
        unidad = dos - decena * 10;
        if(mil == 100)
            pasarTexto(xUno[0]);
        else
        if(uno > 0)
            pasarTexto(xUno[uno]);
        if(dos > 1 && dos < 30 && (paso != 4 || dos != 21))
            pasarTexto(xDos[dos]);
        if(paso == 4 && dos == 21)
            pasarTexto(xDiez[0]);
        if(paso == 4 && dos == 1)
            pasarTexto(xDos[0]);
        if(dos > 29)
        {
            pasarTexto(xDiez[decena]);
            if(unidad > 0)
            {
                pasarTexto(xEsp[0]);
                if(paso != 4 || unidad != 1)
                    pasarTexto(xDos[unidad]);
                if(paso == 4 && unidad == 1)
                    pasarTexto(xDos[0]);
            }
        }
        if(paso == 0 && mil == 1)
            pasarTexto(xEsp[4]);
        if(paso == 0 && mil > 1)
            pasarTexto(xEsp[5]);
        if(paso == 2 && mil > 1 || haymillones == 1)
        {
            pasarTexto(xEsp[3]);
            haymillones = 0;
        }
        if(paso == 1 && mil > 0)
        {
            pasarTexto(xEsp[1]);
            haymillones = 1;
        }
        if(paso == 2 && mil == 1 && haymillones == 0)
            pasarTexto(xEsp[2]);
        if(paso == 3 && mil > 0)
            pasarTexto(xEsp[1]);
    }

    public static String[] getTexto(String cantidad)
    {
        if(cantidad.indexOf('.') < 0)
            return getTexto(Double.parseDouble(cantidad), 0);
        String parte_entera = cantidad.substring(0, cantidad.indexOf('.')) + ".0";
        cantidad = cantidad.substring(cantidad.indexOf('.') + 1);
        if(cantidad.length() > cant_decimales)
            cantidad = cantidad.substring(0, cant_decimales);
        if(cantidad.length() < cant_decimales)
        {
            for(int i = cantidad.length(); i < cant_decimales; i++)
                cantidad = cantidad + "0";

        }
        return getTexto(Double.parseDouble(parte_entera), Integer.parseInt(cantidad));
    }

    public static String[] getTexto(double cantidad)
    {
        return getTexto(cantidad, 0);
    }

    private static String xDos[] = {
        "U|NO", "UN", "DOS", "TRES", "CUA|TRO", "CIN|CO", "SEIS", "SIE|TE", "O|CHO", "NUE|VE", 
        "DIEZ", "ON|CE", "DO|CE", "TRE|CE", "CA|TOR|CE", "QUIN|CE", "DIE|CI|SEIS", "DIE|CI|SIE|TE", "DIE|CI|O|CHO", "DIE|CI|NUE|VE", 
        "VEIN|TE", "VEIN|TI|UN", "VEIN|TI|DOS", "VEIN|TI|TRES", "VEIIN|TI|CUA|TRO", "VEIN|TI|CIN|CO", "VEIN|TI|SEIS", "VEINTI|SIE|TE", "VEIN|TI|O|CHO", "VEIN|TI|NUE|VE"
    };
    private static String xUno[] = {
        "CIEN", "CIEN|TO", "DOS|CIEN|TOS", "TRES|CIEN|TOS", "CUA|TRO|CIEN|TOS", "QUI|NIEN|TOS", "SEIS|CIEN|TOS", "SE|TE|CIEN|TOS", "O|CHO|CIEN|TOS", "NO|VE|CIEN|TOS"
    };
    private static String xDiez[] = {
        "VEIN|TI|U|NO", "", "", "TREIN|TA", "CUA|REN|TA", "CIN|CUEN|TA", "SE|SEN|TA", "SE|TEN|TA", "O|CHEN|TA", "NO|VEN|TA"
    };
    private static String xEsp[] = {
        "Y", "MIL", "MI|LLON", "MI|LLO|NES", "BI|LLON", "BI|LLO|NES"
    };
    private static String prefijo_inicio = "";
    private static String sufijo_enteros = "";
    private static String prefijo_decimales = "CON";
    private static String sufijo_decimales = "|/100";
    private static String sufijo_final = "";
    private static String palabra_cero = "CE|RO";
    private static char caracter_proteccion = '*';
    private static int renglones[] = {
        300
    };
    private static char datos[][];
    private static double importe;
    private static double divisor;
    private static double xtres;
    private static double cantidad;
    private static int tres;
    private static int dos;
    private static int uno;
    private static int paso;
    private static int decena;
    private static int unidad;
    private static int haymillones;
    private static int cant_decimales = 2;
    private static int genero_unidad = 0;
    private static int renglon;
    private static int ajuste;
    private static int posicion;
    private static int posicion_corte;
    private static int silaba;
    private static int decimales;
    private static boolean traduce_decimales = false;

}