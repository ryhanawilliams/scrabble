import java.util.Random;

public class Tile 
{
    private char letter;

    public Tile() 
    {
        this.letter = generateLetter(); 
    }

    public Tile(char c) 
    {
        this.letter = c;
    }

    private char generateLetter() 
    {
        Random random = new Random();
        // the ASCII value of 'A' is 65 and 'Z' is 90
        return (char) (random.nextInt(26) + 'A');
    }

    public char getLetter() 
    {
        return letter;
    }
}