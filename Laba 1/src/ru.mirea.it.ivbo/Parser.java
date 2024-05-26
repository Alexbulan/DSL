package ru.mirea.it.ivbo;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;


public class Parser {
    static List allLines;
    static boolean AST = false;
    private static int i = 0;
    private static Boolean error = false;
    private static String ident = "";
    static String row = "";
    private static String num_of_sym = "";
    private static int num_of_Lbraces = 0;
    private static int num_of_Rbraces = 0;
    private static String sym = "";


    public static void isFunction() {
        if (!Objects.equals(sym, "T_VOID"))
            error("Error in " + ident + " (" + row + ":" + num_of_sym + "). Correct: \"void\"");
        getsym();
        if (!Objects.equals(sym, "T_IDENT") || !Objects.equals(ident, "main"))
            error("Error in " + ident + " (" + row + ":" + num_of_sym + "). Correct: 'main'");
        getsym();
        if (!Objects.equals(sym, "T_LPAREN"))
            error("Error in brackets" + " (" + row + ":" + num_of_sym + "). Correct: \"(\"");
        getsym();
        if (!Objects.equals(sym, "T_RPAREN"))
            error("Error in brackets" + " (" + row + ":" + num_of_sym + "). Correct: \")\"");
        getsym();
        if (!Objects.equals(sym, "T_LBRACE"))
            error("Error in brackets" + " (" + row + ":" + num_of_sym + "). Correct: \"{\"");
        num_of_Lbraces++;
        getsym();
        isReserved();
        if (!Objects.equals(sym, "T_RBRACE"))
            error("Error in brackets" + " (" + row + ":" + num_of_sym + "). Correct: \"}\"");
        num_of_Rbraces++;
        if (i == allLines.size() && num_of_Lbraces == (num_of_Rbraces))
            if (!error) Interpreter.printRes("The parsing was completed successfully!");
            else System.exit(-1);
    }

    public static void isReserved() {
        switch (sym) {
            case "T_INT":
            case "T_CHAR":
            case "T_FLOAT":
            case "T_STRING":
                isInit(true);
                break;
            case "T_IDENT":
                isAssign(true);
                break;
            case "T_IF":
                isIf();
                break;
            case "T_FOR":
                isFor();
                break;
            case "T_WHILE":
                isWhile();
                break;
            case "T_PRINTF":
                isPrintf();
                break;
            case "T_SCANF":
                isScanf();
                break;
            default:
                if (Objects.equals(sym, "T_RBRACE") && i == allLines.size() && num_of_Lbraces == (num_of_Rbraces + 1)) {
                    num_of_Rbraces++;
                    if (!error) {
                        System.out.println("The parsing was completed successfully");
                        System.exit(0);
                    } else System.exit(-1);
                } else if (i == allLines.size() && num_of_Lbraces != (num_of_Rbraces + 1)) {
                    error("Error in brackets" + " (" + row + ":" + num_of_sym + "). Correct: \"}\"");
                    System.exit(-1);
                }
        }

    }

