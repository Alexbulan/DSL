package ru.mirea.it.ivbo;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;



public class Analizator {
    public enum Tokens {
        T_EOF, /* конец файла */
        T_UNDEF, /* неизвестный токен, сигнализирует об ошибке */
        T_IDENT, /* идентификатор */
        T_INTCON,  /* целое число */
        T_FLOATCON, /* число с плавающей точкой */
        T_STRINGCON, /* "" */
        T_CHARCON, /* '' */
        T_ASSGN, /* присваивание */
        T_ADD, /* + */
        T_SUB, /* - */
        T_MUL, /* * */
        T_DIV, /* / */
        T_REMOFDIV,  /* % */
        T_LPAREN, /* ( */
        T_RPAREN, /* ) */
        T_COMM, /* комментарий */
        T_SEMICL, /* ; */
        T_LBRACE, /* { */
        T_RBRACE, /* } */
        T_LSQBRACE, /* [ */
        T_RSQBRACE, /* ] */
        T_COMMA, /* , */
        T_EQUAL, /* == */
        T_LESS, /* < */
        T_LEQUAL, /* <= */
        T_QREATER, /* > */
        T_REQUAL, /* >= */
        T_NOTEQUAL, /* != */
        T_OR, /* | */
        T_AND, /* & */
        T_NOT, /* ! */
        T_ADDEQUAL, /* += */
        T_SUBEQUAL, /* -= */
        T_MULEQUAL, /* *= */
        T_DIVEQUAL, /* /= */
        T_REMEQUAL, /* %= */

    }

