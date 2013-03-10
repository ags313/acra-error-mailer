package pl.ags.logmailer.cfg;

import java.util.Properties;

/**
 * Created by ags on 03/09/13 at 22:47
 */
public class MongoConfigurationProvider
{
  public static final String HOST = "mongo.host";
  public static final String DATABASE = "mongo.database";
  public static final String COLLECTION = "mongo.collection";

  public MongoConfiguration get(Properties properties)
  {
    String host = properties.getProperty(HOST, "localhost");
    String database = properties.getProperty(DATABASE, "acra");
    String collection = properties.getProperty(COLLECTION, "errors");
    return new MongoConfiguration(host, database, collection);
  }
}
