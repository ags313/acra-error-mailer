package pl.ags.logmailer;

import java.util.Map;

/**
 * Created by ags on 03/07/13 at 22:07
 */
public class LogEntry
{
  /* Columns available:
   f
   reportid
   appversioncode
   appversionname
   packagename
   filepath
   phonemodel
   brand
   product
   androidversion
   build
   totalmemsize
   availablememsize
   customdata
   issilent
   stacktrace
   initialconfiguration
   crashconfiguration
   display
   usercomment
   useremail
   userappstartdate
   usercrashdate
   dumpsysmeminfo
   dropbox
   logcat
   eventslog
   radiolog
   deviceid
   installationid
   devicefeatures
   environment
   sharedpreferences
   settingssystem
   settingssecure
  */

  final Map<String, String> filling;

  public LogEntry(Map<String, String> filling)
  {
    this.filling = filling;
  }

  public String getVersionCode()
  {
    return get(Columns.APP_VERSION_CODE);
  }

  public String getVersionName()
  {
    return get(Columns.APP_VERSION_NAME);
  }

  public String getReportId()
  {
    return get(Columns.REPORT_ID);
  }

  public String getTimestamp()
  {
    return get(Columns.TIMESTAMP);
  }

  public String getBrand()
  {
    return get(Columns.BRAND);
  }

  public String getAndroidVersion()
  {
    return get(Columns.ANDROID_VERSION);
  }

  private String get(Columns column) {
    String value = filling.get(column.getAcraColumnName());
    if(value == null)
      return "";

    return value;
  }

  public String getStackTrace()
  {
    return get(Columns.STACKTRACE);
  }

  public Map<String, String> getFilling()
  {
    return filling;
  }

  @Override
  public String toString()
  {
    return "LogEntry{" +
        ", filling=" + filling +
        '}';
  }

  public enum Columns
  {
    TIMESTAMP("f"),
    STACKTRACE("stacktrace"),
    ANDROID_VERSION("androidversion"),
    APP_VERSION_CODE("appversioncode"),
    APP_VERSION_NAME("appversionname"),
    USER_CRASH_DATE("usercrashdate"),
    REPORT_ID("reportid"),
    BRAND("brand");

    private final String acraColumnName;

    Columns(String acraColumnName)
    {
      this.acraColumnName = acraColumnName;
    }

    public String getAcraColumnName()
    {
      return acraColumnName;
    }
  }
}
