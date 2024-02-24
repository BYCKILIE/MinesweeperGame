import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

public class GameTimer {

    private static boolean running = false;
    private static final AtomicInteger time = new AtomicInteger();
    private static final JLabel label = new JLabel();

    public static void reset() {
        time.set(0);
        setLabelTime(0);
    }

    private static void setLabelTime(int val) {
        if (val < 10) {
            label.setText("00" + val);
        } else if (val < 100) {
            label.setText("0" + val);
        } else {
            label.setText(String.valueOf(val));
        }
    }

    public static void startTimer() {
        running = true;
        Thread thread = new Thread(() -> {
            while (running) {
                setLabelTime(time.getAndIncrement());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();
    }

    public static void stopTimer() {
        running = false;
        time.decrementAndGet();
    }

    public static JPanel getContent() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 1, -5));
        panel.setBackground(Color.BLACK);

        label.setFont(new Font("SansSerif Bold", Font.BOLD, 35));
        label.setForeground(new Color(190, 0, 0));

        setLabelTime(0);

        panel.add(label);
        return panel;
    }

    public static int getCurrTime() {
        return time.get();
    }
}
