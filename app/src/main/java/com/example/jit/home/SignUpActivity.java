package com.example.jit.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SignUpActivity extends ActionBarActivity {
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPass;
    private EditText editTextConPass;
    String url = "http://alaashama.esy.es/insert-db.php";
    String url2="http://alaashama.esy.es/read.php";
    private ProgressDialog PD;
    String msg1;String msg2;String msg3;
    TextView tvMsgName;TextView tvMsgEmail;TextView tvMsgPass;
   static Boolean b=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        editTextName = (EditText) findViewById(R.id.name);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPass=(EditText)findViewById(R.id.password);
        editTextConPass=(EditText)findViewById(R.id.conpassword);
        tvMsgName=(TextView)findViewById(R.id.msgname);
        tvMsgEmail=(TextView)findViewById(R.id.msgemail);
        tvMsgPass=(TextView)findViewById(R.id.msgpass);
        PD = new ProgressDialog(this);
        PD.setMessage("Loading.....");
        PD.setCancelable(false);
    }

    public void read(){
        final String name = editTextName.getText().toString();
        final String email = editTextEmail.getText().toString();

    }
    public void insert(View view){

        msg1="";msg2="";msg3="";
        tvMsgEmail.setText("");
        tvMsgName.setText("");
        tvMsgPass.setText("");
       final String name = editTextName.getText().toString();
        final String email = editTextEmail.getText().toString();
        final String password = editTextPass.getText().toString();
        final String conpassword = editTextConPass.getText().toString();

        PD.show();
        ///////////////////////READ
        JsonObjectRequest jreq = new JsonObjectRequest(Request.Method.GET, url2,new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int success = response.getInt("success");

                            if (success == 1) {
                                JSONArray ja = response.getJSONArray("users");
                                for (int i = 0; i < ja.length(); i++) {
                                    JSONObject jobj = ja.getJSONObject(i);
                                    if(jobj.getString("name").equals(name)||jobj.getString("email").equals(email)) {
                                        PD.dismiss();
                                        b=true;
                                        Toast.makeText(getApplicationContext(),
                                                b+"This account is used", Toast.LENGTH_SHORT).show();
                                        tvMsgName.setText("This account is already used");
                                        break;
                                        //
                                    }else {
                                        PD.dismiss();

                                        b=false;
                                    }
                                } // for loop ends

                            } // if ends

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                PD.dismiss();
            }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToReqQueue(jreq);        /////////////////////////////

        if(!b &&!name.equals("")&&!email.equals("")
                &&android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
                &&!password.equals("")&&password.length()>=8&&!conpassword.equals("")&&password.equals(conpassword)){
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        PD.dismiss();
                        Toast.makeText(getApplicationContext(),
                                b+"signed up successfully", Toast.LENGTH_SHORT).show();
                        MainActivity.USER_NAME=name;
                        Intent i= new Intent(SignUpActivity.this,HomeActivity.class);
                        startActivity(i);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                PD.dismiss();
                Toast.makeText(getApplicationContext(),
                        "failed to signed up", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        // Adding request to request queue
        MyApplication.getInstance().addToReqQueue(postRequest);}
        else{

            if (name.equals("")){
                PD.dismiss();
                msg1="name is required";

            }
            if (email.equals("")||!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                PD.dismiss();
                msg2="valid email is required";

            }
            if (password.equals("")){
                PD.dismiss();


                msg3="password is required";

            }
            if(password.length()<8){
                PD.dismiss();
                msg3+="password must be at least 8chars";
            }
            if (conpassword.equals("")){
                PD.dismiss();
                msg3+="confirm your password";

            }
            if (!password.equals(conpassword)){
                PD.dismiss();
                msg3+="your password isn't consistent";

            }
            else {
                PD.dismiss();
                Toast.makeText(getApplicationContext(),
                        "signed up successfully", Toast.LENGTH_SHORT).show();
                MainActivity.USER_NAME=name;
                Intent i= new Intent(SignUpActivity.this,HomeActivity.class);
                startActivity(i);

            }
            tvMsgName.setText(msg1);
            tvMsgEmail.setText(msg2);
            tvMsgPass.setText(msg3);

        }
    }




}
