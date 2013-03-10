package pl.ags.logmailer;

import freemarker.template.TemplateException;
import pl.ags.logmailer.cfg.MailingConfig;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Created by ags on 03/09/13 at 16:14
 */
public class LogMailer
{
  Renderer renderer;
  private final MailingConfig configuration;
  private InternetAddress fromAddress;
  private InternetAddress[] toAddresses;

  public LogMailer(MailingConfig configuration) throws IOException, AddressException
  {
    this.configuration = configuration;
    this.renderer = new Renderer();

    String[] to = configuration.getRecipients();
    String from = configuration.getSender();
    toAddresses = new InternetAddress[to.length];
    for (int i = 0; i < to.length; i++)
    {
      String address = to[i];
      toAddresses[i] = new InternetAddress(address.trim());
    }
    fromAddress = new InternetAddress(from);
    renderer = new Renderer();
  }

  public void doSend(LogEntry entry)
  {
    Session session = getSession();

    try
    {
      MimeMultipart content = new MimeMultipart("alternative");

      MimeBodyPart html = getHtmlMimeBodyPart(entry);
      content.addBodyPart(html);

      MimeMessage message = new MimeMessage(session);
      message.setHeader("MIME-Version", "1.0");
      message.setHeader("Content-Type", content.getContentType());
      message.setHeader("X-Mailer", "My own custom mailer");
      message.setContent(content);

      message.setFrom(fromAddress);
      for (InternetAddress toAddress : toAddresses)
      {
        message.addRecipient(Message.RecipientType.TO, toAddress);
      }

      message.setSubject("Application crash log");

      Transport.send(message);
    }
    catch (MessagingException | TemplateException | IOException mex)
    {
      mex.printStackTrace();
    }
  }

  private Session getSession()
  {Properties properties = System.getProperties();
    properties.setProperty("mail.smtp.host", configuration.getServer());
    return Session.getDefaultInstance(properties);
  }

  private MimeBodyPart getHtmlMimeBodyPart(LogEntry entry) throws IOException, TemplateException, MessagingException
  {
    String htmlBody = renderHtml(entry);
    MimeBodyPart html = new MimeBodyPart();
    html.setContent(htmlBody, "text/html; charset=utf-8");
    html.setHeader("MIME-Version", "1.0");
    return html;
  }

  private MimeBodyPart getTextMimeBodyPart(LogEntry entry) throws IOException, TemplateException, MessagingException
  {
    String textBody = renderText(entry);
    MimeBodyPart text = new MimeBodyPart();
    text.setText(textBody);
    text.setHeader("MIME-Version", "1.0");
    text.setHeader("Content-Type", text.getContentType());
    return text;
  }

  private String renderHtml(LogEntry entry) throws IOException, TemplateException
  {
    return renderer.renderHtml(entry);
  }

  private String renderText(LogEntry entry) throws IOException, TemplateException
  {
    return renderer.renderText(entry);
  }
}

