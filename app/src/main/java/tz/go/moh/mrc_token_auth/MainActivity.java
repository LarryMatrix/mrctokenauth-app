package tz.go.moh.mrc_token_auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tz.go.moh.mrc_token_auth.api.models.Login;
import tz.go.moh.mrc_token_auth.api.models.User;
import tz.go.moh.mrc_token_auth.api.services.Server;

public class MainActivity extends AppCompatActivity {

    Button signIn, getInfo;
    EditText user, pass;

    private static String token;
    private Map<String, String> pubHeader = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signIn = findViewById(R.id.signInBtn);
        getInfo = findViewById(R.id.infoBtn);
        user = findViewById(R.id.editUsername);
        pass = findViewById(R.id.editPassword);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        getInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInfo(pubHeader);
            }
        });

    }

    private void login() {

        String userName = user.getText().toString().trim();
        String passWord = pass.getText().toString().trim();

        Login login = new Login(userName, passWord);

        Call<User> call = Server
                .getServerInstance()
                .getUserClient()
                .login(login);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {

                    token = response.body().getKey();

                    pubHeader.put("Authorization", "Token "+token);

                    Toast.makeText(MainActivity.this, "KEY: "+response.body().getKey(), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MainActivity.this, "Incorrect Details: "+response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error on Login: "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void userInfo(Map<String, String> key) {
        Call<ResponseBody> call = Server
                .getServerInstance()
                .getUserClient()
                .getUser(key);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    String user = response.body().string();
                    Toast.makeText(MainActivity.this, "User Info: "+user, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

//                if (response.isSuccessful()) {
//                    try {
//                        Toast.makeText(MainActivity.this, response.body().string(), Toast.LENGTH_SHORT).show();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    Toast.makeText(MainActivity.this, "Incorrect Token", Toast.LENGTH_SHORT).show();
//                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error of Profile: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
