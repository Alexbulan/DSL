package ru.mirea.it.ivbo;


import javax.script.*;

public class ArithmeticExpression {
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
                    Interpreter.printInterpretationError("Arithmetic expression \"" + expression + "\" is not correct");
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
        return "Arithmetic Expression";
    }

    public static void main(String[] args) {
        new ArithmeticExpression().getRes("1 + 2");
    }
}
