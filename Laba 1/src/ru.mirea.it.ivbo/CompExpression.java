package ru.mirea.it.ivbo;

public class CompExpression implements Expression {
    @Override
    public String toString() {
        return "Comparison Expression";
    }

    @Override
    public void printError(String expression) {
        Interpreter.printInterpretationError("Comparison expression \"" + expression + "\" is not correct");
    }

    public static void main(String[] args) {
        Interpreter.flag_to_do = true;
        Interpreter.nums.put("e", 3);
        Interpreter.nums.put("s", 43);
        new CompExpression().getRes("e < s");
    }

}
