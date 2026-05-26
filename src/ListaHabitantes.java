public class ListaHabitantes {
    private Habitante[] listaHabitantes;
    private int cantActualHab;
    private int cantMaxHab;

    public ListaHabitantes(int cantMaxHab) {
        this.cantMaxHab = cantMaxHab;
        this.listaHabitantes = new Habitante[cantMaxHab];
        this.cantActualHab = 0;
    }

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

    public Habitante obtenerHabitante(int pos){
        if(pos < 0 || pos >= this.cantActualHab){
            return null;
        }
        return this.listaHabitantes[pos];
    }

    public Habitante buscarHabitantePorRut(String rut){
        for(int i = 0; i < this.cantActualHab; i++){
            if (this.listaHabitantes[i].getRut().equals(rut)){
                return this.listaHabitantes[i];
            }
        }
        return null;
    }

    public Habitante buscarHabitantePorUsuario(String nombreUsuario){
        for(int i = 0; i < this.cantActualHab; i++){
            if (this.listaHabitantes[i].getNombreUsuario().equals(nombreUsuario)){
                return this.listaHabitantes[i];
            }
        }
        return null;
    }

    public int getCantActualHab() {
        return cantActualHab;
    }
}
