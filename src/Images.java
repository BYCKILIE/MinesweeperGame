import javax.swing.*;
import java.awt.*;

public class Images {

    private static final Icon[] imageCorrespondent = new Icon[19];

    public static void init() {
        String[] names = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8",
                "facingDown", "bomb", "red_bomb", "flagged", "XFlagged",
                "process_face", "wow_face", "win_face", "lose_face", "just_bomb"};

        for(int i = 0; i < 19; ++i) {
            if (i < 14) {
                imageCorrespondent[i] = resizeIcon(new ImageIcon("images/" + names[i] + ".png"), 26, 26);
            } else {
                imageCorrespondent[i] = resizeIcon(new ImageIcon("images/" + names[i] + ".png"), 50, 50);
            }
        }
    }

    public static Icon image(int nr) {
        return imageCorrespondent[nr];
    }

    public static Icon facingDown() {
        return imageCorrespondent[9];
    }

    public static Icon imageBomb() {
        return imageCorrespondent[10];
    }

    public static Icon imageRedBomb() {
        return imageCorrespondent[11];
    }

    public static Icon imageFlagged() {
        return imageCorrespondent[12];
    }

    public static Icon imageXFlagged() {
        return imageCorrespondent[13];
    }

    public static Icon imageProcessFace() {
        return imageCorrespondent[14];
    }

    public static Icon imageWowFace() {
        return imageCorrespondent[15];
    }

    public static Icon imageWinFace() {
        return imageCorrespondent[16];
    }

    public static Icon imageLoseFace() {
        return imageCorrespondent[17];
    }

    private static Icon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(width, height, 4);
        return new ImageIcon(resizedImage);
    }

}
