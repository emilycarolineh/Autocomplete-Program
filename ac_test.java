//Emily Higgs
//Autocomplete Program

import java.util.*;
import java.io.*;

public class ac_test {

	//Initially populates the user history trie.
	private static FrequencyDLBTrie populateUserHistory() throws UserHistoryException {

		FrequencyDLBTrie history = new FrequencyDLBTrie();
		String line = "";
		char character = 0;
		int numWords = 0;
		int frequency = 0;

		try {
            FileReader file = new FileReader("user-history.txt");
            BufferedReader buffered = new BufferedReader(file);

			//Read in each line by reading each character on each line.
            while((line = buffered.readLine()) != null) {	

                for(int i = 0; i < line.length(); i++) {
					character = line.charAt(i);

					
					//In this case, you've encountered a frequency/end of word.
					if((int)character > 48 && (int)character < 58) {
						frequency = Integer.parseInt(line.substring(i, line.length()));
						break;
					}
					else {
						history.add(character);
					}
				}
				//End the word, adding its frequency.
				history.add('$', frequency);
				numWords++;
            }   
			
			history.setNumberOfWords(numWords);

            //Close file.
            buffered.close(); 
		}
		catch(FileNotFoundException exception) {
			System.out.println("User history data could not be found. Autocomplete will continue from default dictionary.\n");
		}
		catch (IOException exception) {
            throw new UserHistoryException("Something about your user history is formatted incorrectly. Make sure words are separated by new lines.");
		}
		
		return history;

	}
	//Initially populates the dictionary trie. 
	private static DLBTrie populateDictionary() throws DictionaryException {
		
		DLBTrie dictionary = new DLBTrie();
		String line = "";

		try {
            FileReader file = new FileReader("dictionary.txt");
            BufferedReader buffered = new BufferedReader(file);

			//Read in each line by reading each character on each line.
            while((line = buffered.readLine()) != null) {
                for(int i = 0; i < line.length(); i++) {
					dictionary.add(line.charAt(i));
				}
				//End the word.
					dictionary.add('$');	
            }   

            //Close file.
            buffered.close(); 
		}
		catch(FileNotFoundException exception) {
			throw new DictionaryException("Your dictionary cannot be found. Make sure it is in a file called 'dictionary.txt'.");
		}
		catch (IOException exception) {
            throw new DictionaryException("Something about your dictionary is formatted incorrectly. Make sure words are separated by new lines.");
		}

		return dictionary;

	}
	
