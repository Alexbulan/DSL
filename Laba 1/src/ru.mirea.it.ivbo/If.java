package ru.mirea.it.ivbo;

import java.util.Objects;

public class If {
    public If(String expression) {
        if (Interpreter.flag_to_do)
            Interpreter.flag_to_do = Objects.equals(expression, "true") || Objects.equals(expression, "1");
    }
    @Override
    public String toString() {
        return "If";
    }
}
