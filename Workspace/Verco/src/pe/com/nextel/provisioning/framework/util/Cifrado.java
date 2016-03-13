package pe.com.nextel.provisioning.framework.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pe.com.nextel.provisioning.model.bo.LoginBO;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Cifrado {
	private static final Log log = LogFactory.getLog(LoginBO.class);  
  static String algorithm = "DESede";	//DESEDE/ECB/PKCS5Padding
  static Key key =null;
  static Cipher cipher =null;

  public static void cifrar() {
	try{
//		  key = KeyGenerator.getInstance(algorithm).generateKey();
		  cipher = Cipher.getInstance(algorithm);	
		  
		SecureRandom sr = new SecureRandom("seed".getBytes("UTF-8"));
		KeyGenerator kGen = KeyGenerator.getInstance(algorithm);
		kGen.init(168,sr);
		key = kGen.generateKey();
	        
	}catch( Exception e )
	{
	  e.printStackTrace();
	}

	  
  }
  
  public static void main(String[] args) throws Exception {
    String encryptionBytes = encrypt("snavarro");
    System.out.println("Recovered: " + decrypt(encryptionBytes));
  }

  public static String encrypt(String input) throws InvalidKeyException, BadPaddingException,
	  IllegalBlockSizeException, UnsupportedEncodingException {
	  log.info("[encrypt] INICIO");
	  
	  cifrar();
	  
	cipher.init(Cipher.ENCRYPT_MODE, key);
	byte[] inputBytes = input.getBytes("UTF-8");
	
	  // convert encrypted bytes into string
//	  String base64Encoded = new String(Base64.encode(bIn), "UTF-8");// for store use, so must convert to string	
    String hash = (new BASE64Encoder()).encode(cipher.doFinal(inputBytes)); // Traducción a BASE64 
    
    log.info("[encrypt] FIN");
    return hash; 	
	}  


  public static String decrypt(String encryptionBytes) throws InvalidKeyException,
  BadPaddingException, IllegalBlockSizeException, IOException {
	  log.info("[decrypt] INICIO"); 
	  cifrar();
	  
	cipher.init(Cipher.DECRYPT_MODE, key);
//	byte[] bOut = cipher.doFinal(Base64.decode(base64Encoded.getBytes("UTF-8")));
	byte[] hash = (new BASE64Decoder()).decodeBuffer(encryptionBytes); // Traducción a BASE64
	byte[] recoveredBytes = cipher.doFinal(hash);
	String recovered = new String(recoveredBytes,"UTF-8");
	
	log.info("[decrypt] FIN");
	return recovered;
	}
  
  
  
  
  
}