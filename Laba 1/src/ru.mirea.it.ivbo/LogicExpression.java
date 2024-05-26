package ru.mirea.it.ivbo;

public class LogicExpression implements Expression {

    @Override
    public String toString() {
        return "Logic Expression";
    }

    public void printError(String expression){
        Interpreter.printInterpretationError("Logic expression \"" + expression + "\" is not correct");
    }

    public static void main(String[] args) {
        new LogicExpression().getRes("true && false");
    }
}
