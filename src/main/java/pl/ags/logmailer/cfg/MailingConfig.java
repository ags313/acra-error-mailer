package pl.ags.logmailer.cfg;

/**
 * Created by ags on 03/09/13 at 21:50
 */
public class MailingConfig
{
  private final String username;
  private final String password;
  private final String server;
  private final String from;
  private final String to;
  private final String subject;

  public MailingConfig(String username, String password, String server, String from, String to, String subject)
  {
    this.username = username;
    this.password = password;
    this.server = server;
    this.from = from;
    this.to = to;
    this.subject = subject;
  }

  public String getServer()
  {
    return server;
  }

  public String getUsername()
  {
    return username;
  }

  public String getPassword()
  {
    return password;
  }

  public String getRecipient()
  {
    return to;
  }

  public String getSubject()
  {
    return subject;
  }

  public String getSender()
  {
    return from;
  }
}
