import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class DigitalSort {

    public static List<String> processFile(String path) throws FileNotFoundException {
        List<String> uniqueWords = extractWords(path);
        getSortedList(uniqueWords);
        return uniqueWords;
    }

    private static List<String> extractWords(String path) throws FileNotFoundException {
        List<String> wordList = new ArrayList<>();
        Scanner fileScanner = new Scanner(new File(path));
        while (fileScanner.hasNextLine()) {
            String currentLine = fileScanner.nextLine().toLowerCase();
            String[] words = currentLine.split("\\W+");
            for (String word : words) {
                if (!word.isEmpty() && !wordList.contains(word)) {
                    wordList.add(word);
                }
            }
        }
        return wordList;
    }

    private static void getSortedList(List<String> wordList) {
        int maxLength = findMaxLength(wordList);
        for (int position = maxLength - 1; position >= 0; position--) {
            performCountingSort(wordList, position);
        }
    }

    private static int findMaxLength(List<String> wordList) {
        int maxLen = 0;
        for (String word : wordList) {
            if (word.length() > maxLen) {
                maxLen = word.length();
            }
        }
        return maxLen;
    }

    private static void performCountingSort(List<String> wordList, int position) {
        int[] countArray = new int[27];
        List<String> sortedList = new ArrayList<>(Collections.nCopies(wordList.size(), ""));

        for (String word : wordList) {
            int index = getCharacterIndex(word, position);
            countArray[index]++;
        }

        for (int i = 1; i < countArray.length; i++) {
            countArray[i] += countArray[i - 1];
        }

        for (int i = wordList.size() - 1; i >= 0; i--) {
            String word = wordList.get(i);
            int index = getCharacterIndex(word, position);
            sortedList.set(countArray[index] - 1, word);
            countArray[index]--;
        }

        for (int i = 0; i < wordList.size(); i++) {
            wordList.set(i, sortedList.get(i));
        }
    }

    private static int getCharacterIndex(String word, int position) {
        if (position >= word.length()) {
            return 0;
        }
        return word.charAt(position) - 'a' + 1;
    }

    public static void main(String[] args) {
        try {
            String filePath = "Test.txt";
            List<String> sortedWords = processFile(filePath);
            System.out.println(sortedWords);
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
    }
}
