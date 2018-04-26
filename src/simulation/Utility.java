package simulation;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


import java.util.Random;

public class Utility
{
    private static Random random = new Random();

    public static int integerFromTextField(TextField textField)
    {
        return Integer.parseInt(textField.getText());
    }

    public static double doubleFromTextField(TextField textField)
    {
        return Double.parseDouble(textField.getText());
    }

    public static Color getRandomColor()
    {
        int red = random.nextInt(255);
        int green = random.nextInt(255);
        int blue = random.nextInt(255);
        return Color.rgb(red, green, blue);
    }

    public static int getRandomNumber(int max)
    {
        return random.nextInt(max);
    }

    public static Color getSquareColor(Rectangle square)
    {
        return (Color)square.getFill();
    }

    public static double getRandom()
    {
        return random.nextDouble();
    }

    public static Color getAverageColor(Rectangle currentSquare, Rectangle[][] squares, int n, int m, int row, int column)
    {
        Color topNeighbor = Utility.getSquareColor(squares[(row+1)%n][column]);
        Color bottomNeighbor = Utility.getSquareColor(squares[((row-1)%n+n)%n][column]);
        Color leftNeighbor = Utility.getSquareColor(squares[row][((column-1)%m+m)%m]);
        Color rightNeighbor = Utility.getSquareColor(squares[row][(column+1)%m]);
        double newRed = ( 0.25 * (topNeighbor.getRed() + bottomNeighbor.getRed() + leftNeighbor.getRed() + rightNeighbor.getRed()) );
        double newGreen =( 0.25 * (topNeighbor.getGreen() + bottomNeighbor.getGreen() + leftNeighbor.getGreen() + rightNeighbor.getGreen()));
        double newBlue = ( (0.25 * (topNeighbor.getBlue() + bottomNeighbor.getBlue() + leftNeighbor.getBlue() + rightNeighbor.getBlue())));
        Color newColor = Color.rgb((int)(255 *newRed), (int)(255 *newGreen), (int)(255 *newBlue));
        return newColor;
    }
}
