package am.emti.hamsterapp.api.interceptor;

import android.os.Build;

import java.io.IOException;

import am.emti.hamsterapp.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


@SuppressWarnings("WeakerAccess")
public class HeadersInterceptor implements Interceptor {
    String KEY_OS_VERSION = "X-Homo-Client-OS";
    String KEY_APP_VERSION = "X-Homo-Client-Version";
    String KEY_DEVICE_MODEL = "X-Homo-Client-Model";
    String KEY_OS = "Android";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request.newBuilder()
                .addHeader(KEY_OS_VERSION,KEY_OS + Build.VERSION.RELEASE)
                .addHeader(KEY_APP_VERSION , BuildConfig.VERSION_NAME)
                .addHeader(KEY_DEVICE_MODEL,Build.MANUFACTURER + " " + Build.MODEL )
                .build();
        return chain.proceed(request);
    }
}
