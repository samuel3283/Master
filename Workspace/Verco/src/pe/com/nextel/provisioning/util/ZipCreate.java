package pe.com.nextel.provisioning.util;
import java.io.*;
import java.util.zip.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
public class ZipCreate{
private static final Log log = LogFactory.getLog(ZipCreate.class);

public static void ZipFile(File file,String fileOut) throws IOException {
	byte[] b = new byte[512];	
	log.info("Comprimiendo el archivo="+fileOut);
	FileOutputStream out = new FileOutputStream(fileOut);
	ZipOutputStream zout = new ZipOutputStream(out);
	InputStream in = new FileInputStream(file);
	ZipEntry e = new ZipEntry(file.getName());
	zout.putNextEntry(e);
	int len = 0;
	while ((len = in.read(b)) != -1) {
	zout.write(b, 0, len);
	}
	zout.closeEntry();
	zout.close();
}

public void doZip (String zipfile, String destDir) { 

File f = new File(zipfile);
	if(!f.exists())
	{
		System.exit(0);
	}	
	
	byte[] buffer = new byte[18024];
	try{
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(destDir));
		out.setLevel(Deflater.BEST_COMPRESSION);
		FileInputStream in = new FileInputStream(zipfile);
		out.putNextEntry(new ZipEntry(zipfile));
		int len;
		while ((len = in.read(buffer)) > 0){
			out.write(buffer, 0, len);
		}
		out.closeEntry();
		in.close();
		out.close();
	}
	catch (IllegalArgumentException iae){
		iae.printStackTrace();
		System.exit(0);
	}
	catch (FileNotFoundException fnfe){
		fnfe.printStackTrace();
		System.exit(0);
	}
	catch (IOException ioe){
		ioe.printStackTrace();
		System.exit(0);
	}
	}
}