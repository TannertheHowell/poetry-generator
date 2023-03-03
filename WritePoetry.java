import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class WritePoetry {
    // IntelliJ suggested the FileNotFoundException to catch a bad file
    public String writePoem(String file, String startWord, int length, boolean printHashtable) {

        if (printHashtable){
            // print the hashtable
        }
        return null;
    }

    public static void readAndBuild(String file)  throws FileNotFoundException{
        // Making the new hashtable
        HashTable<String, WordFreqInfo> table = new HashTable<>();

        Scanner scanner = new Scanner(new File(file));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] words = line.split("[ ]");

            // Loop over the words in each line
            for (int i = 0; i < words.length; i++) {
                String word = words[i];

                // Check if the line is empty
                if (!word.isEmpty()) {
                    word = word.toLowerCase();
                    char lastChar = word.charAt(word.length() - 1);

                    // Checking to see if there is punctuation at the end of the word
                    if (lastChar == '.' || lastChar == ',' || lastChar == '!' || lastChar == '?') {
                        String wordWithoutPunc = word.substring(0, word.length() - 1);
                        table = addWordToTable(table, wordWithoutPunc, Character.toString(lastChar));
                        if ( i < words.length - 1){
                            table = addWordToTable(table, Character.toString(lastChar), words[i + 1]);
                        }

                    } else{
                        // Words without punctuation are just added to the table
                        if ( i < words.length - 1){
                            table = addWordToTable(table, word, words[i + 1]);
                        }
                    }
                }
            }
        }
    }

    private static HashTable<String, WordFreqInfo> addWordToTable (HashTable<String, WordFreqInfo> table, String wordToAdd, String wordFollow){
        if (table.contains(wordToAdd)){
            table.find(wordToAdd).updateFollows(wordFollow);
        } else{
            WordFreqInfo WFI = new WordFreqInfo(wordToAdd, 0);
            table.insert(wordToAdd, WFI);
            table.find(wordToAdd).updateFollows(wordFollow);
        }
        return table;
    }

}
