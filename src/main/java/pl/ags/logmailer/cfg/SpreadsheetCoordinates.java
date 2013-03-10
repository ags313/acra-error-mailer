package pl.ags.logmailer.cfg;

/**
 * Created by ags on 03/07/13 at 21:10
 */
public class SpreadsheetCoordinates
{
  final String username;
  final String password;
  final String spreadsheetName;

  SpreadsheetCoordinates(String username, String password, String spreadsheetName)
  {
    this.username = username;
    this.password = password;
    this.spreadsheetName = spreadsheetName;
  }

  public String getUsername()
  {
    return username;
  }

  public String getPassword()
  {
    return password;
  }

  public String getSpreadsheetName()
  {
    return spreadsheetName;
  }
}
