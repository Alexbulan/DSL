package ru.mirea.it.ivbo;

public class Initialization {
    public Initialization(String type, String x, String exp){
        if (Interpreter.flag_to_do)
            try {
                switch (type) {
                    case "int" -> {
                            if (!exp.isEmpty() && exp.matches("-?\\d+") && Interpreter.floats.get(x) == null && Interpreter.strings.get(x) == null && Interpreter.chars.get(x) == null) {
                                Interpreter.nums.put(x, Integer.parseInt(exp));
                            }else if (exp.isEmpty() && Interpreter.floats.get(x) == null && Interpreter.strings.get(x) == null && Interpreter.chars.get(x) == null) Interpreter.nums.put(x, 0);
                            else if (Interpreter.floats.get(x) != null) Interpreter.printInterpretationError("Initialization '" + x +  "' is not '" + type + "' ('" + x +  "' is 'float')" );
                            else if (Interpreter.strings.get(x) != null) Interpreter.printInterpretationError("Initialization '" + x +  "' is not '" + type + "' ('" + x +  "' is 'string')" );
                            else if (Interpreter.chars.get(x) != null) Interpreter.printInterpretationError("Initialization '" + x +  "' is not '" + type + "' ('" + x +  "' is 'char')" );
                            else
                                Interpreter.printInterpretationError("Initialization '" + x +  "' is not '" + type + "'");
                    }
                    case "float" -> {
                            if (!exp.isEmpty() && exp.matches("[-+]?[0-9]*\\.?[0-9]+") && Interpreter.nums.get(x) == null && Interpreter.strings.get(x) == null && Interpreter.chars.get(x) == null) {
                                Interpreter.floats.put(x, Float.parseFloat(exp));
                            }else if (exp.isEmpty() && Interpreter.nums.get(x) == null && Interpreter.strings.get(x) == null && Interpreter.chars.get(x) == null) Interpreter.floats.put(x, (float) 0);
                            else if (Interpreter.nums.get(x) != null) Interpreter.printInterpretationError("Initialization '" + x +  "' is not '" + type + "' ('" + x +  "' is 'int')" );
                            else if (Interpreter.strings.get(x) != null) Interpreter.printInterpretationError("Initialization '" + x +  "' is not '" + type + "' ('" + x + "' is 'string')" );
                            else if (Interpreter.chars.get(x) != null) Interpreter.printInterpretationError("Initialization '" + x +  "' is not '" + type + "' ('" + x + "' i is 'char')" );
                            else
                                Interpreter.printInterpretationError("Initialization '" + x +  "' is not '" + type + "'");
                    }
                    case "string" -> {
                        if (!exp.isEmpty() && (exp.charAt(0) == '"' && exp.charAt(exp.length()-1) == '"') && Interpreter.floats.get(x) == null && Interpreter.nums.get(x) == null && Interpreter.chars.get(x) == null) {
                            Interpreter.strings.put(x, exp);
                        }else if (exp.isEmpty() && Interpreter.floats.get(x) == null && Interpreter.nums.get(x) == null && Interpreter.chars.get(x) == null)  Interpreter.strings.put(x, "");
                        else if (Interpreter.floats.get(x) != null) Interpreter.printInterpretationError("Initialization '" + x +  "' is not '" + type + "' ('" + x +  "' is 'float')" );
                        else if (Interpreter.nums.get(x) != null) Interpreter.printInterpretationError("Initialization '" + x +  "' is not '" + type + "' ('" + x +  "' is 'int')" );
                        else if (Interpreter.chars.get(x) != null) Interpreter.printInterpretationError("Initialization '" + x +  "' is not '" + type + "' ('" + x +  "' is 'char')" );
                        else Interpreter.printInterpretationError("Initialization '" + x +  "' is not '" + type + "'");
                    }
                    case "char" -> {
                        if (!exp.isEmpty() && exp.charAt(0) == '\'' && exp.charAt(2) == '\'' && Interpreter.floats.get(x) == null && Interpreter.strings.get(x) == null && Interpreter.nums.get(x) == null) {
                            Interpreter.chars.put(x, exp.charAt(1));
                        } else if (exp.isEmpty() && Interpreter.floats.get(x) == null && Interpreter.strings.get(x) == null && Interpreter.nums.get(x) == null)  Interpreter.chars.put(x, ' ');
                        else if (Interpreter.floats.get(x) != null) Interpreter.printInterpretationError("Initialization '" + x +  "' is not '" + type + "' ('" + x +  "' is 'float')" );
                        else if (Interpreter.strings.get(x) != null) Interpreter.printInterpretationError("Initialization '" + x +  "' is not '" + type + "' ('" + x +  "' is 'string')" );
                        else if (Interpreter.nums.get(x) != null) Interpreter.printInterpretationError("Initialization '" + x +  "' is not '" + type + "' ('" + x + "' is 'int')" );
                        else
                            Interpreter.printInterpretationError("Initialization '" + x +  "' is not '" + type + "'");
                    }
                }
            }catch (Exception e) {
                Interpreter.printInterpretationError("Initialization '" + type + " " + x +  " " + exp + "' is not correct");
            }
    }
    @Override
    public String toString() {
        return  "Initialization";
    }
}
