package pe.com.nextel.provisioning.framework.util.net;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.vfs.FileObject;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemOptions;
import org.apache.commons.vfs.impl.DefaultFileSystemManager;
import org.apache.commons.vfs.impl.StandardFileSystemManager;
import org.apache.commons.vfs.provider.sftp.SftpFileSystemConfigBuilder;

import com.sshtools.j2ssh.SftpClient;
import com.sshtools.j2ssh.SshClient;
import com.sshtools.j2ssh.authentication.KBIAuthenticationClient;
import com.sshtools.j2ssh.authentication.KBIPrompt;
import com.sshtools.j2ssh.authentication.KBIRequestHandler;
import com.sshtools.j2ssh.authentication.PasswordAuthenticationClient;
import com.sshtools.j2ssh.transport.HostKeyVerification;
import com.sshtools.j2ssh.transport.publickey.SshPublicKey;


public class SFTPUtil {
  private static final Log log = LogFactory.getLog(SFTPUtil.class);
  //private static LogUtil log = LogUtil.getLog(SFTPUtil.class);
  private static DefaultFileSystemManager fsManager;
  private static FileSystemOptions fsOptions;
  private FileObject fileObject;
  private boolean isCompressed;
  private static String password = "";

  public SFTPUtil(String host, String user, String password, String remotePath, boolean isCompressed) throws IOException
  {
    fsOptions = new FileSystemOptions();
    SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(fsOptions, "no");

    StandardFileSystemManager mgr = new StandardFileSystemManager();
    try {
      mgr.init();
    } catch (FileSystemException e) {
      log.info("No se puede inicializar el thread-local FileSystemManager.");
    }
    fsManager = mgr;

    this.isCompressed = isCompressed;

    String uri = "sftp://" + user + ":" + password + "@" + host + "/" + remotePath;
    log.info("Starting SFTP connection to: " + uri);
    this.fileObject = fsManager.resolveFile(uri, fsOptions);
  }

  public InputStream getInputStream()
    throws FileSystemException, IOException
  {
    InputStream inputStream;
    if (this.isCompressed)
    {
      inputStream = new GZIPInputStream(this.fileObject.getContent().getInputStream());
    }
    else inputStream = this.fileObject.getContent().getInputStream();

    return inputStream;
  }

  public BufferedReader getReader()
    throws FileSystemException, IOException
  {
    InputStream inputStream;
    if (this.isCompressed)
    {
      inputStream = new GZIPInputStream(this.fileObject.getContent().getInputStream());
    }
    else inputStream = this.fileObject.getContent().getInputStream();

    return new BufferedReader(new InputStreamReader(inputStream));
  }

  public BufferedWriter getWriter()
    throws FileSystemException, IOException
  {
    OutputStream outputStream;
    if (this.isCompressed)
    {
      outputStream = new GZIPOutputStream(this.fileObject.getContent().getOutputStream());
    }
    else outputStream = this.fileObject.getContent().getOutputStream();

    return new BufferedWriter(new OutputStreamWriter(outputStream));
  }

  public void close()
    throws IOException
  {
    log.info("Closing SFTP connection...");
    fsManager.closeFileSystem(this.fileObject.getFileSystem());
  }

  public static void downloadFile(String localPath, String host, String password, String remptePath, String user, boolean isCompressed)
    throws IOException
  {
    try
    {
      log.info("Descargando el fichero ");
      log.info("host: " + host);
      log.info("user: " + user);
      log.info("password: " + password);
      log.info("remptePath: " + remptePath);
      log.info("isCompressed: " + isCompressed);
      
      SFTPUtil sftp = new SFTPUtil(host, user, password, remptePath, isCompressed);
      File file = new File(localPath);
      log.info("Downloading file from SFTP to local file: " + localPath);
      BufferedWriter writer = new BufferedWriter(new FileWriter(file));
      BufferedReader reader = sftp.getReader();
      for (String line = reader.readLine(); line != null; line = reader.readLine()) {
        writer.write(line + System.getProperty("line.separator"));
      }
      writer.flush();
      log.info("Download completed.");
      writer.close();
      reader.close();
      sftp.close();
    } catch (IOException e) {
      log.info("Error al descargar el fichero " + localPath);
      e.printStackTrace();
      throw e;
    }
  }

