//Emily Higgs
//DLB Trie data structure
//The '^' character is trashed in this implementation. It represents a null character. 

import java.util.*;

public class DLBTrie {
	Node current;
	Node first;
	int numberOfWords;

	//These fields only exist to aid in the .predict() method. They are set there, and should not be accessed outside of predictions.
	int predictionsNeeded;
	String[] predictions;

	public DLBTrie() {
		numberOfWords = 0;
		first = new Node('^');
		current = first;
		predictionsNeeded = 0;
	}

	private class Node {
		char value;
		Node nextLetter;
		Node nextOption;

		private Node(char contents) {
			value = contents;
			nextLetter = null;
			nextOption = null;
		}

		private void setNextLetter(Node next) {
			nextLetter = next;
		}

		private void setNextOption(Node next) {
			nextOption = next;
		}

		private void setValue(char val) {
			value = val;
		}
		
		private char getValue() {
			return value;
		}

		private Node getNextLetter() {
			return nextLetter;
		}

		private Node getNextOption() {
			return nextOption;
		}
	}

	//Add a letter. This method should be called in a loop such that the entire word is being handled one letter at a time,
	//as this method will reset the current letter after a '$' character. 
	protected void add(char letter) {

		char comparison = current.getValue();

		while(comparison != '^') {
			//If the values match, move to the next letter.
			if(comparison == letter && letter != '$') {
				current = current.getNextLetter();
				return;
			}
			//If the values match and the word is ending, reset. No word needs a duplicate.
			else if(comparison == letter && letter == '$') {
				current = first;
				return;
			}
			//In this case, values are not equal. Move the linked list forward.
			else {
				if(current.getNextOption() == null && letter != '$') {
					current.setNextOption(new Node(letter));
					current = current.getNextOption();

					current.setNextLetter(new Node('^'));
					current = current.getNextLetter();
					return;
				}
				else if (current.getNextOption() == null && letter == '$') {
					current.setNextOption(new Node(letter));
					
					current = first;
					numberOfWords++;
					return;
				}
				else {
					current = current.getNextOption();
					comparison = current.getValue();
				}
			}
		}

		//If this is the very first word added to the DLBTrie, start the first letter off. 
		if(comparison == '^' && first.getValue() == '^') {
			first.setValue(letter);
			
			first.setNextLetter(new Node('^'));
			current = first.getNextLetter();
		}
		//If you're at the end of the word, add it and reset. 
		else if(comparison == '^' && letter == '$') {
			current.setValue(letter);
			current = first;
			numberOfWords++;
		}
		//If the letter in question currently doesn't exist in this linked list, add it.
		else if(comparison == '^') {
			current.setValue(letter);
			current.setNextLetter(new Node('^'));
			current = current.getNextLetter();
	
		}
	}

	//Searches for the character at a given level of the trie. If found, moves along and returns true.
	protected boolean search(char letter) {
		Node searching;

		//Search until until the letter is found.
		while(current.getValue() != letter) {
			searching = current.getNextOption();
			
			//If there is a next option, move to it.
			if(searching != null)
				current = searching;
			//If no next option exists, the word won't be found in the trie at all. Return false.
			else
				return false;
		}
		//If this point is reached, the letter has been found. Move current deeper in the trie, in preparation for the next letter.
		current = current.getNextLetter();
		return true;
	}

	//Predicts the remaining entries, after the user_history trie has already attempted.
	protected String[] predict(String[] predictionsNeededHere, int numberOfPredictions) {
		Node helperNode = current;
		String currentSuffix = "";
		predictionsNeeded = numberOfPredictions;	
		predictions = predictionsNeededHere;

		//Call a recursive helper function to traverse the trie. 
		return recursiveHelper(currentSuffix, helperNode);
	}
	private String[] recursiveHelper(String currentSuffix, Node helperNode) {
		if(predictionsNeeded <= 0) {
			return predictions;
		}
		else {
			//If the end of a word is not yet reached, add that letter to the suffix and recurse to find the rest of the word.
			if(helperNode.getValue() != '$') 
				predictions = recursiveHelper(currentSuffix + helperNode.getValue(), helperNode.getNextLetter());
			else {

				//Make sure that the suffix is not already present in the predictions.
				boolean notDuplicate = true;

				for(int i = 0; i < 5; i++) {
					if(predictions[i] != null && predictions[i].equals(currentSuffix))
						notDuplicate = false;
				}

				if(notDuplicate) {
					//Add word to predictions.
					predictions[5 - predictionsNeeded] = currentSuffix;
					predictionsNeeded--;
				}
			}
			//Try to move right. If possible, recurse on the right move.
			if(helperNode.getNextOption() != null)
				predictions = recursiveHelper(currentSuffix, helperNode.getNextOption());
		}
			return predictions;
	}
	protected void resetWord() {
		current = first;
	}
}
