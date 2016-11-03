package nmct.jaspernielsmichielhein.watchfriends.helper;

import nmct.jaspernielsmichielhein.watchfriends.api.IMovieDBService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiHelper {
    private static Retrofit RETROFIT_INSTANCE;
    private static IMovieDBService MOVIEDB_SERVICE_INSTANCE;

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

    public static IMovieDBService getMoviedbServiceInstance() {
        if (MOVIEDB_SERVICE_INSTANCE == null) {
            synchronized (lock_service) {
                if (MOVIEDB_SERVICE_INSTANCE == null) {
                    MOVIEDB_SERVICE_INSTANCE = getRetrofitInstance().create(IMovieDBService.class);
                }
            }
        }
        return MOVIEDB_SERVICE_INSTANCE;
    }
}