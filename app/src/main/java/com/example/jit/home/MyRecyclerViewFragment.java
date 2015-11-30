package com.example.jit.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

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


public class MyRecyclerViewFragment extends Fragment {

    ArrayList<Program> Programs;
    MyRecyclerViewAdapter adapter;
    public  static  String name;
    public  static  String time;
    public  static  String detail;
    public  static  int day;
    public  static  String image;
    String url = "http://alaashama.esy.es/programs.php";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.recycler_view_frag_layout, container, false);

        return view;
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
                            String a=""+d.getHours();
                            if(jobj.getInt("day")==d.getDay()&&jobj.getString("time").equals(a) ){
                                name = jobj.getString("name");
                                time = jobj.getString("time")+" - "+ (Integer.parseInt(a)+1) ;
                                detail = jobj.getString("details");
                             image =jobj.getString("image");
                               day = jobj.getInt("day");
                            }
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

        MyApplication.getInstance().addToReqQueue(jreq);        /////////////////////////////

        Program s1 = new Program(name,time,detail,image,day);
        Program s2 = new Program("The cure","10 - 11","Health Program",R.drawable.tc,4);

        Programs = new ArrayList<Program>();
        Programs.add(s2);
      //  Toast.makeText(getActivity(), "bbbbb", Toast.LENGTH_LONG).show();

        final RecyclerView rv = (RecyclerView) this.getView().findViewById(R.id.recyclerView);

        rv.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this.getActivity()).color(Color.GRAY).build());


        adapter = new MyRecyclerViewAdapter(getActivity(), Programs);

        rv.setAdapter(adapter);


        DefaultItemAnimator animator =new DefaultItemAnimator();
        animator.setRemoveDuration(2000);
        animator.setAddDuration(2000);
        rv.setItemAnimator(animator);


        final LinearLayoutManager manager = new LinearLayoutManager(getActivity());

        adapter.setRecyclerViewListener(new MyRecyclerViewAdapter.MyRecyclerViewListener() {
            @Override
            public void viewSelected(View v, int position) {
                Intent i = new Intent(v.getContext(), ProgramActivity.class);
                startActivity(i);
            }
        });
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);

        CheckBox c = (CheckBox)this.getView().findViewById(R.id.checkBox);
        if(MainActivity.USER_NAME==null){
         //   c.setClickable(false);
//            c.setVisibility(View.INVISIBLE);
        }
      //  c.setActivated(false);



    }





}
