package letier.brandon.weatherapp.util;

public class Resource<T> {

    private final ResourceState resourceState;
    private T data;
    private String errorMessage;
    private boolean fatalError;
    private String loadingTitle;
    private String loadingMessage;

    // Success state constructor
    private Resource(ResourceState resourceState, T data) {
        this.resourceState = resourceState;
        this.data = data;
    }

    // Loading state constructor
    private Resource(ResourceState resourceState, String loadingTitle, String loadingMessage) {
        this.resourceState = resourceState;
        this.loadingTitle = loadingTitle;
        this.loadingMessage = loadingMessage;
    }

    // Error state constructor
    private Resource(ResourceState resourceState, String errorMessage, boolean fatalError) {
        this.resourceState = resourceState;
        this.errorMessage = errorMessage;
        this.fatalError = fatalError;
    }

    public static <T> Resource<T> loading(String loadingTitle, String loadingMessage) {
        return new Resource<>(ResourceState.LOADING, loadingTitle, loadingMessage);
    }

    public static <T> Resource<T> success(T data) {
        return new Resource<>(ResourceState.SUCCESS, data);
    }

    public static <T> Resource<T> error(String errorMessage) {
        return error(errorMessage, false);
    }

    public static <T> Resource<T> error(String errorMessage, boolean fatalError) {
        return new Resource<>(ResourceState.ERROR, errorMessage, fatalError);
    }

    public ResourceState getResourceState() {
        return resourceState;
    }

    public T getData() {
        return data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isFatalError() {
        return fatalError;
    }

    public String getLoadingTitle() {
        return loadingTitle;
    }

    public String getLoadingMessage() {
        return loadingMessage;
    }
}