	public static void main (String args[]) {

		//Create an empty DLB for user history.
		//Create an initally empty DLB for the dictionary. 
		DLBTrie dictionary = new DLBTrie();
		FrequencyDLBTrie userHistory = new FrequencyDLBTrie();

		//Populate both the dictionary and user history. 
		dictionary = populateDictionary();
		userHistory = populateUserHistory();

		float[] times = new float[100];
		int wordsPredicted = 0;
		float average = 0;
		String wordPrefix = "";

		System.out.println("Welcome to the autocomplete program.");
		System.out.println("Your entry will be cross-referenced with a copy of the English dictionary and a library of your own previous entries.");
		System.out.println("To end a word, enter '$'. To exit at any time, enter '!'.\n\n");


		String[] predictions = new String[5];
		boolean user = true;
		boolean dict = true;
		int posInWord = 0;


		while(true) {

			if(posInWord == 0)
				System.out.println("Enter your first character: ");
			else if(posInWord == 1)
				System.out.println("Enter your next character: ");
			else
				System.out.println("Enter the first character in the next word: ");

			//Prompt user for character.
			Scanner s = new Scanner(System.in);
			char input = s.next().charAt(0);


			if(input == '!') {
				//Calculate average time for prediction.
				for(int i = 0; i < wordsPredicted; i++) {
					average += times[i];

				}
		
				if(average != 0)
					average = average/wordsPredicted; 

				System.out.printf("\nAverage time: %f s\n", average);
				System.out.println("Bye!");

				//Save user history.
				saveUserHistory(userHistory);

				return;
			} 
			else if(input == '$') {
				//In this case, the word is one not recognized by the dictionary. Add it to the user history. 
				
				//Reset both tries in preparation for next word.
				dictionary.resetWord();
				userHistory.resetWord();
				user = true;
				dict = true;
	
	
				//Add wordPrefix to user history.
				for(int i = 0; i < wordPrefix.length(); i++) {
					userHistory.add(wordPrefix.charAt(i));
				}
				userHistory.add('$');
				
				System.out.println("WORD COMPLETED: " + wordPrefix);
				
				//Reset word prefix and prepare to prompt new word.
				wordPrefix = "";
				posInWord = 2;
					
			}
			else if(input == '1') {
				//First check that 1 is a valid input. 
				if(predictions[0] != null) {
					
					wordPrefix += predictions[0];

					System.out.println("WORD COMPLETED: " + wordPrefix);

					//Reset both tries in preparation for next word.
					dictionary.resetWord();
					userHistory.resetWord();
					user = true;
					dict = true;
	
	
					//Add wordPrefix to user history.
					for(int i = 0; i < wordPrefix.length(); i++) {
						userHistory.add(wordPrefix.charAt(i));
					}
					userHistory.add('$');
				
				
					//Reset word prefix and prepare to prompt new word.
					wordPrefix = "";
					posInWord = 2;
				}
			}
			else if(input == '2') {
				//First check that 2 is a valid input.
				if(predictions[1] != null) {
					
					wordPrefix += predictions[1];

					System.out.println("WORD COMPLETED: " + wordPrefix);

					//Reset both tries in preparation for next word.
					dictionary.resetWord();
					userHistory.resetWord();
					user = true;
					dict = true;
	
	
					//Add wordPrefix to user history.
					for(int i = 0; i < wordPrefix.length(); i++) {
						userHistory.add(wordPrefix.charAt(i));
					}
					userHistory.add('$');
				
				
					//Reset word prefix and prepare to prompt new word.
					wordPrefix = "";
					posInWord = 2;
				}
			}
			else if(input == '3') {
				//First check that 3 is a valid input. 
				if(predictions[2] != null) {
	
					wordPrefix += predictions[2];

					System.out.println("WORD COMPLETED: " + wordPrefix);

					//Reset both tries in preparation for next word.
					dictionary.resetWord();
					userHistory.resetWord();
					user = true;
					dict = true;
	
	
					//Add wordPrefix to user history.
					for(int i = 0; i < wordPrefix.length(); i++) {
						userHistory.add(wordPrefix.charAt(i));
					}
					userHistory.add('$');
				
					//Reset word prefix and prepare to prompt new word.
					wordPrefix = "";
					posInWord = 2;
				}
			}
			else if(input == '4') {
				//First check that 1 is a valid input. 
				if(predictions[3] != null) {

					wordPrefix += predictions[3];

					System.out.println("WORD COMPLETED: " + wordPrefix);

					//Reset both tries in preparation for next word.
					dictionary.resetWord();
					userHistory.resetWord();
					user = true;
					dict = true;
	
	
					//Add wordPrefix to user history.
					for(int i = 0; i < wordPrefix.length(); i++) {
						userHistory.add(wordPrefix.charAt(i));
					}
					userHistory.add('$');
				
					//Reset word prefix and prepare to prompt new word.
					wordPrefix = "";
					posInWord = 2;
				}
			}
			else if(input == '5') {
				//First check that 1 is a valid input. 
				if(predictions[4] != null) {

					wordPrefix += predictions[4];

					System.out.println("WORD COMPLETED: " + wordPrefix);

					//Reset both tries in preparation for next word.
					dictionary.resetWord();
					userHistory.resetWord();
					user = true;
					dict = true;
	
	
					//Add wordPrefix to user history.
					for(int i = 0; i < wordPrefix.length(); i++) {
						userHistory.add(wordPrefix.charAt(i));
					}
					userHistory.add('$');
				
					//Reset word prefix and prepare to prompt new word.
					wordPrefix = "";
					posInWord = 2;
				}
			}
			else {
	
				wordPrefix += input;

				//Start the timer. 
				long startTime = System.nanoTime();
	
				//Empty predictions array.
				for(int i = 0; i < 5; i++)
					predictions[i] = null;
			
	
				//Search user_history for character.
				if(user)
					user = userHistory.search(input);
				//Search dictionary for character.
				if(dict)
					dict = dictionary.search(input);
				
				//Pass predictions array to user_history trie, if it's possible for it to hold value. 
				if(user) {
					predictions = userHistory.predict();
				}
				
				//Check if there need to be predictions supplied by the dictionary.
				int predictionsNeeded = 0;
	
				for(int i = 0; i < 5; i++) {
					if(predictions[i] == null) {
						predictionsNeeded = 5 - i;
						break;
					}
				}
				
				//If incomplete, pass to dictionary trie.
				if(dict && predictionsNeeded > 0)
					predictions = dictionary.predict(predictions, predictionsNeeded);
				  
				//At this point, either way, you have predicted as much as possible. Move on.
	
				//Stop the timer and convert from nanoseconds to seconds.
 				long totalTime = System.nanoTime() - startTime; 
				float timeInSeconds = (float)totalTime / (float)1000000000.0;
		
				//Store the total time for later averaging.
				if(wordsPredicted <= 100) {
					times[wordsPredicted] = timeInSeconds;
				}
				wordsPredicted++;
					
				System.out.printf("\n(%f s)\n", timeInSeconds);

				if(predictions[0] != null) {
					System.out.println("Predictions: ");
					
	
					//Print out each available prediction. 
					for(int i = 0; i < predictions.length; i++) {
						if(predictions[i] != null)
							System.out.print("(" + (i + 1) + ") " + wordPrefix + predictions[i] + "     ");
					}
				}
				else 
					System.out.println("No predictions have been found, but feel free to continue entering letters.");
				System.out.println("\n");
	
				posInWord = 1;
			}
		}  	
	}
	//Write user history to 'user-history.txt'.
	protected static void saveUserHistory(FrequencyDLBTrie history) throws UserHistoryException {
		//Save a copy of 'user_history.txt'.

		try {
			BufferedWriter buffered = new BufferedWriter(new FileWriter("user-history.txt"));
		 	String[] words;

			int num = history.getNumberOfWords();
			char character; 

			words = history.retrieveWords();


			//Iterate through array of words.
			for(int i = 0; i < num; i++) {
				//Iterate through the word, writing each character to the file.
				for(int j = 0; j < words[i].length(); j++) {
					character = (words[i]).charAt(j);

					buffered.write(character);
				}
				buffered.newLine();
			}

			//Close file.
            buffered.close(); 
		}
		catch(IOException exception) {
			throw new UserHistoryException("User history could not be saved.");
		}
	}
}
