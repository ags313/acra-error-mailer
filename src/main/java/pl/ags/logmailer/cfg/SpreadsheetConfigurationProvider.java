package pl.ags.logmailer.cfg;

import java.io.IOException;
import java.util.Properties;

public class SpreadsheetConfigurationProvider
{
  private static final String USERNAME_KEY = "drive.username";
  private static final String PASSWORD_KEY = "drive.password";
  private static final String SPREADSHEET_NAME_KEY = "spreadsheet";

  public SpreadsheetCoordinates get(Properties properties) throws IOException
  {
    return getCoordinates(properties);
  }

  private SpreadsheetCoordinates getCoordinates(Properties prop)
  {
    String username = prop.getProperty(USERNAME_KEY, "USERNAME_NOT_SET");
    String password = prop.getProperty(PASSWORD_KEY, "PASSWORD_NOT_SET");
    String spreadsheetName = prop.getProperty(SPREADSHEET_NAME_KEY, "SPREADSHEET_NAME_NOT_SET");

    return new SpreadsheetCoordinates(username, password, spreadsheetName);
  }
}