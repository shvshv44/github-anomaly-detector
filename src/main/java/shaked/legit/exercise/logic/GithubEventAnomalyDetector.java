package shaked.legit.exercise.logic;

import shaked.legit.exercise.models.GithubEvent;

public interface GithubEventAnomalyDetector {

    void process(GithubEvent event);
    String[] getProcessedEventTypes();

}
