package com.example.ahmedragab.clientsapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    public static String USER_ID = "" ;
    EditText name;
    EditText password;
    public static boolean loggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name= findViewById(R.id.txt_username);
        password= findViewById(R.id.txt_password);
    }

    public void Login(View view) {
        if(name.getText().toString().isEmpty() && password.getText().toString().isEmpty()){
            Toast.makeText(LoginActivity.this,"please enter your username & password!", Toast.LENGTH_LONG).show();
        }else{
            if(name.getText().toString().isEmpty()){
                Toast.makeText(LoginActivity.this,"please enter your username!", Toast.LENGTH_LONG).show();
            }else{
                if(password.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this,"please enter your password!", Toast.LENGTH_LONG).show();
                }else{
                    loginUser(name.getText().toString().trim(), password.getText().toString().trim());
                }
            }
        }
    }

    public void loginUser(final String username, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        String url = Config.LOGIN_URL;

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("testres",""+response);

                if(response.equals("not exist")){
                    showDialog("warning", "user not exist .. you need to register first!");
                }else{
                    try {
                        loggedIn = true;
                        JSONObject user=new JSONObject(response);

                        USER_ID = user.getString("id").toString();

                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                /*Intent i=new Intent(LoginActivity.this,HomeActivity.class);
                startActivity(i);
                finish();*/

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url

                Map<String, String> params = new HashMap<>();
                params.put("username", ""+username);
                params.put("password", ""+password);

                return params;
            }

        };

        // Adding request to request queue
        ApplicationActivity.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void showDialog(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);

        // Set up the buttons
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               dialog.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
