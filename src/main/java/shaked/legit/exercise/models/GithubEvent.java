package shaked.legit.exercise.models;

import lombok.Data;

import java.time.Instant;

@Data
public class GithubEvent {

    private String name;
    private String action;
    private Instant startTime;
    private String rawEvent;

}
