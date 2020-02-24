package tz.go.moh.mrc_token_auth.api.services;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import tz.go.moh.mrc_token_auth.api.models.Login;
import tz.go.moh.mrc_token_auth.api.models.User;

public interface UserClient {

    @POST("rest-auth/login/")
    Call<User> login(@Body Login login);

    @GET("rest-auth/user/")
    Call<ResponseBody> getUser(@HeaderMap Map<String, String> headers);

}
