package shaked.legit.exercise.logic.detectors;

import com.google.common.cache.LoadingCache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import shaked.legit.exercise.constants.github.GithubEventTypes;
import shaked.legit.exercise.logic.GithubEventAnomalyDetector;
import shaked.legit.exercise.models.GithubEvent;
import shaked.legit.exercise.models.repository.GithubRepositoryEvent;
import shaked.legit.exercise.services.AlertService;
import shaked.legit.exercise.util.json.JsonParser;

import static shaked.legit.exercise.constants.github.GithubRepositoryEventActionTypes.CREATED;
import static shaked.legit.exercise.constants.github.GithubRepositoryEventActionTypes.DELETED;

@Component
public class ShortPeriodRepositoryAnomalyDetector implements GithubEventAnomalyDetector {

    private static final Logger logger = LogManager.getLogger(ShortPeriodRepositoryAnomalyDetector.class);

    private final LoadingCache<String, String> repoCache;
    private final JsonParser jsonParser;
    private final AlertService alertService;

    @Autowired
    public ShortPeriodRepositoryAnomalyDetector(LoadingCache<String, String> repoCache,
                                                JsonParser jsonParser,
                                                AlertService alertService) {
        this.repoCache = repoCache;
        this.jsonParser = jsonParser;
        this.alertService = alertService;
    }

    @Override
    public void process(GithubEvent event) {
        try {
            String repoName = getRepoNameFromEvent(event);
            if (event.getAction().equals(CREATED.getValue())) {
                repoCache.put(repoName, repoName);
            } else if (event.getAction().equals(DELETED.getValue())){
                if (repoCache.asMap().containsKey(repoName)) {
                    alertService.alert("Repository named '" + repoName + "' has deleted after suspicious time from its creation!");
                }
            }
        } catch (Exception ex) {
            logger.error("Error while processing repository event: " + ex.getMessage());
        }
    }

    @Override
    public String[] getProcessedEventTypes() {
        return new String[] {GithubEventTypes.REPOSITORY.getValue()};
    }

    private String getRepoNameFromEvent(GithubEvent event) {
        GithubRepositoryEvent pasedEvent = jsonParser.convertFromJson(event.getRawEvent(), GithubRepositoryEvent.class);
        return pasedEvent.getRepository().getName();
    }
}