    public static String[] reservedTokens = new String[]{"T_CHAR", "T_INT", "T_FLOAT", "T_VOID", "T_IF", "T_ELSE", "T_WHILE", "T_FOR", "T_RETURN", "T_PRINTF", "T_SCANF"};
    public static String[] operations = new String[]{"T_EQUAL", "T_LESS", "T_LEQUAL", "T_GREATER", "T_REQUAL", "T_NOTEQUAL", "T_OR", "T_AND", "T_NOT", "T_ADDEQUAL",
            "T_SUBEQUAL", "T_MULEQUAL", "T_DIVEQUAL", "T_REMEQUAL", "T_ADD", "T_SUB", "T_MUL", "T_DIV", "T_REMOFDIV"};
    public static String[] reserved = new String[]{"char", "int", "float", "void", "if", "else", "while", "for", "return", "printf", "scanf"};
    public static String[] withoutParam = new String[]{"T_COMMA", "T_SEMICL"};
    static Character[] letters = new Character[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я',
            'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я'
    };
    static Character[] digits = new Character[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    Character[] symbols = new Character[]{'+', '–', '*', '/', '%', '(', ')', '{', '}', '[', ']', '=', '|', '&', '<', '>', '!', ';', ','};

    static String[] TOKEN_NAMES = new String[]{
            "T_UNDEF",
            "T_IDENT",
            "T_INTCON",
            "T_ASSGN",
            "T_ADD",
            "T_SUB",
            "T_MUL",
            "T_DIV",
            "T_LPAREN",
            "T_RPAREN",
            "T_COMM"
    };
    static String sym = new String("T_UNDEF");
    static StringBuilder ident = new StringBuilder();
    static int int_num;
    static float float_num;
    static char ch = ' ';
    static int count = 0;
    static int line = 1;
    static boolean next_line = false;

    public static char getchar() {
        try {
            if(next_line){ line++; count = 0; next_line = false;}
            int s = System.in.read();
            while (0 <= s && s <= 31){
                if (s == 10) { next_line = true; }
                s = System.in.read();
            }
            return (char) s;
        } catch (Exception e) {
            e.printStackTrace();
            return (char) -1;
        }
    }

    public static String getsym() {
        try {
            while (ch == ' ') {
                ch = getchar();
                count += 1;
            }
            if (ch == '/'){
                ch = getchar();
                count += 1;
                if (ch == '*') {
                    ch = getchar();
                    count += 1;
                    while (ch != '*') {
                        ch = getchar();
                        count += 1;
                    }
                    ch = getchar();
                    count += 1;
                    if (ch == '/') {
                        sym = "T_COMM";
                        ch = getchar();
                        count += 1;
                    } else {
                        sym = "T_UNDEF";
                        return sym;
                    }
                }
            }
            while (ch == ' ') {
                ch = getchar();
                count += 1;
            }
            if (ch == (char) -1) {
                sym = "T_EOF";
                return sym;
            }

            if (isalpha(ch) || ch == '_') {
                ident = new StringBuilder();
                do {
                    ident.append(ch);
                    ch = getchar();
                    count += 1;
                } while ((isalpha(ch) || isdigit(ch) || ch == '_' )&& ! next_line);
                sym = reserve(ident);
                count += 1;
            } else if (isdigit(ch)) {
                int_num = 0;
                do {
                    int_num = int_num * 10 + (ch - '0');
                    ch = getchar();
                    count += 1;
                } while (isdigit(ch));
                if (ch == '.'){
                    float_num = int_num;
                    ch = getchar();
                    count += 1;
                    float i = 0.1F;
                    do {
                        float_num = float_num + i * (ch - '0');
                        ch = getchar();
                        count += 1;
                        i *= 0.1;
                    } while (isdigit(ch));
                    sym = "T_FLOATCON";
                }else {
                    sym = "T_INTCON";
                }
            } else if (isstr(ch) || ischar(ch)) {
                ident = new StringBuilder();
                do {
                    ch = getchar();
                    count += 1;
                    if (!isstr(ch) && !ischar(ch)) ident.append(ch);
                } while (!isstr(ch) && !ischar(ch));
                if (isstr(ch))
                    sym = "T_STRINGCON";
                else if(ischar(ch))
                    sym = "T_CHARCON";
                ch = getchar();
                count += 1;
            } else {
                switch (ch) {
                    case '+' -> {
                        ch = getchar();
                        count += 1;
                        if (ch == '='){
                            sym = "T_ADDEQUAL";
                            ident = new StringBuilder("+=");
                            ch = getchar();
                            count += 1;
                        }else {
                            sym = "T_ADD";
                            ident = new StringBuilder("+");
                        }
                    }
                    case '-' -> {
                        ch = getchar();
                        count += 1;
                        if (ch == '='){
                            sym = "T_SUBEQUAL";
                            ident = new StringBuilder("-=");
                            ch = getchar();
                            count += 1;
                        }else {
                            sym = "T_SUB";
                            ident = new StringBuilder("-");
                        }
                    }
                    case '*' -> {
                        ch = getchar();
                        count += 1;
                        if (ch == '='){
                            sym = "T_MULEQUAL";
                            ident = new StringBuilder("*=");
                            ch = getchar();
                            count += 1;
                        }else {
                            sym = "T_MUL";
                            ident = new StringBuilder("*");
                        }
                    }
                    case '/' -> {
                        ch = getchar();
                        count += 1;
                        if (ch == '='){
                            sym = "T_DIVEQUAL";
                            ident = new StringBuilder("/=");
                            ch = getchar();
                            count += 1;
                        }else {
                            sym = "T_DIV";
                            ident = new StringBuilder("/");
                        }
                    }
                    case '%' -> {
                        ch = getchar();
                        count += 1;
                        if (ch == '='){
                            sym = "T_REMEQUAL";
                            ident = new StringBuilder("%=");
                            ch = getchar();
                            count += 1;
                        }else {
                            sym = "T_REMOFDIV";
                            ident = new StringBuilder("%");
                        }
                    }
                    case '|' -> {
                        sym = "T_OR";
                        ch = getchar();
                        ident = new StringBuilder("|");
                        count += 1;
                    }
                    case '&' -> {
                        sym = "T_AND";
                        ident = new StringBuilder("&");
                        ch = getchar();
                        count += 1;
                    }
                    case '!' -> {
                        ch = getchar();
                        count += 1;
                        if (ch == '=') {
                            sym = "T_NOTEQUAL";
                            ident = new StringBuilder("!=");
                            ch = getchar();
                            count += 1;
                        }else {
                            sym = "T_NOT";
                            ident = new StringBuilder("!");
                        }
                    }
                    case '(' -> {
                        sym = "T_LPAREN";
                        ch = getchar();
                        count += 1;
                    }
                    case ')' -> {
                        sym = "T_RPAREN";
                        ch = getchar();
                        count += 1;
                    }
                    case '{' -> {
                        sym = "T_LBRACE";
                        ch = getchar();
                        count += 1;
                    }
                    case '}' -> {
                        sym = "T_RBRACE";
                        ch = getchar();
                        count += 1;
                    }
                    case '[' -> {
                        sym = "T_LSQBRACE";
                        ch = getchar();
                        count += 1;
                    }
                    case ']' -> {
                        sym = "T_RSQBRACE";
                        ch = getchar();
                        count += 1;
                    }
                    case '=' -> {
                        ch = getchar();
                        count += 1;
                        if (ch == '='){
                            ident = new StringBuilder("=");
                            sym = "T_EQUAL";
                        }else{
                            sym = "T_ASSGN";
                        }
                        ch = getchar();
                        count += 1;
                    }
                    case '<' -> {
                        ch = getchar();
                        count += 1;
                        if (ch == '='){
                            ident = new StringBuilder("<=");
                            sym = "T_LEQUAL";
                        }else{
                            ident = new StringBuilder("<");
                            sym = "T_LESS";
                        }
                        ch = getchar();
                        count += 1;
                    }
                    case '>' -> {
                        ch = getchar();
                        count += 1;
                        if (ch == '='){
                            ident = new StringBuilder(">=");
                            sym = "T_REQUAL";
                        }else{
                            ident = new StringBuilder(">");
                            sym = "T_GREATER";
                        }
                        ch = getchar();
                        count += 1;
                    }
                    case ';' -> {
                        sym = "T_SEMICL";
                        ch = getchar();
                        count += 1;
                    }
                    case ',' -> {
                        sym = "T_COMMA";
                        ch = getchar();
                        count += 1;
                    }
                    default -> sym = "T_UNDEF";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sym;
    }

    private static boolean isalpha(char ch) {
        return Arrays.asList(letters).contains(ch);
    }

    private static boolean isdigit(char ch) {
        return Arrays.asList(digits).contains(ch);
    }
    private static boolean isstr(char ch) {
        return ch == '"';
    }
    private static boolean ischar(char ch) {
        return ch == '\'';
    }
    private static String reserve(StringBuilder chars) {
        String res = chars.toString();
        if (Arrays.asList(reserved).contains(res)) {
            switch (res) {
                case "int" -> {
                    return "T_INT";
                }
                case "char" -> {
                    return "T_CHAR";
                }
                case "float" -> {
                    return "T_FLOAT";
                }
                case "if" -> {
                    return "T_IF";
                }
                case "while" -> {
                    return "T_WHILE";
                }
                case "for" -> {
                    return "T_FOR";
                }
                case "void" -> {
                    return "T_VOID";
                }
                case "else" -> {
                    return "T_ELSE";
                }
                case "return" -> {
                    return "T_RETURN";
                }
                case "printf" -> {
                    return "T_PRINTF";
                }
                case "scanf" -> {
                    return "T_SCANF";
                }
                default -> {
                    return "T_IDENT";
                }
            }
        }else{
            return "T_IDENT";
        }
    }


    public static void main(String[] args) {
        try {
            InputStream inputStream = new FileInputStream("C:/Users/vladi/Java 2c/DSL/Laba 1/src/ru.mirea.it.ivbo/input.txt");
            System.setIn(inputStream);

            OutputStream outputStream = new FileOutputStream("C:/Users/vladi/Java 2c/DSL/Laba 1/src/ru.mirea.it.ivbo/output.txt");
            System.setOut(new PrintStream(outputStream));

            String sym = getsym();

            while (!Objects.equals(sym, "T_EOF")) {
                System.out.printf("%d, %d, %s, ", line, count, sym);

                if (Objects.equals(sym, "T_IDENT")) {
                    System.out.printf("\"%s\"\n", ident);
                } else if (Objects.equals(sym, "T_INTCON")) {
                    System.out.printf("%d\n", int_num);
                }else if (Objects.equals(sym, "T_FLOATCON")) {
                    System.out.printf(float_num + "\n");
                }else if (Objects.equals(sym, "T_CHARCON") || Objects.equals(sym, "T_STRINGCON")) {
                        System.out.printf("\"%s\"\n", ident);
                } else if (Arrays.asList(reservedTokens).contains(sym)) {
                    System.out.printf("\"%s\"\n", ident);
//                }else if (Arrays.asList(withoutParam).contains(sym)) {
//                        System.out.println("");
                } else if(Arrays.asList(operations).contains(sym)){
                    System.out.printf("%s\n", ident);
                }else{
                    System.out.println("-");
                }
                sym = getsym();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



///* set global var */
//int i = 0;
///* main func */
//int main (void)
//{
//i = i + 1; /* incr of var */
//printf ("i = %d\n", i); /* print */
//return 0;
//}

///* определение глобальной переменной */
//float i = 10.1134;
//char t = '1';
//int o = 122;
//for (int i = 0; i < 10; i += 1){
//if (x > 0){
//return o;
//}
//else{
//x = 0;
//}
//}