    // flag: false - for, true - !for
    public static void isInit(boolean flag) {
        String x = "", type = "", exp = "";
        ;
        if (!Objects.equals(sym, "T_INT") && !Objects.equals(sym, "T_FLOAT") && !Objects.equals(sym, "T_CHAR") && !Objects.equals(sym, "T_STRING"))
            error("Error in " + ident + " (" + row + ":" + num_of_sym + "). Incorrect initialization");
        switch (sym) {
            case "T_INT" -> {
                getsym();
                if (!Objects.equals(sym, "T_IDENT"))
                    error("Error in " + ident + " (" + row + ":" + num_of_sym + "). Incorrect initialization");
                x = ident;
                type = "int";
            }
            case "T_CHAR" -> {
                getsym();
                if (!Objects.equals(sym, "T_IDENT"))
                    error("Error in " + ident + " (" + row + ":" + num_of_sym + "). Incorrect initialization");
                x = ident;
                type = "char";
            }
            case "T_FLOAT" -> {
                getsym();
                if (!Objects.equals(sym, "T_IDENT"))
                    error("Error in " + ident + " (" + row + ":" + num_of_sym + "). Incorrect initialization");
                x = ident;
                type = "float";
            }
            case "T_STRING" -> {
                getsym();
                if (!Objects.equals(sym, "T_IDENT"))
                    error("Error in " + ident + " (" + row + ":" + num_of_sym + "). Incorrect initialization");
                x = ident;
                type = "string";
            }
        }
//        if (!Objects.equals(sym, "T_IDENT")) error("Error in " + ident + " (" + row + ":" + num_of_sym + "). Incorrect initialization");
        getsym();
        if (Objects.equals(sym, "T_ASSGN")) {
            getsym();
            exp = "";
            if (Objects.equals(sym, "T_NOT")) isLogic(exp);
            else if (!Objects.equals(sym, "T_IDENT") && !Objects.equals(sym, "T_INTCON")
                    && !Objects.equals(sym, "T_CHARCON") && !Objects.equals(sym, "T_FLOATCON")
                    && !Objects.equals(sym, "T_STRINGCON") && !Objects.equals(sym, "T_LSQBRACE"))
                error("Error in " + ident + " (" + row + ":" + num_of_sym + "). Incorrect initialization");
            exp += ident;
            if (Objects.equals(sym, "T_LSQBRACE")) isArray();
            else {
                getsym();
                if (Objects.equals(sym, "T_ADD") || Objects.equals(sym, "T_SUB")
                        || Objects.equals(sym, "T_MUL") || Objects.equals(sym, "T_DIV")
                        || Objects.equals(sym, "T_REMOFDIV")) {
                    exp += " " + ident + " ";
                    exp = isArithmetic(exp);
                } else if (Objects.equals(sym, "T_EQUAL") || Objects.equals(sym, "T_LESS")
                        || Objects.equals(sym, "T_GREATER") || Objects.equals(sym, "T_LEQUAL")
                        || Objects.equals(sym, "T_REQUAL") || Objects.equals(sym, "T_NOTEQUAL")) {
                    exp += ident + " ";
                    exp = isComp(exp);
                } else if (Objects.equals(sym, "T_OR") || Objects.equals(sym, "T_AND")) {
                    exp += ident + " ";
                    exp = isLogic(exp);
                }
            }
        }
        if (!Objects.equals(sym, "T_SEMICL"))
            error("Error in \";\" " + "(" + row + ":" + num_of_sym + "). Incorrect initialization");
        else {
            if (f) {
                Initialization initialization = new Initialization(type, x, exp);
                if (AST) print(initialization.toString());
            }
            if (flag) {
                getsym();
                isReserved();
            }
        }
    }

    // flag: false - for, true - !for
//    static boolean self = false;

