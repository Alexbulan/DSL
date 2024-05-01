package ru.mirea.it.ivbo;

import java.util.Objects;

public class Else {
    public Else (String expression) {
        if (Interpreter.flag_to_do)
            Interpreter.flag_to_do = Objects.equals(expression, "false") || Objects.equals(expression, "0");
    }
    public String toString() {
        return "Else";
    }
}
