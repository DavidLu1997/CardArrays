import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;

//Deck class, stores card objects
public class Deck implements ActionListener{

	//Cards array
	private Card[] cards;
	
	//Default Constructor
	public Deck()
	{
		cards = new Card[52];
		
		//Index
		int index = 0;
		
		//For each card
		for(int i = 1; i <= 4; i++)
		{
			for(int j = 1; j <= 13; j++)
			{
				cards[index] = new Card(j, i);
				index++;
			}
		}
	}
	
	//Cards Constructor
	public Deck(int n)
	{
		cards = new Card[n];
		
		//Index
		int index = 0;
		
		//While index is still valid
		while(index < cards.length)
		{
			//For each card
			for(int i = 1; i <= 4 && index < cards.length; i++)
			{
				for(int j = 1; j <= 13 && index < cards.length; j++)
				{
					cards[index] = new Card(j, i);
					index++;
				}
			}
		}
	}
	
	//Getter for cards
	public Card[] getCards()
	{
		return cards;
	}
	
	//Getter for size
	public int getLength()
	{
		return cards.length;
	}
	
	//Getter for card
	public Card getCard(int n)
	{
		//Check for valid
		if(n<cards.length)
			return cards[n];
		
		//Null otherwise
		return null;
	}
	
	//Displays the deck
	public void show()
	{
		//Text
		for(int i = 0; i < cards.length; i++)
		{
			System.out.println(cards[i].niceToString());
		}
		
		//Redraw frame
		Main.update();
	}
	
	//AddCard, adds a card at the end
	public void addCard(Card c)
	{
		Card[] temp = new Card[cards.length + 1];
		
		//Assign temp
		for(int i = 0; i < cards.length; i++)
		{
			temp[i] = cards[i];
		}
		
		//Assign c
		temp[cards.length] = c;
		
		//Assign temp
		cards = temp;
		
		System.out.println(c + " has been added at " + cards.length);
	}
	
	//AddCard, adds a card at a given location
	public void addCard(Card c, int n)
	{
		if(n > cards.length)
		{
			System.out.println(n + " is out of bounds.");
			return;
		}
		
		Card[] temp = new Card[cards.length + 1];
		
		//Reassign up to n
		for(int i = 0; i < n; i++)
		{
			temp[i] = cards[i];
		}
		
		//Assign n
		temp[n] = c;
		
		//Assign from n+1 to end
		for(int i = n + 1; i < temp.length; i++)
		{
			temp[i] = cards[i-1];
		}
		
		//Assign temp
		cards = temp;
		
		System.out.println(c + " has been added at " + n);
	}
	
	//RemoveCard, removes the card, returns the removed card or null
	public Card removeCard(Card c)
	{
		for(int i = 0; i < cards.length; i++)
		{
			if(cards[i].equals(c))
			{
				//Temp array
				Card[] temp = new Card[cards.length - 1];
				
				//Reassign everything up to card
				for(int j = 0; j < i; j++)
				{
					temp[j] = cards[j];
				}
				
				//Assign removed
				Card removed = cards[i];
				
				//Reassign from card location to end
				for(int j = i+1; j < cards.length; j++)
				{
					temp[j-1] = cards[j];
				}
				
				//Assign temp to card
				cards = temp;
				
				//Print success
				System.out.println("The card has been removed.");
				
				return removed;
			}
		}
		
		//Print failures
		System.out.println("The card has not been removed.");
		
		return null;
	}
	
	//Removes a card at a given location
	public Card removeCard(int n)
	{
		return removeCard(cards[n]);
	}
	
	//Search method, returns locations of occurrences of c
	public int[] search(Card c)
	{
		int[] locations = new int[0];
		
		//Go through array
		for(int i = 0; i < cards.length; i++)
		{
			//If c is equal
			if(c.equals(cards[i]))
			{
				//Create temp
				int[] temp = new int[locations.length + 1];
				
				//Assign to temp
				for(int j = 0; j < locations.length; j++)
				{
					temp[j] = locations[j];
				}
				
				//Assign i
				temp[locations.length] = i;
				
				//Resize locations
				locations = temp;
			}
		}
		
		return locations;
	}
	
	//FlipCards, flips cards given an array of indexes
	public void flipCards(int[] indexes)
	{
		//Flips each card
		for(int i = 0; i < indexes.length; i++)
		{
			cards[indexes[i]].flip();
		}
	}
	
	//Shuffle, shuffles the deck using Fisher-Yates Shuffle
	public void shuffle()
	{
		for(int i = cards.length - 1; i >= 0; i--)
		{
			int temp = (int)(Math.random() * (i + 1));
			
			Card temp1 = cards[temp];
			cards[temp] = cards[i];
			cards[i] = temp1;
		}
	}
	
	//Sort, calls quicksort
	public void sort()
	{
		quicksort(0, cards.length-1);
	}
	
