package ru.mirea.it.ivbo;

public class Initialization {
    public Initialization(String type, String x, String exp){
        x = x.replace("\"", "");
//        System.out.println(exp);
        if (Interpreter.flag_to_do)
            try {
                switch (type) {
                    case "int" -> {
                        Interpreter.nums.put(x, 0);
                        if (!exp.isEmpty()) {
                            Interpreter.nums.put(x, Integer.parseInt(exp));
                        }
                    }
                    case "float" -> {
                        Interpreter.floats.put(x, (float) 0);
                        if (!exp.isEmpty()) {
                            Interpreter.floats.put(x, Float.parseFloat(exp));
                        }
                    }
                    case "char" -> {
                        Interpreter.strings.put(x, "");
                        if (!exp.isEmpty()) {
                            Interpreter.strings.put(x, exp);
                        }
                    }
                }
            }catch (Exception e) {
                Interpreter.printInterpretationError("Initialization \"" + type + " " + x +  " " + exp + "\" is not correct");
            }
    }
    @Override
    public String toString() {
        return  "Initialization";
    }
}
