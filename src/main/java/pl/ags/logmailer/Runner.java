package pl.ags.logmailer;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import pl.ags.logmailer.cfg.Configuration;
import pl.ags.logmailer.cfg.FileBasedConfigurationLoader;
import pl.ags.logmailer.cfg.MailingConfig;
import pl.ags.logmailer.cfg.SpreadsheetCoordinates;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.logging.Logger;

import javax.mail.internet.AddressException;

/**
 * Created by ags on 03/07/13 at 20:52
 */
public class Runner
{
  private static final String URL_STRING = "https://spreadsheets.google.com/feeds/spreadsheets/private/full";
  private final Logger LOG = Logger.getLogger(getClass().getName());

  LogExtractor extractor;
  MongoImporter importer;

  public static void main(String[] args) throws ServiceException, IOException, AddressException
  {
    String fileName = getFilenameFromArgs(args);
    new Runner().doRun(fileName);
  }

  private static String getFilenameFromArgs(String[] args)
  {
    String fileName;
    if (args.length == 0)
    {
      fileName = "acra.properties";
    }
    else
    {
      fileName = args[0];
    }
    return fileName;
  }

  private void doRun(String fileName) throws ServiceException, IOException, AddressException
  {
    Configuration cfg = new FileBasedConfigurationLoader().load(fileName);

    SpreadsheetCoordinates coords = cfg.spreadsheetCoordinates();
    SpreadsheetService service = connect(coords);
    extractor = new LogExtractor(service);

    MailingConfig configuration = cfg.mailingConfig();
    LogMailer mailer = new LogMailer(configuration);
    importer = new MongoImporter(mailer, cfg.getMongo());

    SpreadsheetEntry spreadSheet = findSpreadsheet(service, coords);
    List<LogEntry> logEntries = extractor.extractEntries(spreadSheet);
    importer.importNewOnes(logEntries);
  }

  private SpreadsheetEntry findSpreadsheet(SpreadsheetService service, SpreadsheetCoordinates coords) throws IOException, ServiceException
  {
    URL SPREADSHEET_FEED_URL = new URL(URL_STRING);

    SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
    List<SpreadsheetEntry> spreadsheets = feed.getEntries();

    String soughtName = coords.getSpreadsheetName();
    for (SpreadsheetEntry spreadsheet : spreadsheets)
    {
      String candidateSheetTitle = spreadsheet.getTitle().getPlainText();
      if(soughtName.equalsIgnoreCase(candidateSheetTitle))
      {
        return spreadsheet;
      }
    }
    LOG.severe(String.format("Couldn't find a spreadsheet named [%s]", soughtName));
    return null;
  }

  private SpreadsheetService connect(SpreadsheetCoordinates coords) throws AuthenticationException
  {
    SpreadsheetService service = new SpreadsheetService("MySpreadsheetIntegration-v1");
    service.setProtocolVersion(SpreadsheetService.Versions.V3);
    service.setUserCredentials(coords.getUsername(), coords.getPassword());
    return service;
  }
}

