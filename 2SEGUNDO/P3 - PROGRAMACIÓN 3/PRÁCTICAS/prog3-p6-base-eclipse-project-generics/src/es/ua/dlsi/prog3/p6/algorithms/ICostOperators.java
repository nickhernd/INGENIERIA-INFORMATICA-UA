package es.ua.dlsi.prog3.p6.algorithms;

public interface ICostOperators<T> {
    T add(T a, T b);        // Operación de suma
    T zero();               // Valor cero
    T maximumValue();           // Valor máximo
}

