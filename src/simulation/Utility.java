package simulation;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

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
}
