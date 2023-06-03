import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final HashMap<String, Integer> ROMAN_NUMERALS = new HashMap<>() {{
        put("I", 1);
        put("II", 2);
        put("III", 3);
        put("IV", 4);
        put("V", 5);
        put("VI", 6);
        put("VII", 7);
        put("VIII", 8);
        put("IX", 9);
        put("X", 10);
        put("XX", 20);
        put("XXX", 30);
        put("XL", 40);
        put("L", 50);
        put("LX", 60);
        put("LXX", 70);
        put("LXXX", 80);
        put("XC", 90);
        put("C", 100);
    }};
    private static boolean isRoman;

    public static String calc(String input) {
        try {
            String[] operandsAndOperator = parsInput(input.replaceAll(" ", ""));

            checkUniformityEnteredData(operandsAndOperator);

            String result = calculate(operandsAndOperator);

            checkResult(result);
            return (isRoman) ? parsArabianToRoman(result) : result;
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    private static void checkResult(String result) throws Exception {
        if (isRoman) {
            int res = Integer.parseInt(result);
            if (res == 0) {
                throw new Exception("throws Exception //т.к. в римской системе нет числа 0");
            } else if (res < 0) {
                throw new Exception("throws Exception //т.к. в римской системе нет отрицательных чисел");
            }
        }
    }

    private static String[] parsInput(String input) throws Exception {
        String[] result = new String[3];

        Pattern pattern = Pattern.compile("([IVX]+|\\d+)([+\\-*/])([IVX]+|\\d+)");
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            result[0] = matcher.group(1);
            result[1] = matcher.group(2);
            result[2] = matcher.group(3);
        } else {
            throw new Exception("throws Exception //т.к. строка не является математической операцие");
        }

        return result;
    }

    private static void checkUniformityEnteredData(String[] operandsAndOperator) throws Exception {
        String operand1 = operandsAndOperator[0];
        String operand2 = operandsAndOperator[2];

        boolean operand1IsRoman = ROMAN_NUMERALS.containsKey(operand1);
        boolean operand2IsRoman = ROMAN_NUMERALS.containsKey(operand2);

        if (!operand1IsRoman) {
            checkCorrectnessOperand(operand1);
        }
        if (!operand2IsRoman) {
            checkCorrectnessOperand(operand2);
        }


        if (operand1IsRoman != operand2IsRoman) {
            throw new Exception("throws Exception //т.к. используются одновременно разные системы счисления");
        }

        isRoman = operand1IsRoman;
    }

    private static void checkCorrectnessOperand(String operand) throws Exception {
        try {
            Integer.parseInt(operand);
        } catch (Exception e) {
            throw new Exception("throws Exception //т.к. введено некоректное значение = " + operand);
        }
    }

    private static String calculate(String[] operandsAndOperator) throws Exception {
        int operand1 = getOperand(operandsAndOperator[0]);
        int operand2 = getOperand(operandsAndOperator[2]);
        String operator = operandsAndOperator[1];
        int result = 0;
        switch (operator) {
            case ("+") -> result = operand1 + operand2;
            case ("-") -> result = operand1 - operand2;
            case ("/") -> result = operand1 / operand2;
            case ("*") -> result = operand1 * operand2;
        }

        return String.valueOf(result);
    }

    private static int getOperand(String operand) throws Exception {
        int result;
        if (isRoman) {
            result = ROMAN_NUMERALS.get(operand);
        } else {
            result = Integer.parseInt(operand);
        }

        if (result < 1 || result > 10) {
            throw new Exception("throws Exception //т.к. калькулятор принимает на вход числа от 1 до 10 включительно");
        }

        return result;
    }

    private static String parsArabianToRoman(String answer) {
        int valueAnswer = Integer.parseInt(answer);
        StringBuilder result = new StringBuilder();

        int units = valueAnswer % 10;
        int tens = valueAnswer - units;


        for (Map.Entry<String, Integer> entry : ROMAN_NUMERALS.entrySet()) {
            if (entry.getValue() == units) {
                result.append(entry.getKey());
            }
            if (entry.getValue() == tens) {
                result.insert(0, entry.getKey());
            }
        }

        return result.toString();
    }

}
