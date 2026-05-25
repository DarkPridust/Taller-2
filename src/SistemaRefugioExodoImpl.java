import ucn.ArchivoEntrada;
import ucn.Registro;

import java.io.IOException;
import java.util.Random;

public class SistemaRefugioExodoImpl implements SistemaRefugioExodo {
    private ListaHabitantes listaHabitantes;
    private ListaSuministros listaSuministros;
    private ListaNexoMisiones listaNexoMisiones;

    public SistemaRefugioExodoImpl() {
        this.listaHabitantes = new ListaHabitantes(100);
        this.listaSuministros = new ListaSuministros(100);
        this.listaNexoMisiones = new ListaNexoMisiones();
    }

    @Override
    public String cargaDeDatos() throws IOException {
        ArchivoEntrada archivoEntradaHabitantes = new ArchivoEntrada("clientes.txt");
        Habitante superviviente;
        try {
            while (!archivoEntradaHabitantes.isEndFile()) {
                Registro regEnt = archivoEntradaHabitantes.getRegistro();
                try {
                    String rut = regEnt.getString();
                    String nombre = regEnt.getString();
                    String apellido = regEnt.getString();
                    String nombreUsuario = regEnt.getString();
                    String contrasenia = regEnt.getString();
                    String rol = regEnt.getString();
                    Rango rango = Rango.valueOf(regEnt.getString().toUpperCase());
                    if (rol.equals("Explorador")) {
                        Random random = new Random();
                        int numero = random.nextInt(10);
                        superviviente = new Explorador(rut, nombre, apellido, nombreUsuario, contrasenia, rol, rango, numero);
                    } else if (rol.equals("Medico")) {
                        Random random = new Random();
                        int numero = random.nextInt(10);
                        superviviente = new Medico(rut, nombre, apellido, nombreUsuario, contrasenia, rol, rango, numero);
                    } else if (rol.equals("Defensor")) {
                        Random random = new Random();
                        int numero = random.nextInt(10);
                        superviviente = new Defensor(rut, nombre, apellido, nombreUsuario, contrasenia, rol, rango, numero);
                    } else {
                        continue;
                    }
                    listaHabitantes.agregarHabitante(superviviente);
                } catch (Exception e) {
                    return "Error al agregar al habitante: " + e.getMessage();
                }
            }
        } catch (Exception e) {
            return "Error, No se pudo leer el archivo de habitantes: " + e.getMessage();
        }

        ArchivoEntrada archivoEntradaSuministros = new ArchivoEntrada("suministros.txt");
        try {
            while (!archivoEntradaSuministros.isEndFile()) {

                Registro regEnt = archivoEntradaSuministros.getRegistro();
                try {
                    int id = regEnt.getInt();
                    String tipo = regEnt.getString();
                    String descripcion = regEnt.getString();
                    double peso = regEnt.getDouble();
                    int cantidad = regEnt.getInt();
                    String estado = regEnt.getString();
                    Suministro suministro = new Suministro(id, tipo, descripcion, peso, cantidad, estado);
                    listaSuministros.agregarSuministro(suministro);
                } catch (Exception e) {
                    return "Error al agregar un suministro: " + e.getMessage();
                }
            }
        } catch (Exception e) {
            return "Error, No se pudo leer el archivo de suministros: " + e.getMessage();
        }

        ArchivoEntrada archivoEntradaMisiones = new ArchivoEntrada("misiones.txt");
        try {
            while (!archivoEntradaMisiones.isEndFile()) {

                Registro regEnt = archivoEntradaMisiones.getRegistro();
                try {
                    String rutHabitante = regEnt.getString();
                    int idSuministro = regEnt.getInt();
                    String fecha = regEnt.getString();
                    int cantidadRecuperada = regEnt.getInt();
                    String resultado = regEnt.getString();
                    Habitante habitante = listaHabitantes.buscarHabitantePorRut(rutHabitante);
                    Suministro suministro = listaSuministros.obtenerSumPorId(idSuministro);
                    Mision mision = new Mision(habitante, suministro, fecha, cantidadRecuperada, resultado);
                    listaNexoMisiones.agregarInicio(mision);
                } catch (Exception e) {
                    return "Error al agregar una misión: " + e.getMessage();
                }
            }
        }catch (Exception e) {
            return "Error, No se pudo leer el archivo de misiones: " + e.getMessage();
        }
        return "Se han leido los archivos correctamente";
    }

    @Override
    public boolean datosCargados() {
        return false;
    }

    @Override
    public boolean registrarSupervivencia() {
        return false;
    }
}
