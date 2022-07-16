package shaked.legit.exercise.constants.github;

public enum GithubRepositoryEventActionTypes {

    CREATED("created"),
    DELETED("deleted"),
    ;

    private String value;

    GithubRepositoryEventActionTypes(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
