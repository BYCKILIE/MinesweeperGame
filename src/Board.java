import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Board {

    private final JPanel board = new JPanel();

    private final static int SQUARE_SIZE = 25;
    private static int H, W, NR_BOMBS, gameOver;
    private static boolean started;

    public Board(int H, int W, int NR_BOMBS) {
        Board.H = H;
        Board.W = W;
        Board.NR_BOMBS = NR_BOMBS;
        Board.started = false;
        Board.gameOver = 0;

        int height = H * SQUARE_SIZE;
        int width = W * SQUARE_SIZE - 5;

        board.setLayout(new FlowLayout(FlowLayout.CENTER, -1, -1));
        board.setPreferredSize(new Dimension(width, height));
        board.setBackground(Color.GRAY);

        JButton[][] matrix = new JButton[H][W];
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                matrix[i][j] = new JButton();
                matrix[i][j].setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
                matrix[i][j].setFocusable(false);
                matrix[i][j].addMouseListener(new ButtonMouseAdapter(i, j));
                matrix[i][j].setIcon(Images.facingDown());

                board.add(matrix[i][j]);
            }
        }

        Gameplay.init(matrix);
    }

    private static class ButtonMouseAdapter extends MouseAdapter {

        private final int row, col;

        public ButtonMouseAdapter(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            if (Board.gameOver != 0) {
                return;
            }
            if (e.getButton() == 1) {
                Gameplay.clickAndHoldEffect(row, col);
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            super.mouseReleased(e);
            if (Board.gameOver != 0) {
                return;
            }

            if (e.getButton() == 3) {
                Gameplay.mark(row, col);
            } else if (e.getButton() == 1) {
                if (!Board.started) {
                    Gameplay.generate(row, col);
                    GameTimer.startTimer();
                    Board.started = true;
                }
                int[] utilRowCol = Gameplay.releaseEffect(row, col);

                if (utilRowCol != null) {
                    Board.gameOver = Gameplay.discover(utilRowCol[0], utilRowCol[1]);
                } else {
                    Board.gameOver = Gameplay.discover(row, col);
                }

                if (Board.gameOver == 1) {
                    GameTimer.stopTimer();
                    BestScore.setBestScore();
                    // win
                    // music
                } else if (Board.gameOver == -1) {
                    GameTimer.stopTimer();
                    // lose
                    // music
                }
            }
        }
    }

    public static int getH() {
        return H;
    }

    public static int getW() {
        return W;
    }

    public static int getNrBombs() {
        return NR_BOMBS;
    }

    public JPanel getBoard() {
        return board;
    }
}
