// Decompiled by DJ v3.7.7.81 Copyright 2004 Atanas Neshkov  Date: 16/07/2008 05:07:51 p.m.
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   UtilZip.java

package pe.com.nextel.provisioning.framework.util.zip;

import java.io.*;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.*;
import org.apache.log4j.Logger;

import pe.com.nextel.provisioning.framework.exception.FileUtilException;
import pe.com.nextel.provisioning.framework.util.files.FileUtil;
import pe.com.nextel.provisioning.framework.util.common.Utilities;

public class UtilZip
{
    private static Logger logger;

    static 
    {
        logger = Logger.getLogger(pe.com.nextel.provisioning.framework.util.zip.UtilZip.class.getName());
    }
    public UtilZip()
    {
    }

    public static void zipea(String filename, String outFilename)
        throws FileUtilException
    {
        if(FileUtil.isDirectory(filename))
        {
            zipea(FileUtil.listDirectoryRecursive(filename, true), outFilename, filename);
        } else
        {
            String filenames[] = {
                filename
            };
            zipea(filenames, outFilename, null);
        }
    }

    private static void zipea(String filenames[], String outFilename, String nameDirectory)
        throws FileUtilException
    {
        byte buf[] = new byte[1024];
        try
        {
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outFilename));
            out.setLevel(1);
            for(int i = 0; i < filenames.length; i++)
            {
                String nameFile = filenames[i];
                if(!Utilities.isNull(nameDirectory))
                    nameFile = nameFile.substring(1 + nameDirectory.lastIndexOf(File.separator));
                out.putNextEntry(new ZipEntry(nameFile));
                if(!FileUtil.isDirectory(filenames[i]))
                {
                    FileInputStream in = new FileInputStream(filenames[i]);
                    int len;
                    while((len = in.read(buf)) > 0) 
                        out.write(buf, 0, len);
                    in.close();
                }
                out.closeEntry();
            }

            out.close();
            logger.debug("Se realiz\363 la compresi\363n con destino : " + outFilename + " con \351xito");
        }
        catch(IOException e)
        {
            logger.error(e.getMessage(), e);
            throw new FileUtilException("Ocurri\363 una excepci\363n tratando de crear el archivo: " + outFilename, e);
        }
    }

    public static void unCompress(String file, String destiny)
        throws FileUtilException
    {
        try
        {
            BufferedOutputStream dest = null;
            FileInputStream fis = new FileInputStream(file);
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
            byte data[] = new byte[1024];
            FileUtil.createDirectory(destiny);
            ZipEntry entry;
            while((entry = zis.getNextEntry()) != null) 
                if(!entry.isDirectory())
                {
                    String entryName = entry.getName();
                    String destFN = destiny + File.separator + entry.getName();
                    if(!FileUtil.isDirectory(destFN.substring(0, destFN.lastIndexOf(File.separator))))
                        FileUtil.createDirectory(destFN.substring(0, destFN.lastIndexOf(File.separator)));
                    FileOutputStream fos = new FileOutputStream(destFN);
                    dest = new BufferedOutputStream(fos, 1024);
                    int count;
                    while((count = zis.read(data, 0, 1024)) != -1) 
                        dest.write(data, 0, count);
                    dest.flush();
                    dest.close();
                } else
                {
                    System.out.println(destiny + File.separator + entry.getName());
                    FileUtil.createDirectory(destiny + File.separator + entry.getName());
                }
            zis.close();
            logger.debug("Se realiz\363 la operaci\363n de descompresi\363n del archivo: " + file + " con \351xito");
        }
        catch(Exception e)
        {
            FileUtil.deleteFile(destiny);
            logger.error(e.getMessage(), e);
            throw new FileUtilException("No se pudo completar la descompresi\363n del archivo " + file, e);
        }
    }

    public static String[] listFilesInsideZip(String file)
    {
        List listaFiles = new ArrayList();
        try
        {
            FileInputStream fis = new FileInputStream(file);
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
            DateFormat df = DateFormat.getDateTimeInstance(3, 3);
            ZipEntry entry;
            while((entry = zis.getNextEntry()) != null) 
                if(!entry.isDirectory())
                    listaFiles.add(entry.getName());
        }
        catch(Exception e)
        {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        String listNameFiles[] = new String[listaFiles.size()];
        for(int k = 0; k < listaFiles.size(); k++)
            listNameFiles[k] = (String)listaFiles.get(k);

        logger.debug("Ingresamos al m\351todo listFilesInsideZip del UtilZip");
        return listNameFiles;
    }

    public static void unCompressGz(String file, String outFilename)
    throws FileUtilException
{
    try
    {
      GZIPInputStream in = new GZIPInputStream(new FileInputStream(file));
      
      // Open the output file
      OutputStream out = new FileOutputStream(outFilename);
  
      // Transfer bytes from the compressed file to the output file
      byte[] buf = new byte[1024];
      int len;
      while ((len = in.read(buf)) > 0) {
          out.write(buf, 0, len);
      }
  
      // Close the file and stream
      in.close();
      out.close();
      if(FileUtil.existsFile(file)) {
      FileUtil.deleteFile(file);
      }
      
    } catch (IOException e) {
    }

    catch(Exception e)
    {   
        FileUtil.deleteFile(outFilename);
        logger.error(e.getMessage(), e);
        throw new FileUtilException("No se pudo completar la descompresi\363n del archivo " + file, e);
    }
}

}