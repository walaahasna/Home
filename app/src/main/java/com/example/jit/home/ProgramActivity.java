package com.example.jit.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ProgramActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);
ProgramDetail f=new ProgramDetail();
this.getSupportFragmentManager().beginTransaction().replace(R.id.framlayout1, f, "stf").commit();


    }

}
