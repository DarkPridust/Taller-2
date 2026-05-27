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

    public int getCantDatos() {
        return this.cantElementos;
    }

}
