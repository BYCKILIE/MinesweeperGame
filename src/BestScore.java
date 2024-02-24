import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Scanner;

public class BestScore {

    private static final String scoreFile = "best.score";
    private static final JLabel label = new JLabel();
    private static String[] content;

    public static void applyBestScore() {
        Scanner scanner;
        try {
            scanner = new Scanner(new File(scoreFile));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        content = scanner.nextLine().split(" ");
        setLabelTime(Integer.parseInt(content[GameMode.getDifficulty()]));
    }

    public static void setBestScore() {
        if (GameTimer.getCurrTime() >= Integer.parseInt(content[GameMode.getDifficulty()])) {
            return;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(scoreFile))) {
            content[GameMode.getDifficulty()] = String.valueOf(GameTimer.getCurrTime());
            writer.write(content[0] + " " + content[1] + " " + content[2]);
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
    }

    private static void setLabelTime(int val) {
        if (val == 1000) {
            label.setText("N/A");
            return;
        }

        if (val < 10) {
            label.setText("00" + val);
        } else if (val < 100) {
            label.setText("0" + val);
        } else {
            label.setText(String.valueOf(val));
        }
    }

    public static JPanel getContent() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 1, -5));
        panel.setBackground(Color.BLACK);

        label.setFont(new Font("SansSerif Bold", Font.BOLD, 35));
        label.setForeground(Color.GRAY);

        panel.add(label);
        return panel;
    }

}
