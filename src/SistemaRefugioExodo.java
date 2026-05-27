import java.io.IOException;

public interface SistemaRefugioExodo {
    String cargaDeDatos() throws IOException;
    boolean datosCargados();
    String registrarSuperviviente();
    String mostrarSuministrosDisponibles();
}
