package simulation;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

public class Controller
{
    @FXML
    private GridPane simulationGridPane;
    @FXML
    private TextField mTextField, nTextField, kTextField, pTextField;
    private int m, n;
    private double probability, speed;

    public void startSimulation()
    {
        initializeFields();
        initializeBoard();
    }

    public void initializeBoard()
    {
        final double height = simulationGridPane.getHeight() - n * simulationGridPane.getVgap();
        final double divisor = Math.max(m,n);

        for(int row = 0; row < n; row++)
        {
            for(int column = 0; column < m; column++)
            {
                Rectangle rect = new Rectangle(height/divisor,height/divisor);
                rect.setFill(Utility.getRandomColor());
                simulationGridPane.add(rect, column, row);
            }
        }
    }

    public void initializeFields()
    {
        m = Utility.integerFromTextField(mTextField);
        n = Utility.integerFromTextField(nTextField);
        speed = Utility.doubleFromTextField(kTextField);
        probability = Utility.doubleFromTextField(pTextField);
    }
}
