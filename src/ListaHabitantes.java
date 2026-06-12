/**
 * Representa a la identidad ListaHabitante.
 */
public class ListaHabitantes {
    /**
     * Arreglo de Habitantes.
     */
    private Habitante[] listaHabitantes;
    /**
     * Cantidad actual de Habitantes.
     */
    private int cantActualHab;
    /**
     * Cantidad maxima de Habitantes.
     */
    private int cantMaxHab;

    /**
     * Constructor de la clase ListaHabitantes.
     * @param cantMaxHab    Cantidad maxima de Habitantes.
     */
    public ListaHabitantes(int cantMaxHab) {
        this.cantMaxHab = cantMaxHab;
        this.listaHabitantes = new Habitante[cantMaxHab];
        this.cantActualHab = 0;
    }

    /**
     * Agrega un Habitante a ListaHabitantes.
     * @param    h un objeto de clase Habitante.
     * @return   un booleano con el resultado.
     */
    public boolean agregarHabitante(Habitante h) {
        if(this.cantActualHab >= cantMaxHab) {
            System.out.println("No se puede agregar más supervivientes. Capacidad maxima alcanzada.");
            return false;
        }
        if (this.buscarHabitantePorRut(h.getRut()) != null){
            System.out.println("Ya existe un superviviente con el RUT: " + h.getRut());
            return false;
        }
        if (this.buscarHabitantePorUsuario(h.getNombreUsuario()) != null){
            System.out.println("Ya existe un superviviente con el nombre de usuario: " + h.getNombreUsuario());
            return false;
        }
        this.listaHabitantes[cantActualHab] = h;
        this.cantActualHab++;
        return true;
    }

    /**
     * Obtiene un Habitante de la ListaHabitantes por la posición.
     * @param pos   un entero con la posición del habitante.
     * @return      un objeto de tipo Habitante.
     */
    public Habitante obtenerHabitante(int pos){
        if(pos < 0 || pos >= this.cantActualHab){
            return null;
        }
        return this.listaHabitantes[pos];
    }

    /**
     * Busca un Habitante de la ListaHabitante por su rut.
     * @param rut   un string con el rut del habitante.
     * @return      un objeto de tipo Habitante.
     */
    public Habitante buscarHabitantePorRut(String rut){
        for(int i = 0; i < this.cantActualHab; i++){
            if (this.listaHabitantes[i].getRut().equals(rut)){
                return this.listaHabitantes[i];
            }
        }
        return null;
    }

    /**
     * Busca un Habitante de la ListaHabitante por su usuario.
     * @param nombreUsuario un string con el usuario del Habitante.
     * @return              un objeto de tipo Habitante.
     */
    public Habitante buscarHabitantePorUsuario(String nombreUsuario){
        for(int i = 0; i < this.cantActualHab; i++){
            if (this.listaHabitantes[i].getNombreUsuario().equals(nombreUsuario)){
                return this.listaHabitantes[i];
            }
        }
        return null;
    }

    /**
     *          Obtiene la cantidad actual de Habitante de ListaHabitante.
     * @return  un entero con la cantidad actual de Habitantes en la ListaHabitante.
     */
    public int getCantActualHab() {
        return cantActualHab;
    }

    /**
     * Obtiene los habitantes de su contenedor
     * @return un objeto Habitante
     */
    public Habitante[] getListaHabitantes(){
        return this.listaHabitantes;
    }
}
