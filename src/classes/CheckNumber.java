package classes;

public class CheckNumber {

    public static boolean checkNumbers(String s) {

        try {
            Double temp = Double.parseDouble(s);

            if (twoDecimalPlaces(s)) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean twoDecimalPlaces(String s) {

        int b = s.length();
        boolean temp = false;
        int temp2 = 0;

        for (int i = 0; i < b; i++) {

            if (temp) {
                temp2++;
            }

            if (s.charAt(i) == '.') {
                temp = true;
            }

            if (temp2 > 2) {
                return false;
            } else if (temp2 == 0 && temp == true && (b - 1) == i) {
                return false;
            }
        }

        return true;
    }
}
