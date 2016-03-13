package pe.com.nextel.provisioning.framework.util.net;

import java.util.*;
import java.io.*;

import com.jcraft.jsch.*;
import com.jcraft.jsch.ChannelSftp.*;
import javax.swing.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pe.com.nextel.provisioning.framework.exception.NetException;

/**
 * SFTP wrapper for JSch.
 * 
 * 
 * 
 * @author 
 */
public class SFTP {
  
  private JSch jsch;
  private Session session;
  private Channel channel;
  private ChannelSftp channelSftp;
  //private WordProcessor myWP;
  private static final Log log;

  static {
    log = LogFactory.getLog(SFTP.class);
  }
  
  /**
   * Constructor of this class
   * 
   * @param  wp 
   *       Reference to a running WordProcessor.
   */
  public SFTP() {
    jsch = new JSch();
  }
  
  /**
   * Method used to connect to server.
   * 
   * @param c ConnectDialog
   *              A connect dialog collects the information for log-in. 
 * @throws JSchException 
   */
  public void connect(String username, String password, String host, String remotepath, int puerto) throws NetException {
    // Attempt to connect.
	  try {
      session = jsch.getSession( username, host, puerto );
      session.setPassword(password);
      Properties prop = new Properties();
      prop.put("StrictHostKeyChecking", "no");
      session.setConfig(prop);
      session.connect();
      channel = session.openChannel( "sftp" );
      channel.connect();
      channelSftp = ( ChannelSftp ) channel;
      this.changeDirectory(remotepath);
	  } catch(Exception e) {
	    
	    throw new NetException(e.getMessage(), e);
	  }
	  
	  
  }
  
  /**
   * Saves an outputstream to a remote file.
   * 
   * @param os 
   *        Outputstream for saving.
   * @param filename 
   *          Remote filename. 
   */
  public void save( OutputStream os, String filename ) {
    try {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      out = (ByteArrayOutputStream) os;
      InputStream is = new ByteArrayInputStream(out.toByteArray());
      channelSftp.put(is, filename );     
      
      } catch( Exception e ){
        //myWP.errorMsg( e.toString() );
      }
  }
  
  /**
   * Saves an output stream to a remote file, BUT
   * the file MIGHT NOT EXIST on the server, if 
   * file exists, then alert message will pop-up. 
   * 
   * @param os 
   *        Outputstream for saving.
   * @param filename 
   *          Remote filename.
   */
  public void saveAsNew( OutputStream os, String filename ) {
    if ( exists( filename ) ) {
      /*
      if ( !myWP.okayCancel( "File already exists.  Overwrite?" ) ) 
        return;
      */
    }
    save( os, filename );
  }

  /**
   * Loads remote file into an input stream.
   * 
   * @param filename   
   *          Name of file to load and return.
 * @throws SftpException 
 * @throws IOException 
   */
  public void load( String filename, String pathlocal ) throws FileNotFoundException, IOException {
    InputStream inputStream = null;
      try  {
  	  inputStream = channelSftp.get(filename);
      File f=new File(pathlocal);
      OutputStream out=new FileOutputStream(f);
      byte buf[]=new byte[1024];
      int len;
      while((len=inputStream.read(buf))>0)
      out.write(buf,0,len);
      out.close();
      inputStream.close();
      log.info("\nFile is created...");
	  } catch (SftpException e) {
		  log.info(e.getMessage());
		  //throw new IOException(e.getMessage(), e);
	  } catch(FileNotFoundException e) {
		  log.info(e.getMessage());
		  //throw new IOException(e.getMessage(), e);
	  } catch(IOException e) {
	  log.info(e.getMessage());
	  //throw new IOException(e.getMessage(), e);
	  }
	  
  }
  
  /**
   * Change directory.
   * 
   * @param path   
   *        New directory.
   */
  public void changeDirectory( String path ) {
    try {
      channelSftp.cd( path );
    } catch( Exception e ){
      //myWP.errorMsg( e.toString() );
    }
  }
  
  /**
   * Get the current path.
   * 
   * @return full remote path
   */
  public String getPath() {
    String path="";
    try {
      path = channelSftp.pwd();
    } catch (Exception e) {
      
    }
    return path;
  }
  
