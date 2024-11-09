package ee.taltech.iti0202.travelagency.logging;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class TravelAgencyLogger {
        private static final Logger LOGGER = Logger.getLogger(TravelAgencyLogger.class.getName());

    /**
     * Logger class constructor.
     * @throws IOException for logger API.
     */
    public TravelAgencyLogger() throws IOException {
            FileHandler fileTxt = new FileHandler("Logging.txt");
            LOGGER.addHandler(fileTxt);
    }

    /**
     * Retrieves the common logger instance used for logging in the Travel Agency system.
     * Method provides access to the shared logger instance across multiple classes.
     * @return common Logger instance used for logging.
     */
    public static Logger getLogger() {
        return LOGGER;
    }
}
