public class Explorador extends Habitante {
    private int nivelSigilo;
    Explorador(String rut, String nombre, String apellido, String nombreUsuario, String contrasenia,
               String rol, Rango rango,  int nivelSigilo) {
        super(rut, nombre, apellido, nombreUsuario, contrasenia, rol, rango);
        this.nivelSigilo = nivelSigilo;
    }

    public int getNivelSigilo() {
        return nivelSigilo;
    }

    public void setNivelSigilo(int nivelSigilo) {
        this.nivelSigilo = nivelSigilo;
    }
}
