package pl.ags.logmailer;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.CustomElementCollection;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.util.ServiceException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ags on 03/07/13 at 22:31
 */
class LogExtractor
{
  final SpreadsheetService service;

  LogExtractor(SpreadsheetService service)
  {
    this.service = service;
  }

  public List<LogEntry> extractEntries(SpreadsheetEntry spreadSheet) throws IOException, ServiceException
  {
    WorksheetEntry worksheet = spreadSheet.getDefaultWorksheet();
    URL listFeedUrl = worksheet.getListFeedUrl();
    ListFeed feed = service.getFeed(listFeedUrl, ListFeed.class);
    return getLogEntries(feed);
  }

  private List<LogEntry> getLogEntries(ListFeed feed)
  {
    List<LogEntry> result = new ArrayList<>();
    for (ListEntry entry : feed.getEntries())
    {
      CustomElementCollection customElements = entry.getCustomElements();
      LogEntry e = getLogEntry(customElements);
      result.add(e);
    }
    return result;
  }

  private LogEntry getLogEntry(CustomElementCollection customElements)
  {
    Map<String, String> properties = new HashMap<>();
    Set<String> tags = customElements.getTags();

    for (String tag : tags)
    {
      properties.put(tag, customElements.getValue(tag));
    }
    return new LogEntry(properties);
  }
}
