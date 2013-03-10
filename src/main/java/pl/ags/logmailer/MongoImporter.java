package pl.ags.logmailer;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import pl.ags.logmailer.cfg.MongoConfiguration;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import static pl.ags.logmailer.LogEntry.Columns;

/**
 * Created by ags on 03/07/13 at 22:30
 */
class MongoImporter
{
  private final MongoConfiguration configuration;
  private final LogMailer mailer;

  MongoImporter(LogMailer mailer, MongoConfiguration cfg)
  {
    this.mailer = mailer;
    this.configuration = cfg;
  }

  MongoClient getMongo() throws UnknownHostException
  {
    return new MongoClient(configuration.getHost());
  }

  public void importNewOnes(List<LogEntry> logEntries) throws UnknownHostException
  {
    MongoClient mongo = getMongo();

    DB database = mongo.getDB(configuration.getDatabase());
    DBCollection collection = database.getCollection(configuration.getCollection());

    for (LogEntry entry : logEntries)
    {
      if (collection.count(key(entry)) < 1)
      {
        System.out.println("inserting " + entry);
        DBObject from = from(entry);
        collection.insert(from, WriteConcern.ACKNOWLEDGED);
        mailer.doSend(entry);
      }
    }
  }

  DBObject key(LogEntry entry)
  {
    BasicDBObject queryKey = new BasicDBObject();

    Columns[] columns = new Columns[]{
        Columns.TIMESTAMP,
        Columns.REPORT_ID,
        Columns.ANDROID_VERSION,
        Columns.APP_VERSION_CODE,
        Columns.USER_CRASH_DATE
    };

    Map<String,String> filling = entry.getFilling();
    for (Columns column : columns)
    {
      String key = column.getAcraColumnName();
      queryKey.put(key, filling.get(key));
    }

    return queryKey;
  }

  DBObject from(LogEntry entry)
  {
    return new BasicDBObject(entry.getFilling());
  }
}
