package ru.andrikeev.android.synoptic.model.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static ru.andrikeev.android.synoptic.model.repository.Status.ERROR;
import static ru.andrikeev.android.synoptic.model.repository.Status.FETCHING;
import static ru.andrikeev.android.synoptic.model.repository.Status.SUCCESS;

/**
 * A generic class that holds a value with its loading status.
 *
 * @param <T>
 */
public class Resource<T> {

    @NonNull
    private final Status status;

    @Nullable
    private final Throwable error;

    @Nullable
    private final T data;

    private Resource(@NonNull Status status, @Nullable T data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    static <T> Resource<T> success(@Nullable T data) {
        return new Resource<>(SUCCESS, data, null);
    }

    static <T> Resource<T> error(@NonNull Throwable error) {
        return new Resource<>(ERROR, null, error);
    }

    static <T> Resource<T> fetching(@Nullable T data) {
        return new Resource<>(FETCHING, data, null);
    }

    @NonNull
    public Status getStatus() {
        return status;
    }

    @Nullable
    public Throwable getError() {
        return error;
    }

    @Nullable
    public T getData() {
        return data;
    }
}
