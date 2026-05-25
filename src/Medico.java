public class Medico extends Habitante{
    private int capacidadMedica;
    public Medico(String rut, String nombre, String apellido, String nombreUsuario, String contrasenia,
                  String rol, Rango rango, int capacidadMedica) {
        super(rut, nombre, apellido, nombreUsuario, contrasenia, rol, rango);
        this.capacidadMedica = capacidadMedica;
    }
    public int getCapacidadMedica() {
        return capacidadMedica;
    }
    public void setCapacidadMedica(int capacidadMedica) {
        this.capacidadMedica = capacidadMedica;
    }
}
