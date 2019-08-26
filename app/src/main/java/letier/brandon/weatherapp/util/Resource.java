package letier.brandon.weatherapp.util;

public class Resource<T> {

    private final ResourceState resourceState;
    private T data;

    // Success state constructor
    private Resource(ResourceState resourceState, T data) {
        this.resourceState = resourceState;
        this.data = data;
    }

    // Loading state constructor
    // Error state constructor
    private Resource(ResourceState resourceState) {
        this.resourceState = resourceState;
    }

    public static <T> Resource<T> loading() {
        return new Resource<>(ResourceState.LOADING);
    }

    public static <T> Resource<T> success(T data) {
        return new Resource<>(ResourceState.SUCCESS, data);
    }

    public static <T> Resource<T> error() {
        return new Resource<>(ResourceState.ERROR);
    }

    public ResourceState getResourceState() {
        return resourceState;
    }

    public T getData() {
        return data;
    }
}