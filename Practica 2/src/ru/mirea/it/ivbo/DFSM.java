package ru.mirea.it.ivbo;

import java.io.*;

public class DFSM {
    private static int state = 0;
    private static char ch = ' ';

    public static char getchar() {
        try {
            int s = System.in.read();
            while (0 <= s && s <= 31) {
                s = System.in.read();
            }
            System.out.println((char) s + " " + state);
            return (char) s;
        } catch (Exception e) {
            e.printStackTrace();
            return (char) -1;
        }
    }

    static void fsm() {
        switch (state) {
            case 0:
                ch = getchar();
                if (ch != 'm' && ch != 'k') {
                    state = 1;
                }else{
                    while (ch == 'm' || ch == 'k'){
                        ch = getchar();
                    }
                    state = 2;
                }
                fsm();
                break;
            case 1:
                printErrorMessage();
                break;
            case 2:
                if (ch != 'b'){
                    state = 1;
                }else{
                    ch = getchar();
                    if (ch == 'c'){
                        ch = getchar();
                        if (ch != 'b'){
                            state = 3;
                        } else state = 2;
                    }
                }
                fsm();
                break;
            case 3:
                if (ch == 'z') {
                    ch = getchar();
                    if (ch == 'z') state = 3;
                    else if(ch == (char) -1) state = 4;
                } else {
                    state = 1;
                }
                fsm();
                break;
            case 4:
                printSuccessMessage();
                break;
        }
    }

    private static void printSuccessMessage() {
        System.out.println("YES");
    }

    private static void printErrorMessage() {
        System.out.println("NO");
    }

    public static void main(String[] args) {
        try {
            InputStream inputStream = new FileInputStream("C:/Users/vladi/Java 2c/DSL/Practica 2/src/ru/mirea/it/ivbo/input.txt");
            System.setIn(inputStream);

            OutputStream outputStream = new FileOutputStream("C:/Users/vladi/Java 2c/DSL/Practica 2/src/ru/mirea/it/ivbo/output.txt");
            System.setOut(new PrintStream(outputStream));

            fsm();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