	//SlowSort, calls insertionsort
	public void slowSort()
	{
		insertionSort();
	}
	
	//Quicksort, sorts via quicksort
	private void quicksort(int left, int right)
	{
		//As long as the list has more than 1 element
		if(left < right)
		{
			//Using middle as pivot
			int pivotNewIndex = partition(left, right, left+(right-left)/2);
			
			//Sort everything smaller
			quicksort(left, pivotNewIndex - 1);
			
			//Sort everything larger
			quicksort(pivotNewIndex + 1, right);
		}
	}
	
	//Partition function for quicksort
	private int partition(int left, int right, int pivotIndex)
	{
		//Get value of pivot
		Card pivotValue = cards[pivotIndex];
		
		//Swap array[pivotIndex] and array[right]
		Card temp = cards[pivotIndex];
		cards[pivotIndex] = cards[right];
		cards[right] = temp;
		
		//Store index
		int storeIndex = left;
		
		//From left to right
		for(int i = left; i < right; i++)
		{
			//Compare array[i] with pivot value
			if(cards[i].compareTo(pivotValue) <= 0)
			{
				//Swap with store index
				temp = cards[i];
				cards[i] = cards[storeIndex];
				cards[storeIndex] = temp;
				
				//Increment storeindex
				storeIndex++;
			}
		}
		
		//Swap store index and right
		temp = cards[storeIndex];
		cards[storeIndex] = cards[right];
		cards[right] = temp;
		
		//Return store index
		return storeIndex;
	}
	
	//Insertionsort, sorts via insertion sort
	private void insertionSort()
	{
		//Hole and value
		Card value;
		int hole;

		//Go through array
		for(int i = 0; i < cards.length; i++)
		{
			//Update value and whole
			value = cards[i];
			hole = i;

			//Shift leftwards until value is bigger than preceding
			while(hole > 0 && value.compareTo(cards[hole-1]) < 0)
			{
				cards[hole] = cards[hole-1];
				hole--;
			}

			//Put into hole
			cards[hole]=value;
		}
	}

	//ActionListener overriding
	public void actionPerformed(ActionEvent e)
	{
		int rank, suit, size;
		
		switch(e.getActionCommand())
		{
		//Generate
		case "generate":
			
			//If text isn't null
			if(Main.generate.getText() != null)
			{
				size = Integer.parseInt(Main.generate.getText());
				
				//If size is valid, assign new deck
				if(size > 0)
				{
					cards = new Card[size];
					
					//Index
					int index = 0;
					
					//While index is still valid
					while(index < cards.length)
					{
						//For each card
						for(int i = 1; i <= 4 && index < cards.length; i++)
						{
							for(int j = 1; j <= 13 && index < cards.length; j++)
							{
								cards[index] = new Card(j, i);
								index++;
							}
						}
					}
				}
			}
			
			//Update frame
			Main.update();
			
		break;
		
		//Shuffles
		case "shuffle":
			
			shuffle();
			
			//Update frame
			Main.update();
			
			break;
			
		//Sorts
		case "sort":
			
			sort();
			
			//Update frame
			Main.update();
			
			break;
			
		case "slowsort":
			
			insertionSort();
			
			//Update frame
			Main.update();
			
			break;
			
		//Deals
		case "deal":
			
			removeCard((int)Main.location.getSelectedItem()-1);
			
			//Update frame
			Main.update();
			
			break;
			
		//Adds
		case "add":
			
			//Get card
			rank = Main.rank.getSelectedIndex() + 1;
			suit = Main.suit.getSelectedIndex() + 1;
			
			addCard(new Card(rank, suit));
			
			//Update frame
			Main.update();
			
			break;
			
		//Removes
		case "remove":
			
			//Get card
			rank = Main.rank.getSelectedIndex() + 1;
			suit = Main.suit.getSelectedIndex() + 1;
			
			removeCard(new Card(rank, suit));
			
			//Update frame
			Main.update();
			
			break;
			
		//Finds and flips
		case "find":
			
			//Get card
			rank = Main.rank.getSelectedIndex() + 1;
			suit = Main.suit.getSelectedIndex() + 1;
			
			//Search for cards
			flipCards(search(new Card(rank, suit)));
			
			//Set button to reset
			Main.findB.setText("Reset");
			Main.findB.setActionCommand("reset");
			Main.findB.setMnemonic(KeyEvent.VK_R);
			
			//Update frame
			Main.update();
			
			break;
			
		case "reset":
			//Get card
			rank = Main.rank.getSelectedIndex() + 1;
			suit = Main.suit.getSelectedIndex() + 1;
			
			//Unflip the cards
			flipCards(search(new Card(rank, suit)));
			
			//Set button to find
			Main.findB.setText("Find");
			Main.findB.setActionCommand("find");
			Main.findB.setMnemonic(KeyEvent.VK_F);
			
			//Update frame
			Main.update();
			
			break;
		}
	}
}
