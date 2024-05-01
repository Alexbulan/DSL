package ru.mirea.it.ivbo;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Kkodirovanie {
    static char ch = ' ';
    static int index = 0;
    static int len = 0;
    static String result = "";
    static boolean next = false;
    static ArrayList<Integer> digits = new ArrayList<>();
    static ArrayList<String> strs = new ArrayList<>();

    public static char getchar() {
        try {
            int s = System.in.read();
            System.out.println(index + " " + (char) s);
            while (0 <= s && s <= 31){
                if (s == 10) {return (char) -1; }
                s = System.in.read();
            }
            return (char) s;
        } catch (Exception e) {
            e.printStackTrace();
            return (char) -1;
        }
    }
    public static void isDigit(){
        ch = getchar();
        index += 1;
        if (ch == 0){
            ch = getchar();
            index += 1;
            if(ch != 'e') {
                error(index, "0 'e'");
            }
        }else{
            int digit = 0;
            while(ch != 'e'){
                if(!Character.isDigit(ch)){
                    error(index, "digit 'e'");
                }
                digit = Integer.parseInt(String.valueOf(digit) + ch);
                ch = getchar();
                index += 1;
            }
            result += digit;
//            System.out.print(digit);
            digits.add(digit);
        }
    }
    public static void isDict(){
        ch = getchar();
        index += 1;
        if (ch != 'e') {
            getres();
            result += " => ";
            ch = getchar();
            index += 1;
            getres();
        }
        ch = getchar();
        index += 1;
        while (ch != 'e') {
            result += ", ";
            getres();
            result += " => ";
            ch = getchar();
            index += 1;
            getres();
        }
//        ch = getchar();
//        index += 1;
//        switch (ch) {
//            case 'i':
//                isDigit();
//                break;
//            case 'l':
//                result += "[ ";
//                isList();
//                break;
//            case 'd':
//                result += "[ ";
//                isDict();
//                break;
//            case 'e':
//                result += " ]";
//                break;
//            default:
//                if (Character.isDigit(ch)) {
//                    isStr();
//                }break;
//        }
//        ch = getchar();
//        index += 1;
//        if(ch != 'e') result += " => ";
//        switch (ch) {
//            case 'i':
//                isDigit();
//                break;
//            case 'l':
//                result += "[ ";
//                isList();
//                break;
//            case 'd':
//                result += "[ ";
//                isDict();
//                break;
//            case 'e':
//                result += " ] => ";
//                break;
//            default:
//                if (Character.isDigit(ch)) {
//                    isStr();
//                }else if (ch != 'e'){
//                    error(index, "dict 'e'");
//                }break;
//        }
//        result += ", ";

//        ch = getchar();
//        index += 1;
        if (ch != 'e')
            error(index, "dict 'e'");
        result += "]";
    }
    public static void isStr(){
        len = 0;
        while (Character.isDigit(ch)){
            len = Integer.parseInt(String.valueOf(len) + ch);
            ch = getchar();
            index += 1;
        }if(ch != ':')  error(index, "str ':'");
        else if (len != 0){
            ch = getchar();
            index += 1;
            int i = 1;
            StringBuilder str = new StringBuilder();
            while (i < len && Character.isLetter(ch)){
                str.append(ch);
                ch = getchar();
                index += 1; i += 1;
            }
            str.append(ch);
            result += "'" + str + "'";
            strs.add(str.toString());
//            System.out.println(strs);
        } else result += "''";
    }

    public static void isList(){
        ch = getchar();
        index += 1;
        if (ch != 'e') getres();
        ch = getchar();
        index += 1;
        while (ch != 'e') {
            result += ", ";
            getres();
            ch = getchar();
            index += 1;
        }
//        result += ", ";
//        getres();
//        ch = getchar();
//        index += 1;
//        switch (ch) {
//            case 'i':
//                isDigit();
//                break;
//            case 'l':
//                result += "[ ";
//                isList();
//                break;
//            case 'd':
//                result += "[ ";
//                isDict();
//                break;
//            case 'e':
//                result += " ]";
//                break;
//            default:
//                if (Character.isDigit(ch)){
//                    isStr();
//                }else if (ch != 'e'){
//                    error(index, "list 'e'");
//                }break;
//        }
//        result += ", ";

//        ch = getchar();
//        index += 1;
        if (ch != 'e')
            error(index, "list 'e'");
        result += " ]";
    }

    public static void getres() {

        if (ch != (char) -1) {
            switch (ch) {
                case 'i':
                    isDigit();
                    break;
                case 'l':
                    result += "[ ";
                    isList();
                    break;
                case 'd':
                    result += "[ ";
                    isDict();
                    break;
//                case 'e':
//                    result += " ]";
//                    break;
                default:
                    if (Character.isDigit(ch)){
                        isStr();
                    }
//                    error(index, "res");
                    break;
            }
//            getres();
        }
    }

    public static void error(int ind, String str){
        System.out.println("Error at position " + ind + " in " + str);
        System.exit(-1);
    }

    public static void main(String[] args) {
        try {
            InputStream inputStream = new FileInputStream("C:\\Users\\vladi\\Java 2c\\DSL\\Practica 3\\src\\ru\\mirea\\it\\ivbo\\input.txt");
            System.setIn(inputStream);
            OutputStream outputStream = new FileOutputStream("C:\\Users\\vladi\\Java 2c\\DSL\\Practica 3\\src\\ru\\mirea\\it\\ivbo\\output.txt");
            System.setOut(new PrintStream(outputStream));
            ch = getchar();
            index += 1;
            getres();
            System.out.println("ok");
            System.out.println(result);
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
            e.printStackTrace();
        }
    }
}