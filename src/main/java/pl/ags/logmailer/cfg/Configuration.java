package pl.ags.logmailer.cfg;

/**
 * Created by ags on 03/09/13 at 22:11
 */
public class Configuration
{
  private final MailingConfig mailingConfig;
  private final SpreadsheetCoordinates spreadsheetCoordinates;
  private final MongoConfiguration mongo;

  public Configuration(MailingConfig mailingConfig, SpreadsheetCoordinates spreadsheetCoordinates, MongoConfiguration mongo)
  {
    this.mailingConfig = mailingConfig;
    this.spreadsheetCoordinates = spreadsheetCoordinates;
    this.mongo = mongo;
  }

  public MongoConfiguration getMongo()
  {
    return mongo;
  }

  public SpreadsheetCoordinates spreadsheetCoordinates()
  {
    return spreadsheetCoordinates;
  }

  public MailingConfig mailingConfig()
  {
    return mailingConfig;
  }
}