    public static void isAssign(boolean flag) {
        boolean self = false;
        String exp = "", x = "";
        if (flag) {
            if (!Objects.equals(sym, "T_IDENT"))
                error("Error in " + ident + " (" + row + ":" + num_of_sym + "). Incorrect assignment");
            x = ident;
            exp += x + " ";
            getsym();
            if (!Objects.equals(sym, "T_ASSGN"))
                if (Objects.equals(sym, "T_ADDEQUAL") || Objects.equals(sym, "T_SUBEQUAL") || Objects.equals(sym, "T_MULEQUAL")
                        || Objects.equals(sym, "T_DIVEQUAL") || Objects.equals(sym, "T_REMEQUAL")) {
                    exp += ident.charAt(0) + " ";
                    isSelfAssign(x, true, exp);
//                    self = true;
                    self = true;
                } else error("Error in \"=\" (" + row + ":" + num_of_sym + "). Incorrect assignment");
        }
        exp = "";
//        if (!self) {
        if (!self) {
            getsym();
            if (Objects.equals(sym, "T_NOT")) isLogic("");
            else if (!Objects.equals(sym, "T_IDENT") && !Objects.equals(sym, "T_INTCON")
                    && !Objects.equals(sym, "T_CHARCON") && !Objects.equals(sym, "T_FLOATCON")
                    && !Objects.equals(sym, "T_STRINGCON") && !Objects.equals(sym, "T_LSQBRACE"))
                error("Error in " + ident + " (" + row + ":" + num_of_sym + "). Incorrect assignment");
            exp += ident;
            if (Objects.equals(sym, "T_LSQBRACE")) isArray();
            else {
                getsym();
                if (Objects.equals(sym, "T_ADD") || Objects.equals(sym, "T_SUB")
                        || Objects.equals(sym, "T_MUL") || Objects.equals(sym, "T_DIV")
                        || Objects.equals(sym, "T_REMOFDIV")) {
                    exp += " " + ident + " ";
                    exp = isArithmetic(exp);
                } else if (Objects.equals(sym, "T_EQUAL") || Objects.equals(sym, "T_LESS")
                        || Objects.equals(sym, "T_GREATER") || Objects.equals(sym, "T_LEQUAL")
                        || Objects.equals(sym, "T_REQUAL") || Objects.equals(sym, "T_NOTEQUAL")) {
                    exp += " " + ident + " ";
                    exp = isComp(exp);
                } else if (Objects.equals(sym, "T_OR") || Objects.equals(sym, "T_AND")) {
                    exp += " " + ident + " ";
                    exp = isLogic(exp);
                }
            }
            if (!Objects.equals(sym, "T_SEMICL"))
                error("Error in \";\" " + "(" + row + ":" + num_of_sym + "). Incorrect assignment");
            else {
                if (f) {
                    Assign assign = new Assign(x, exp);
                    if (AST) print(assign.toString());
                }
                if (flag) {
                    getsym();
                    isReserved();
                }
            }
        }
    }

    public static void isSelfAssign(String x, boolean flag, String exp) {
        if (flag) {
            if (!Objects.equals(sym, "T_ADDEQUAL") && !Objects.equals(sym, "T_SUBEQUAL") && !Objects.equals(sym, "T_MULEQUAL")
                    && !Objects.equals(sym, "T_DIVEQUAL") && !Objects.equals(sym, "T_REMEQUAL"))
                error("Error in (" + row + ":" + num_of_sym + "). Incorrect assignment");
        }
        getsym();
        if (!Objects.equals(sym, "T_IDENT") && !Objects.equals(sym, "T_INTCON")
                && !Objects.equals(sym, "T_CHARCON") && !Objects.equals(sym, "T_FLOATCON")
                && !Objects.equals(sym, "T_STRINGCON"))
            error("Error in " + ident + " (" + row + ":" + num_of_sym + "). Incorrect assignment");
        exp += ident;
        getsym();
        if (!Objects.equals(sym, "T_SEMICL"))
            error("Error in \";\" " + "(" + row + ":" + num_of_sym + "). Incorrect assignment");
        else {
            Assign assign = new Assign(x, new ArithmeticExpression().getRes(exp));
            if (AST) print(assign.toString());
//            self = false;
            if (flag) {
                getsym();
                isReserved();
            }
        }
    }

    public static void isArray() {
        getsym();
        while (Objects.equals(sym, "T_IDENT") || Objects.equals(sym, "T_INTCON")
                || Objects.equals(sym, "T_CHARCON") || Objects.equals(sym, "T_FLOATCON")
                || Objects.equals(sym, "T_STRINGCON") || Objects.equals(sym, "T_LSQBRACE")) {
            getsym();
            if (Objects.equals(sym, "T_RSQBRACE")) break;
            if (!Objects.equals(sym, "T_COMMA"))
                error("Error in \",\" " + "(" + row + ":" + num_of_sym + "). Incorrect array structure");
            getsym();
        }
        if (!Objects.equals(sym, "T_RSQBRACE"))
            error("Error in brackets" + " (" + row + ":" + num_of_sym + "). Correct: \"]\"");
        getsym();
    }

