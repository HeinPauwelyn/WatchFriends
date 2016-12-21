package nmct.jaspernielsmichielhein.watchfriends.helper;

import nmct.jaspernielsmichielhein.watchfriends.api.WatchFriendsService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiWatchFriendsHelper extends ApiHelper {

    private static Retrofit RETROFIT_INSTANCE;
    private static WatchFriendsService WATCHFRIENDS_SERVICE_INSTANCE;

    private static Object lock_retrofit = new Object();
    private static Object lock_service = new Object();

    public static Retrofit getRetrofitInstance() {
        if (RETROFIT_INSTANCE == null) {
            synchronized (lock_retrofit) {
                if (RETROFIT_INSTANCE == null) {
                    OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new LoggingInterceptor()).build();

                    RETROFIT_INSTANCE = new Retrofit.Builder().baseUrl(Contract.WATCHFRIENDS_BASE_URL).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
                }
            }
        }
        return RETROFIT_INSTANCE;
    }

    public static WatchFriendsService getWatchFriendsServiceInstance() {
        if (WATCHFRIENDS_SERVICE_INSTANCE == null) {
            synchronized (lock_service) {
                if (WATCHFRIENDS_SERVICE_INSTANCE == null) {
                    WATCHFRIENDS_SERVICE_INSTANCE = getRetrofitInstance().create(WatchFriendsService.class);
                }
            }
        }
        return WATCHFRIENDS_SERVICE_INSTANCE;
    }
}