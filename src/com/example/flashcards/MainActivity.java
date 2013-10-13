package com.example.flashcards;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class MainActivity extends FragmentActivity{
    // When requested, this adapter returns a DemoObjectFragment,
    // representing an object in the collection.
    ListPagerAdapter mListPagerAdapter;
    
    ViewPager mViewPager;

    public void onCreate(Bundle savedInstanceState) {
        final ActionBar actionBar = getActionBar();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        mListPagerAdapter =
                new ListPagerAdapter(
                        getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mListPagerAdapter);
        mViewPager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between pages, select the
                        // corresponding tab.
                        getActionBar().setSelectedNavigationItem(position);
                    }
                });
        

        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
			@Override
			public void onTabReselected(Tab tab,
					android.app.FragmentTransaction arg1) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onTabSelected(Tab tab,
					android.app.FragmentTransaction arg1) {
	            	mViewPager.setCurrentItem(tab.getPosition());
			}

			@Override
			public void onTabUnselected(Tab tab,
					android.app.FragmentTransaction arg1) {
				// TODO Auto-generated method stub	
			}
        };

        actionBar.addTab(actionBar.newTab()
        		.setText("Downloaded")
                .setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab()
        		.setText("Online")
                .setTabListener(tabListener));
    }

  public class ListPagerAdapter extends FragmentPagerAdapter {
   private int NUM_TABS = 2;
   
   public ListPagerAdapter(FragmentManager fm) {
       super(fm);
   }

   @Override
   public Fragment getItem(int i) {
       switch (i){
	       case 0:
	    	   return new DownloadedListFragment();
	       case 1:
	    	   return new OnlineListFragment();
   		}
       //this shouldn't happen...if it does make the app crash
       return null;
   }

   @Override
   public int getCount() {
       return NUM_TABS;
   }
  }
}
  
  


