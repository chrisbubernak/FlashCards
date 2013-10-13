package com.example.flashcards;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DataSource {
	private SQLiteDatabase db;
	private MySQLiteOpenHelper dbHelper;
	private String [] allFlashcardColumns = {
			MySQLiteOpenHelper.COLUMN_ID,
			MySQLiteOpenHelper.COLUMN_FRONT,
			MySQLiteOpenHelper.COLUMN_BACK,
			MySQLiteOpenHelper.COLUMN_DECK_ID
	};
	private String [] allDeckColumns = {
			MySQLiteOpenHelper.COLUMN_ID,
			MySQLiteOpenHelper.COLUMN_TITLE
	};
	
	DataSource(Context context){
		dbHelper = new MySQLiteOpenHelper(context);
	}
	
	public void open() throws SQLException{
		db = dbHelper.getWritableDatabase();
		db.execSQL("PRAGMA foreign_keys=ON");
	}
	
	public void close(){
		dbHelper.close();
	}
	
	public Flashcard createFlashcard(String front, String back, Deck d) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteOpenHelper.COLUMN_FRONT, front);
		values.put(MySQLiteOpenHelper.COLUMN_BACK, back);
		values.put(MySQLiteOpenHelper.COLUMN_DECK_ID, d.getId());
		long insertId = db.insert(MySQLiteOpenHelper.TABLE_FLASHCARDS, null, values);
		Cursor cursor = db.query(MySQLiteOpenHelper.TABLE_FLASHCARDS, allFlashcardColumns, MySQLiteOpenHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		Flashcard newFlashcard = cursorToFlashcard(cursor);
		cursor.close();
		return newFlashcard;
	}
	
	public void deleteFlashcard(Flashcard flashcard){
		long id = flashcard.getId();
		db.delete(MySQLiteOpenHelper.TABLE_FLASHCARDS, MySQLiteOpenHelper.COLUMN_ID + " = " + id, null);
	}
	
	public List <Flashcard> getAllFlashcards(){
		List <Flashcard> flashcards = new ArrayList<Flashcard>();
		
		Cursor cursor = db.query(MySQLiteOpenHelper.TABLE_FLASHCARDS, allFlashcardColumns, null, null, null, null, null);
		
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			Flashcard flashcard = cursorToFlashcard(cursor);
			flashcards.add(flashcard);
			cursor.moveToNext();
		}
		cursor.close();
		return flashcards;
	}
	
	public List <Flashcard> getFlashcardsForDeck(int deckId){
		List<Flashcard> flashcards = new ArrayList<Flashcard>();
		Cursor cursor = db.query(MySQLiteOpenHelper.TABLE_FLASHCARDS, allFlashcardColumns, MySQLiteOpenHelper.COLUMN_DECK_ID + "=" + deckId, null, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			Flashcard flashcard = cursorToFlashcard(cursor);
			flashcards.add(flashcard);
			cursor.moveToNext();
		}
		cursor.close();
		return flashcards;
	}
	
	private Flashcard cursorToFlashcard(Cursor cursor){
		Flashcard flashcard = new Flashcard();
		flashcard.setId(cursor.getLong(0));
		flashcard.setFront(cursor.getString(1));
		flashcard.setBack(cursor.getString(2));
		flashcard.setDeckId(cursor.getLong(3));
		return flashcard;
	}
	
	public Deck createDeck(String title) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteOpenHelper.COLUMN_TITLE, title);
		long insertId = db.insert(MySQLiteOpenHelper.TABLE_DECKS, null, values);
		Cursor cursor = db.query(MySQLiteOpenHelper.TABLE_DECKS, allDeckColumns, MySQLiteOpenHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		Deck newDeck = cursorToDeck(cursor);
		cursor.close();
		return newDeck;
	}
	
	public void insertDeck(Deck deck) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteOpenHelper.COLUMN_TITLE, deck.getTitle());
		values.put(MySQLiteOpenHelper.COLUMN_ID, deck.getId());
		long insertId = db.insert(MySQLiteOpenHelper.TABLE_DECKS, null, values);
	}
	
	public void insertFlashcard(Flashcard flashcard) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteOpenHelper.COLUMN_FRONT, flashcard.getFront());
		values.put(MySQLiteOpenHelper.COLUMN_BACK, flashcard.getBack());
		values.put(MySQLiteOpenHelper.COLUMN_ID, flashcard.getId());
		values.put(MySQLiteOpenHelper.COLUMN_DECK_ID, flashcard.getDeckId());
		long insertId = db.insert(MySQLiteOpenHelper.TABLE_FLASHCARDS, null, values);
	}
	
	
	public void deleteDeck(Deck deck){
		long id = deck.getId();
		db.delete(MySQLiteOpenHelper.TABLE_DECKS, MySQLiteOpenHelper.COLUMN_ID + " = " + id, null);
	}
	
	public List <Deck> getAllDecks(){
		List <Deck> decks = new ArrayList<Deck>();
		
		Cursor cursor = db.query(MySQLiteOpenHelper.TABLE_DECKS, allDeckColumns, null, null, null, null, null);
		
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			Deck deck = cursorToDeck(cursor);
			decks.add(deck);
			cursor.moveToNext();
		}
		cursor.close();
		return decks;
	}
	
	private Deck cursorToDeck(Cursor cursor){
		Deck deck = new Deck();
		deck.setId(cursor.getInt(0));
		deck.setTitle(cursor.getString(1));
		return deck;
	}
}
