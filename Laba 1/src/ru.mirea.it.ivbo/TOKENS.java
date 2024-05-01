package ru.mirea.it.ivbo;

public enum TOKENS {
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
