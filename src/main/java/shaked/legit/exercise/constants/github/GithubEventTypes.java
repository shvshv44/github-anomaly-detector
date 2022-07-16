package shaked.legit.exercise.constants.github;

public enum GithubEventTypes {

    PUSH("push"),
    TEAM("team"),
    REPOSITORY("repository")
    ;

    private String value;

    GithubEventTypes(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
