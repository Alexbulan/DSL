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
            ident = ident.replace("\"", "");
            try {
                switch (d.charAt(0)) {
                    case 'd' -> {
                        if (Interpreter.nums.get(ident) != null)
                            res = String.format(str, Interpreter.nums.get(ident));
                        else res = ident;
                    }
                    case 's' -> {
                        if (Interpreter.strings.get(ident) != null)
                            res = String.format(str, Interpreter.strings.get(ident));
                        else res = ident;
                    }
                    case 'f' -> {
                        if (Interpreter.floats.get(ident) != null)
                            res = String.format(str, Interpreter.floats.get(ident));
                        else res = ident;
                    }
                    default -> Interpreter.printInterpretationError("Printf \"" + str + " " + ident + " " + d.charAt(0) + "\" is not correct");
                }
                if (!Objects.equals(res, "null")) Interpreter.printRes(res);
                else Interpreter.printInterpretationError("Printf \"" + str + " " + ident + "\" is not correct");
            } catch (Exception e) {
                Interpreter.printInterpretationError("Printf \"" + str + " " + ident + "\" is not correct");
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
