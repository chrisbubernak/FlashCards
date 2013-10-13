package com.example.flashcards;

import java.util.ArrayList;
import java.util.List;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableQueryCallback;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class OnlineListFragment extends ListFragment {
	  private DataSource datasource;
	  private static OnlineListFragment singleton;
	  private MobileServiceClient mClient;
	  private MobileServiceTable<Deck> mDeckTable;
	  private MobileServiceTable<Flashcard> mFlashcardTable;
	  
	  @Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    if (singleton == null){
	    	singleton = this;
	    }
		datasource = new DataSource(getActivity());
	    loadFromDB();
	  }
	  

	  @Override
	  public void onListItemClick(ListView l, View v, int position, long id) {
		  Toast.makeText(getActivity(), "Downloading: " + l.getAdapter().getItem(position), Toast.LENGTH_SHORT).show();
		  datasource.open();
		  Deck d = (Deck) l.getAdapter().getItem(position);
		  datasource.insertDeck(d);
		  datasource.close();
		  downloadCards(d.getId());
		  DownloadedListFragment.refresh();
	  }
	  
	  public static void refresh(){
		  if (singleton != null){
			  singleton.loadFromDB();
		  }
	  }
	  
	  public void loadFromDB(){
		    Context context = getActivity();
		    try {
				mClient = new MobileServiceClient(
					      "https://flashcardservice.azure-mobile.net/",
					      "fLfJmmaxfDFmlPTUZKqCMEBNTRGDDc96",
					      context
				);
				mDeckTable = mClient.getTable(Deck.class);
				mDeckTable.select("id", "title").execute(new TableQueryCallback<Deck>(){
					@Override
					public void onCompleted(List<Deck> result, int count,
							Exception exception, ServiceFilterResponse arg3) {
						if (exception == null){
						    ArrayAdapter<Deck> adapter = new ArrayAdapter<Deck>(getActivity(),
							        android.R.layout.simple_list_item_1, result);
							    setListAdapter(adapter);
						    
						}
					}
				});
			}
			catch (Exception e) {
				Log.d("CHRIS", "CHRIS: " + e.getMessage());
			}
	  }
	  
	  public void downloadCards(int deckId){
		    final Context context = getActivity();
		    try {
				mClient = new MobileServiceClient(
					      "https://flashcardservice.azure-mobile.net/",
					      "fLfJmmaxfDFmlPTUZKqCMEBNTRGDDc96",
					      context
				);
				mFlashcardTable = mClient.getTable(Flashcard.class);
				mFlashcardTable.where().field("deck").eq(deckId).select("id", "front", "back", "deck").execute(new TableQueryCallback<Flashcard>(){
					@Override
					public void onCompleted(List<Flashcard> result, int count,
							Exception exception, ServiceFilterResponse arg3) {
						if (exception == null){
							datasource.open();
							for (Flashcard f : result){
								datasource.insertFlashcard(f);
							}
							datasource.close();
						}
						else {
							Toast.makeText(context, "Failed to download cards: " + exception, Toast.LENGTH_LONG).show();
						}
					}
				});
			}
			catch (Exception e) {
				Log.d("CHRIS", "CHRIS: " + e.getMessage());
			}
	  }
}
