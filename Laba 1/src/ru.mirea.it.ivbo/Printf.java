package ru.mirea.it.ivbo;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Objects;

public class Printf {
    public Printf(String str, String ident) {
        if (Interpreter.flag_to_do) {
            String d = str.split("%")[1];
            String res = "";
            try {
                switch (d.charAt(0)) {
                    case 'd' -> {
                        if (Interpreter.nums.get(ident) != null)
                            res = String.format(str, Interpreter.nums.get(ident));
                        else if (!ident.isEmpty() && ident.matches("-?\\d+"))
                            res = String.format(str, Integer.parseInt(ident));
                        else
                            Interpreter.printInterpretationError("Printf '" + str + " " + ident + "' is not correct (" + ident + " is not 'int')");
                    }
                    case 'c' -> {
                        if (Interpreter.chars.get(ident) != null)
                            res = String.format(str, Interpreter.chars.get(ident));
                        else if (!ident.isEmpty() && (ident.charAt(0) == '\'' && ident.charAt(2) == '\''))
                            res = String.format(str, ident);
                        else Interpreter.printInterpretationError("Printf '" + str + " " + ident + "' is not correct (" + ident + " is not 'char')");
                    }
                    case 's' -> {
                        if (Interpreter.strings.get(ident) != null)
                            res = String.format(str, Interpreter.strings.get(ident));
                        else if (!ident.isEmpty() && (ident.charAt(0) == '"' && ident.charAt(ident.length()-1) == '"'))
                            res = String.format(str, ident);
                        else Interpreter.printInterpretationError("Printf '" + str + " " + ident + "' is not correct (" + ident + " is not 'string')");
                    }
                    case 'f' -> {
                        if (Interpreter.floats.get(ident) != null)
                            res = String.format(str, Interpreter.floats.get(ident));
                        else if (!ident.isEmpty() && ident.matches("[-+]?[0-9]*\\.?[0-9]+"))
                            res = String.format(str, Float.parseFloat(ident));
                        else Interpreter.printInterpretationError("Printf '" + str + " " + ident + "' is not correct (" + ident + " is not 'float')");
                    }
                    default -> Interpreter.printInterpretationError("Printf '" + str + " " + ident + "' ('" + d.charAt(0) + "' is not correct)");
                }
                if (!Objects.equals(res, "")) Interpreter.printRes(res);
//                else Interpreter.printInterpretationError("Printf '" + str + " " + ident + "' is not correct");
            } catch (Exception e) {
                Interpreter.printInterpretationError("Printf '" + str + " " + ident + "' is not correct");
            }
        }
    }


    @Override
    public String toString() {
        return  "Printf";
    }

    public static void main(String[] args) {
        new Printf("%d", "x");
    }

}
