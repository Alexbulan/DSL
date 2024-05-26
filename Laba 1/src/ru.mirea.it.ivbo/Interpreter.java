package ru.mirea.it.ivbo;

import java.io.*;
import java.util.HashMap;


public class Interpreter {
    public static final int LOOP_COUNT = 100;
    static HashMap<String, Integer> nums = new HashMap<>();
    static HashMap<String, String> strings = new HashMap<>();
    static HashMap<String, Character> chars = new HashMap<>();
    static HashMap<String, Float> floats = new HashMap<>();
    static Boolean flag_to_do = true;
    static int loop_count = 0;

    public static void printInterpretationError(String error) {
        System.out.print("INTERPRETATION ERROR! " + error + "\n");
    }

    public static void printSyntaxError(String error) {
        System.out.print("SYNTAX ERROR! " + error + "\n");
    }
    public static void printLexerError(String error) {
        System.out.print("LEXICAL ERROR! " + error + "\n");
        System.exit(-1);
    }

    public static void printRes(String res) {
//        System.out.println(nums);
//        System.out.println(strings);
//        System.out.println(floats);
        if(!Parser.AST) System.out.print(res.replace("\"", "") + "\n");
    }

    public static void printAST(String res){
            System.out.println(res);
    }

    public static void main(String[] args) {
        try {
            String file = "C:/Users/vladi/Java 2c/DSL/Laba 1/src/ru.mirea.it.ivbo/outputInterpritatorResult.txt";
            OutputStream outputStream = new FileOutputStream(file);
            System.setOut(new PrintStream(outputStream));
        } catch (FileNotFoundException e) {
            System.out.println("The file 'outputInterpritatorResult.txt' was not found");
        }
        Analizator.main(args);
        Parser.main(args);
    }
}

