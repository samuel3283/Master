package pe.com.nextel.provisioning.framework.util;

import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;



public class ManagerFile {
	public ManagerFile(){}
	 private static ManagerFile single = null;
	 public ManagerFile getInstance() {
		    if (single == null)
		        single = new ManagerFile();
		    return single;
	 }
	 
	public void LevantarFile(String pathFile,String nomArchivo,String tipoFile,HttpServletResponse response) {
	    try {	      
	      System.out.println("ARCHIVO A LEVANTAR=" + pathFile);
	      File f = new File(pathFile);
	      InputStream in;
	      ServletOutputStream outp;
	      int bit;
	      response.setContentType(tipoFile);
	      nomArchivo = "attachment;filename=\"" + nomArchivo + "\"";
	      response.setHeader("Content-Disposition", nomArchivo.trim());

	      in = new FileInputStream(pathFile);
	      outp =response.getOutputStream();
	      bit = 256;

	      while ((bit) >= 0) {
	        bit = in.read();
	        outp.write(bit);
	      }

	      outp.flush();
	      outp.close();
	      in.close();
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }
	
	public void copiarFileToFile(String srFileAcopiar, String dtFileDestino){
	    try{
	      File f1 = new File(srFileAcopiar);
	      File f2 = new File(dtFileDestino);
	      InputStream in = new FileInputStream(f1);
	      OutputStream out = new FileOutputStream(f2);

	      byte[] buf = new byte[1024];
	      int len;
	      while ((len = in.read(buf)) > 0){
	        out.write(buf, 0, len);
	      }
	      in.close();
	      out.close();
	      System.out.println("Archivo Copiado="+srFileAcopiar);
	    }
	    catch(FileNotFoundException ex){
	      System.out.println(ex.getMessage() + " en el directorio especificado.");
	      System.exit(0);
	    }
	    catch(IOException e){
	      System.out.println(e.getMessage());      
	    }
	  }
	
	 public void copiarDirectorio(File srcDir, File dstDir) throws IOException { 
	        if (srcDir.isDirectory()) { 
	            if (!dstDir.exists()) { 
	                dstDir.mkdir(); 
	            } 
	             
	            String[] children = srcDir.list(); 
	            for (int i=0; i<children.length; i++) { 
	            	copiarDirectorio(new File(srcDir, children[i]), 
	                    new File(dstDir, children[i])); 
	            } 
	        } else { 
	        	copiar(srcDir, dstDir); 
	        } 
	    } 
	 
	 public void copiar(File src, File dst) throws IOException { 
	        InputStream in = new FileInputStream(src); 
	        OutputStream out = new FileOutputStream(dst); 
	         
	         
	        byte[] buf = new byte[1024]; 
	        int len; 
	        while ((len = in.read(buf)) > 0) { 
	            out.write(buf, 0, len); 
	        } 
	        in.close(); 
	        out.close(); 
	    } 

	 public void EliminarFile(File src) throws IOException {
		 if (src.exists()){
			 src.delete();
		 }
	 }
	 
	 public void EliminarDirectorio(File path) {
		    if( path.exists() ) {
		      File[] files = path.listFiles();
		      for(int i=0; i<files.length; i++) {
		         if(files[i].isDirectory()) {
		        	 EliminarDirectorio(files[i]);
		         }
		         else {
		           files[i].delete();
		         }
		      }
		    }
		    path.delete();
		  }
	
}
