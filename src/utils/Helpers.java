package utils;

public class Helpers {

    public static boolean isDouble(String amount) {
        try {
            Double balance = Double.parseDouble(amount);
            System.out.println(balance + " is a number.");
            return true;
        } catch (NumberFormatException e) {
            System.out.println(amount + " is not a number.");
            return false;
        }
    }
}