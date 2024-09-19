import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class EightQueens extends JPanel {
    private final int SIZE = 8;
    private ArrayList<int[]> solutions = new ArrayList<>();
    private int currentSolutionIndex = 0;
    
    public EightQueens() {
        setPreferredSize(new Dimension(500, 500));
        setBackground(Color.WHITE);
        findSolutions(new int[SIZE], 0);
        setupUI();
    }

    // Find all solutions recursively
    private void findSolutions(int[] queens, int row) {
        if (row == SIZE) {
            // Found a valid solution, add a copy to the list
            solutions.add(queens.clone());
        } else {
            for (int col = 0; col < SIZE; col++) {
                if (isSafe(queens, row, col)) {
                    queens[row] = col;
                    findSolutions(queens, row + 1);
                }
            }
        }
    }

    // Check if it's safe to place a queen at the given row and column
    private boolean isSafe(int[] queens, int row, int col) {
        for (int i = 0; i < row; i++) {
            int diff = Math.abs(col - queens[i]);
            if (diff == 0 || diff == row - i) {
                return false;
            }
        }
        return true;
    }

    // Setup UI components
    private void setupUI() {
        JButton nextButton = new JButton("Next");
        JButton prevButton = new JButton("Previous");

        nextButton.addActionListener(e -> showNextSolution());
        prevButton.addActionListener(e -> showPreviousSolution());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);

        JFrame frame = new JFrame("Eight Queens");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(this, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Show the next solution
    private void showNextSolution() {
        if (!solutions.isEmpty()) {
            currentSolutionIndex = (currentSolutionIndex + 1) % solutions.size();
            repaint();
        }
    }

    // Show the previous solution
    private void showPreviousSolution() {
        if (!solutions.isEmpty()) {
            currentSolutionIndex = (currentSolutionIndex - 1 + solutions.size()) % solutions.size();
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
        if (!solutions.isEmpty()) {
            drawQueens(g, solutions.get(currentSolutionIndex));
        }
    }

    // Draw the chessboard
    private void drawBoard(Graphics g) {
        int squareSize = getWidth() / SIZE;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if ((i + j) % 2 == 0) {
                    g.setColor(Color.LIGHT_GRAY);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(j * squareSize, i * squareSize, squareSize, squareSize);
            }
        }
    }

    // Draw queens on the board
    private void drawQueens(Graphics g, int[] queens) {
        int squareSize = getWidth() / SIZE;
        g.setColor(Color.RED);
        for (int i = 0; i < SIZE; i++) {
            int col = queens[i];
            g.fillOval(col * squareSize, i * squareSize, squareSize, squareSize);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EightQueens::new);
    }
}
