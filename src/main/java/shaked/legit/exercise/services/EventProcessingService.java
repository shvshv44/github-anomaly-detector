package shaked.legit.exercise.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shaked.legit.exercise.logic.GithubEventAnomalyDetector;
import shaked.legit.exercise.models.GithubEvent;
import shaked.legit.exercise.util.routing.GithubEventRoutingHolder;

import java.util.Optional;
import java.util.Set;

@Service
public class EventProcessingService {

    private final GithubEventRoutingHolder routingHolder;

    @Autowired
    public EventProcessingService(GithubEventRoutingHolder routingHolder) {
        this.routingHolder = routingHolder;
    }

    public void process(GithubEvent event) {
        Optional<Set<GithubEventAnomalyDetector>> optionalProcessors = routingHolder.getProcessorsByEvent(event.getName());
        optionalProcessors.ifPresent(processors -> processors.forEach(processor -> processor.process(event)));
    }

}
