package ru.mirea.it.ivbo;

public class Token {
    private String name;
    private int line;
    private String argument;

    public Token(String name, int line, String argument) {
        this.name = name;
        this.line = line;
        this.argument = argument;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getLine() {
        return line;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }

    public String getArgument() {
        return argument;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
