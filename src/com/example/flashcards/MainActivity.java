package com.example.flashcards;

import java.io.IOException;
import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;


public class MainActivity extends Activity {
	private MobileServiceClient mClient;
	private static final String ASSET_SUB_FOLDER = "flashcarddecks";
	private static final String ASSET_EXTENSION = ".json";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		
		AssetManager am = getAssets();
		String[] a = null;
		try {
			a = am.list(ASSET_SUB_FOLDER);
		} catch (IOException e) {
			e.printStackTrace();
		}
		final String [] flashCardDecks = new String[a.length];
		for(int i=0; i < a.length; i++){
			flashCardDecks[i] = a[i].substring(0, a[i].length()-ASSET_EXTENSION.length());
		}
		
		GridView gridView = (GridView) findViewById(R.id.gridView);
		gridView.setAdapter(new ImageAdapter(this, flashCardDecks));
		
		gridView.setOnItemClickListener(new OnItemClickListener (){
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Intent intent = new Intent(MainActivity.this, FlashcardFragmentActivity.class);
				intent.putExtra("filename", ASSET_SUB_FOLDER + "/" + flashCardDecks[position] + ASSET_EXTENSION);
				startActivity(intent);
			}
		});
		
		gridView.setOnItemLongClickListener(new OnItemLongClickListener (){
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Toast.makeText(MainActivity.this, "Do you want to remove this deck?", Toast.LENGTH_SHORT).show();
				return false;
			}
		});		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
