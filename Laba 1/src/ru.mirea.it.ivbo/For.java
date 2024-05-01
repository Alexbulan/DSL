package ru.mirea.it.ivbo;

import java.util.Objects;

public class For {
    public For(String exp) {
        if (Interpreter.flag_to_do) {
            try {
                String[] ch = exp.split(" ");
                if (!ch[0].matches("-?\\d+") && !ch[0].matches("[-+]?[0-9]*\\.?[0-9]+") || !ch[2].matches("-?\\d+") && !ch[2].matches("[-+]?[0-9]*\\.?[0-9]+")) {
                    exp = new LogicExpression().getRes(exp);
                    Interpreter.flag_to_do = Objects.equals(exp, "true");
                    Interpreter.loop_count += 1;
                    if (Interpreter.loop_count > Interpreter.LOOP_COUNT) {
                        Interpreter.printInterpretationError("There is an infinite recursion in the loop for with exception \"(" + exp + ")\". It is not correct");
                        System.exit(-1);
                    }
                } else {
                    Interpreter.printInterpretationError("There is an infinite recursion in the loop for with exception \"(" + exp + ")\". It is not correct");
                    Interpreter.flag_to_do = false;
                    System.exit(-1);
                }
            } catch (Exception ex) {
                Interpreter.printInterpretationError("Loop for with exception \"(" + exp + ")\" is not correct");
            }
        }
    }

    @Override
    public String toString() {
        return "For";
    }
}
