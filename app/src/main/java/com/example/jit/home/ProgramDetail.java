package com.example.jit.home;


import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProgramDetail extends android.support.v4.app.Fragment {
    EditText et;
    Button b;
    ListView lv;
    ArrayList<String> al;
    public static ArrayAdapter<String> aa;
    String url = "http://alaashama.esy.es/readcomment.php";
    String url2 = "http://alaashama.esy.es/insertcomment.php";
    public  static  String name;
    public  static  String mycomment;
    String item;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.programdetail_layout, container, false);



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        et = (EditText)this.getView().findViewById(R.id.editText1);
         b = (Button)this.getView(). findViewById(R.id.button1);
        lv = (ListView)this.getView(). findViewById(R.id.listView1);
       // al = new ArrayList<String>();//initialize array list

        aa = new ArrayAdapter<String>(getActivity(),  android.R.layout.simple_list_item_1, readcomment());
        lv.setAdapter(aa);
        aa.notifyDataSetChanged();
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                item = et.getText().toString();
               
                insert(v);
                aa = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, readcomment());
                lv.setAdapter(aa);
                aa.notifyDataSetChanged();
                et.setText("");

            }
        });}



        /*VideoView StudentLife = (VideoView)this.getView().findViewById(R.id.videoView);
        String path1="http://www.dailymotion.com/video/x2llw0d";
        MediaController mc = new MediaController(this.getActivity());
        mc.setAnchorView(StudentLife);
        mc.setMediaPlayer(StudentLife);
        Uri video = Uri.parse(path1);
        StudentLife.setMediaController(mc);
        StudentLife.setVideoURI(video);
        StudentLife.start();*/


    public ArrayList<String> readcomment() {
        final ArrayList<String> messages = new ArrayList<>();
        JsonObjectRequest jreq = new JsonObjectRequest(Request.Method.GET, url,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int success = response.getInt("success");
                    if (success == 1) {
                        JSONArray ja = response.getJSONArray("comment");
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jobj = ja.getJSONObject(i);
                            name = jobj.getString("name");
                            mycomment = jobj.getString("mycomment");
                            messages.add(mycomment);
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MyApplication.getInstance().addToReqQueue(jreq);


        return messages;

    }

    public void insert(View view){
        StringRequest postRequest = new StringRequest(Request.Method.POST, url2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(),"yes", Toast.LENGTH_SHORT).show();
            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("mycomment", item);

                return params;
            }
        };}

    }





