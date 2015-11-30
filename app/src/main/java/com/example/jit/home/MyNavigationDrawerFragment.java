package com.example.jit.home;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by pc01 on 02/08/2015.
 */
public class MyNavigationDrawerFragment extends Fragment {
    private String[] mNavigationDrawerItemTitles;
    private ListView mDrawerList;
    ArrayAdapter<String> mAdapter;
    NavDrawerListener listener;
    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;


    private TypedArray navMenuIcons;

    private ArrayList<MyNavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.nav_drawer_frag_loayout,container,false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
        mTitle = mDrawerTitle = getActivity().getTitle();
        mNavigationDrawerItemTitles= getResources().getStringArray(R.array.navigation_drawer_items_array);
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        mDrawerList = (ListView) getView().findViewById(R.id.listview);
        navDrawerItems = new ArrayList<MyNavDrawerItem>();

        // adding nav drawer items to array
        navDrawerItems.add(new MyNavDrawerItem(MainActivity.USER_NAME, 4));
        // Home
        navDrawerItems.add(new MyNavDrawerItem(mNavigationDrawerItemTitles[0], navMenuIcons.getResourceId(0, -1)));
        // Programs
        navDrawerItems.add(new MyNavDrawerItem(mNavigationDrawerItemTitles[1], navMenuIcons.getResourceId(1, -1)));
        // Favorites
        navDrawerItems.add(new MyNavDrawerItem(mNavigationDrawerItemTitles[2], navMenuIcons.getResourceId(2, -1)));
        //Log out
        navDrawerItems.add(new MyNavDrawerItem(mNavigationDrawerItemTitles[3], navMenuIcons.getResourceId(3, -1)));
        navMenuIcons.recycle();
        adapter = new NavDrawerListAdapter(getActivity().getApplicationContext(),
                navDrawerItems);
       // mAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mNavigationDrawerItemTitles);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

    }
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }
    private void displayView(int position) {

        // update the main content by replacing fragments
    Fragment fragment = null;
    switch (position) {

        case 1:
           /* Intent i =new Intent(getActivity(),HomeActivity.class);
            startActivity(i);*/
            HomeActivity.t.setTitle(mNavigationDrawerItemTitles[0]);

            fragment = new SlidingTabFragment();
            break;
        case 2:
            HomeActivity.t.setTitle("All "+mNavigationDrawerItemTitles[1]);

            fragment = new Fragment1();
            break;
        case 3:
            HomeActivity.t.setTitle("My "+mNavigationDrawerItemTitles[2]);

            fragment = new Fragment2();
            break;
        case 4:
            MainActivity.USER_NAME=null;
            Intent i1 =new Intent(getActivity(),MainActivity.class);
            startActivity(i1);
            break;


        default:
            break;
    }

    if (fragment != null) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.framlayout, fragment).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        mDrawerList.setSelection(position);
      //  setTitle(mNavigationDrawerItemTitles[position]);
        HomeActivity.drawerLayout.closeDrawer(Gravity.LEFT);
       // getActivity().getActionBar().setTitle(mNavigationDrawerItemTitles[position]);

        // HomeActivity.drawerLayout.closeDrawer(mDrawerList);
    } else {
        // error in creating fragment
        Log.e("MainActivity", "Error in creating fragment");
    }
}

    public void setTitle(CharSequence title) {
        mTitle = title;
       getActivity().getActionBar().setTitle(mTitle);
    }
    public interface NavDrawerListener{
        public void btnpressed();
    }

    @Override
    public void onAttach(Activity activity) {
         super.onAttach(activity);
        if(activity instanceof NavDrawerListener){
            listener=(NavDrawerListener)activity;
        }
    }
}
