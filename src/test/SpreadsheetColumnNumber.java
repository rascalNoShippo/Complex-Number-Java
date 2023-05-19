package test;

public class SpreadsheetColumnNumber {
    private static final byte BYTE_VALUE_A = 'A';
    private static final int COUNT_ALPHABETS = 26;

    public static void main(String[] args) {
        System.out.println(strToNum("TEAMLAB"));
        System.out.println(numToStr(6238416004L));
    }

    public static String numToStr(long num) {
        String result = "";
        while (num > 0) {
            int charNo = (int) (num % COUNT_ALPHABETS);
            charNo += charNo == 0 ? 26 : 0;
            result = (char) (BYTE_VALUE_A + charNo - 1) + result;
            num = (num - charNo) / COUNT_ALPHABETS;
        }
        return result;
    }

    public static long strToNum(String str) {
        str = str.trim().toUpperCase();
        if (!str.matches("[A-Z]+")) {
            throw new IllegalArgumentException();
        }
        final int len = str.length();
        final byte[] bytes = str.getBytes();
        long result = 0;
        for (int i = 0; i < len; i++) {
            result += (long) Math.pow(COUNT_ALPHABETS, len - (i + 1)) * (bytes[i] - BYTE_VALUE_A + 1);
        }
        return result;
    }
}
