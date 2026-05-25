public class Defensor extends Habitante{
    private int fuerza;
    public Defensor(String rut, String nombre, String apellido, String nombreUsuario, String contrasenia,
                    String rol, Rango rango, int fuerza) {
        super(rut, nombre, apellido, nombreUsuario, contrasenia, rol, rango);
        this.fuerza = fuerza;
    }
    public int getFuerza() {
        return fuerza;
    }
    public void setFuerza(int fuerza) {
        this.fuerza = fuerza;
    }
}
