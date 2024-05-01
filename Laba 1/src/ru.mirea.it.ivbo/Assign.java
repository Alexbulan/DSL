package ru.mirea.it.ivbo;

public class Assign {
    public Assign(String x, String exp){
        if (Interpreter.flag_to_do)
            try {
                x = x.replace("\"", "");
                if (Interpreter.nums.get(x) != null) Interpreter.nums.put(x, Integer.parseInt(exp));
                else if (Interpreter.floats.get(x) != null) Interpreter.floats.put(x, Float.parseFloat(exp));
                else if (Interpreter.strings.get(x) != null) Interpreter.strings.put(x, exp);
                else Interpreter.printInterpretationError("Assign \"" + x + "\" is not correct");
//                System.out.println(Interpreter.nums);
//                System.out.println(Interpreter.floats);
//                System.out.println(Interpreter.strings);
            }catch (Exception e) {
                Interpreter.printInterpretationError("Assign \"" + x + "\" is not correct");
            }
    }

    @Override
    public String toString() {
        return  "Assign";
    }
}
//    int x = 2;
//    int s = 4;
//    s %= 2;
//    printf("s = %d", s);
//    x = s - x;
//    printf("x = %d", x);
//    char d = "YES";
//    char r = "NO";