import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Random;

public class Scrabble 
{
    // variables
    private Tile[] tiles;

    public Scrabble() 
    {
        // creates an array of 7 tiles each containing a random uppercase letter from A to Z
        this.tiles = new Tile[7]; 
        Random rand = new Random();

        for (int i = 0; i < tiles.length; i++) 
        {
            char randomLetter = (char) ('A' + rand.nextInt(26)); 
            tiles[i] = new Tile(randomLetter); 
        }
    } 

    // validates the tile array ensuring that it contains 7 elements 
    public Scrabble(Tile[] tiles) 
    {
        // throws an illegalargumentexception if the condition is not met 
        if (tiles.length != 7) 
        {
            throw new IllegalArgumentException("Tile array doesn't have 7 elements!");
        }
        this.tiles = tiles;
    }

    // returns a string containing the letters from the tile array 
    public String getLetters() 
    {
        StringBuilder letters = new StringBuilder(); 
        for (Tile tile : tiles) 
        {
            letters.append(tile.getLetter()); 
        }
        return letters.toString();
    }
    
    public ArrayList<String> getWords() throws FileNotFoundException 
    {
        // stores all string of letters that can spell a word into an array list
        ArrayList<String> spellableWords = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("CollinsScrabbleWords2019.txt"))) 
        {
            String word;

            // reads the file line by line 
            while ((word = br.readLine()) != null) 
            {    
                // if length of the word is greater than 7 letters, moves on as we only have space for 7 tiles
                if (word.length() > 7) 
                {
                    continue;
                }

                // takes the letters from our tiles 
                String myLetters = getLetters(); 
                boolean canSpell = true;

                // for every letter checks if it can spell a word 
                for (int i = 0; i < word.length(); i++) 
                {
                    char letter = word.charAt(i);

                    // checks if the letter exists in our current tile letters
                    if (myLetters.indexOf(letter) != -1) 
                    {

                        // if it does, remove one instance of it so it can't be reused
                        myLetters = myLetters.replaceFirst(String.valueOf(letter), "");
                    } 
                    else 
                    {

                        // if the letter doesn't exist, we can't spell the word
                        canSpell = false;
                        break; // stops checking the rest of the word
                    }
                }

                // if we were able to spell the whole word, add it to the list
                if (canSpell) 
                {
                    spellableWords.add(word);
                }
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        return spellableWords;
    }
    public int[] getScores() throws FileNotFoundException 
    {
        // gets all the words we can spell using the tiles we have
        ArrayList<String> spellableWords = this.getWords();

        // creates an array to store the score of each word
        int[] wordScores = new int[spellableWords.size()];

        // loops through every word
        for (int i = 0; i < spellableWords.size(); i++) 
        {
            String word = spellableWords.get(i);
            int score = 0;

            // adds up the score for each letter in the word
            for (int j = 0; j < word.length(); j++) 
            {
                char letter = word.charAt(j);
                score += getLetterValue(letter); // gets the value for each letter
            }

            // stores the final score of the word in the array
            wordScores[i] = score;
        }

        // sorts all the word scores in ascending order
        Arrays.sort(wordScores);

        // returns the sorted array
        return wordScores;
    }
    private int getLetterValue(char letter) 
    {
        // returns the value of the letter based on scrabble rules
        if (letter == 'A' || letter == 'E' || letter == 'I' || letter == 'L' || letter == 'N' || letter == 'O' || letter == 'R' || letter == 'S' || letter == 'T' || letter == 'U') 
        {
            return 1;
        } 
        else if (letter == 'D' || letter == 'G') 
        {
            return 2;
        } 
        else if (letter == 'B' || letter == 'C' || letter == 'M' || letter == 'P') 
        {
            return 3;
        } 
        else if (letter == 'F' || letter == 'H' || letter == 'V' || letter == 'W' || letter == 'Y') 
        {
            return 4;
        } 
        else if (letter == 'K') 
        {
            return 5;
        } 
        else if (letter == 'J' || letter == 'X') 
        {
            return 8;
        } 
        else if (letter == 'Q' || letter == 'Z') 
        {
            return 10;
        } 
        else 
        {
            // if it's not a valid letter, throw an error
            throw new IllegalArgumentException("Invalid Scrabble letter: " + letter);
        }
    }

    public boolean equals(Scrabble s) 
    {
        // gets the letters from both scrabble objects and turns them into arrays
        char[] thisCharArray = this.getLetters().toCharArray();
        char[] otherCharArray = s.getLetters().toCharArray();

        // sorts both arrays so we can compare them regardless of order
        Arrays.sort(thisCharArray); 
        Arrays.sort(otherCharArray);

        // returns true if both scrabble hands have the same letters
        return Arrays.equals(thisCharArray, otherCharArray);
    }
}