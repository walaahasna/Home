package com.example.jit.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by jit on 16/08/2015.
 */
public class Fragment1 extends Fragment {
    ArrayList<Program> Programs;
    MyRecyclerViewAdapt adapter;
    public  static  String name;
    public  static  String time;
    public  static  int day;
    public  static  String image;
    String url = "http://alaashama.esy.es/programs.php";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View v=inflater.inflate(R.layout.recycler_view_frag_layout,container,false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        JsonObjectRequest jreq = new JsonObjectRequest(Request.Method.GET, url,new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    int success = response.getInt("success");

                    if (success == 1) {
                        JSONArray ja = response.getJSONArray("programs");
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject jobj = ja.getJSONObject(i);
                            Date d = new Date();
                            int a=Integer.parseInt(jobj.getString("time"));
                                name = jobj.getString("name");
                                time = jobj.getString("time")+" - "+ ((a)+1) ;
                                image =jobj.getString("image");
                                day = jobj.getInt("day");
                        //    Program s1 = new Program(name,time,null,image,day);
                         //   Programs.add(s1);
                        } // for loop ends
                        //    Toast.makeText(getActivity(), "aaaa", Toast.LENGTH_LONG).show();

                    } // if ends

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        // Adding request to request queue
        //  Toast.makeText(getActivity(), "xxxx", Toast.LENGTH_LONG).show();
Programs=new ArrayList<Program>();
        MyApplication.getInstance().addToReqQueue(jreq);
        final RecyclerView rv = (RecyclerView) this.getView().findViewById(R.id.recyclerView);

        rv.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this.getActivity()).color(Color.GRAY).build());


        adapter = new MyRecyclerViewAdapt(getActivity(), MainActivity.All_PROG);

        rv.setAdapter(adapter);


        DefaultItemAnimator animator =new DefaultItemAnimator();
        animator.setRemoveDuration(2000);
        animator.setAddDuration(2000);
        rv.setItemAnimator(animator);


        final LinearLayoutManager manager = new LinearLayoutManager(getActivity());

       /* adapter.setRecyclerViewListener(new MyRecyclerViewAdapt.MyRecyclerViewListener() {
            @Override
            public void viewSelected(View v, int position) {
                Intent i = new Intent(v.getContext(), ProgramActivity.class);
                startActivity(i);
            }
        });*/
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);

}}

