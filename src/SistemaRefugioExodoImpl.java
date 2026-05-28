import ucn.ArchivoEntrada;
import ucn.Registro;
import ucn.StdIn;
import ucn.StdOut;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
                    if (tipo.equals("Comida") || tipo.equals("Medicina") || tipo.equals("Municion")) {
                        String descripcion = regEnt.getString();
                        double peso = regEnt.getDouble();
                        int cantidad = regEnt.getInt();
                        String estado = regEnt.getString();
                        Suministro suministro = new Suministro(id, tipo, descripcion, peso, cantidad, estado);
                        listaSuministros.agregarSuministro(suministro);
                    }
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
        archivoEntradaHabitantes.close();
        archivoEntradaSuministros.close();
        archivoEntradaMisiones.close();
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
            StdOut.println(habitante.describirHabilidad());
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
                    System.out.println(registrarNuevaMision(habitante));
                    break;
                case 2:
                    System.out.println(consultarHistorial(habitante));
                    break;
                case 4:
                    administrarInventario(habitante);
                    break;
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

    @Override
    public String registrarNuevaMision(Habitante usuario) {
        System.out.println(mostrarSuministrosDisponibles());
        System.out.println("Ingrese el id del suministro que desea buscar: ");
        int id = StdIn.readInt();
        System.out.println("Ingrese la cantidad de suministros que desea buscar: ");
        int cantidad = StdIn.readInt();
        Suministro sum = listaSuministros.obtenerSumPorId(id);
        if(sum == null){
            return "El suministro que quiere buscar no existe.";
        }
        if (cantidad <= 0) {
            return "La cantidad ingresada debe ser mayor a 0.";
        }else if (cantidad > sum.getCantidad()) {
            return "La cantidad ingresada supera el stock actual.";
        }
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String resultado = "";
        Random r = new Random();
        double random = r.nextDouble();
        double probabilidadFinal = usuario.calcularProbabilidadExito();
        if(random < probabilidadFinal){
            resultado = "Exitosa";
            System.out.println("La misión ha sido exitosa, se han recuperado la cantidad recuperada total.");
            if (sum.getCantidad() == 0){
                sum.setEstado("Agotado");
            }
            sum.setCantidad(sum.getCantidad()-cantidad);
            System.out.println("Creando comprobante de la misión...");
            Mision mision = new Mision(usuario, sum, fecha, cantidad, resultado);
            this.listaNexoMisiones.agregarInicio(mision);
            return comprobanteMision(mision);
        }else {
            resultado = "Fallida";
            Mision mision = new Mision(usuario, sum, fecha, cantidad, resultado);
            this.listaNexoMisiones.agregarInicio(mision);
            return "La misión ha sido fallida, se han perdido la cantidad recuperada.";
        }
    }

    @Override
    public String mostrarSuministrosDisponibles() {
        if(listaNexoMisiones.getCantDatos() == 0){
             return "Actualmente no existen misiones registradas";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("---- [ Suministros Disponibles ] ----").append("\n");;
        for(int i = 0; i < listaSuministros.getCantActualSum(); i++){
            Suministro sum = listaSuministros.buscarSuministro(i);
            if(sum != null){
                if(sum.getEstado().equalsIgnoreCase("Disponible")){
                    sb.append(sum.toString());
                }
            }
        }
        return sb.toString();
    }

    public String comprobanteMision(Mision m) {
        if (m.getResultado().equals("Fallida")) {
            return "\n" +
                    "*****************************************************\n" +
                    "            COMPROBANTE DE MISION\n"+
                    "Superviviente: " + m.getHabitante().getNombreCompleto() + " " + m.getHabitante().describirHabilidad() + "\n" +
                    "Suministro: " + m.getSuministro().getId() + " - " + m.getSuministro().getDescripcion() + "\n" +
                    "Cantidad buscada: " + m.getCantidadRecuperada() + "  |  Recuperada: " + m.getCantidadPerdida() + "\n" +
                    "Fecha: " + m.getFecha() + "\n" +
                    "Resultado: " + m.getResultado().toUpperCase() + "\n" +
                    "*****************************************************\n";
        }else{
            return "\n" +
                    "*****************************************************\n" +
                    "            COMPROBANTE DE MISION\n"+
                    "Superviviente: " + m.getHabitante().getNombreCompleto() + " " + m.getHabitante().describirHabilidad() + "\n" +
                    "Suministro: " + m.getSuministro().getId() + " - " + m.getSuministro().getDescripcion() + "\n" +
                    "Cantidad buscada: " + m.getCantidadRecuperada() + "  |  Recuperada: " + m.getCantidadRecuperada() + "\n" +
                    "Fecha: " + m.getFecha() + "\n" +
                    "Resultado: " + m.getResultado().toUpperCase() + "\n" +
                    "*****************************************************\n";
        }
    }

    public String consultarHistorial(Habitante usuario){
        int misionesHechas = 0;
        int exitos = 0;
        int fallidos = 0;
        int acumuladorCantRecuperadas = 0;
        double porcentaje;
        listaNexoMisiones.ordenarMisiones();
        for (int i = 0; i < listaNexoMisiones.getCantDatos(); i++) {
            Mision m = listaNexoMisiones.obtenerPorPosicion(i);
            if (m == null){
                return "La lista está vacía.";
            }else{
                if(m.getHabitante().getRut().equals(usuario.getRut())){
                    System.out.println(comprobanteMision(m));
                    if(m.getResultado().equals("Exitosa")){
                        exitos += exitos + 1;
                    } else if (m.getResultado().equals("Fallida")){
                        fallidos += fallidos + 1;
                    }
                    acumuladorCantRecuperadas += m.getCantidadRecuperada();
                    misionesHechas = misionesHechas + 1;
                }
            }
        }
        porcentaje = (double) exitos / listaNexoMisiones.getCantDatos();
        porcentaje *= 100;

        StringBuilder sb = new StringBuilder();
        sb.append("Total de misiones realizadas: ").append(misionesHechas).append("\n");
        sb.append("Misiones exitosas: ").append(exitos).append("  |  Misiones fallidas: ").append(fallidos).append("\n");
        sb.append("Total de unidades recuperadas: ").append(acumuladorCantRecuperadas).append("\n");
        sb.append("Tasa de éxito: ").append(porcentaje).append("%");
        return sb.toString();
    }

    @Override
    public void administrarInventario(Habitante usuario) {
        if (usuario.getRango().name().equalsIgnoreCase("Leyenda")) {
            int opcionA = 0;
            do {
                StdOut.println("===== Administración de mesas =====");
                StdOut.println("[1] Agregar nueva Mesa");
                StdOut.println("[2] Cambiar estado de una mesa");
                StdOut.println("[3] Volver al menu anterior");
                StdOut.println("Ingrese una opción: ");
                try {
                    opcionA  = StdIn.readInt();
                    switch (opcionA) {
                        case 1:
                            System.out.println(agregarSuministro());
                            break;
                        case 2:
                            System.out.println(cambiarEstado());

                            break;
                        case 3:
                            System.out.println("Volviendo al menú anterior...");
                            break;
                        default:
                            System.out.println("La opción ingresada es invalida");
                    }
                } catch(InputMismatchException e){
                    System.out.println("Debe de ingresar un digito entero.");
                    break;
                }
            }while (opcionA != 3);
        } else {
            System.out.println("Acceso restringido. Solo disponible para clientes Platino.");
        }


    }

    @Override
    public String agregarSuministro() {
        do{
            System.out.println("------- Crear Mesa --------");
            System.out.println("Ingrese el tipo de suministro: ");
            String tipo = StdIn.readString();
            if (tipo.equalsIgnoreCase("Comida") || tipo.equalsIgnoreCase("Medicina") || tipo.equalsIgnoreCase("Municion")) {
                System.out.println("Ingrese la descripción del suministro: ");
                String descripcion = StdIn.readString();
                System.out.println("Ingresa el peso del suministro: ");
                double peso = StdIn.readDouble();
                if(peso <= 0){
                    return "El peso debe ser mayor a 0";
                }
                System.out.println("Ingresa la cantidad del suministro: ");
                int cantidad = StdIn.readInt();
                if(cantidad <= 0){
                    return "El cantidad debe ser mayor a 0";
                }
                System.out.println("Ingrese el estado del suministro: ");
                String estado = StdIn.readString();
                int idSum = listaSuministros.getCantActualSum() + 1;
                Suministro sum = new Suministro(idSum, tipo, descripcion, peso, cantidad, estado);
                listaSuministros.agregarSuministro(sum);
                return "El suministro ha sido agregado al sistema con exito";
            } else {
                return "ERROR: Tipo de suministro no válido.";
            }
        }while(false);
    }

    @Override
    public String cambiarEstado() {
        StringBuilder sb = new StringBuilder();
        sb.append("---- [ Suministros ] ----").append("\n");
        for(int i = 0; i < listaSuministros.getCantActualSum(); i++){
            Suministro sum = listaSuministros.buscarSuministro(i);
            if(sum != null){
                sb.append(sum.toString());
            }
        }
        System.out.print(sb.toString());
        System.out.print("Ingrese el id del suministro que desee cambiar de estado: ");
        int id = StdIn.readInt();
        if(id < 1 || id > listaSuministros.getCantActualSum()){

        }

    }
}
