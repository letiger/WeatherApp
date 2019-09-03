package letier.brandon.weatherapp.util;

import io.reactivex.CompletableTransformer;
import io.reactivex.MaybeTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public interface SchedulerProvider {
    <T> ObservableTransformer<T, T> applySchedulers();

    CompletableTransformer applySchedulersCompletable();

    <T> SingleTransformer<T, T> applySchedulersSingle();

    <T> MaybeTransformer<T, T> applySchedulersMaybe();

    SchedulerProvider DEFAULT = new SchedulerProvider() {
        @Override
        public <T> ObservableTransformer<T, T> applySchedulers() {
            return upstream -> upstream
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        @Override
        public CompletableTransformer applySchedulersCompletable() {
            return upstream -> upstream
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        @Override
        public <T> SingleTransformer<T, T> applySchedulersSingle() {
            return upstream -> upstream
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        @Override
        public <T> MaybeTransformer<T, T> applySchedulersMaybe() {
            return upstream -> upstream
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };

    // For synchronous tests
    SchedulerProvider IMMEDIATE = new SchedulerProvider() {
        @Override
        public <T> ObservableTransformer<T, T> applySchedulers() {
            return upstream -> upstream
                    .subscribeOn(Schedulers.trampoline())
                    .observeOn(Schedulers.trampoline());
        }

        @Override
        public CompletableTransformer applySchedulersCompletable() {
            return upstream -> upstream
                    .subscribeOn(Schedulers.trampoline())
                    .observeOn(Schedulers.trampoline());
        }

        @Override
        public <T> SingleTransformer<T, T> applySchedulersSingle() {
            return upstream -> upstream
                    .subscribeOn(Schedulers.trampoline())
                    .observeOn(Schedulers.trampoline());
        }

        @Override
        public <T> MaybeTransformer<T, T> applySchedulersMaybe() {
            return upstream -> upstream
                    .subscribeOn(Schedulers.trampoline())
                    .observeOn(Schedulers.trampoline());
        }
    };
}