  public static void accesoFtp(String localPath, String host, String password, String remptePath, String user, boolean isCompressed)
  throws IOException
{
  try
  {
    log.info("Verificando Acceso a PortaNode... ");
    log.info("host: " + host);
    log.info("user: " + user);
    log.info("password: " + password);
    log.info("remotePath: " + remptePath);
    //log.info("isCompressed: " + isCompressed);

    SFTPUtil sftp = new SFTPUtil(host, user, password, remptePath, isCompressed);
    //File file = new File(localPath);
    //log.info("Downloading file from SFTP to local file: " + localPath);
    //BufferedWriter writer = new BufferedWriter(new FileWriter(file));
    BufferedReader reader = sftp.getReader();
    /*for (String line = reader.readLine(); line != null; line = reader.readLine()) {
      writer.write(line + System.getProperty("line.separator"));
    }
    writer.flush();*/
    log.info("Verificacion Satisfactoria...");
    //writer.close();
    reader.close();
    sftp.close();
  } catch (IOException e) {
    log.info("Error al acceder a PortaNode...");
    e.printStackTrace();
    throw e;
  }
}

  
  public static byte[] downloadFile(String host, String password, String remptePath, String user, boolean isCompressed)
    throws IOException
  {
    SFTPUtil sftp = new SFTPUtil(host, user, password, remptePath, isCompressed);
    log.info("Downloading file from SFTP");
    InputStream is = sftp.getInputStream();
    sftp.close();
    return inputStreamToBytes(is);
  }

  public static void uploadFile(String localPath, String host, String password, String remptePath, String user, boolean isCompressed)
    throws IOException
  {
    SFTPUtil sftp = new SFTPUtil(host, user, password, remptePath, isCompressed);
    File file = new File(localPath);
    log.info("Uploading file from SFTP to local file: " + localPath);
    BufferedWriter writer = sftp.getWriter();
    BufferedReader reader = new BufferedReader(new FileReader(file));
    for (String line = reader.readLine(); line != null; line = reader.readLine()) {
      writer.write(line + System.getProperty("line.separator"));
    }
    writer.flush();
    log.info("Upload completed.");
    writer.close();
    reader.close();
    sftp.close();
  }

  public static void subirSFTP(SFTPConnectionBean bean) throws Exception {
    SshClient ssh = new SshClient();
    try {
      HostKeyVerification hkv = new HostKeyVerification() {
        public boolean verifyHost(String name, SshPublicKey key) {
          return true;
        }

      };
      ssh.connect(bean.getHostname(), hkv);
      List list = ssh.getAvailableAuthMethods(bean.getUser());
      int result = 0;

      if (list.contains("password")) {
        SshPublicKey key = ssh.getServerHostKey();
        PasswordAuthenticationClient pwd = new PasswordAuthenticationClient();
        pwd.setUsername(bean.getUser());
        pwd.setPassword(bean.getPass());
        ssh.acceptsKey(bean.getUser(), key);

        result = ssh.authenticate(pwd);

        log.info("Resultado de la conexión: " + result);
      } else if (list.contains("keyboard-interactive")) {
        KBIAuthenticationClient kbi = new KBIAuthenticationClient();
        password = bean.getPass();
        kbi.setUsername(bean.getUser());
        kbi.setKBIRequestHandler(new KBIRequestHandler() {
          public void showPrompts(String name, String instructions, KBIPrompt[] prompts) {
            if (prompts != null) {
              for (int i = 0; i < prompts.length; ++i) {
                try {
                  prompts[i].setResponse(SFTPUtil.password);
                } catch (Exception ex) {
                  prompts[i].setResponse("");
                  ex.printStackTrace();
                }
              }
            }
          }

        });
        SshPublicKey key = ssh.getServerHostKey();
        ssh.acceptsKey(bean.getUser(), key);
        result = ssh.authenticate(kbi);
        log.info("Resultado de la conexión: " + result);
      }
      if (result == 4) {
        SftpClient sftp = ssh.openSftpClient();

        sftp.lcd("/");
        sftp.put(bean.getLocalftp(), bean.getRemoteftp(), null);
        sftp.quit();
      }
      ssh.disconnect();
    }
    catch (Exception e) {
      e.printStackTrace();
      File fichero = new File(bean.getLocalftp() + File.separator + bean.getFile());
      if ((fichero.exists()) && 
        (fichero.length() == 0L)) {
        fichero.delete();
      }
      throw e;
    }
  }

  private static byte[] inputStreamToBytes(InputStream is) throws IOException
  {
    int len;
    ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
    byte[] buffer = new byte[1024];

    while ((len = is.read(buffer)) >= 0) {
      out.write(buffer, 0, len);
    }
    is.close();
    out.close();
    return out.toByteArray();
  }
/*
  public static void main(String[] args) {
    SFTPConnectionBean bean = new SFTPConnectionBean();
    String host = Parametros.getValor("ADQ_IP");
    String user = Parametros.getValorOperador("00001", "ADQ_SFTP_USER");
    String password = Parametros.getValorOperador("00001", "ADQ_SFTP_PASS");
    String name = null;

    String path = null;

    String pathR = null;

    String remoteFile = pathR + "/" + name + ".gz";
    String localFile = path + "/" + name + ".gz";

    bean.setLocalftp(localFile);
    log.debug("Directorio Local:" + localFile);
    bean.setRemoteftp(remoteFile);
    bean.setHostname(host);
    bean.setUser(user);
    bean.setPass(password);
    try {
      subirSFTP(bean);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }*/
}