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

/**
 * Created by walaa-p on 08/22/2015.
 */
public class MyRecyclerViewFragment3 extends Fragment {



    ArrayList<Program> Programs;
    MyRecyclerViewAdapter adapter;
    RecyclerView rv;
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
        Programs = new ArrayList<Program>();
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
                            if(jobj.getInt("day")==(d.getDay()+1 )){
                                name = jobj.getString("name");
                                time = jobj.getString("time")+" - "+ ((a)+1) ;
                                detail = jobj.getString("details");
                                image =jobj.getString("image");
                                day = jobj.getInt("day");
                                Program s1 = new Program(name,time,detail,image,day);
                                Programs.add(s1);
                                //




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

            }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToReqQueue(jreq);        /////////////////////////////

        Program s1 = new Program("Celebrity News     ","11 - 12","Star News      ", R.drawable.cn,5);
        Program s2 = new Program("Sport Center","14 - 15","Sport Program",R.drawable.sc,5);
        Program s3 = new Program("Talk Show","16 - 17","Lifestyle Program",R.drawable.ts,5);
      //  Programs = new ArrayList<Program>();
       Programs.add(s1);
        Programs.add(s2);
      Programs.add(s3);
        MainActivity.All_PROG.add(s1);
        MainActivity.All_PROG.add(s2);
        MainActivity.All_PROG.add(s3);

        //  Toast.makeText(getActivity(), "zzzzz", Toast.LENGTH_LONG).show();



        /*
        if(this.getArguments() != null) {
            String name = this.getArguments().getString("name");
            Student s = new Student(name, R.drawable.f1);
            students.add(0,s);
        }
*/
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
                //Toast.makeText(getApplicationContext(), position + "", Toast.LENGTH_LONG).show();
                //Program  s =Programs.get(position);
                ////String n= s.getName().toString();


                Intent i = new Intent(v.getContext(), ProgramActivity.class);

                startActivity(i);
            }
        });
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        //rv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        rv.setLayoutManager(manager);




    }}
