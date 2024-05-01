package ru.mirea.it.ivbo;

import java.util.Objects;

public class While {
    public While(String exp) {
        if (Interpreter.flag_to_do) {
            try {
                String[] ch = exp.split(" ");
                if (!ch[0].matches("-?\\d+") && !ch[0].matches("[-+]?[0-9]*\\.?[0-9]+") || !ch[2].matches("-?\\d+") && !ch[2].matches("[-+]?[0-9]*\\.?[0-9]+")) {
                    exp = new LogicExpression().getRes(exp);
                    Interpreter.flag_to_do = Objects.equals(exp, "true");
                    Interpreter.loop_count += 1;
                    if (Interpreter.loop_count > Interpreter.LOOP_COUNT) {
                        Interpreter.printInterpretationError("There is an infinite recursion in the loop \"while(" + exp + ") { ... }\". It is not correct");
                        System.exit(-1);
                    }
                } else {
                    Interpreter.printInterpretationError("There is an infinite recursion in the loop \"while(" + exp + ") { ... }\". It is not correct");
                    Interpreter.flag_to_do = false;
                    System.exit(-1);
                }
            } catch (Exception ex) {
                Interpreter.printInterpretationError("Loop \"while(" + exp + ") { ... }\" is not correct");
            }
        }
    }

    @Override
    public String toString() {
        return "While";
    }
}
// Ошибка условия - бесконечная рекурсия
//    void main()
//    {
//        int e = 5;
//        if (e > 3){
//            e = e - 1;
//            printf("e = %d", e);
//        } else {
//            e += 4;
//            printf("e = %d", e);
//        }
//        while(10 > 3) {
//            e -= 1;
//            printf("e = %d", e);
//        }
//
//    }

// Ошибка условия - бесконечная рекурсия
//    void main()
//    {
//        int e = 5;
//        int d = 3;
//        if (e > 3){
//            e = e - 1;
//            printf("e = %d", e);
//        } else {
//            e += 4;
//            printf("e = %d", e);
//        }
//        while(e >= d) {
//            printf("e = %d", e);
//            e -= 1;
//            d -= 1;
//        }
//    }
