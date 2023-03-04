import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class WritePoetry {
    // IntelliJ suggested the FileNotFoundException to catch a bad file
    public String writePoem(String file, String startWord, int length, boolean printHashtable) {ArrayList<String> cars = new ArrayList<String>();
        if (printHashtable){
            // print the hashtable
        }
        return null;
    }

    public static HashTable<String, WordFreqInfo> readAndBuild(String file)  throws FileNotFoundException{
        // Making the new hashtable
        HashTable<String, WordFreqInfo> table = new HashTable<>();
        ArrayList<String[]> words = new ArrayList<String[]>();

        Scanner scanner = new Scanner(new File(file));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] lineWords = line.split("[ ]");
            if(!lineWords[0].equals("")){
                words.add(lineWords);
            }
        }

        for(int lineIndex = 0; lineIndex < words.size(); lineIndex++){
            String [] currentLine = words.get(lineIndex);
            // Loop over the words in each line
            for (int i = 0; i < currentLine.length; i++) {
                String word = currentLine[i];

                // Check if the line is empty
                if (!word.isEmpty()) {
                    word = word.toLowerCase();
                    char lastChar = word.charAt(word.length() - 1);

                    // Checking to see if there is punctuation at the end of the word
                    if (lastChar == '.' || lastChar == ',' || lastChar == '!' || lastChar == '?') {
                        String wordWithoutPunc = word.substring(0, word.length() - 1);
                        table = addWordToTable(table, wordWithoutPunc, Character.toString(lastChar));
                        if ( i < currentLine.length - 1){
                            table = addWordToTable(table, Character.toString(lastChar), currentLine[i + 1]);
                        } else{
                            if (lineIndex < words.size() - 1){
                                String followWord = words.get(lineIndex + 1)[0];
                                table = addWordToTable(table, Character.toString(lastChar), followWord);
                            } else{
                                table = addWordToTable(table, Character.toString(lastChar));
                            }
                        }

                    } else{
                        // Words without punctuation are just added to the table
                        if ( i < currentLine.length - 1){
                            table = addWordToTable(table, word, currentLine[i + 1]);
                        } else{
                            if (lineIndex < words.size() - 1){
                                String followWord = words.get(lineIndex + 1)[0];
                                table = addWordToTable(table, word, followWord);
                            } else{
                                table = addWordToTable(table, word);
                            }
                        }
                    }
                }
            }
        }
        return table;
    }

    private static HashTable<String, WordFreqInfo> addWordToTable (HashTable<String, WordFreqInfo> table, String wordToAdd, String wordFollow){
        char lastChar = wordFollow.charAt(wordFollow.length() - 1);

        // Checking to see if there is punctuation at the end of the word
        if (lastChar == '.' || lastChar == ',' || lastChar == '!' || lastChar == '?') {
            wordFollow = wordFollow.substring(0, wordFollow.length() - 1);
        }

        if (table.contains(wordToAdd)){
            table.find(wordToAdd).updateFollows(wordFollow.toLowerCase());
        } else{
            WordFreqInfo WFI = new WordFreqInfo(wordToAdd, 0);
            table.insert(wordToAdd, WFI);
            table.find(wordToAdd).updateFollows(wordFollow.toLowerCase());
        }
        return table;
    }
    private static HashTable<String, WordFreqInfo> addWordToTable (HashTable<String, WordFreqInfo> table, String wordToAdd){
        if (table.contains(wordToAdd)){
            table.find(wordToAdd).updateFollows("");
        } else{
            WordFreqInfo WFI = new WordFreqInfo(wordToAdd, 1);
            table.insert(wordToAdd, WFI);
            table.find(wordToAdd).updateFollows("");
        }
        return table;
    }

}
