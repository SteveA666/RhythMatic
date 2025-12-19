package game.rhythm;

public class StatCalculator {

    public static String calculateGrade(int score) {

        if (score > 1000000) {
            return "SS";
        }
        if (score >= 975000) {
            return "S+";
        }
        if (score >= 920000) {
            return "S";
        }
        if (score >= 880000) {
            return "A";
        }
        if (score >= 800000) {
            return "B";
        }
        if (score >= 700000) {
            return "C";
        }
        return "Failed";
    }

    public static int calculateScore(int exP, int ex, int fab, int neat, int miss) {

        int notes = exP + ex + fab + neat + miss;
        if (notes <= 0) {
            return 0;
        }

        // A “unit” is 1,000,000 / notes. EX+ is worth 1.1 units (max score is 1,100,000).
        double unit = 1000000.0 / notes;

        double rawScoreUnits = (1.1 * exP) + (1.0 * ex) + (0.7 * fab) + (0.4 * neat);
        int score = (int) Math.round(unit * rawScoreUnits);

        // Safety clamp (shouldn’t exceed 1,100,000 by design)
        if (score < 0) {
            return 0;
        }
        if (score > 1100000) {
            return 1100000;
        }
        return score;
    }

    public static double calculateAcc(int exP, int ex, int fab, int neat, int miss) {

        int notes = exP + ex + fab + neat + miss;
        if (notes <= 0) {
            return 0;
        }

        // Accuracy weights: (EX + EX+) count as 1.0 each, then Fabulous 0.7, Neat 0.4
        double numerator = (1.0 * (exP + ex)) + (0.7 * fab) + (0.4 * neat);
        double accPercent = (numerator / notes) * 100.0;

        if (accPercent > 100.0) {
            accPercent = 100.0;
        }
        if (accPercent < 0.0) {
            accPercent = 0.0;
        }

        return accPercent;
    }
}

