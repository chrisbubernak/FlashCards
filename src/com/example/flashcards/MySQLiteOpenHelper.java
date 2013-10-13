package com.example.flashcards;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

	public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "FlashcardDB";
    public static String COLUMN_ID = "_id";
    public static String COLUMN_TITLE = "title";
    public static final String TABLE_DECKS = "decks";
    
    public static String COLUMN_FRONT = "front";
    public static String COLUMN_BACK = "back";
    public static String COLUMN_DECK_ID = "deckId";
    public static final String TABLE_FLASHCARDS = "flashcards";

    private Context context;
    
    //table creation sql statements
    private static final String DECK_TABLE_CREATE = "create table "
        + TABLE_DECKS + "(" + COLUMN_ID
        + " integer primary key autoincrement, " + COLUMN_TITLE
        + " text not null);";
 
    private static final String FLASHCARD_TABLE_CREATE = "create table "
            + TABLE_FLASHCARDS + "(" + 
        	COLUMN_ID + " integer primary key autoincrement, " + 
            COLUMN_FRONT + " text not null, " +
            COLUMN_BACK + " text not null, " +
            COLUMN_DECK_ID + " text not null, " +
            "FOREIGN KEY (" + COLUMN_DECK_ID + ") REFERENCES " + TABLE_DECKS + "(" + COLUMN_ID + ") ON DELETE CASCADE ON UPDATE CASCADE" +
            ");";
    
	MySQLiteOpenHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
		    db.execSQL(DECK_TABLE_CREATE);
			db.execSQL(FLASHCARD_TABLE_CREATE);
		}
		catch (SQLException e){
	        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FLASHCARDS);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_DECKS);
			Toast.makeText(this.context, "Unable to create DB", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    Log.w(MySQLiteOpenHelper.class.getName(),
	            "Upgrading database from version " + oldVersion + " to "
	                + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_FLASHCARDS);
	    	db.execSQL("DROP TABLE IF EXISTS " + TABLE_DECKS);
	        onCreate(db);
	}

}
