package ru.mirea.it.ivbo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;


public class Interpreter {
    public static final int LOOP_COUNT = 100;
    static HashMap<String, Integer> nums = new HashMap<>();
    static HashMap<String, String> strings = new HashMap<>();
    static HashMap<String, Float> floats = new HashMap<>();
    static Boolean flag_to_do = true;
    static int loop_count = 0;

    public static void printInterpretationError(String error){
            System.out.print("INTERPRETATION ERROR! " + error + "\n");
    }
    public static void printSyntaxError(String error){
        System.out.print("SYNTAX ERROR! " + error + "\n");
    }
    public static void printRes(String res){
//        System.out.println(nums);
//        System.out.println(strings);
//        System.out.println(floats);
        System.out.print(res.replace("\"", "") + "\n");
    }

    public static String rewrite_exp(String exp){
        String[] ch = exp.split(" ");
        String x;
        if (!ch[0].matches("-?\\d+") && !ch[0].matches("[-+]?[0-9]*\\.?[0-9]+")){
            x = ch[0];
            x = x.replace("\"", "");
            if (Interpreter.nums.get(x) != null) ch[0] = String.valueOf(Interpreter.nums.get(x));
            else if (Interpreter.floats.get(x) != null) ch[0] = String.valueOf(Interpreter.floats.get(x));
            else if (Interpreter.strings.get(x) != null) ch[0] = Interpreter.strings.get(x);
        }
        if (!ch[2].matches("-?\\d+") && !ch[2].matches("[-+]?[0-9]*\\.?[0-9]+")){
            x = ch[2];
            x = x.replace("\"", "");
            if (Interpreter.nums.get(x) != null) ch[2] = String.valueOf(Interpreter.nums.get(x));
            else if (Interpreter.floats.get(x) != null) ch[2] = String.valueOf(Interpreter.floats.get(x));
            else if (Interpreter.strings.get(x) != null) ch[2] = Interpreter.strings.get(x);
        }
        exp = ch[0] + ch[1] + ch[2];
        return exp;
    }

    public static void main(String[] args) {
        try {
            OutputStream outputStream = new FileOutputStream("C:/Users/vladi/Java 2c/DSL/Laba 1/src/ru.mirea.it.ivbo/output2.txt");
            System.setOut(new PrintStream(outputStream));
        }catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.println("The file was not found");
        }
        Analizator.main(args);
        Parser.main(args);
    }
}

