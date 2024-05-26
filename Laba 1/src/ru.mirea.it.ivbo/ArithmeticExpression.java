package ru.mirea.it.ivbo;


public class ArithmeticExpression implements Expression{
    @Override
    public String toString() {
        return "Arithmetic Expression";
    }
    public void printError(String expression){
        Interpreter.printInterpretationError("Arithmetic expression \"" + expression + "\" is not correct");
    }

    public static void main(String[] args) {
        new ArithmeticExpression().getRes("1 + 2");
    }
}
