/**
 * Representa la identidad ListaNexoMisiones.
 */
public class ListaNexoMisiones {
    /**
     * Primer NodoMision de la ListaNexoMisiones.
     */
    private NodoMision head;
    /**
     * Cantidad actual de NodoMision.
     */
    private int cantElementos;

    /**
     * Constructor de la ListaNexoMisiones.
     */
    public ListaNexoMisiones() {
        this.head = null;
        this.cantElementos = 0;
    }

    /**
     * Verifica si la lista esta vacia.
     * @return un booleano con el resultado.
     */
    public boolean esVacio() {
        return this.head == null;
    }

    /**
     * Agrega la inicio un nuevo NodoMision.
     * @param nuevo un objeto de tipo NodoMision.
     * @return      un booleano con el resultado.
     */
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

    /**
     * Busca un NodoMision por su dato.
     * @param dato  un objeto de tipo Mision.
     * @return      un objeto de tipo NodoMision.
     */
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

    /**
     * Obtiene una Mision por su posición.
     * @param pos   un entero con la posición de la Mision.
     * @return      un obtejo de tipo Mision.
     */
    public Mision obtenerPorPosicion(int pos) {
        if(esVacio()){
            return null;
        }

        NodoMision aux = this.head;
        int i = 0;
        while(aux != null){
            if (i == pos){
                return aux.getDato();
            }
            aux = aux.getSiguiente();
            i++;
        }
        throw new IndexOutOfBoundsException("La posición ingresada está fuera de rango.");
    }

    /**
     * Ordena las misiones por su fecha de realización.
     */
    public void ordenarMisiones(){
        if(esVacio()){
            System.out.println("La lista está vacía");
        }
        boolean ordenado;
        do{
            ordenado = false;
            NodoMision aux = this.head;
            while(aux.getSiguiente() != null){
                if (aux.getDato().getFechaHoy().isBefore(aux.getSiguiente().getDato().getFechaHoy())){
                    Mision temp = aux.getDato();
                    aux.setDato(aux.getSiguiente().getDato());
                    aux.getSiguiente().setDato(temp);
                    ordenado = true;
                }
                aux = aux.getSiguiente();
            }

        }while(ordenado);
    }

    /**
     * Obtiene la cantidad de elementos de la ListaNexoMisiones.
     * @return un entero con la cantidad de elementos de la ListaNexoMisiones.
     */
    public int getCantDatos() {
        return this.cantElementos;
    }

}