    public static void isIf() {
        if (!Objects.equals(sym, "T_IF"))
            error("Error in " + ident + " (" + row + ":" + num_of_sym + "). Correct: \"if\"");
        getsym();
        if (!Objects.equals(sym, "T_LPAREN"))
            error("Error in brackets in \"if\" " + "(" + row + ":" + num_of_sym + "). Correct: \"(\"");
        getsym();
        String exp = "";
        if (Objects.equals(sym, "T_NOT")) isLogic(exp);
        else if (!Objects.equals(sym, "T_IDENT") && !Objects.equals(sym, "T_INTCON")
                && !Objects.equals(sym, "T_CHARCON") && !Objects.equals(sym, "T_FLOATCON")
                && !Objects.equals(sym, "T_STRINGCON"))
            error("Error in " + ident + " (" + row + ":" + num_of_sym + "). Incorrect condition in \"if\"");
        exp += ident + " ";
        getsym();
        if (Objects.equals(sym, "T_EQUAL") || Objects.equals(sym, "T_LESS")
                || Objects.equals(sym, "T_GREATER") || Objects.equals(sym, "T_LEQUAL")
                || Objects.equals(sym, "T_REQUAL") || Objects.equals(sym, "T_NOTEQUAL")) {
            exp = isComp(exp);
        } else if (Objects.equals(sym, "T_OR") || Objects.equals(sym, "T_AND")) {
            exp += ident + " ";
            exp = isLogic(exp);
        }
        if (!Objects.equals(sym, "T_RPAREN"))
            error("Error in brackets in \"if\" " + "(" + row + ":" + num_of_sym + "). Correct: \")\"");
        getsym();
        if (!Objects.equals(sym, "T_LBRACE"))
            error("Error in brackets in \"if\" " + "(" + row + ":" + num_of_sym + "). Correct: \"{\"");
        num_of_Lbraces++;
        getsym();
        If f = new If(exp);
        if (AST) print(f.toString());
        if (!Objects.equals(sym, "T_RBRACE")) {
            isReserved();
            if (!Objects.equals(sym, "T_RBRACE"))
                error("Error in brackets in \"if\" " + "(" + row + ":" + num_of_sym + "). Correct: \"}\"");
        }
        if (Objects.equals(sym, "T_RBRACE")) {
            num_of_Rbraces++;
            Interpreter.flag_to_do = true;
        }
        getsym();
        if (Objects.equals(sym, "T_ELSE")) isElse(exp);
        isReserved();
    }

    public static void isElse(String exp) {
        getsym();
        Else e = new Else(exp);
        if (AST) print(e.toString());
        if (!Objects.equals(sym, "T_LBRACE"))
            error("Error in brackets in \"else\" " + "(" + row + ":" + num_of_sym + "). Correct: \"{\"");
        num_of_Lbraces++;
        getsym();
        if (!Objects.equals(sym, "T_RBRACE")) {
            isReserved();
            if (!Objects.equals(sym, "T_RBRACE"))
                error("Error in brackets in \"else\" " + "(" + row + ":" + num_of_sym + "). Correct: \"}\"");
        }
        if (Objects.equals(sym, "T_RBRACE")) {
            num_of_Rbraces++;
            Interpreter.flag_to_do = true;
        }
        getsym();
//        }
    }

    static boolean f = true;

