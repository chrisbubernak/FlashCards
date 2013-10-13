package com.example.flashcards;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

public class FlashcardFragmentActivity extends FragmentActivity {
	public List<Flashcard> flashcards = new ArrayList<Flashcard>();
	
	//the pager widget which handles the animation and swiping
	private ViewPager mPager;
	
	//the pager adapter which provides the pages to the pager widget
	private PagerAdapter mPagerAdapter;
	private DataSource datasource;
	private int mDeckId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		Bundle b = getIntent().getExtras();
		mDeckId = b.getInt("deckId");
		datasource = new DataSource(this);
		setContentView(R.layout.activity_screen_slide);
        
		//instantiate a ViewPager and a PagerAdapter
		mPager = (ViewPager)findViewById(R.id.pager);
		mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
		mPager.setAdapter(mPagerAdapter);
		mPager.setPageTransformer(true, new DepthPageTransformer());
	}

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    public void flip(View view){
    	if(((TextView)((View)view.getParent()).findViewById(R.id.front)).isShown()){
        	((TextView)((View)view.getParent()).findViewById(R.id.front)).setVisibility(View.GONE);
        	((TextView)((View)view.getParent()).findViewById(R.id.back)).setVisibility(View.VISIBLE);
    	}
    	else{
        	((TextView)((View)view.getParent()).findViewById(R.id.back)).setVisibility(View.GONE);
        	((TextView)((View)view.getParent()).findViewById(R.id.front)).setVisibility(View.VISIBLE);
    	}
    }
    
	
	
	private class PagerAdapter extends FragmentStatePagerAdapter {
		public PagerAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
			datasource.open();
			flashcards = datasource.getFlashcardsForDeck(mDeckId);
		    datasource.close();
		}

		@Override 
		public Fragment getItem(int position) {
			return FlashcardFragment.create(flashcards.get(position));
		}
		
		@Override
		public int getCount() {
			return flashcards.size();
		}
	}
	
	public class DepthPageTransformer implements ViewPager.PageTransformer {
	    private static final float MIN_SCALE = 0.75f;

	    public void transformPage(View view, float position) {
	        int pageWidth = view.getWidth();

	        if (position < -1) { // [-Infinity,-1)
	            // This page is way off-screen to the left.
	            view.setAlpha(0);

	        } else if (position <= 0) { // [-1,0]
	            // Use the default slide transition when moving to the left page
	            view.setAlpha(1);
	            view.setTranslationX(0);
	            view.setScaleX(1);
	            view.setScaleY(1);

	        } else if (position <= 1) { // (0,1]
	            // Fade the page out.
	            view.setAlpha(1 - position);

	            // Counteract the default slide transition
	            view.setTranslationX(pageWidth * -position);

	            // Scale the page down (between MIN_SCALE and 1)
	            float scaleFactor = MIN_SCALE
	                    + (1 - MIN_SCALE) * (1 - Math.abs(position));
	            view.setScaleX(scaleFactor);
	            view.setScaleY(scaleFactor);

	        } else { // (1,+Infinity]
	            // This page is way off-screen to the right.
	            view.setAlpha(0);
	        }
	    }
	}
}
