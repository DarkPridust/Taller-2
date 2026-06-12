import java.io.IOException;

public interface SistemaRefugioExodo {
    String cargaDeDatos() throws IOException;
    boolean datosCargados();
    void iniciarSistema() throws IOException;
    boolean iniciarSesion(String nombreUsuario, String contrasenia);
    void menuUsuario(String nombreUsuario);
    String registrarSuperviviente();
    String registrarNuevaMision(Habitante usuario);
    String comprobanteMision(Mision m);
    String mostrarSuministrosDisponibles();
    String consultarHistorial(Habitante usuario);
    String subirRango(Habitante usuario);
    void administrarInventario(Habitante usuario);
    String agregarSuministro();
    String cambiarEstado();
    String mostrarEstadisticas();
    String supervivienteMejorExito();
    String suministroMasBuscado();
    void subirDatos() throws IOException;
}
