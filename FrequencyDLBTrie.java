//Emily Higgs
//DLB Trie data structure
//The '^' character is used in the .add method to signify a coming character.

import java.util.*;
import java.util.*;

public class FrequencyDLBTrie {
	Node current;
	Node first;
	int numberOfWords;

	//These fields only exist to aid in the .predict() method. They are set there, and should not be accessed outside of predictions.
	int predictionsNeeded;
	StringFreq[] predictions;

	//This array is used to store the user history's words for saving later.
	String[] words;
	
	public FrequencyDLBTrie() {
		numberOfWords = 0;
		first = new Node('^');
		current = first;
		predictionsNeeded = 0;
		predictions = new StringFreq[6];
	}

	private class Node {
		char value;
		Node nextLetter;
		Node nextOption;
		int frequency;

		private Node(char contents) {
			value = contents;
			nextLetter = null;
			nextOption = null;
			frequency = 0;
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

		private int getFrequency() {
			return frequency;
		}

		private void incrementFrequency() {
			frequency++;
			return;
		}

		private void setFrequency(int freq) {
			frequency = freq;
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
			//If the values match and the word is ending, reset. Increment the frequency of that word by 1.
			else if(comparison == letter && letter == '$') {
				current.incrementFrequency();
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
				//Ending of a new word. Add the ending character and increment the word's frequency.
				else if (current.getNextOption() == null && letter == '$') {
					current.setNextOption(new Node(letter));
					
					current = current.getNextOption();
					current.incrementFrequency();
					
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
			current.incrementFrequency();
			
			resetWord();
			numberOfWords++;
		}
		//If the letter in question currently doesn't exist in this linked list, add it.
		else if(comparison == '^') {
			current.setValue(letter);
			current.setNextLetter(new Node('^'));
			current = current.getNextLetter();
	
		}
	}
	protected void add(char letter, int frequency) {
		//This method is only ever called when letter = '$' and current is a node whose value is '^'.
		current.setValue('$');
		current.setFrequency(frequency);

		numberOfWords++;
		resetWord();

	}
	//Searches for the character at a given level of the trie. If found, moves along and returns true.
	//The boolean value 
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
	protected void resetWord() {
		current = first;
	}

	//Class StringFreq is pretty straightforward; these objects
	//are required to compare suffix frequencies, if there are more than 5 possible predictions.
	private class StringFreq {
		String suffix;
		int frequency;
		
		private StringFreq(String suff, int freq) {
			suffix = suff;
			frequency = freq;
		}

		private String getSuffix() {
			return suffix;
		}
		private int getFrequency() {
			return frequency;
		}

	}
	//Predicts the remaining entries, after the user_history trie has already attempted.
	protected String[] predict() {

		Node helperNode = current;
		String currentSuffix = "";
		predictionsNeeded = 5;	

		//Clear out the predictions array.
		for(int i = 0; i < 5; i++)
			predictions[i] = null;

		//Call a recursive helper function to traverse the trie and gather predictions. 
		recursiveHelper(currentSuffix, helperNode);

		//Copy over the predictions from the StringFreq array to the String array.
		String[] result = new String[5];
		for(int i = 0; i < (5 - predictionsNeeded) ; i++) {
			result[i] = (predictions[i].getSuffix());
		}

		return result;
	}
	private void recursiveHelper(String currentSuffix, Node helperNode) {
			//If the end of a word is not yet reached, add that letter to the suffix and recurse to find the rest of the word.
			if(helperNode.getValue() != '$') 
				recursiveHelper(currentSuffix + helperNode.getValue(), helperNode.getNextLetter());
			else {

				StringFreq potentialPrediction = new StringFreq(currentSuffix, helperNode.getFrequency());
				StringFreq temp;

				//Add new predicted suffix to set of predictions where it belongs (keep descending by frequency).
				for(int i = 0; i < (5 - predictionsNeeded); i++) {
						//If the new prediction is more frequent than the old one, replace it. 
						if(potentialPrediction.getFrequency() > predictions[i].getFrequency()) {
							temp = predictions[i];
							predictions[i] = potentialPrediction;
							potentialPrediction = temp;
						}
					}

				if(predictionsNeeded > 0) {
					//Store the prediction of lowest frequency at the end of the array.
					predictions[5 - predictionsNeeded] = potentialPrediction;

					predictionsNeeded--;
				}
				//Otherwise, the array of predictions is full. The prediction of lowest frequency (potentialPrediction) can be trashed.
		
			}
			//Try to move right. If possible, recurse on the right move.
			if(helperNode.getNextOption() != null)
				recursiveHelper(currentSuffix, helperNode.getNextOption());
			else
				return;
	}

	protected String[] retrieveWords() {
		String prefix = "";
		Node helperNode = current;
		words = new String[numberOfWords];

		return recursiveWriter(prefix, helperNode);

	}
	protected int getNumberOfWords() {
		return numberOfWords;
	}
	protected void setNumberOfWords(int num) {
		numberOfWords = num;
		return;
	}
	//A recusive method written in the form of DLBTrie's recursive predictor. 
	private String[] recursiveWriter(String prefix, Node helperNode) {
		if(numberOfWords <= 0) {
				return words;
		}
		else {
			//If the end of a word is not yet reached, add that letter to the suffix and recurse to find the rest of the word.
			if(helperNode.getValue() != '$') 
				words = recursiveWriter(prefix + helperNode.getValue(), helperNode.getNextLetter());
			else {

					//Add word to predictions.
					//
					//
					words[numberOfWords - 1] = prefix + helperNode.getFrequency();
					numberOfWords--;
				
			}
			//Try to move right. If possible, recurse on the right move.
			if(helperNode.getNextOption() != null)
				words = recursiveWriter(prefix, helperNode.getNextOption());
		}
			return words;
	}
}
