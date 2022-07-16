package shaked.legit.exercise.logic.detectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import shaked.legit.exercise.constants.github.GithubEventTypes;
import shaked.legit.exercise.constants.github.GithubTeamEventActionTypes;
import shaked.legit.exercise.logic.GithubEventAnomalyDetector;
import shaked.legit.exercise.models.GithubEvent;
import shaked.legit.exercise.models.team.GithubTeam;
import shaked.legit.exercise.models.team.GithubTeamEvent;
import shaked.legit.exercise.services.AlertService;
import shaked.legit.exercise.util.json.JsonParser;

import java.util.Optional;

import static shaked.legit.exercise.constants.PropertiesNames.TEAM_CREATION_PREFIX;

@Component
public class HackerTeamAnomalyDetector implements GithubEventAnomalyDetector {

    private final String anomalyPrefix;
    private final JsonParser jsonParser;
    private final AlertService alertService;

    @Autowired
    public HackerTeamAnomalyDetector(@Value("${" + TEAM_CREATION_PREFIX + "}") String anomalyPrefix,
                                     JsonParser jsonParser,
                                     AlertService alertService) {
        this.anomalyPrefix = anomalyPrefix;
        this.jsonParser = jsonParser;
        this.alertService = alertService;
    }

    @Override
    public void process(GithubEvent event) {
        if (isEventOfTypeCreated(event)) {
            Optional<String> teamName = fetchTeamNameFromEvent(event);
            if (teamName.isPresent() && isStringStartsWithPrefix(teamName.get(), anomalyPrefix)) {
                alertService.alert("Team with name '" + teamName.get()
                        + "' has been created! (violated prefix: "
                        + anomalyPrefix + ")");
            }
        }
    }

    @Override
    public String[] getProcessedEventTypes() {
        return new String[] {GithubEventTypes.TEAM.getValue()};
    }

    private boolean isEventOfTypeCreated(GithubEvent event) {
        return event.getAction().equals(GithubTeamEventActionTypes.CREATED.getValue());
    }

    private Optional<String> fetchTeamNameFromEvent(GithubEvent event) {
        GithubTeamEvent teamEvent = jsonParser.convertFromJson(event.getRawEvent(), GithubTeamEvent.class);
        Optional<GithubTeam> optionalTeam = Optional.ofNullable(teamEvent.getTeam());
        if (optionalTeam.isPresent()) {
            return Optional.ofNullable(optionalTeam.get().getName());
        }

        return Optional.empty();
    }

    private boolean isStringStartsWithPrefix(String someString, String prefix) {
        return someString.toLowerCase().startsWith(prefix.toLowerCase());
    }
}
