package simulation;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


import java.util.Random;

/**
 * This class provides a bunch of auxiliary methods whose purpose is to get a neater, better-organized and easily
 * readable code.
 * @author Eryk Trzeciakiewicz
 */
public class Utility
{
    private static Random random = new Random();

    /**
     * Returns the integer value of th input text field
     * @param textField The text field whose value is to be returned as an integer.
     * @return An integer parsed from the TextField's text.
     */
    public static int integerFromTextField(TextField textField)
    {
        return Integer.parseInt(textField.getText());
    }

    /**
     * Returns the double value of the input text field
     * @param textField The text field whose value is to be returned as a double number.
     * @return A double value parsed from the TextField's text.
     * @see #integerFromTextField(TextField)
     */
    public static double doubleFromTextField(TextField textField)
    {
        return Double.parseDouble(textField.getText());
    }

    /**
     * Returns a random Color generated using the rgb() scheme.
     * @return a random Color
     */
    public static Color getRandomColor()
    {
        int red = random.nextInt(255);
        int green = random.nextInt(255);
        int blue = random.nextInt(255);
        return Color.rgb(red, green, blue);
    }

    /**
     * Returns a random integer starting from between 0 and max inclusively.
     * @param max the maximum integer that can be drawn.
     * @return a random integer bounded by 0 and max
     */
    public static int getRandomNumber(int max)
    {
        return random.nextInt(max);
    }

    /**
     * Returns the color of the square passed as its argument.
     * @param square the Rectangle object whose fill is to be returned
     * @return The Color of the input Rectangle.
     */
    public static Color getSquareColor(Rectangle square)
    {
        return (Color)square.getFill();
    }

    /**
     * Returns a random number from the interval [0, 1)
     * @return a random number from the interval [0, 1)
     */
    public static double getRandom()
    {
        return random.nextDouble();
    }

    /**
     * Returns the average color of the top, bottom, left and right neighbor of the input Rectangle.
     * @param currentSquare the square the average color of whose neighbors is to be returned.
     * @param squares the 2-dimensional array containing all squares on the board
     * @param n the total number of rows
     * @param m the total nubmer of columns
     * @param row the row the currentSquare is in in the array.
     * @param column the column the current square is in the array.
     * @return The color being the average color of the four neighbors of the input currentSquare generated in the rgb() scheme.
     */
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
