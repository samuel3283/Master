// Decompiled by DJ v3.7.7.81 Copyright 2004 Atanas Neshkov  Date: 16/07/2008 05:03:27 p.m.
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   FileUtil.java

package pe.com.nextel.provisioning.framework.util.files;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import pe.com.nextel.provisioning.framework.exception.FileUtilException;

public class FileUtil
{
    private static Logger logger;
    static List listFiles = new ArrayList();
    static List stack = new ArrayList();

    static 
    {
        logger = Logger.getLogger(pe.com.nextel.provisioning.framework.util.files.FileUtil.class.getName());
    }
    
    public FileUtil()
    {
    }

    public static boolean makeFile(String lines[], String path, String fileName, boolean carReturn, boolean reWrite)
        throws FileUtilException
    {
        boolean rpta = false;
        String RETORNO_CARRO = "\r\n";
        if(!carReturn)
            RETORNO_CARRO = "";
        try
        {
            FileWriter file = new FileWriter(path.trim() + fileName.trim(), reWrite);
            for(int k = 0; k < lines.length; k++) {
            	if(k==lines.length-1)
           		file.write(lines[k]);
            	else
            	file.write(lines[k] + RETORNO_CARRO);
            }
            file.flush();
            file.close();
            rpta = true;
            logger.debug("Se cre\363 el archivo " + fileName + " con " + lines.length + " lineas.");
        }
        catch(IOException e)
        {
            logger.error(e.getMessage(), e);
            throw new FileUtilException(e);
        }
        return rpta;
    }

    public static String[] listActualDirectory(String dir, boolean withAbsolutePath)
    {
        File f = new File(dir);
        String files[] = f.list();
        if(withAbsolutePath)
        {
            for(int k = 0; k < files.length; k++)
                files[k] = f.getAbsolutePath() + File.separator + files[k];

        }
        return files;
    }

    public static synchronized String[] listDirectoryRecursive(String dir, boolean withAbsolutePath)
    {
        File f = new File(dir);
        if(f.isDirectory())
        {
            f.mkdir();
            File array[] = f.listFiles();
            for(int i = 0; i < array.length; i++)
                if(isDirectory(array[i].getAbsolutePath()))
                    stack.add(array[i].getAbsolutePath());
                else
                if(withAbsolutePath)
                    listFiles.add(new String(array[i].getAbsolutePath()));
                else
                    listFiles.add(new String(array[i].getName()));

            if(stack.size() > 0)
            {
                String colaElement = stack.get(0).toString();
                stack.remove(0);
                listDirectoryRecursive(colaElement, withAbsolutePath);
            }
        }
        Collections.sort(listFiles);
        String files[] = new String[listFiles.size()];
        files[0] = dir + File.separator;
        for(int k = 0; k < listFiles.size(); k++)
            files[k] = (String)listFiles.get(k);

        return files;
    }

    public static boolean isDirectory(String nameDirectory)
    {
        File f = new File(nameDirectory);
        return f.isDirectory();
    }

    public static boolean existsFile(String nameFile)
    {
        boolean rpta = false;
        File file = new File(nameFile);
        if(file.exists())
            rpta = true;
        return rpta;
    }

    public static void createDirectory(String path)
        throws FileUtilException
    {
        File dir = new File(path);
        if(dir.exists())
            return;
        if(dir.mkdirs())
        {
            return;
        } else
        {
            logger.debug("No se puedo crear el Directorio" + path);
            throw new FileUtilException("No se puedo crear el Directorio");
        }
    }

    public static boolean deleteFile(String fileName)
        throws FileUtilException
    {
        if(fileName == null)
            return false;
        File file = new File(fileName);
        if(file.isDirectory())
        {
            String dirListing[] = file.list();
            int len = dirListing.length;
            for(int i = 0; i < len; i++)
                if(!deleteFile(file.getAbsolutePath() + File.separator + dirListing[i]))
                    return false;

        }
        if(!file.delete())
        {
            logger.error("No se pudo eliminar : " + file.getAbsolutePath());
            throw new FileUtilException("No se pudo eliminar : " + file.getAbsolutePath());
        } else
        {
            return true;
        }
    }

    public static void copyFile(String fileName, String destino, String fileNameDestino)
        throws FileUtilException
    {
        try
        {
            InputStream is = new BufferedInputStream(new FileInputStream(fileName));
            OutputStream os = new BufferedOutputStream(new FileOutputStream(destino + fileNameDestino));
            byte buffer[] = new byte[4096];
            int readed;
            while((readed = is.read(buffer)) != -1) 
                os.write(buffer, 0, readed);
            is.close();
            os.close();
        }
        catch(FileNotFoundException e)
        {
            logger.error(e.getMessage(), e);
            throw new FileUtilException(e.getMessage());
        }
        catch(IOException e)
        {
            logger.error(e.getMessage(), e);
            throw new FileUtilException(e.getMessage());
        }
    }

    public static void copyFileGenerated(String fileName, HttpServletRequest request)
        throws Exception
    {
        if(!fileName.equals("public_nodata_500x300.png") && !fileName.equals("public_nodata_500x300.png"))
        {
            File file = new File(System.getProperty("java.io.tmpdir"), fileName);
            copyFile(file.getAbsolutePath(), "initialParameters.parameterFilesTempPath", request.getSession().getId() + fileName.substring(10));
            deleteFile(file.getAbsolutePath());
            logger.debug("La ruta que gener\363 es:" + request.getSession().getId() + fileName.substring(10));
            fileName = "pages/temp/" + request.getSession().getId() + fileName.substring(10);
        } else
        {
            fileName = "_commons/images/" + fileName;
        }
        request.setAttribute("filename", fileName);
    }

    public static void main(String args[])
    {
        File file[] = File.listRoots();
        System.out.println(file.length);
        for(int i = 0; i < file.length; i++)
            System.out.println(file[i].getPath());

    }


}