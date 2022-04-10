package Clases;

public interface Operador<T>
{
    /**
     * Aplica el operador a un objeto.
     * @return resultado del operador.
     */
    T run(T t);
}