    public static void isFor() {
        String s = "";
        int r = i;
        if (!Objects.equals(sym, "T_FOR"))
            error("Error in " + ident + " (" + row + ":" + num_of_sym + "). Correct: \"for\"");
        getsym();
        if (!Objects.equals(sym, "T_LPAREN"))
            error("Error in brackets in \"for\" " + "(" + row + ":" + num_of_sym + "). Correct: \"(\"");
        getsym();
        if (Objects.equals(sym, "T_INT") || Objects.equals(sym, "T_FLOAT")) isInit(false);
        else if (Objects.equals(sym, "T_IDENT")) isAssign(false);
        else error("Incorrect initialization or assignment in \"for\" " + "(" + row + ":" + num_of_sym + ")");
        getsym();
        String exp = "", x, ex;
        if (Objects.equals(sym, "T_NOT")) isLogic("");
        else if (!Objects.equals(sym, "T_IDENT") && !Objects.equals(sym, "T_INTCON")
                && !Objects.equals(sym, "T_CHARCON") && !Objects.equals(sym, "T_FLOATCON")
                && !Objects.equals(sym, "T_STRINGCON"))
            error("Error in " + ident + " (" + row + ":" + num_of_sym + "). Incorrect condition in \"for\"");
        exp += ident + " ";
        getsym();
        if (Objects.equals(sym, "T_EQUAL") || Objects.equals(sym, "T_LESS")
                || Objects.equals(sym, "T_GREATER") || Objects.equals(sym, "T_LEQUAL")
                || Objects.equals(sym, "T_REQUAL") || Objects.equals(sym, "T_NOTEQUAL")) {
            s = isComp(exp);
        } else if (Objects.equals(sym, "T_OR") || Objects.equals(sym, "T_AND")) {
            exp += ident + " ";
            s = isLogic(exp);
        }
        if (!Objects.equals(sym, "T_SEMICL")) error("Error in \";\" in \"for\" " + "(" + row + ":" + num_of_sym + ")");
        ex = compr_expression;
        getsym();
        exp = "";
        if (!Objects.equals(sym, "T_IDENT"))
            error("Error in " + ident + ". Incorrect expression in \"for\" " + "(" + row + ":" + num_of_sym + ")");
        else {
            x = ident;
            exp += x + " ";
            getsym();
            if (Objects.equals(sym, "T_ASSGN")) isAssign(false);
            else if (Objects.equals(sym, "T_ADDEQUAL") || Objects.equals(sym, "T_SUBEQUAL") || Objects.equals(sym, "T_MULEQUAL")
                    || Objects.equals(sym, "T_DIVEQUAL") || Objects.equals(sym, "T_REMEQUAL")) {
                exp += ident.charAt(0) + " ";
                isSelfAssign(x, false, exp);
            }
            getsym();
        }
        if (!Objects.equals(sym, "T_RPAREN"))
            error("Error in brackets in \"for\" " + "(" + row + ":" + num_of_sym + "). Correct: \")\"");
        getsym();
        if (!Objects.equals(sym, "T_LBRACE"))
            error("Error in brackets in \"for\" " + "(" + row + ":" + num_of_sym + "). Correct: \"{\"");
        num_of_Lbraces++;
        getsym();
        if (!Objects.equals(sym, "T_RBRACE")) {
            isReserved();
            if (!Objects.equals(sym, "T_RBRACE"))
                error("Error in brackets in \"for\" " + "(" + row + ":" + num_of_sym + "). Correct: \"}\"");
        }
        if (Objects.equals(sym, "T_RBRACE")) {
            if (Objects.equals(s, "true")) {
                if (AST) print(new For(ex).toString());
                else new For(ex);
                i = r - 1;
                f = false;
            } else {
                f = true;
                Interpreter.loop_count = 0;
                Interpreter.flag_to_do = true;
            }
            num_of_Rbraces++;
        }
        getsym();
        isReserved();
    }

