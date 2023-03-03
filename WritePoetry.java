import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class WritePoetry {
    // Making the new hashtable
    HashTable<String, WordFreqInfo> table = new HashTable<>();

    // IntelliJ suggested the FileNotFoundException to catch a bad file
    public String writePoem(String file, String startWord, int length, boolean printHashtable) {

        if (printHashtable){
            // print the hashtable
        }
        return null;
    }

    public static void readAndBuild(String file)  throws FileNotFoundException{
        Scanner scanner = new Scanner(new File(file));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] words = line.split("[ ]");
            for (String word : words) {
                if (!word.isEmpty()) {
                    word = word.toLowerCase();
                    char lastChar = word.charAt(word.length() - 1);
                    if (lastChar == '.' || lastChar == ',' || lastChar == '!' || lastChar == '?') {
                        System.out.printf("The current word is: %s\n", word.substring(0, word.length() - 1));
                        System.out.printf("Followed by a punctuation mark: %s\n", lastChar);
                        // add the word minus the punctuation to the hashtable
                        // add the punctuation to the hashtable
                    } else{
                        // if no punctuation, just add it to the hashtable
                        System.out.printf("The current word is: %s\n", word);
                    }
                }
            }
        }
    }

}
