import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Random;

public class Scrabble 
{
    private Tile[] tiles;

    public Scrabble() 
    {
        this.tiles = new Tile[7]; 
        Random rand = new Random();

        for (int i = 0; i < tiles.length; i++) 
        {
            char randomLetter = (char) ('A' + rand.nextInt(26)); 
            tiles[i] = new Tile(randomLetter); 
        }
    } 

    public Scrabble(Tile[] tiles) 
    {
        if (tiles.length != 7) 
        {
            throw new IllegalArgumentException("Tile array doesn't have 7 elements!");
        }
        this.tiles = tiles;
    }

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
        ArrayList<String> spellableWords = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("CollinsScrabbleWords2019.txt"))) 
        {
            String word;
            while ((word = br.readLine()) != null) 
            {    
                if (word.length() > 7) 
                {
                    continue;
                }

                String myLetters = getLetters(); 
                boolean canSpell = true;

                for (int i = 0; i < word.length(); i++) 
                {
                    char letter = word.charAt(i);
                    if (myLetters.indexOf(letter) != -1) 
                    {
                        myLetters = myLetters.replaceFirst(String.valueOf(letter), "");
                    } 
                    else 
                    {
                        canSpell = false;
                        break;
                    }
                }
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
        ArrayList<String> spellableWords = this.getWords();
        int[] wordScores = new int[spellableWords.size()];

        for (int i = 0; i < spellableWords.size(); i++) 
        {
            String word = spellableWords.get(i);
            int score = 0;

            for (int j = 0; j < word.length(); j++) 
            {
                char letter = word.charAt(j);
                score += getLetterValue(letter);
            }

            wordScores[i] = score;
        }
        Arrays.sort(wordScores);
        return wordScores;
    }
    private int getLetterValue(char letter) 
    {
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
            throw new IllegalArgumentException("Invalid Scrabble letter: " + letter);
        }
    }
    public boolean equals(Scrabble s) 
    {
        char[] thisCharArray = this.getLetters().toCharArray();
        char[] otherCharArray = s.getLetters().toCharArray();

        Arrays.sort(thisCharArray); 
        Arrays.sort(otherCharArray);

        return Arrays.equals(thisCharArray, otherCharArray);
    }
}