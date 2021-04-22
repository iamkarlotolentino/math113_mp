package team.bitwin.parser;

public class ValidatorUtils {
    public static boolean isNumber(String s) {
        boolean hasDecimal = false;
        if (s.isEmpty()) return false;
        for (int i = 0; i < s.length(); i++) {
            if (i == 0 && s.charAt(i) == '-') {
                if (s.length() == 1) return false;
                else continue;
            }
            if (s.charAt(i) == '.') {
                if (hasDecimal) {
                    return false;
                } else {
                    hasDecimal = true;
                    continue;
                }
            }
            if (Character.digit(s.charAt(i), 10) < 0) return false;
        }
        return true;
    }

    public static boolean isAlpha(String s) {
        for (char c : s.toCharArray()) {
            if (!Character.isAlphabetic(c)) {
                return false;
            }
        }
        return true;
    }
}
