/**
 * Representa la lista de suministros.
 */
public class ListaSuministros {
    /**
     * Arreglo de Suministros.
     */
    private Suministro[] listaSuministros;
    /**
     * Cantidad actual de Suministros.
     */
    private int cantActualSum;
    /**
     * Cantidad maxima de Suministros.
     */
    private int cantMaxSum;

    /**
     * Contructor de la ListaSumnistro.
     * @param cantMaxSum un entero con la cantidad maxima de Suministros.
     */
    public ListaSuministros(int cantMaxSum) {
        this.cantMaxSum = cantMaxSum;
        this.listaSuministros = new Suministro[cantMaxSum];
        this.cantActualSum = 0;
    }

    /**
     * Agrega un Suministro a la ListaSuministro.
     * @param s un objeto de tipo Suministro.
     * @return  un booleano con el resultado.
     */
    public boolean agregarSuministro(Suministro s) {
        if(this.cantActualSum >= this.cantMaxSum) {
            System.out.println("No se puede agregar más suministros. Capacidad maxima alcanzada.");
            return false;
        }
        if(this.buscarSuministro(s.getId()) != null) {
            System.out.println("Ya existe un suministro con la id " + s.getId());
            return false;
        }
        this.listaSuministros[this.cantActualSum] = s;
        this.cantActualSum++;
        return true;
    }

    /**
     * Busca un Suministro por su Posición.
     * @param pos un entero con la posición del Suministro.
     * @return    un objeto de tipo Suministro.
     */
    public Suministro buscarSuministro(int pos){
        if(pos < 0 || pos >= this.cantActualSum) {
            return null;
        }
        return this.listaSuministros[pos];
    }

    /**
     * Obtiene un Suministro por su id.
     * @param id un entero con el identificador del Suministro.
     * @return   un objeto de tipo Suministro.
     */
    public Suministro obtenerSumPorId(int id){
        for(int i = 0; i < this.cantActualSum; i++){
            if (listaSuministros[i].getId() == id){
                return listaSuministros[i];
            }
        }
        return null;
    }

    /**
     * Obtiene la cantidad actual de Suministros de ListaSuministro.
     * @return un entero con la cantidad actual de Suministros.
     */
    public int getCantActualSum() {
        return cantActualSum;
    }
}
