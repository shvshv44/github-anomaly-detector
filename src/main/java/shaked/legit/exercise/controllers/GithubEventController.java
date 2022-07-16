package shaked.legit.exercise.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import shaked.legit.exercise.models.GithubEvent;
import shaked.legit.exercise.services.EventBuildingService;
import shaked.legit.exercise.services.EventProcessingService;

@RestController
public class GithubEventController {

    private static final Logger logger = LogManager.getLogger(GithubEventController.class);

    private final EventBuildingService buildingService;
    private final EventProcessingService processingService;

    @Autowired
    public GithubEventController(EventBuildingService buildingService,
                                 EventProcessingService processingService) {
        this.buildingService = buildingService;
        this.processingService = processingService;
    }

    @PostMapping("/hook")
    public void catchEvent(@RequestBody String body, @RequestHeader HttpHeaders httpHeaders) {
        logger.debug("New Raw Event Arrived: " + body);
        logger.debug("New Event Headers: " + httpHeaders);
        GithubEvent event = buildingService.buildEvent(body, httpHeaders);
        logger.debug("Generic Event: " + event);
        processingService.process(event);
    }

}
