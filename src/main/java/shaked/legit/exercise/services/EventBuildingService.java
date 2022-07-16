package shaked.legit.exercise.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import shaked.legit.exercise.models.GithubEvent;
import shaked.legit.exercise.models.RawGithubEvent;
import shaked.legit.exercise.util.json.JsonParser;

import java.time.Instant;
import java.util.List;

import static shaked.legit.exercise.constants.github.GithubCommonConstants.EVENT_NAME_HEADER;

@Service
public class EventBuildingService {

    private static final Logger logger = LogManager.getLogger(EventBuildingService.class);

    private final JsonParser jsonParser;

    @Autowired
    public EventBuildingService(JsonParser jsonParser) {
        this.jsonParser = jsonParser;
    }

    public GithubEvent buildEvent(String rawEvent, HttpHeaders eventHeaders) {
        GithubEvent newEvent = new GithubEvent();
        setEventNameIfExists(newEvent, eventHeaders);
        setEventActionFromRawData(newEvent, rawEvent);
        newEvent.setStartTime(Instant.now());
        newEvent.setRawEvent(rawEvent);
        return newEvent;
    }

    private void setEventNameIfExists(GithubEvent newEvent, HttpHeaders eventHeaders) {
        List<String> values = eventHeaders.get(EVENT_NAME_HEADER);
        if (values != null && !values.isEmpty()) {
            newEvent.setName(values.get(0));
        }
    }

    private void setEventActionFromRawData(GithubEvent newEvent, String rawEvent) {
        try {
            RawGithubEvent parsedRawEvent = jsonParser.convertFromJson(rawEvent, RawGithubEvent.class);
            newEvent.setAction(parsedRawEvent.getAction());
        } catch (Exception ex) {
            logger.error("Error occurred while parsing event: " + ex.getMessage());
        }
    }

}
