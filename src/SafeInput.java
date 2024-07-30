import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SafeInput {

    public static String getNonZeroLenString(Scanner scanner, String prompt) {
        String inputString = "";
        do {
            System.out.print("\n" + prompt + ": ");
            inputString = scanner.nextLine().trim();
        } while (inputString.isEmpty());
        return inputString;
    }

    public static int getInt(Scanner scanner, String prompt) {
        int inputValue = 0;
        while (true) {
            try {
                System.out.print("\n" + prompt + ": ");
                inputValue = Integer.parseInt(scanner.nextLine());
                if (inputValue > 0) {
                    break;
                }
            } catch (NumberFormatException e) {
            }
        }
        return inputValue;
    }

    public static double getDouble(Scanner scanner, String prompt) {
        double inputValue = 0;
        while (true) {
            try {
                System.out.print("\n" + prompt + ": ");
                inputValue = Double.parseDouble(scanner.nextLine());
                if (inputValue > 0) {
                    break;
                }
            } catch (NumberFormatException e) {
            }
        }
        return inputValue;
    }

    public static int getRangedInt(Scanner scanner, String prompt, int low, int high) {
        int inputValue = 0;
        while (true) {
            inputValue = getInt(scanner, prompt);
            if (inputValue >= low && inputValue <= high) {
                break;
            }
        }
        return inputValue;

    }
    public static double getRangedDouble(Scanner scanner, String prompt, double low, double high) {
        double inputValue = 0;
        while (true) {
            inputValue = getDouble(scanner, prompt);
            if (inputValue >= low && inputValue <= high) {
                break;
            }
        }
        return inputValue;
    }
    public static boolean getYNConfirm(Scanner scanner, String prompt) {
        String inputString = "";
        while (true) {
            inputString = getNonZeroLenString(scanner, prompt + " (y/n)").toLowerCase();
            if (inputString.equals("y") || inputString.equals("n")) {
                break;
            }
        }
        return inputString.equals("y");
    }

    public static String getRegExString(Scanner scanner, String prompt, String regex) {
        String inputString = "";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher;
        do {
            inputString = getNonZeroLenString(scanner, prompt);
            matcher = pattern.matcher(inputString);
        } while (!matcher.matches());
        return inputString;
    }

    public static void createHeader(String headerText) {
        int totalWidth = 60;
        int textLength = headerText.length();
        int padding = (totalWidth - textLength - 2) / 2;

        for (int i = 0; i < totalWidth; i++) {
            System.out.print("*");
        }
        System.out.println();

        for (int i = 0; i < padding; i++) {
            System.out.print("*");
        }
        System.out.print(" " + headerText + " ");
        for (int i = 0; i < padding; i++) {
            System.out.print("*");
        }

        if ((textLength + 2) % 2 != 0) {
            System.out.print("*");
        }
        System.out.println();

        for (int i = 0; i < totalWidth; i++) {
            System.out.print("*");
        }
        System.out.println();
    }
}