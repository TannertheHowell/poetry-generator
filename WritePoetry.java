import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

public class WritePoetry {
    public HashTable<String, WordFreqInfo> table = new HashTable<>();

    // IntelliJ suggested the FileNotFoundException to catch a bad file
    public String writePoem(String file, String startWord, int length, boolean printHashtable) throws FileNotFoundException {
        readAndBuild(file);
        WordFreqInfo WFI = table.find(startWord);
        Random rnd = new Random();
        StringBuilder poemWords = new StringBuilder();

        if (printHashtable){
            System.out.println(table.toString(table.size()));
        }
        for (int i = 0; i < length; i++ ){
            int count = rnd.nextInt((WFI.getOccurCount()));
            String followWord = WFI.getFollowWord(count);

            if (followWord.equals(".")|| followWord.equals("?") || followWord.equals("!") || followWord.equals(",")){
                poemWords.deleteCharAt(poemWords.length() - 1);
                poemWords.append(followWord + "\n");
            } else {
                poemWords.append(followWord + " ");
            }
            WFI = table.find(followWord);
        }
        char lastCharacter = poemWords.charAt(poemWords.length() - 1);
        if (lastCharacter == ' ' ){
            poemWords.deleteCharAt(poemWords.length() - 1);
            poemWords.append(".");
        }
        return poemWords.toString();
    }

    public void readAndBuild(String file)  throws FileNotFoundException{
        // Making the new hashtable
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
                        addWordToTable(wordWithoutPunc, Character.toString(lastChar));
                        if ( i < currentLine.length - 1){
                            addWordToTable(Character.toString(lastChar), currentLine[i + 1]);
                        } else{
                            if (lineIndex < words.size() - 1){
                                String followWord = words.get(lineIndex + 1)[0];
                                addWordToTable(Character.toString(lastChar), followWord);
                            }
                        }

                    } else{
                        // Words without punctuation are just added to the table
                        if ( i < currentLine.length - 1){
                            addWordToTable(word, currentLine[i + 1]);
                        } else{
                            if (lineIndex < words.size() - 1){
                                String followWord = words.get(lineIndex + 1)[0];
                                addWordToTable(word, followWord);
                            }
                        }
                    }
                }
            }
        }
    }

    private void addWordToTable (String wordToAdd, String wordFollow){
        if (wordFollow.length() > 1) {
            char lastChar = wordFollow.charAt(wordFollow.length() - 1);

            // Checking to see if there is punctuation at the end of the word
            if (lastChar == '.' || lastChar == ',' || lastChar == '!' || lastChar == '?') {
                wordFollow = wordFollow.substring(0, wordFollow.length() - 1);
            }
        }

        if (table.contains(wordToAdd)){
            table.find(wordToAdd).updateFollows(wordFollow.toLowerCase());
        } else{
            WordFreqInfo WFI = new WordFreqInfo(wordToAdd, 0);
            table.insert(wordToAdd, WFI);
            table.find(wordToAdd).updateFollows(wordFollow.toLowerCase());
        }
    }
}
