public class GameMode {

    private static int difficulty = 1;

    public static Board setGameMode(int diff) {
        Board util;
        if (diff == 0) {
            util = easy();
        } else if (diff == 1) {
            util = medium();
        } else {
            util = hard();
        }
        BestScore.applyBestScore();
        return util;
    }

    private static Board easy() {
        difficulty = 0;
        return new Board(8, 10, 10);
    }

    private static Board medium() {
        difficulty = 1;
        return new Board(14, 18, 40);
    }

    private static Board hard() {
        difficulty = 2;
        return new Board(20, 24, 99);
    }

    public static Board reset() {
        return setGameMode(difficulty);
    }

    public static int getDifficulty() {
        return difficulty;
    }

}
