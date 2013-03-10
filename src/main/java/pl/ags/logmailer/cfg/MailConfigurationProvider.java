package pl.ags.logmailer.cfg;

import java.util.Properties;

/**
 * Created by ags on 03/07/13 at 21:09
 */
class MailConfigurationProvider
{
  public static final String SERVER = "smtp.server";
  public static final String USERNAME = "smtp.username";
  public static final String PASSWORD = "smtp.password";

  public static final String RECIPIENT = "mail.to";
  public static final String SENDER = "mail.from";
  public static final String SUBJECT = "mail.subject";

  public MailingConfig get(Properties properties)
  {
    String username = properties.getProperty(USERNAME, "");
    String password = properties.getProperty(PASSWORD, "");
    String server = properties.getProperty(SERVER, "");
    String from = properties.getProperty(SENDER, "");
    String to = properties.getProperty(RECIPIENT, "");
    String subject =properties.getProperty(SUBJECT, "");

    return new MailingConfig(username, password, server, from, to, subject);
  }
}
