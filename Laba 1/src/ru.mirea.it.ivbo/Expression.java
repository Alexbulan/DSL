package ru.mirea.it.ivbo;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public interface Expression {
    default String getRes(String expression) {
        if (Interpreter.flag_to_do) {
            expression = rewrite_exp(expression);
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            if (engine != null) {
                try {
                    Object res = engine.eval(expression);
                    return res.toString();
                } catch (ScriptException s) {
                    printError(expression);
                    return "";
                }
            } else {
                System.out.println("JavaScript engine not found.");
                return "";
            }
        }
        return "";
    }

    static String rewrite_exp(String exp) {
        try {
            String[] ch = exp.split(" ");
            String x;
            if (!ch[0].matches("-?\\d+") && !ch[0].matches("[-+]?[0-9]*\\.?[0-9]+")) {
                x = ch[0];
                if (Interpreter.nums.get(x) != null) ch[0] = String.valueOf(Interpreter.nums.get(x));
                else if (Interpreter.floats.get(x) != null) ch[0] = String.valueOf(Interpreter.floats.get(x));
                else if (Interpreter.strings.get(x) != null) ch[0] = Interpreter.strings.get(x);
            }
            if (!ch[2].matches("-?\\d+") && !ch[2].matches("[-+]?[0-9]*\\.?[0-9]+")) {
                x = ch[2];
                if (Interpreter.nums.get(x) != null) ch[2] = String.valueOf(Interpreter.nums.get(x));
                else if (Interpreter.floats.get(x) != null) ch[2] = String.valueOf(Interpreter.floats.get(x));
                else if (Interpreter.strings.get(x) != null) ch[2] = Interpreter.strings.get(x);
            }
            exp = ch[0] + ch[1] + ch[2];
        }catch (Exception e){
            Interpreter.printInterpretationError("Expression \"" + exp + "\" is not correct");
        }
        return exp;
    }
    void printError(String expression);
    @Override
    String toString();
}
