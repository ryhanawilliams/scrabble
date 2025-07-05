import java.util.Random;

public class Tile 
{

    // variables
    private char letter;

    // sets the letter to a random uppercase letter
    public Tile() 
    {
        this.letter = generateLetter(); 
    }

    // sets the letter to a specific character
    public Tile(char c) 
    {
        this.letter = c;
    }

    // generates a random uppercase letter from A to Z
    private char generateLetter() 
    {
        Random random = new Random();
        // the ASCII value of 'A' is 65 and 'Z' is 90
        return (char) (random.nextInt(26) + 'A');
    }

    // returns the letter on the tile
    public char getLetter() 
    {
        return letter;
    }
}