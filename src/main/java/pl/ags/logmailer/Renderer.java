package pl.ags.logmailer;

import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by ags on 03/09/13 at 21:31
 */
class Renderer
{
  private Configuration cfg = new Configuration();

  Renderer() throws IOException
  {
    cfg.setClassForTemplateLoading(getClass(), "");
    cfg.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
  }

  public String renderText(LogEntry entry) throws IOException, TemplateException
  {
    Template template = cfg.getTemplate("textMessage.ftl");

    StringWriter writer = new StringWriter();

    SimpleHash params = new SimpleHash();
    params.put("entry", entry);
    template.process(params, writer);

    writer.flush();
    return writer.toString();
  }

  public String renderHtml(LogEntry entry) throws IOException, TemplateException
  {
    return renderText(entry);
  }
}
