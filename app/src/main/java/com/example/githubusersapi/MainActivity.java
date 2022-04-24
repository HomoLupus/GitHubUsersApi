package com.example.githubusersapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    UserHolder userHolder;
    //TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //textView = findViewById(R.id.textView);

        createFakeApiList();

        fetchUsers();


    }

    public void setUpRecyclerView() {
        RecyclerView rvUsers = (RecyclerView) findViewById(R.id.rvUsers);

        UsersRecyclerViewAdapter adapter = new UsersRecyclerViewAdapter(userHolder.getUserList());

        rvUsers.setAdapter(adapter);

        rvUsers.setLayoutManager(new LinearLayoutManager(this));

    }


    public void fetchUsers() {
        String myUrl = "https://api.github.com/users";
        StringRequest myRequest = new StringRequest(Request.Method.GET, myUrl,
                response -> {
                    try {
                        //Create a JSON object containing information from the API.
                        //JSONObject myJsonObject = new JSONObject(response);
                        JSONArray myJsonArray = new JSONArray(response);
                        userHolder.getUserList().clear();

                        for (int i = 0; i < myJsonArray.length(); i++) {
                            JSONObject myJsonObject = myJsonArray.getJSONObject(i);
                            fillList(myJsonObject);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        //textView.setText("exc");
                    }
                },
                volleyError -> {
                    Toast.makeText(MainActivity.this, volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    //textView.setText("error");
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(myRequest);

        setUpRecyclerView();

    }
    public void fillList(JSONObject jsonObject) {
        String login = "null";
        String url = "null";
        int id = 1;
        try {
            login = jsonObject.getString("login");
            url = jsonObject.getString("avatar_url");
            id = jsonObject.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        User user = new User(login,id,url);
        userHolder.getUserList().add(user);

    }


    public void createFakeApiList() {

        ArrayList<User> user_list = new ArrayList<>();

        User user0 = new User("null", 0, "null");


        user_list.add(user0);


        userHolder = UserHolder.getInstance(user_list);


    }

}