import javax.swing.*;
import java.util.Random;

public class Gameplay {

    private static final Random random = new Random();
    private static final int[] VH = new int[]{-1, 0, 1, 0, -1, -1, 1, 1};
    private static final int[] VW = new int[]{0, 1, 0, -1, -1, 1, 1, -1};

    private static char[][] clearBoard;
    private static char[][] viewBoard;
    private static JButton[][] buttonBoard;
    private static int H, W, NR_BOMBS;
    private static int status, target;

    public static void init(JButton[][] buttonBoard) {
        Gameplay.H = Board.getH();
        Gameplay.W = Board.getW();
        Gameplay.NR_BOMBS = Board.getNrBombs();
        Gameplay.buttonBoard = buttonBoard;

        clearBoard = new char[H][W];
        viewBoard = new char[H][W];
        status = 0;
        target = H * W - NR_BOMBS;

        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                clearBoard[i][j] = '0';
                viewBoard[i][j] = '?';
            }
        }
    }

    public static void generate(int row, int col) {
        int[] pos;
        for (int i = 0; i < NR_BOMBS; i++) {
            do {
                pos = randomBombPos();
            } while (!bombCorrectPos(row, col, pos));
            placeBomb(pos[0], pos[1]);
        }
    }

    private static int[] randomBombPos() {
        return new int[]{random.nextInt(0, H), random.nextInt(0, W)};
    }

    private static boolean bombCorrectPos(int row, int col, int[] pos) {
        if (clearBoard[pos[0]][pos[1]] == 'X') {
            return false;
        }
        if (pos[0] == row && pos[1] == col) {
            return false;
        }
        int posH, posW;

        for (int i = 0; i < 8; i++) {
            posH = row + VH[i];
            posW = col + VW[i];

            if (pos[0] == posH && pos[1] == posW) {
                return false;
            }
        }
        return true;
    }

    private static void placeBomb(int row, int col) {
        clearBoard[row][col] = 'X';
        int posH, posW;

        for (int i = 0; i < 8; i++) {
            posH = row + VH[i];
            posW = col + VW[i];
            if (outsideCond(posH, posW)) {
                continue;
            } else if (clearBoard[posH][posW] == 'X') {
                continue;
            }
            clearBoard[posH][posW] += 1;
        }
    }

    private static boolean discoverUtil(int row, int col) {
        if (outsideCond(row, col)) {
            return false;
        } else if (viewBoard[row][col] != '?') {
            return false;
        } else if (clearBoard[row][col] == 'X') {
            return true;
        }
        playerView(row, col);
        viewBoard[row][col] = '.';
        status += 1;

        if (clearBoard[row][col] == '0') {
            for (int k = 0; k < 8; ++k) {
                discoverUtil(row + VH[k], col + VW[k]);
            }
        }
        return false;
    }

    public static int discover(int row, int col) {
        if (discoverUtil(row, col)) {
            loseBoard(row, col);
            return -1;
        } else if (status == target) {
            winBoard();
            return 1;
        }
        return 0;
    }

    private static void winBoard() {
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (clearBoard[i][j] == 'X') {
                    buttonBoard[i][j].setEnabled(false);
                }
            }
        }
    }

    private static void loseBoard(int row, int col) {
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (viewBoard[i][j] == '.') {
                    continue;
                } else if (i == row && j == col) {
                    buttonBoard[i][j].setIcon(Images.imageRedBomb());
                    continue;
                } else if (clearBoard[i][j] == 'X') {
                    if (viewBoard[i][j] == '?') {
                        buttonBoard[i][j].setIcon(Images.imageBomb());
                    }
                    continue;
                } else if (clearBoard[i][j] != 'X' && viewBoard[i][j] == '!') {
                    buttonBoard[i][j].setIcon(Images.imageXFlagged());
                    continue;
                }
                playerView(i, j);
            }
        }
    }

    public static void mark(int row, int col) {
        if (viewBoard[row][col] == '.') {
            return;
        } else if (viewBoard[row][col] == '!') {
            viewBoard[row][col] = '?';
            buttonBoard[row][col].setIcon(Images.facingDown());
            NR_BOMBS += 1;
            return;
        }
        viewBoard[row][col] = '!';
        buttonBoard[row][col].setIcon(Images.imageFlagged());
        NR_BOMBS -= 1;
    }

    public static void clickAndHoldEffect(int row, int col) {
        if (viewBoard[row][col] == '!') {
            return;
        }
        if (viewBoard[row][col] == '?') {
            buttonBoard[row][col].setIcon(Images.image(0));
            return;
        }
        for (int i = 0; i < 8; ++i) {
            if (outsideCond(row + VH[i], col + VW[i])) {
                continue;
            }
            if (viewBoard[row + VH[i]][col + VW[i]] != '?') {
                continue;
            }
            buttonBoard[row + VH[i]][col + VW[i]].setIcon(Images.image(0));
        }
    }

    public static int[] releaseEffect(int row, int col) {
        if (viewBoard[row][col] != '.') {
            return null;
        }
        boolean flag1 = true, flag2 = false;
        for (int i = 0; i < 8; ++i) {
            if (outsideCond(row + VH[i], col + VW[i])) {
                continue;
            }
            if (viewBoard[row + VH[i]][col + VW[i]] == '.') {
                continue;
            }
            if (viewBoard[row + VH[i]][col + VW[i]] == '!' &&
                    clearBoard[row + VH[i]][col + VW[i]] != 'X') {
                return loseFromEffect(row, col);
            }
            if (viewBoard[row + VH[i]][col + VW[i]] == '?' &&
                    clearBoard[row + VH[i]][col + VW[i]] == 'X') {
                flag1 = false;
            }
            if (viewBoard[row + VH[i]][col + VW[i]] == '!' &&
                    clearBoard[row + VH[i]][col + VW[i]] == 'X') {
                flag2 = true;
            }
        }
        if (flag1 && flag2) {
            discoverByEffect(row, col);
        } else {
            repairEffect(row, col);
        }
        return null;
    }

    private static void repairEffect(int row, int col) {
        for (int i = 0; i < 8; ++i) {
            if (outsideCond(row + VH[i], col + VW[i])) {
                continue;
            }
            if (viewBoard[row + VH[i]][col + VW[i]] != '?') {
                continue;
            }
            buttonBoard[row + VH[i]][col + VW[i]].setIcon(Images.facingDown());
        }
    }

    private static void discoverByEffect(int row, int col) {
        for (int i = 0; i < 8; ++i) {
            if (outsideCond(row + VH[i], col + VW[i])) {
                continue;
            }
            if (viewBoard[row + VH[i]][col + VW[i]] != '?') {
                continue;
            }
            discover(row + VH[i], col + VW[i]);
        }
    }

    private static int[] loseFromEffect(int row, int col) {
        for (int i = 0; i < 8; ++i) {
            if (outsideCond(row + VH[i], col + VW[i])) {
                continue;
            }
            if (viewBoard[row + VH[i]][col + VW[i]] != '?') {
                continue;
            }
            if (viewBoard[row + VH[i]][col + VW[i]] == '?' &&
                    clearBoard[row + VH[i]][col + VW[i]] == 'X') {
                return new int[]{row + VH[i], col + VW[i]};
            }
        }
        return null;
    }

    private static void playerView(int row, int col) {
        buttonBoard[row][col].setIcon(Images.image(clearBoard[row][col] - '0'));
    }

    private static boolean outsideCond(int row, int col) {
        return row < 0 | row > H - 1 | col < 0 | col > W - 1;
    }
}
