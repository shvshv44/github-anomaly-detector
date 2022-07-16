package shaked.legit.exercise.logic.detectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import shaked.legit.exercise.constants.github.GithubEventTypes;
import shaked.legit.exercise.logic.GithubEventAnomalyDetector;
import shaked.legit.exercise.models.GithubEvent;
import shaked.legit.exercise.services.AlertService;

import java.time.ZoneId;

import static shaked.legit.exercise.constants.PropertiesNames.PUSH_ABNORMAL_END_HOUR;
import static shaked.legit.exercise.constants.PropertiesNames.PUSH_ABNORMAL_START_HOUR;

@Component
public class BadTimePushAnomalyDetector implements GithubEventAnomalyDetector {

    private final Integer startHour;
    private final Integer endHour;
    private final AlertService alertService;

    @Autowired
    public BadTimePushAnomalyDetector(@Value("${" + PUSH_ABNORMAL_START_HOUR + "}") Integer startHour,
                                      @Value("${" + PUSH_ABNORMAL_END_HOUR + "}") Integer endHour,
                                      AlertService alertService) {
        this.startHour = startHour;
        this.endHour = endHour;
        this.alertService = alertService;
    }

    @Override
    public void process(GithubEvent event) {
        Integer eventHour = event.getStartTime().atZone(ZoneId.systemDefault()).getHour();
        if (eventHour >= startHour && eventHour <= endHour) {
            alertService.alert("Code pushed by a certain user in suspicious time: "
                    + event.getStartTime() + " (violated no code pushing between "
                    + startHour + ":00" + " and " + endHour + ":00)");
        }
    }

    @Override
    public String[] getProcessedEventTypes() {
        return new String[] {GithubEventTypes.PUSH.getValue()};
    }
}