    public static void isWhile() {
        int r = i;
        if (!Objects.equals(sym, "T_WHILE"))
            error("Error in " + ident + " (" + row + ":" + num_of_sym + "). Correct: \"while\"");
        getsym();
        if (!Objects.equals(sym, "T_LPAREN"))
            error("Error in brackets in \"while\" " + "(" + row + ":" + num_of_sym + "). Correct: \"(\"");
        getsym();
        String exp = "", s = "";
        if (Objects.equals(sym, "T_NOT")) isLogic("");
        else if (!Objects.equals(sym, "T_IDENT") && !Objects.equals(sym, "T_INTCON")
                && !Objects.equals(sym, "T_CHARCON") && !Objects.equals(sym, "T_FLOATCON")
                && !Objects.equals(sym, "T_STRINGCON"))
            error("Error in " + ident + " (" + row + ":" + num_of_sym + "). Incorrect condition in \"while\"");
        exp += ident + " ";
        getsym();
        if (Objects.equals(sym, "T_EQUAL") || Objects.equals(sym, "T_LESS")
                || Objects.equals(sym, "T_GREATER") || Objects.equals(sym, "T_LEQUAL")
                || Objects.equals(sym, "T_REQUAL") || Objects.equals(sym, "T_NOTEQUAL")) {
            s = isComp(exp);
        } else if (Objects.equals(sym, "T_OR") || Objects.equals(sym, "T_AND")) {
            exp += ident + " ";
            s = isLogic(exp);
        }
        if (!Objects.equals(sym, "T_RPAREN"))
            error("Error in brackets in \"while\" " + "(" + row + ":" + num_of_sym + "). Correct: \")\"");
        getsym();
        if (!Objects.equals(sym, "T_LBRACE"))
            error("Error in brackets in \"while\" " + "(" + row + ":" + num_of_sym + "). Correct: \"{\"");
        num_of_Lbraces++;
        String ex = compr_expression;
        getsym();
        if (!Objects.equals(sym, "T_RBRACE")) {
            isReserved();
            if (!Objects.equals(sym, "T_RBRACE")) getsym();
            if (!Objects.equals(sym, "T_RBRACE"))
                error("Error in brackets in \"while\" " + "(" + row + ":" + num_of_sym + "). Correct: \"}\"");
        }
        if (Objects.equals(sym, "T_RBRACE")) {
            if (Objects.equals(s, "true")) {
                if (AST) print(new While(ex).toString());
                else new While(ex);
                i = r - 1;
            } else {
                Interpreter.loop_count = 0;
                Interpreter.flag_to_do = true;
            }
            num_of_Rbraces++;
//            Interpreter.flag_to_do = true;
        }
        getsym();
        isReserved();
    }

    public static void isPrintf() {
        String string, c;
        getsym();
        if (!Objects.equals(sym, "T_LPAREN"))
            error("Error in brackets in \"printf\" " + "(" + row + ":" + num_of_sym + "). Correct: \"(\"");
        getsym();
        if (!Objects.equals(sym, "T_STRINGCON"))
            error("Error in args in \"printf\" " + "(" + row + ":" + num_of_sym + ").");
        string = ident;
        getsym();
        if (!Objects.equals(sym, "T_COMMA"))
            error("The absence of a comma in \"printf\" " + "(" + row + ":" + num_of_sym + ").");
        getsym();
        if (!Objects.equals(sym, "T_IDENT") && !Objects.equals(sym, "T_STRINGCON") && !Objects.equals(sym, "T_CHARCON") && !Objects.equals(sym, "T_INTCON")
                && !Objects.equals(sym, "T_FLOATCON"))
            error("\"Error in " + ident + " in \"printf\" " + "(" + row + ":" + num_of_sym + ").");
        c = ident;
        getsym();
        if (!Objects.equals(sym, "T_RPAREN"))
            error("Error in brackets in \"printf\" " + "(" + row + ":" + num_of_sym + "). Correct: \")\"");
        getsym();
        if (!Objects.equals(sym, "T_SEMICL"))
            error("Error in \";\". Incorrect \"printf\" " + "(" + row + ":" + num_of_sym + ")");
        Printf printf = new Printf(string, c);
        if (AST) print(printf.toString());
        getsym();
        isReserved();
    }

