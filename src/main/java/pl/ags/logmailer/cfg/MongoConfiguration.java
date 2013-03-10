package pl.ags.logmailer.cfg;

/**
 * Created by ags on 03/09/13 at 22:46
 */
public class MongoConfiguration
{
  private final String host;
  private final String database;
  private final String collection;

  public MongoConfiguration(String host, String database, String collection)
  {
    this.host = host;
    this.database = database;
    this.collection = collection;
  }

  public String getHost()
  {
    return host;
  }

  public String getDatabase()
  {
    return database;
  }

  public String getCollection()
  {
    return collection;
  }
}
