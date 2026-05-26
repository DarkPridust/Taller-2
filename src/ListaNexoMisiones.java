public class ListaNexoMisiones {
    private NodoMision head;
    private int cantElementos;

    public ListaNexoMisiones() {
        this.head = null;
        this.cantElementos = 0;
    }
    public boolean esVacio() {
        return this.head == null;
    }

    public boolean agregarInicio(Mision nuevo) {
        NodoMision nuevoNodo = new NodoMision(nuevo);

        if(esVacio()){
            this.head = nuevoNodo;
            this.cantElementos++;
            return true;
        }
        nuevoNodo.setSiguiente(this.head);
        this.head = nuevoNodo;
        this.cantElementos++;
        return true;
    }

    public NodoMision obtener(Mision dato) {
        NodoMision aux = this.head;
        while(aux != null){
            if(aux.getDato().equals(dato)){
                return aux;
            }
            aux = aux.getSiguiente();
        }
        return null;
    }

    public int getCantDatos() {
        return this.cantElementos;
    }

}
