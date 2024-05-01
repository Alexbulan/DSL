package ru.mirea.it.ivbo;

public class noResultException extends Exception {
    public noResultException() {
        super("This expression has no solution!");
    }
}