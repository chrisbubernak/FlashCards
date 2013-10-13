package com.example.flashcards;

public class Deck {
	public String title;
	public int id;
	
	
	Deck(String title){
		this.title = title;
	}
	
	Deck(){
	}
	
	public int getId(){
		return this.id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	@Override
	public String toString() {
		return this.title;
	}

}
