import ucn.ArchivoEntrada;
import ucn.Registro;
import ucn.StdIn;
import ucn.StdOut;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Random;

public class SistemaRefugioExodoImpl implements SistemaRefugioExodo {
    private ListaHabitantes listaHabitantes;
    private ListaSuministros listaSuministros;
    private ListaNexoMisiones listaNexoMisiones;

    public SistemaRefugioExodoImpl() throws IOException {
        boolean archivosValidos = false;
        this.listaHabitantes = new ListaHabitantes(100);
        this.listaSuministros = new ListaSuministros(100);
        this.listaNexoMisiones = new ListaNexoMisiones();
        try{
            this.cargaDeDatos();
            archivosValidos = this.datosCargados();
            if (!archivosValidos) {
                System.out.println("Advertencia: Uno o más archivos están vacíos.");
            }
            iniciarSistema();
        } catch (FileNotFoundException e) {
            System.out.println("Error: No se encontraron los archivos necesarios para que funcione el sistema.");
        }

    }

    @Override
    public String cargaDeDatos() throws IOException {
        ArchivoEntrada archivoEntradaHabitantes = new ArchivoEntrada("habitantes.txt");
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
        return listaHabitantes.getCantActualHab()> 0
                && listaNexoMisiones.getCantDatos() > 0
                && listaSuministros.getCantActualSum() > 0;
    }

    public void iniciarSistema() {
        int opcion = 0;
        do{
            StdOut.println("==== REFUGIO ÉXODO ====");
            StdOut.println("[1] Iniciar sesión");
            StdOut.println("[2] Registrar nuevo superviviente");
            StdOut.println("[3] Salir");
            StdOut.println("Ingrese una opción: ");
            try{
                opcion = StdIn.readInt();
                switch (opcion) {
                    case 1:
                        if(!datosCargados()){
                            StdOut.println("Error: No se encuentran datos en el sistema.");
                            StdOut.println("No es posible iniciar sesión sin datos cargados.");
                            break;
                        }
                        StdOut.println("------- Inicio de Sesión --------");
                        StdOut.println("Ingrese el nombre de usuario con el que desea iniciar sesión: ");
                        String nombreUsuario = StdIn.readString();

                        StdOut.println("Ingrese su contraseña: ");
                        String contrasenia = StdIn.readString();
                        boolean inicioExitoso = iniciarSesion(nombreUsuario, contrasenia);
                        if(inicioExitoso){
                            menuUsuario(nombreUsuario);
                        }else {
                            System.out.println("Credenciales invalidas. Intente nuevamente.");
                        }
                        StdOut.println("--------------------");
                        break;
                    case 2:
                        StdOut.println("--------------------");
                        StdOut.println(registrarSuperviviente());
                        StdOut.println("--------------------");
                        break;
                    case 3:
                        StdOut.println("--------------------");
                        StdOut.println("Cerrando el sistema");
                        StdOut.println("--------------------");
                        System.exit(0);
                    default:
                        StdOut.println("La opción ingresada es incorrecta");
                        break;
                }
            } catch(InputMismatchException e){
                System.out.println("Debe de ingresar un digito entero.");
                break;
            }
        }while(true);
    }

    public boolean iniciarSesion(String nombreUsuario, String contrasenia){
        if(listaHabitantes.buscarHabitantePorUsuario(nombreUsuario) != null){
            if(listaHabitantes.buscarHabitantePorUsuario(nombreUsuario).getContrasenia().equals(contrasenia)){
                return true;
            }
        }
        return false;
    }

    public void menuUsuario(String nombreUsuario){
        int opcion1 = 0;
        Habitante habitante = listaHabitantes.buscarHabitantePorUsuario(nombreUsuario);
        do{
            StdOut.println("\n----- Bienvenido " + nombreUsuario + "-----");
            StdOut.println("Rol: " + habitante.getRol() + "   Rango: " + habitante.getRango());
            StdOut.println("-------------------------------------------");
            StdOut.println("[1] Registrar misión de exploración");
            StdOut.println("[2] Consultar historial de expediciones");
            StdOut.println("[3] Gestionar rango");
            StdOut.println("[4] Administrar inventario (solo Leyenda)");
            StdOut.println("[5] Estadísticas del refugio");
            StdOut.println("[6] Cerrar Sesión");
            StdOut.println("Ingrese una opción: ");
            opcion1 = StdIn.readInt();
            switch(opcion1){
                case 1:
                case 6:
                    StdOut.println("Volviendo al menú anterior.");
                    break;
                default:
                    StdOut.println("La opción ingresada es invalida");
                    break;
            }
        }while(opcion1 != 6);
    }

    @Override
    public String registrarSuperviviente() {
        Habitante superviviente;
        StdOut.println("------- Registrar superviviente --------");
        StdOut.println("Ingrese el RUT del nuevo cliente (En formato: 12.345.678-9): ");
        String rutSuperviviente = StdIn.readString();
        StdOut.println("Ingrese el nombre del superviviente: ");
        String nombre = StdIn.readString();
        StdOut.println("Ingrese el apellido paterno del superviviente: ");
        String apellido = StdIn.readString();
        StdOut.println("Ingrese el nombre de usuario del superviviente: ");
        String nombreUsuario = StdIn.readString();
        StdOut.println("Ingrese el rol del superviviente: ");
        String rol = StdIn.readString();

        String rutSinVerificador = rutSuperviviente.replaceAll("[^0-9]", "");
        String contrasenia = rutSinVerificador.substring(0, rutSinVerificador.length() - 1);
        Rango rango = Rango.NOVATO;
        if (rol.equals("Explorador")) {
            Random random = new Random();
            int numero = random.nextInt(10);
            superviviente = new Explorador(rutSuperviviente, nombre, apellido, nombreUsuario, contrasenia, rol, rango, numero);
        } else if (rol.equals("Medico")) {
            Random random = new Random();
            int numero = random.nextInt(10);
            superviviente = new Medico(rutSuperviviente, nombre, apellido, nombreUsuario, contrasenia, rol, rango, numero);
        } else if (rol.equals("Defensor")) {
            Random random = new Random();
            int numero = random.nextInt(10);
            superviviente = new Defensor(rutSuperviviente, nombre, apellido, nombreUsuario, contrasenia, rol, rango, numero);
        } else {
            return "Error: No se pudo registrar el superviviente";
        }
        listaHabitantes.agregarHabitante(superviviente);
        return "Se ha registrado el superviviente en el sistema.";
    }
}
