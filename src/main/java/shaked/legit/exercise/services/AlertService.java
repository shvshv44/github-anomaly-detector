package shaked.legit.exercise.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class AlertService {

    private static final Logger logger = LogManager.getLogger(AlertService.class);

    public void alert(String message) {
        logger.warn("Anomaly Alert: " + message);
    }

}
