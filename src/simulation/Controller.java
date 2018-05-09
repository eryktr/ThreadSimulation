package simulation;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This class is responsible for the direct communication with the program window. It provides methods initializing, running
 * and updating the simulation.
 * @author Eryk Trzeciakiewicz
 */
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

    /**
     * The thread class responsible for controlling the simulation. One thread will be generated for every
     * field on the board, and so the program will run the total of n x m threads.
     */
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
                    int randomNumber = Utility.getRandomNumber(1000) + 1;
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

        /**
         * The constructor of the Animation Thread
         * @param row the row of the corresponding square
         * @param column the column of the corresponding square
         */
        public AnimationThread(int row, int column)
        {
            this.row = row;
            this.column = column;
        }
    }

    /**
     * Starts the simulation, namely: initializes the fields responsible for it, draws the board and updates
     * simulation information.
     */
    public void startSimulation()
    {
        initializeFields();
        initializeBoard();
        simulate();
    }

    /**
     * Stops the simulation - cancels all running threads, erases the board and enables the user
     * to start it once more.
     */
    public void stopSimulation()
    {
        continueSimulation = false;
        startStopBtn.setText("Start");
        startStopBtn.setOnAction(event -> startSimulation());
        squares = null;
        threads = null;
        simulationGridPane.getChildren().clear();
    }

    /**
     * This method sets up the simulation board. Draws n x m squares, calculates their height based on the input information
     * and creates and populates two 2-dimensional arrays - containing squares and containing threads.
     */
    public void initializeBoard()
    {
        Rectangle[][] squares = new Rectangle[n][m];
        AnimationThread[][] threads = new AnimationThread[n][m];
        final double height = simulationGridPane.getHeight() - n * simulationGridPane.getVgap();
        final double min, max;
        min = Math.min(m, n);
        max = Math.max(m, n);
        final double divisor = (height/min * n < simulationGridPane.getHeight() - n * simulationGridPane.getVgap() ? min : max);
        final double side = height / divisor;

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

    /**
     * Initializes the fields in Controller class
     */
    public void initializeFields()
    {
        continueSimulation = true;
        m = Utility.integerFromTextField(mTextField);
        n = Utility.integerFromTextField(nTextField);
        speed = Utility.integerFromTextField(kTextField);
        probability = Utility.doubleFromTextField(pTextField);
    }

    /**
     * Runs all the threads in the AnimationThread[][] array.
     */
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