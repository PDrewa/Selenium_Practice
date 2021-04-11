import org.apache.logging.log4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LogUtil {

    public static void logStackTrace(Throwable e, Logger logger) {

        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        logger.error(stringWriter.toString());
    }

}
