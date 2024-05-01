package ru.mirea.it.ivbo;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class Scanf {

    public Scanf(String str, String ident) {
        if (Interpreter.flag_to_do) {
            try {
                List<String> allLines = Files.readAllLines(Paths.get("C:/Users/vladi/Java 2c/DSL/Laba 1/src/ru.mirea.it.ivbo/input2.txt"));
                String line = allLines.get(0);
                allLines.remove(line);
                Files.write(Paths.get("C:/Users/vladi/Java 2c/DSL/Laba 1/src/ru.mirea.it.ivbo/input2.txt"), allLines);
                String d = str.split("%")[1];
                ident = ident.replace("\"", "");
                switch (d.charAt(0)) {
                    case 'd' -> {
                        int i = Integer.parseInt(line);
                        Interpreter.nums.put(ident, i);
                    }
                    case 's' -> {
                        Interpreter.strings.put(ident, line);
                    }
                    case 'f' -> {
                        float f = Float.parseFloat(line);
                        Interpreter.floats.put(ident, f);
                    }
                    default -> Interpreter.printInterpretationError("Scanf \"" + str + " " + ident + "\" is not correct");
                }
            } catch (Exception e) {
                Interpreter.printInterpretationError("Scanf \"" + str + " " + ident + "\" is not correct");
            }
        }
    }

    @Override
    public String toString() {
        return  "Scanf";
    }

    public static void main(String[] args) {
        new Scanf("%d", "i");
    }
}


