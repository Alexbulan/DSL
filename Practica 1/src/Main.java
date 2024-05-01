import ru.mirea.it.ivbo.ReversePolishRecord;
import ru.mirea.it.ivbo.ReversePolishRecord2;
import ru.mirea.it.ivbo.noResultException;

import java.io.*;


public class Main {
    public static void main(String[] args) throws noResultException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("C:/Users/vladi/Java 2c/DSL/Practica 1/src/ru/mirea/it/ivbo/input.txt"));
            String line = reader.readLine();
            OutputStream outputStream = new FileOutputStream("C:/Users/vladi/Java 2c/DSL/Practica 1/src/ru/mirea/it/ivbo/output.txt");
            System.setOut(new PrintStream(outputStream));
            System.out.println(line);
            ReversePolishRecord postfix = new ReversePolishRecord();
            ReversePolishRecord2 infix = new ReversePolishRecord2();
//            double res = postfix.getResult(line);
//            System.out.printf(String.valueOf(res));
            String res2 = infix.getResult(line);
            System.out.println(res2);
            double res = postfix.getResult(res2);
            System.out.printf(String.valueOf(res));
        }catch (Exception e) {
            System.out.print("This expression has no solution!");
            throw new noResultException();
        }
    }
}
