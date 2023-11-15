/*
Programmer: Kai Schenkel
Class: CS 145
Data: 11/13/2023
Assignment: Lab 5 PascalTriangle
FileName: PascalTriangle.java
*/
import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

//This class is useing the methods from the JFrame class, 
public class PascalTriangle extends JFrame {

    private static int ROWS;
    private static int CELL_SIZE;
    private static int MODULUS_NUMBER;
    private static int GAP;
    private static final int ANIMATION_DELAY = 0; // Milliseconds
    
   

    public static void main(String[] args) {
        getInputs_FromUser();
        SwingUtilities.invokeLater(() -> new PascalTriangle());
    }

//This method will set the values for the Triangel patten that will be print of the Screen
    public static void getInputs_FromUser() {
        Scanner inputScanner = new Scanner(System.in);

        System.out.println("How many rows do you want to go down?");
        ROWS = inputScanner.nextInt();

        System.out.println("How big do you want to boxs to in pixels?");
        CELL_SIZE = inputScanner.nextInt();

        System.out.println("Enter pixle gap number");
        GAP = inputScanner.nextInt();

        System.out.println("Enter a number to get a different pattern");
        MODULUS_NUMBER = inputScanner.nextInt();

        inputScanner.close();
    }

//This method sets up the
    public PascalTriangle() {
        //This sets the Title of the Window
        setTitle("Pascal's Triangle");

        //This sets the size of the Window
        setSize(1600, 1000);

        //This will stop the program if the user hits the rex(x) button in to top right corner of the Window 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PascalPanel pascalPanel = new PascalPanel();

        add(pascalPanel);

        setLocationRelativeTo(null);

        //This allows use to see the Window
        setVisible(true);

        // Start the animation
        pascalPanel.startAnimation();
    }

    public class PascalPanel extends JPanel {
        private Timer timer;
        private int currentRow;
        private int currentColumn;

        public PascalPanel() {
            currentRow = 0;
            currentColumn = 0;

        //This is the timer for the animation
            timer = new Timer(ANIMATION_DELAY, e -> {
                if (currentRow < ROWS) {
                    repaint();
                    currentColumn++;
                    if (currentColumn > currentRow) {
                        currentRow++;
                        currentColumn = 0;
                    }
                } else {
                    timer.stop();
                }
            });
        }

    //This method starts the Animation
        public void startAnimation() {
            timer.start();
        }

    //This method dose the pixel manipulation and also grabs the data from the Pascals Triangle method 
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int[][] triangle = generatePascalsTriangle(currentRow + 1);

            for (int i = 0; i <= currentRow; i++) {//This is the Start of the loop that hands the "y" coordinates for the paintComponent

                for (int j = 0; j <= i; j++) {//This is the Start of the loop that hands the "x" coordinates for the paintComponent
                    int value = triangle[i][j];

                    // Highlight odd numbers with Red
                    if (value % MODULUS_NUMBER != 0) {
                        g.setColor(Color.RED);

                    } else {//Other wise paint it Black
                        g.setColor(Color.BLACK);
                    }

                    // This line of code deals with the "x" to to place the box in the Window
                    int x = getWidth() / 2 + j * (CELL_SIZE + GAP) - i * CELL_SIZE / 2;

                    // This line of code deals with the "y" to to place the box in the Window
                    int y = i * (CELL_SIZE + GAP);
                    g.fillRect(x, y, CELL_SIZE, CELL_SIZE);

                    // Draw the number in the center of the cell, if you want to use it dont go pass 10 rows other wise there Numbers get to big
                    //remove the comments around the code to then show the numbers in the Window

                    /*
                    g.setColor(Color.WHITE);
                    String number = Integer.toString(value);
                    FontMetrics fm = g.getFontMetrics();
                    int textWidth = fm.stringWidth(number);
                    int textHeight = fm.getAscent();
                    g.drawString(number, x + (CELL_SIZE - textWidth) / 2, y + (CELL_SIZE + textHeight) / 2);
                    */
                }
            }
        }

    //This method is meant for making the values for the Pascals Triangle to then be sent to the pixel painter  
        public int[][] generatePascalsTriangle(int rows) {
            int[][] triangle = new int[rows][];
            for (int row_Index = 0; row_Index < rows; row_Index++) {// Start of i for loop this deals wit y

                triangle[row_Index] = new int[row_Index + 1];
                triangle[row_Index][0] = 1;
                triangle[row_Index][row_Index] = 1;

                for (int col_Index = 1; col_Index < row_Index; col_Index++) {// Start of j for loop, this deals wit x

                /*
                This is the line of code below is what makes this hole thing work, it has to do with 
                    the nested for loop and the two dimensional array
                */
                triangle[row_Index][col_Index] = triangle[row_Index - 1][col_Index - 1] + triangle[row_Index - 1][col_Index];

                }// End of j for loop
            }// End of i for loop
            return triangle;
        }
    }
}