import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameControl {

    private final JFrame frame;
    private final JPanel upper;
    private final JPanel lower;

    private final JComboBox<String> difficulty = new JComboBox<>();
    private final JButton reset = new JButton();

    private Board board;

    public GameControl(JFrame frame, JPanel upper, JPanel lower) {
        this.frame = frame;
        this.upper = upper;
        this.lower = lower;

        JPanel left = new JPanel();
        left.setLayout(new FlowLayout(FlowLayout.LEFT));
        left.setBackground(Color.GRAY);
        JPanel center = new JPanel();
        center.setLayout(new FlowLayout(FlowLayout.CENTER));
        center.setBackground(Color.GRAY);
        JPanel right = new JPanel();
        right.setLayout(new FlowLayout(FlowLayout.RIGHT));
        right.setBackground(Color.GRAY);

        upper.setLayout(new BorderLayout());
        lower.setLayout(new FlowLayout());

        upper.setBackground(Color.GRAY);
        lower.setBackground(Color.GRAY);

        difficulty.addItem("EASY");
        difficulty.addItem("MEDIUM");
        difficulty.addItem("HARD");

        difficulty.setBackground(Color.GRAY);
        difficulty.setFont(new Font("SansSerif Bold", Font.PLAIN, 20));
        difficulty.setForeground(Color.BLACK);
        difficulty.setPreferredSize(new Dimension(120, 35));
        difficulty.setFocusable(false);

        difficulty.setSelectedIndex(1);
        board = GameMode.setGameMode(1);

        difficulty.addActionListener(mouseAction());

        reset.setPreferredSize(new Dimension(50, 50));
        reset.addActionListener(mouseAction());
        reset.setIcon(Images.imageProcessFace());

        left.add(BestScore.getContent());

        center.add(reset);

        right.add(difficulty);
        right.add(GameTimer.getContent());

        upper.add(left, BorderLayout.WEST);
        upper.add(center, BorderLayout.CENTER);
        upper.add(right, BorderLayout.EAST);

        lower.add(board.getBoard());
    }

    private ActionListener mouseAction() {
        return e -> {
            lower.remove(board.getBoard());

            if (e.getSource() == reset) {
                board = GameMode.reset();
            } else if (e.getSource() == difficulty) {
                board = GameMode.setGameMode(difficulty.getSelectedIndex());
            }

            lower.add(board.getBoard());

            GameTimer.stopTimer();
            GameTimer.reset();

            refresh();
        };
    }

    private void refresh() {
        frame.revalidate();
        frame.repaint();

        upper.revalidate();
        upper.repaint();

        lower.revalidate();
        lower.repaint();

        frame.pack();
    }

}
