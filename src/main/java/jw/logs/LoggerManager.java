package jw.logs;

import java.util.logging.Logger;

public class LoggerManager
{

    private final Logger logger;

    public LoggerManager(String name)
    {
        logger = Logger.getLogger(name);
    }

    public Logger getLogger()
    {
        return logger;
    }
}