    public static void isScanf() {
        String string, c;
        getsym();
        if (!Objects.equals(sym, "T_LPAREN"))
            error("Error in brackets in \"scanf\" " + "(" + row + ":" + num_of_sym + "). Correct: \"(\"");
        getsym();
        if (!Objects.equals(sym, "T_STRINGCON"))
            error("Error in args in \"scanf\" " + "(" + row + ":" + num_of_sym + ").");
        string = ident;
        getsym();
        if (!Objects.equals(sym, "T_COMMA"))
            error("The absence of a comma in \"scanf\" " + "(" + row + ":" + num_of_sym + ").");
        getsym();
        if (!Objects.equals(sym, "T_IDENT"))
            error("\"Error in " + ident + " in \"scanf\" " + "(" + row + ":" + num_of_sym + ").");
        c = ident;
        getsym();
        if (!Objects.equals(sym, "T_RPAREN"))
            error("Error in brackets in \"scanf\" " + "(" + row + ":" + num_of_sym + "). Correct: \")\"");
        getsym();
        if (!Objects.equals(sym, "T_SEMICL"))
            error("Error in \";\". Incorrect \"scanf\" " + "(" + row + ":" + num_of_sym + ")");
        Scanf scanf = new Scanf(string, c);
        if (AST) print(scanf.toString());
        getsym();
        isReserved();
    }

    static String logic_expression = "";

    public static String isLogic(String exp) {
        logic_expression = exp;
        if (Objects.equals(sym, "T_NOT")) {
            logic_expression += "! ";
            getsym();
            if (!Objects.equals(sym, "T_IDENT") && !(Objects.equals(sym, "T_INTCON") && Objects.equals(ident, "0") || Objects.equals(ident, "1")))
                error("Error in " + ident + " (" + row + ":" + num_of_sym + "). Incorrect boolean expression");
            logic_expression += ident + " ";
            getsym();
            if (!Objects.equals(sym, "T_OR") && !Objects.equals(sym, "T_AND"))
                error("Error in logic operation. Incorrect boolean expression " + " (" + row + ":" + num_of_sym + ").");
            logic_expression = Objects.equals(sym, "T_OR") ? logic_expression + "|| " : logic_expression + "&& ";
            getsym();
            if (Objects.equals(sym, "T_NOT")) {
                getsym();
                logic_expression += " ! ";
            }
            if (!Objects.equals(sym, "T_IDENT") && !(Objects.equals(sym, "T_INTCON") && Objects.equals(ident, "0") || Objects.equals(ident, "1")))
                error("Error in " + ident + " (" + row + ":" + num_of_sym + "). Incorrect boolean expression");
            logic_expression += ident + " ";
            if (AST) print(new LogicExpression().toString());
            return new LogicExpression().getRes(logic_expression);
        } else {
            getsym();
            if (Objects.equals(sym, "T_NOT")) {
                logic_expression += ident + " ";
                getsym();
            }
            if (!Objects.equals(sym, "T_IDENT") && !(Objects.equals(sym, "T_INTCON") && Objects.equals(ident, "0") || Objects.equals(ident, "1"))) {
                error("Error in " + ident + " (" + row + ":" + num_of_sym + "). Incorrect boolean expression");
                return "";
            } else {
                logic_expression += ident;
                getsym();
                LogicExpression logicExpression = new LogicExpression();
                if (AST) print(logicExpression.toString());
                return logicExpression.getRes(logic_expression);
            }
        }
    }

    static String arith_expression = "";

    public static String isArithmetic(String exp) {
        String exp_ident;
        arith_expression = exp;
        getsym();
        if (!Objects.equals(sym, "T_IDENT") && !Objects.equals(sym, "T_INTCON")
                && !Objects.equals(sym, "T_CHARCON") && !Objects.equals(sym, "T_FLOATCON")
                && !Objects.equals(sym, "T_STRINGCON"))
            error("Error in " + ident + " (" + row + ":" + num_of_sym + "). Incorrect arithmetic expression");
//        arith_expression += ident;
//        getsym();

        exp_ident = ident;
        getsym();
        ArithmeticExpression arithmeticExpression = new ArithmeticExpression();
        if (Objects.equals(sym, "T_ADD") || Objects.equals(sym, "T_SUB")
                || Objects.equals(sym, "T_MUL") || Objects.equals(sym, "T_DIV")
                || Objects.equals(sym, "T_REMOFDIV")) {
            exp_ident += " " + ident + " ";
            arith_expression += isArithmetic(exp_ident);
//            System.out.println(arith_expression);
            return arithmeticExpression.getRes(arith_expression);
        }else {
            arith_expression += exp_ident;
//            System.out.println(arith_expression);
            if (AST) print(arithmeticExpression.toString());
            return arithmeticExpression.getRes(arith_expression);
        }
    }

