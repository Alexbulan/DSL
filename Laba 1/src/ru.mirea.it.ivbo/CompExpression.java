package ru.mirea.it.ivbo;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.SortedMap;

public class CompExpression {
    public String getRes(String expression) {
        if (Interpreter.flag_to_do) {
            expression = Interpreter.rewrite_exp(expression);
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            if (engine != null) {
                try {
                    Object res = engine.eval(expression);
                    return res.toString();
                } catch (ScriptException s) {
                    s.printStackTrace();
                    Interpreter.printInterpretationError("Comparison expression \"" + expression + "\" is not correct");
                    return "";
                }
            } else {
                System.out.println("JavaScript engine not found.");
                return "";
            }
        }
        return "";
    }

    @Override
    public String toString() {
        return "Comparison Expression";
    }

    public static void main(String[] args) {
        Interpreter.flag_to_do = true;
        Interpreter.nums.put("e", 3);
        Interpreter.nums.put("s", 43);
        new CompExpression().getRes("e < s");
    }
}
