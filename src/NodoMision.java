/**
 * Representa a la identidad NodoMision
 */
public class NodoMision {
    /**
     * Mision asociada al Nodo
     */
    private Mision dato;
    /**
     * Siguiente Nodo
     */
    private NodoMision siguiente;

    /**
     * Contructor de la clase NodoMision
     * @param dato un objeto de tipo Mision
     */
    public NodoMision(Mision dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    /**
     * Obtiene la Mision asociada al NodoMision
     * @return un objeto de tipo Mision
     */
    public Mision getDato() {
        return dato;
    }

    /**
     * Establece una Mision a NodoMision
     * @param dato un objeto de tipo Mision
     */
    public void setDato(Mision dato) {
        this.dato = dato;
    }

    /**
     * Obtiene el siguiente NodoMision
     * @return un objeto de tipo NodoMision
     */
    public NodoMision getSiguiente() {
        return siguiente;
    }

    /**
     * Establece el siguiente NodoMision
     * @param siguiente un objeto de tipo NodoMision
     */
    public void setSiguiente(NodoMision siguiente) {
        this.siguiente = siguiente;
    }
}