    static String compr_expression = "";

    public static String isComp(String exp) {
        String exp_ident;
        compr_expression = exp;
        if (!Objects.equals(sym, "T_EQUAL") && !Objects.equals(sym, "T_LESS")
                && !Objects.equals(sym, "T_GREATER") && !Objects.equals(sym, "T_LEQUAL")
                && !Objects.equals(sym, "T_REQUAL") && !Objects.equals(sym, "T_NOTEQUAL"))
            error("Error in " + ident + " (" + row + ":" + num_of_sym + "). Incorrect comparison expression");
        compr_expression += ident + " ";
        getsym();
        if (!Objects.equals(sym, "T_IDENT") && !Objects.equals(sym, "T_INTCON")
                && !Objects.equals(sym, "T_CHARCON") && !Objects.equals(sym, "T_FLOATCON")
                && !Objects.equals(sym, "T_STRINGCON"))
            error("Error in " + ident + " (" + row + ":" + num_of_sym + "). Incorrect comparison expression");
        exp_ident = ident;
        getsym();

        if (Objects.equals(sym, "T_ADD") || Objects.equals(sym, "T_SUB")
                || Objects.equals(sym, "T_MUL") || Objects.equals(sym, "T_DIV")
                || Objects.equals(sym, "T_REMOFDIV")) {
            exp_ident += " " + ident + " ";
            compr_expression += isArithmetic(exp_ident);
        }
        else if (Objects.equals(sym, "T_OR") || Objects.equals(sym, "T_AND")) {
            exp_ident += " " + ident + " ";
            compr_expression += isLogic(exp_ident);
        }else
            compr_expression += exp_ident;

        CompExpression compExpression = new CompExpression();
        if (AST) print(compExpression.toString());
        return compExpression.getRes(compr_expression);
    }

    public static void error(String err) {
        try {
            Interpreter.printSyntaxError(err);
            isReserved();
            error = true;
        } catch (StackOverflowError e) {
            System.exit(-1);
        }

    }
    public static void print(String str){
        Interpreter.printAST(str);
    }

    public static void getsym() {
        try {
            String line = (String) allLines.get(i);
            String[] n = line.split(", ");
            row = n[0];
            num_of_sym = n[1];
            sym = n[2];
            ident = n[3];
            i += 1;
            if (i == 1) isFunction();
//            else if (i > allLines.size()) System.exit(-1);
        } catch (Exception e) {
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        try {
            allLines = Files.readAllLines(Paths.get("C:/Users/vladi/Java 2c/DSL/Laba 1/src/ru.mirea.it.ivbo/outputParserTokens.txt"));
            OutputStream outputStream = new FileOutputStream("C:/Users/vladi/Java 2c/DSL/Laba 1/src/ru.mirea.it.ivbo/outputInterpritatorResult.txt");
            System.setOut(new PrintStream(outputStream));
            getsym();
            if (error) {
                System.exit(-1);
            } else {
                Interpreter.printRes("The parsing was completed successfully!");
            }
        } catch (FileNotFoundException e) {
            System.out.println("The file was not found");
        } catch (IOException e) {
//            throw new RuntimeException(e);
            System.exit(-1);
        }
    }

}

// скобки в выражениях, стэк ошибок, идентификаторы в резервированных
//void main()
//{
//
//int f = 0;
//printf("f = %d", f);
///*
//int x;
//scanf("%d", x);
//if (x >= 1) { s = false;
//    for (int i = 0; i < 10; i += 1;) {
//
//        printf("f = %d", f);
//    }
//    int d = ["a", "1"];
//    float k = 1.202;
//    char j = '';
//    while (i >= 100){
//        if (k == 0) {
//            k = k + 10;
//        } else {
//            k = 0;
//        }
//        i += 2;
//    }
//    printf("k = %d\n", k);
//}
//*/
//}