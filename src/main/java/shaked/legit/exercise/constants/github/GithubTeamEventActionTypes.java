package shaked.legit.exercise.constants.github;

public enum GithubTeamEventActionTypes {

    CREATED("created"),
    ;

    private String value;

    GithubTeamEventActionTypes(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
