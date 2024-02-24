import javax.swing.*;
import java.awt.*;

public class MainFrame {

    public MainFrame() {
        Images.init();

        JFrame frame = new JFrame("Minesweeper");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.GRAY);
        frame.setIconImage(new ImageIcon("images/flagged.png").getImage());


        JPanel upper = new JPanel(), lower =new JPanel();

        frame.add(upper, BorderLayout.NORTH);
        frame.add(lower, BorderLayout.SOUTH);

        new GameControl(frame, upper, lower);

        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
