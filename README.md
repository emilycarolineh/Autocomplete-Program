# Autocomplete-Program
This program was originally submitted as a classwork assignment focusing on mastery of search algorithms and search tables. I chose to use modified de la Briandias tries in my implementation for their optimal performance with relatively sparse data sets and prefixed searches.


# Program Behavior
Entering letters one at a time, the user is provided up to five suggested words prefixed with their input. Each time a user enters a letter, the program outputs the amount of time taken by the program to populate those suggestions. Suggestions are found in the default dictionary ('dictionary.txt') and words previously entered by the user ('user-history.txt', which is generated by the program if it does not yet exist). These words are stored within DLB tries upon starting the program. Users can select suggested words by entering the number associated with them in their suggestion or end a word that is not suggested by entering '$'. Suggestion precedence is given to more frequenty entered words from the user history than less frequently entered words or suggestions from the default dictionary.  To end the program, the user can enter '!' at any time. Upon exit, the average time taken to generate word predictions is calculated and displayed.



# How to Run
To run this program, all files must reside in the same directory. Compile on the command line with 'javac ac_test.java' and run with 'java ac_test'. 
