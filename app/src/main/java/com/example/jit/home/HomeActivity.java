package com.example.jit.home;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Date;


public class HomeActivity extends AppCompatActivity {

   public static DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    LinearLayout mainContentLayout;
    public static ActionBar ACTION_BAR;
    public static  android.support.v7.widget.Toolbar t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SlidingTabFragment stf =new SlidingTabFragment();
        this.getSupportFragmentManager().beginTransaction().replace(R.id.framlayout, stf, "stf").commit();

        mainContentLayout = (LinearLayout) this.findViewById(R.id.maincontent_frag);
         t=(android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        this.setSupportActionBar(t);
        this.getSupportActionBar().setHomeButtonEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        toggle=new ActionBarDrawerToggle(this,drawerLayout,t,R.string.opendrawer,R.string.closedrawer){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {

                super.onDrawerClosed(drawerView);
               // getActionBar().setTitle(mTitle);

            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                mainContentLayout.setTranslationX(drawerView.getWidth()*slideOffset);
                drawerLayout.bringChildToFront(drawerView);
                mainContentLayout.requestLayout();
            }
        };

        drawerLayout.setDrawerListener(toggle);
        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                toggle.syncState();
            }
        });

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            //displayView(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }
      /* else if(id==R.id.home){
            NavUtils.navigateUpTo(this,this.getSupportParentActivityIntent());
        }*/

        return super.onOptionsItemSelected(item);
    }

}
