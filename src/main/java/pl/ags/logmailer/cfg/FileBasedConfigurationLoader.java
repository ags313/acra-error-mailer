package pl.ags.logmailer.cfg;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by ags on 03/09/13 at 22:11
 */
public class FileBasedConfigurationLoader
{
  SpreadsheetConfigurationProvider spreadsheet = new SpreadsheetConfigurationProvider();
  MailConfigurationProvider mailing = new MailConfigurationProvider();
  MongoConfigurationProvider mongo = new MongoConfigurationProvider();

  public Configuration load(String fileName) throws IOException
  {
    File targetFile = findTargetFile(fileName);
    Properties properties = loadProperties(targetFile);

    MailingConfig mailingConfig = mailing.get(properties);
    SpreadsheetCoordinates spreadsheet = this.spreadsheet.get(properties);
    MongoConfiguration mongo = this.mongo.get(properties);
    return new Configuration(mailingConfig, spreadsheet, mongo);
  }

  private File findTargetFile(final String fileName)
  {
    File file;

    file = new File(fileName);
    if(file.exists() && file.isFile())
    {
      return file;
    }

    String home = System.getProperty("user.home");
    final File homeDirectory = new File(home);
    File[] files = homeDirectory.listFiles(new FileFilter()
    {
      @Override
      public boolean accept(File pathname)
      {
        return fileName.equalsIgnoreCase(pathname.getName());
      }
    });
    if (files.length == 1)
    {
      return files[0];
    }

    throw new RuntimeException("No file matching " + fileName + " found");
  }

  private Properties loadProperties(File androidDir) throws IOException
  {
    Properties prop = new Properties();
    InputStream in = new FileInputStream(androidDir);
    prop.load(in);

    return prop;
  }
}
