package simulation;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Controller
{
    @FXML
    private GridPane simulationGridPane;
    @FXML
    private TextField mTextField, nTextField, kTextField, pTextField;
    @FXML
    private Button startStopBtn;
    private int m, n, speed;
    private double probability;
    private Rectangle[][] squares;
    private AnimationThread[][] threads;
    private boolean continueSimulation;

    private class AnimationThread extends Thread
    {
        private int row, column;

        public synchronized void run()
        {

            while (continueSimulation)
            {
                try
                {
                    Rectangle currentSquare = squares[row][column];
                    int threshold = (int) (probability * 1000);
                    int randomNumber = Utility.getRandomNumber(1000);
                    Color newColor = (randomNumber <= threshold) ? (Utility.getRandomColor()) : Utility.getAverageColor(currentSquare, squares, n, m, row, column);
                    Platform.runLater(() -> currentSquare.setFill(newColor));
                    wait((long) (speed * (0.5 + Utility.getRandom())));
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }

        public AnimationThread(int row, int column)
        {
            this.row = row;
            this.column = column;
        }
    }

    public void startSimulation()
    {
        initializeFields();
        initializeBoard();
        simulate();
    }

    public void stopSimulation()
    {
        continueSimulation = false;
        startStopBtn.setText("Start");
        startStopBtn.setOnAction(event -> startSimulation());
        squares = null;
        threads = null;
        simulationGridPane.getChildren().clear();
    }

    public void initializeBoard()
    {
        Rectangle[][] squares = new Rectangle[n][m];
        AnimationThread[][] threads = new AnimationThread[n][m];
        final double height = simulationGridPane.getHeight() - n * simulationGridPane.getVgap();
        final double divisor = Math.max(m, n);
        final double side = height/divisor;

        for (int row = 0; row < n; row++)
        {
            for (int column = 0; column < m; column++)
            {
                Rectangle rect = new Rectangle(side, side);
                rect.setFill(Utility.getRandomColor());
                threads[row][column] = new AnimationThread(row, column);
                simulationGridPane.add(rect, column, row);
                squares[row][column] = rect;
            }
        }
        this.squares = squares;
        this.threads = threads;
        startStopBtn.setText("Stop");
        startStopBtn.setOnAction(event -> stopSimulation());
    }

    public void initializeFields()
    {
        continueSimulation = true;
        m = Utility.integerFromTextField(mTextField);
        n = Utility.integerFromTextField(nTextField);
        speed = Utility.integerFromTextField(kTextField);
        probability = Utility.doubleFromTextField(pTextField);
    }

    public synchronized void simulate()
    {
        for (AnimationThread[] threadArray : threads)
        {
            for (AnimationThread thread : threadArray)
            {
                thread.start();
            }
        }
    }
}
