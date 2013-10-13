package com.example.flashcards;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DownloadedListFragment extends ListFragment {
	  private DataSource datasource;
	  private static ArrayAdapter<Deck>adapter;
	  private static DownloadedListFragment singleton;
	  private static List<Deck> decks;
	  
	  @Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    if (singleton == null){
	    	singleton = this;
	    }
	    getListView().setOnItemLongClickListener(new DownloadedLongClickListener());
	    loadFromDB();
	  }

	  @Override
	  public void onListItemClick(ListView l, View v, int position, long id) {
		  Deck d = (Deck) l.getAdapter().getItem(position);
		  Intent intent = new Intent(getActivity(), FlashcardFragmentActivity.class);
		  intent.putExtra("deckId", d.getId());
		  startActivity(intent);
	  }
	  
	  
	  public static void refresh(){
		  if (singleton != null){
			  singleton.loadFromDB();
		  }
	  }
	  
	  public void loadFromDB(){
		    Context context = getActivity();
		    datasource =  new DataSource(context);
		    try {
		    	datasource.open();
		    	decks = datasource.getAllDecks();

			    adapter = new ArrayAdapter<Deck>(context,
				        android.R.layout.simple_list_item_1, decks);
				    setListAdapter(adapter);		
			}
			catch (Exception e) {
				Log.d("CHRIS", "CHRIS: " + e.getMessage());
			}
	  }
	  
	  class DownloadedLongClickListener implements OnItemLongClickListener{
		  public boolean onItemLongClick(AdapterView<?> deck, View view,
				  final int position, long id) {
			  new AlertDialog.Builder(getActivity())
		      	.setTitle("Delete Deck")
				.setMessage("Are you sure you want to delete this Deck?")
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) { 
						datasource.open();
					    datasource.deleteDeck(decks.get(position));
						datasource.close();
						loadFromDB();
				     }
				})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) { 
				    }
				}).show();
				return true;
		  }
	  }
}