  /**
   * Check if a file exists.<br>
   * WARNING: this is a slow function!  
   * Use sparingly.
   * 
   * @param filename name of the file to check
   * @return true if the file exists, otherwise
   * false.
   */
  public boolean exists( String filename ) {
    Vector<String> list = getFileList();
    for( String f : list ) {
      if ( f.equals(filename)) return true;
    }
    return false;
  }

  /**
   * Disconnects from server.
   */
  public void disconnect() throws NetException {
    try {
    channelSftp.quit();
    session.disconnect();
  	} catch(Exception e) {
  		log.info(":"+e.getMessage());
	  }
  }

  /**
   * True if connection to a server is constructed.
   * 
   * @return true if connected to server, false otherwise.
   */
  public boolean isConnected() {
    return session == null ? false : session.isConnected();
  }
  
  /**
   * Get the file lists from the current account on the server
   * 
   * @return list of files on the server.
   */
  public Vector<String> getFileList() {
    return getFileList( null );
  }

  /**
   * Method using extension to retrieve different
   * type of content on the server.
   * 
   * @param extension only return files with this extension.
   * @return list of files on the server.
   */
  public Vector<String> getFileList( String extension ) {
    Vector<String> fileList = new Vector<String>();
    String path = ".";
    
    if ( extension != null ) {
      extension = extension.toLowerCase();
    }
    
    try {
      // Get entire content listing.
      Vector ls = channelSftp.ls( path );
      if( ls != null) {
        // Iterate listing.
        for( int i = 0; i < ls.size(); i++ ) {
          LsEntry entry = (LsEntry) ls.elementAt( i );
          String filename = entry.getFilename();
          
          // Copy only non-directories (i.e. files) to 
          // our new vector.
          if ( !entry.getAttrs().isDir() ) {
            
            // Enforce extension rule.
            if ( extension != null ) {
              if ( filename.toLowerCase().endsWith(extension) ) {
                fileList.add( filename );
              }
            } else {
              fileList.add( filename );
            }
          }
        }
      }
    } catch( SftpException e ){
      //myWP.errorMsg( e.toString() );
    }
    
    // Sort file list.
    Collections.sort( fileList, String.CASE_INSENSITIVE_ORDER );
    
    return fileList;
  }
  
  /**
   * Method used to retrieve the list of directories on the server
   * 
   * @return list of directories on the server.
   */
  public Vector<String> getDirList() {
    Vector<String> dirList = new Vector<String>();
    String path = ".";
    
    try {
      // Get entire content listing.
      Vector ls = channelSftp.ls( path );
      if( ls != null) {
        // Iterate listing.
        for( int i = 0; i < ls.size(); i++ ) {
          LsEntry entry = (LsEntry) ls.elementAt( i );
          String filename = entry.getFilename();
          
          // Copy only directories to our new vector.
          if ( entry.getAttrs().isDir() ) {
            dirList.add( filename );
          }
        }
      }
    } catch( SftpException e ){
      //myWP.errorMsg( e.toString() );
    }
    
    // Sort directory list.
    Collections.sort( dirList, String.CASE_INSENSITIVE_ORDER );
    
    return dirList;
  } 
  
  /**
   * Progress Monitor for file loading and saving
   * 
   * Source: JSch examples
   *
   */
  public static class MyProgressMonitor implements SftpProgressMonitor{
      ProgressMonitor monitor;
      long count=0;
      long max=0;
      public void init(int op, String src, String dest, long max){
        this.max=max;
        monitor=new ProgressMonitor(null, 
                                    ((op==SftpProgressMonitor.PUT)? 
                                     "put" : "get")+": "+src, 
                                    "",  0, (int)max);
        count=0;
        percent=-1;
        monitor.setProgress((int)this.count);
        monitor.setMillisToDecideToPopup(1000);
      }
      private long percent=-1;
      public boolean count(long count){
        this.count+=count;

        if(percent>=this.count*100/max){ return true; }
        percent=this.count*100/max;

        monitor.setNote("Completed "+this.count+"("+percent+"%) out of "+max+".");     
        monitor.setProgress((int)this.count);

        return !(monitor.isCanceled());
      }
      public void end(){
        monitor.close();
      }
    }
}

