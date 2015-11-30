package com.example.jit.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class LoginActivity extends ActionBarActivity {
    String url = "http://alaashama.esy.es/read.php";
    ArrayList<HashMap<String, String>> Item_List;
    ProgressDialog PD;
    public static final String NAME = "name";
    public static final String PASS = "password";
    EditText etName;
    EditText etPass;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etName=(EditText)findViewById(R.id.name);
        etPass=(EditText)findViewById(R.id.password);
        tv=(TextView)findViewById(R.id.textView);
        Item_List = new ArrayList<HashMap<String, String>>();
        PD = new ProgressDialog(this);

        Button b3=(Button) findViewById(R.id.button);//login
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReadDataFromDB();
            }
        });
    }
    private void ReadDataFromDB() {
        final String pass = etPass.getText().toString();
        final String name = etName.getText().toString();

        PD.setMessage("Loading.....");
        PD.setCancelable(false);
        PD.show();

        JsonObjectRequest jreq = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            int success = response.getInt("success");
                            Boolean b = false;
                            if (success == 1) {
                                JSONArray ja = response.getJSONArray("users");

                                for (int i = 0; i < ja.length(); i++) {

                                    JSONObject jobj = ja.getJSONObject(i);
                                    if(jobj.getString(NAME).equals(name)&&jobj.getString(PASS).equals(pass)) {
                                        b = true;
                                        break;
                                    }
                                } // for loop ends
                                if(b){
                                    PD.dismiss();
                                    MainActivity.USER_NAME=name;
                                    Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                                    startActivity(intent);}
                                else{
                                    PD.dismiss();
                                    tv.setText("Wrong username or password!");
                                }
                            } // if ends


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        catch (Exception e){
                            //  Toast.makeText(getApplicationContext(),"aaaaaaa",Toast.LENGTH_LONG).show();

                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                PD.dismiss();
            }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToReqQueue(jreq);
        if(name.equals("alaa")&&pass.equals("123")){
            PD.dismiss();
            MainActivity.USER_NAME=name;
            Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
            startActivity(intent);
        }
        else {
            PD.dismiss();

            tv.setText("Wrong username or password!");
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
