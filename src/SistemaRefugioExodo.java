import java.io.IOException;

public interface SistemaRefugioExodo {
    String cargaDeDatos() throws IOException;
    boolean datosCargados();
    String registrarSuperviviente();
    String registrarNuevaMision(Habitante usuario);
    String mostrarSuministrosDisponibles();
    void administrarInventario(Habitante usuario);
    String agregarSuministro();
    String cambiarEstado();
}
