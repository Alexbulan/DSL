package ru.mirea.it.ivbo;

import java.util.Stack;

public class ReversePolishRecord2 {

    private String result;
    private String line;

    public String getResult(String line){
        this.line = line;
        result = "";
        Stack<Character> res = new Stack<>();
        for (int i = 0; i < line.length(); i++) {
            char symbol = line.charAt(i);
//            System.out.println(result + " " + res);
                if (Character.isDigit(symbol)) {
                    result += symbol;
                } else if (symbol == '+' || symbol == '-' || symbol == '~' || symbol == '*' || symbol == '/' || symbol == '^') {
                    while (!res.isEmpty() && ((isLeftAssociative(symbol) && (getPriority(symbol) <= getPriority(res.peek()))) ||
                            (isRightAssociative(symbol) && (getPriority(symbol) < getPriority(res.peek()))))) {
                        result += res.pop();
                    }
                    res.push(symbol);
                } else if (symbol == '(') {
                    res.push(symbol);
                } else if (symbol == ')') {
                    while (!res.isEmpty() && res.peek() != '(') {
                        result += res.pop();
                    }
                    if (!res.isEmpty() && res.peek() == '(') {
                        res.pop();
                    }
                } else if (symbol == ' ') {
                    result += symbol;
                }
        }
        while (!res.isEmpty()) {
            result += res.pop();
        }
        return result;
    }

    private boolean isLeftAssociative(char s) {
        return s == '+' || s == '-' || s == '*' || s == '/' || s == '^';
    }

    private boolean isRightAssociative(char s) {
        return s == '+' || s == '-';
    }

    private int getPriority(char s) {
        return switch (s) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            case '^' -> 3;
            default -> 0;
        };
    }
}
