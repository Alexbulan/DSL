package ru.mirea.it.ivbo;

import java.util.Stack;

public class ReversePolishRecord {

    private double result;
    private String line;

    public double getResult(String line) throws noResultException {
        this.line = line;
        result = 0;
        String number = "";
        Stack<Double> res = new Stack<>();
        for (int i = 0; i < line.length(); i++) {
            char symbol = line.charAt(i);
            try {
                if (symbol != ' ' && symbol != '*' && symbol != '/' && symbol != '+' && symbol != '-' && symbol != '~'&& symbol != '^') {
                    number += symbol;
                } else {
                    if(!number.equals("")) {
                        Double num = Double.parseDouble(number);
                        res.push(num);
                        number = "";
                    }
                    if (symbol != ' ') {
                        if (symbol == '~') {
                            double a = res.pop();
                            result = (-1) * a;
                            res.push(result);
                        }
                        else if (res.size() >= 2) {
                            double a = res.pop();
                            double b = res.pop();
                            switch (symbol){
                                case '+' -> result = a + b;
                                case '-' -> result = b - a;
                                case '^' -> result = Math.pow(b, a);
                                case '*' -> {
                                    symbol = line.charAt(i + 1);
                                    if (symbol == '*') {
                                        result = Math.pow(b, a);
                                        i += 1;
                                    }else result = b * a;
                                }
                                case '/' -> {
                                    if (a != 0)
                                        result = b / a;
                                    else throw new noResultException();

                                }
                            }
                            res.push(result);
                        } else throw new noResultException();
                    }
                }
            }catch (Exception e) {
                throw new noResultException();
            }
        }
        result = res.pop();
        return result;
    }
}
