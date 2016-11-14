package nmct.jaspernielsmichielhein.watchfriends.helper;

import nmct.jaspernielsmichielhein.watchfriends.api.MovieDBService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ApiHelper {
    private static Retrofit RETROFIT_INSTANCE;
    private static MovieDBService MOVIEDB_SERVICE_INSTANCE;

    private static Object lock_retrofit = new Object();
    private static Object lock_service = new Object();

    public static Retrofit getRetrofitInstance() {
        if (RETROFIT_INSTANCE == null) {
            synchronized (lock_retrofit) {
                if (RETROFIT_INSTANCE == null) {
                    OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new LoggingInterceptor()).build();

                    RETROFIT_INSTANCE = new Retrofit.Builder().baseUrl(Contract.MOVIEDB_BASE_URL).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
                }
            }
        }
        return RETROFIT_INSTANCE;
    }

    public static MovieDBService getMoviedbServiceInstance() {
        if (MOVIEDB_SERVICE_INSTANCE == null) {
            synchronized (lock_service) {
                if (MOVIEDB_SERVICE_INSTANCE == null) {
                    MOVIEDB_SERVICE_INSTANCE = getRetrofitInstance().create(MovieDBService.class);
                }
            }
        }
        return MOVIEDB_SERVICE_INSTANCE;
    }

    public static <T> void subscribe(rx.Observable<T> observable, Action1<T> action){
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).onErrorReturn(new Func1<Throwable, T>() {
            @Override
            public T call(Throwable throwable) {
                return null;
            }
        }).subscribe(action);
    }
}