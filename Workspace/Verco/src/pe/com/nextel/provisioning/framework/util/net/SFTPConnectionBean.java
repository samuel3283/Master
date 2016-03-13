package pe.com.nextel.provisioning.framework.util.net;


public class SFTPConnectionBean
{
  private String file;
  private String localFile;
  private String localftp;
  private String remoteftp;
  private String hostname;
  private String user;
  private String pass;

  public String getLocalFile()
  {
    return this.localFile;
  }

  public void setLocalFile(String localFile) {
    this.localFile = localFile;
  }

  public String getFile() {
    return this.file;
  }

  public void setFile(String file) {
    this.file = file;
  }

  public String getLocalftp() {
    return this.localftp;
  }

  public void setLocalftp(String localftp) {
    this.localftp = localftp;
  }

  public String getHostname() {
    return this.hostname;
  }

  public void setHostname(String hostname) {
    this.hostname = hostname;
  }

  public String getUser() {
    return this.user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getPass() {
    return this.pass;
  }

  public void setPass(String pass) {
    this.pass = pass;
  }

  public String getRemoteftp() {
    return this.remoteftp;
  }

  public void setRemoteftp(String remoteftp) {
    this.remoteftp = remoteftp;
  }
}