package com.example.jit.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by iyad on 8/9/2015.
 */
public class SlidingTabFragment extends Fragment {
    int Numboftabs =3;
    ViewPager viewPager;
    SlidingTabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sliding_tab_frag_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewPager = (ViewPager) this.getView().findViewById(R.id.viewPager);
        tabLayout = (SlidingTabLayout) this.getView().findViewById(R.id.tabs);



        CharSequence Titles[]={"Now","Today","Tomorrow"};


        tabLayout.setCustomTabView(R.layout.tab_content_layout, R.id.tabText);
        tabLayout.setDistributeEvenly(true);
        tabLayout.setBackgroundColor(this.getResources().getColor(R.color.primary_light));
        tabLayout.setSelectedIndicatorColors(this.getResources().getColor(R.color.primary_dark));

        tabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.primary_dark);
            }
        });
        ViewPagerAdapter adapter = new ViewPagerAdapter(this.getFragmentManager(),Titles,Numboftabs);
        viewPager.setAdapter(adapter);

        tabLayout.setViewPager(viewPager);


    }

class ViewPagerAdapter extends FragmentPagerAdapter{

        CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
        int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


        // Build a Constructor and assign the passed Values to appropriate values in the class
        public ViewPagerAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb) {
            super(fm);

            this.Titles = mTitles;
            this.NumbOfTabs = mNumbOfTabsumb;

        }

        //This method return the fragment for the every position in the View Pager
        @Override
        public Fragment getItem(int position) {

            if(position == 0) // if the position is 0 we are returning the First tab
            {
                MyRecyclerViewFragment f1 = new MyRecyclerViewFragment();
                return f1;
            }
            else  if(position == 1) // if the position is 0 we are returning the First tab
            {
                MyRecyclerViewFragment2 f2=new MyRecyclerViewFragment2();
                return f2;
            }else  {

                MyRecyclerViewFragment3 f3=new MyRecyclerViewFragment3();
                return f3;

            }
        }

        // This method return the titles for the Tabs in the Tab Strip

        @Override
        public CharSequence getPageTitle(int position) {
            return Titles[position];
        }

        // This method return the Number of tabs for the tabs Strip

        @Override
        public int getCount() {
            return NumbOfTabs;
        }
    }
}
