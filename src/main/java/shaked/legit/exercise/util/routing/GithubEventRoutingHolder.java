package shaked.legit.exercise.util.routing;

import shaked.legit.exercise.logic.GithubEventAnomalyDetector;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class GithubEventRoutingHolder extends HashMap<String, Set<GithubEventAnomalyDetector>> {

    public void registerProcessor(GithubEventAnomalyDetector processor) {
        if (processor != null && processor.getProcessedEventTypes() != null) {
            for (String eventName : processor.getProcessedEventTypes()) {
                if (!containsKey(eventName)) {
                    put(eventName, new HashSet<>());
                }
                get(eventName).add(processor);
            }
        }
    }

    public Optional<Set<GithubEventAnomalyDetector>> getProcessorsByEvent(String eventName) {
        return Optional.ofNullable(get(eventName));
    }


}
