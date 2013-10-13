package com.example.flashcards;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FlashcardFragment extends Fragment{
		public String front;
		public String back;
		public boolean forward;
		public ViewGroup rootView;
		@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	        // Inflate the layout containing a title and body text.
	        rootView = (ViewGroup) inflater
	                .inflate(R.layout.flashcard, container, false);
        	front = getArguments().getString("front");
        	back = getArguments().getString("back");
        	forward = true;
	        ((TextView)rootView.findViewById(R.id.front)).setText(front);
	        ((TextView)rootView.findViewById(R.id.back)).setText(back);
	        ((TextView)rootView.findViewById(R.id.back)).setVisibility(View.GONE);
	        return rootView;
	    }  
	  
	    /**
	     * Factory method for this fragment class. Constructs a new fragment for the given page number.
	     */
	    public static FlashcardFragment create(Flashcard f) {
	        FlashcardFragment fragment = new FlashcardFragment();
	        Bundle args = new Bundle();
	        args.putString("front", f.getFront());
	        args.putString("back", f.getBack());
	        fragment.setArguments(args);       
	        return fragment;
	    }


}

