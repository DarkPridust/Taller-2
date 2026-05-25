public class NodoMision {
    private Mision dato;
    private NodoMision siguiente;

    public NodoMision(Mision dato) {
        this.dato = dato;
        this.siguiente = null;
    }
    public Mision getDato() {
        return dato;
    }
    public void setDato(Mision dato) {
        this.dato = dato;
    }

    public NodoMision getSiguiente() {
        return siguiente;
    }
    public void setSiguiente(NodoMision siguiente) {
        this.siguiente = siguiente;
    }
}
