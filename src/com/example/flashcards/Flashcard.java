package com.example.flashcards;


public class Flashcard {
	private String front;
	private String back;
	private long deck;
	private long id;
	
	Flashcard(){
	}
	
	public String getFront(){
		return this.front;
	}
	
	public void setFront(String front){
		this.front = front;
	}
	
	public String getBack(){
		return this.back;
	}
	
	public void setBack(String back){
		this.back = back;
	}
	
	public long getId(){
		return this.id;
	}
	
	public void setId(long id){
		this.id = id;
	}

	public long getDeckId(){
		return this.deck;
	}
	
	public void setDeckId(long deck){
		this.deck = deck;
	}
}
