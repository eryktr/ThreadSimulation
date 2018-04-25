package simulation;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
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

            while (true)
            {
                try
                {
                    Rectangle currentSquare = squares[row][column];
                    Color currentColor = (Color) currentSquare.getFill();
                    int threshold = (int)(probability * 1000);
                    int randomNumber = Utility.getRandomNumber(1000);
                    Color newColor;
                    if(randomNumber <= threshold)
                    {
                        newColor = Utility.getRandomColor();
                    }
                    else
                    {
                        Color topNeighbor = Utility.getSquareColor(squares[(row+1)%n][column]);
                        Color bottomNeighbor = Utility.getSquareColor(squares[((row-1)%n+n)%n][column]);
                        Color leftNeighbor = Utility.getSquareColor(squares[row][((column-1)%m+m)%m]);
                        Color rightNeighbor = Utility.getSquareColor(squares[row][(column+1)%m]);
                        double newRed = ( 0.25 * (topNeighbor.getRed() + bottomNeighbor.getRed() + leftNeighbor.getRed() + rightNeighbor.getRed()) );
                        double newGreen =( 0.25 * (topNeighbor.getGreen() + bottomNeighbor.getGreen() + leftNeighbor.getGreen() + rightNeighbor.getGreen()));
                        double newBlue = ( (0.25 * (topNeighbor.getBlue() + bottomNeighbor.getBlue() + leftNeighbor.getBlue() + rightNeighbor.getBlue())));
                        newColor = Color.rgb((int)(255 *newRed), (int)(255 *newGreen), (int)(255 *newBlue));
                        System.out.printf("%f, %f, %f", 255 * newRed, 255 * newGreen, 255 * newBlue);
                    }
                    Platform.runLater(() -> currentSquare.setFill(newColor));
                    wait((long)(speed * (0.5 + Utility.getRandom())));



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

    public void initializeBoard()
    {
        Rectangle[][] squares = new Rectangle[n][m];
        AnimationThread[][] threads = new AnimationThread[n][m];
        final double height = simulationGridPane.getHeight() - n * simulationGridPane.getVgap();
        final double divisor = Math.max(m,n);

        for(int row = 0; row < n; row++)
        {
            for(int column = 0; column < m; column++)
            {
                Rectangle rect = new Rectangle(height/divisor,height/divisor);
                rect.setFill(Utility.getRandomColor());
                threads[row][column] = new AnimationThread(row, column);
                simulationGridPane.add(rect, column, row);
                squares[row][column] = rect;
            }
        }
        this.squares = squares;
        this.threads = threads;
    }

    public void initializeFields()
    {
        m = Utility.integerFromTextField(mTextField);
        n = Utility.integerFromTextField(nTextField);
        speed = Utility.integerFromTextField(kTextField);
        probability = Utility.doubleFromTextField(pTextField);
        continueSimulation = true;
    }

    public synchronized void simulate()
    {
        for(AnimationThread[] threadArray : threads)
        {
            for(AnimationThread thread : threadArray)
            {
                thread.start();
            }
        }

    }
}
