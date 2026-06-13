// Imports de todas las utilidades
import ucn.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Random;

/**
 * Se asocia el implementador con la interface.
 */
public class SistemaRefugioExodoImpl implements SistemaRefugioExodo {
    private ListaHabitantes listaHabitantes;
    private ListaSuministros listaSuministros;
    private ListaNexoMisiones listaNexoMisiones;

    /**
     * Función que inicializa las listas si se lograron leer con exito los archivos txt.
     * @throws IOException
     */
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
            System.exit(1);
        }

    }

    /**
     * Función que carga los datos de los archivos txt (habitantes, misiones, suministros).
     * @return un String con las diferentes posibilidades al leer el archivo txt.
     * @throws IOException
     */
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

    /**
     * Función que verifica si los contenedores tienen al menos un elemento cargado al sistema.
     * @return un booleano que indica si existen elementos cargados.
     */
    @Override
    public boolean datosCargados() {
        return listaHabitantes.getCantActualHab()> 0
                && listaNexoMisiones.getCantDatos() > 0
                && listaSuministros.getCantActualSum() > 0;
    }

    /**
     * Función que despliega el menú inicial del sistema
     */
    @Override
    public void iniciarSistema() throws IOException {
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
                        subirDatos();
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

    /**
     * Función que permite iniciar sesión si las credenciales entregadas están correctamente asociadas a un habitante.
     * @param nombreUsuario un String con el nombre de usuario.
     * @param contrasenia un String con la contraseña del usuario.
     * @return un booleano que determina si se logro iniciar sesion o no.
     */
    @Override
    public boolean iniciarSesion(String nombreUsuario, String contrasenia){
        if(listaHabitantes.buscarHabitantePorUsuario(nombreUsuario) != null){
            if(listaHabitantes.buscarHabitantePorUsuario(nombreUsuario).getContrasenia().equals(contrasenia)){
                return true;
            }
        }
        return false;
    }

    /**
     * Función que despliega el menú de usuario luego de iniciar sesión.
     * @param nombreUsuario un String con el nombre de usuario.
     */
    @Override
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
                case 3:
                    System.out.println(subirRango(habitante));
                    break;
                case 4:
                    administrarInventario(habitante);
                    break;
                case 5:
                    System.out.println(mostrarEstadisticas());
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

    /**
     * Función que permite registrar un nuevo cliente.
     * @return un String con la confirmación del registro del nuevo superviviente.
     */
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
        StdOut.println("Ingrese el rol del superviviente(Explorador/Medico/Defensor): ");
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

    /**
     * Función que permite registrar una nueva misión.
     * @param usuario un objeto de tipo Habitante.
     * @return un String con la confirmación del registro de la nueva misión.
     */
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
            Mision mision = new Mision(usuario, sum, fecha, 0, resultado);
            this.listaNexoMisiones.agregarInicio(mision);
            return "La misión ha sido fallida, se han perdido la cantidad recuperada.";
        }
    }

    /**
     * Funcion que indica los suministros totales con el estado "disponible",
     * @return un String con los suministros disponibles actualmente.
     */
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

    /**
     * Función que despliega el comprobante con la información de la misión realizada.
     * @param m un objeto de tipo Mision.
     * @return un String con el resultado tras realizar una misión.
     */
    @Override
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

    /**
     * Función que muestra el historial asociado al superviviente.
     * @param usuario un objeto de tipo Habitante.
     * @return un String con el historial del superviviente.
     */
    @Override
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
        if(misionesHechas != 0){
            porcentaje = (double) exitos / misionesHechas;
        } else {
            porcentaje = 0;
        }
        porcentaje *= 100;

        StringBuilder sb = new StringBuilder();
        sb.append("Total de misiones realizadas: ").append(misionesHechas).append("\n");
        sb.append("Misiones exitosas: ").append(exitos).append("  |  Misiones fallidas: ").append(fallidos).append("\n");
        sb.append("Total de unidades recuperadas: ").append(acumuladorCantRecuperadas).append("\n");
        sb.append("Tasa de éxito: ").append(porcentaje).append("%");
        return sb.toString();
    }
    /**
     * Función que verifica y permite subir de rango del superviviente
     * @param usuario un objeto de tipo Habitante.
     */
    @Override
    public String subirRango(Habitante usuario) {
        String rut = usuario.getRut();
        String rangoActual = usuario.getRango().toString().toUpperCase();
        Rango rango = Rango.valueOf(rangoActual);
        int misionesExitosas = listaNexoMisiones.contarMisionesExitosasPorRut(rut, rangoActual);


        if (rango == Rango.LEYENDA) {
            return "Ya tienes el rango máximo";
        }
        if (rango.puedeSubirRango(misionesExitosas)) {
            System.out.println("Cumples con los requisitos para subir de rango!");
        } else {
            System.out.println("No cumples con los requisitos para subir de rango");
            return "Realiza más misiones para poder subir de rango";
        }

        System.out.println("=== Subir de Rango ===");
        System.out.println("Rango actual: " + rangoActual);
        System.out.println("Misiones exitosas: " + misionesExitosas);
        System.out.println("¿Deseas subir de rango? (Si/No)");
        String respuesta = StdIn.readString();
        if (respuesta.equalsIgnoreCase("Si")) {
            if (rango.puedeSubirRango(misionesExitosas)) {
                Rango siguienteRango = rango.rangoSiguiente();
                usuario.setRango(siguienteRango);
                return "Su rango ha sido ascendido a " + siguienteRango.name() + " exitosamente";
            }
        }
        return "Mantuviste tu rango actual: " + rangoActual;
    }
    /**
     * Función que permite administrar el inventario de suministros.
     * @param usuario un objeto de tipo Habitante.
     */
    @Override
    public void administrarInventario(Habitante usuario) {
        if (usuario.getRango().name().equalsIgnoreCase("Leyenda")) {
            int opcionA = 0;
            do {
                StdOut.println("===== Administración de inventario =====");
                StdOut.println("[1] Agregar nuevo Suministro");
                StdOut.println("[2] Cambiar estado de un Suministro");
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

    /**
     * Función que permite agregar un suministro.
     * @return un String con los posibles resultados al intentar agregar un suministro.
     */
    @Override
    public String agregarSuministro() {
        do{
            System.out.println("------- Crear Suministro --------");
            System.out.println("Ingrese el tipo de suministro(Comida/Medicina/Municion): ");
            String tipo = StdIn.readString();
            if (tipo.equalsIgnoreCase("Comida") || tipo.equalsIgnoreCase("Medicina") || tipo.equalsIgnoreCase("Municion")) {
                System.out.println("Ingrese la descripción del suministro: ");
                String descripcion = StdIn.readString();
                System.out.println("Ingresa el peso del suministro(en kilogramos): ");
                double peso = StdIn.readDouble();
                if(peso <= 0){
                    return "El peso debe ser mayor a 0";
                }
                System.out.println("Ingresa la cantidad del suministro(en unidades): ");
                int cantidad = StdIn.readInt();
                if(cantidad <= 0){
                    return "El cantidad debe ser mayor a 0";
                }
                System.out.println("Ingrese el estado del suministro(Disponible/Agotado): ");
                String estado = StdIn.readString();
                if(!estado.equalsIgnoreCase("Disponible") && !estado.equalsIgnoreCase("Agotado")){
                    return "El estado ingresado esta fuera de los disponibles";
                }
                int idSum = listaSuministros.getCantActualSum() + 1;
                Suministro sum = new Suministro(idSum, tipo, descripcion, peso, cantidad, estado);
                listaSuministros.agregarSuministro(sum);
                return "El suministro ha sido agregado al sistema con exito";
            } else {
                return "ERROR: Tipo de suministro no válido.";
            }
        }while(false);
    }

    /**
     * Función que permite cambiar el estado de un suministro.
     * @return un String con los posibles resultados al intentar cambiar el estado de un suministro.
     */
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
            return "La id ingresada está fuera del alcance de la lista.";
        } else {
            Suministro sum = listaSuministros.buscarSuministro(id - 1);
            String estado = sum.getEstado();
            if(estado.equals("Disponible")) {
                sum.setEstado("Agotado");
                return "El suministro con el id " + id + ", se cambio su estado a Agotado";
            }
            if (estado.equals("Agotado")) {
                sum.setEstado("Disponible");
                return "El suministro con el id " + id + ", se cambio su estado a Disponible";
            }
        }
        return "Volviendo al menú";
    }

    /**
     * Función que despliega las estadísticas del sistema.
     * @return un String con las estadísticas del sistema.
     */
    @Override
    public String mostrarEstadisticas() {
        StringBuilder sb = new StringBuilder();
        sb.append("=========================================================== \n");
        sb.append("La cantidad de misiones realizadas dentro del sistema es: ").append(listaNexoMisiones.getCantDatos()).append("\n");

        int sumRecuperado = 0;
        for(int i=0;i< listaNexoMisiones.getCantDatos();i++){
            Mision m = listaNexoMisiones.obtenerPorPosicion(i);
            sumRecuperado += m.getCantidadRecuperada();
        }
        if(sumRecuperado==0){
            sb.append("No se ha recuperado ni un suministro").append("\n");
        }
        sb.append("El total de suministros recuperados en total es de: ").append(sumRecuperado).append("\n");
        sb.append(supervivienteMejorExito()).append("\n");
        sb.append(suministroMasBuscado()).append("\n");

        int misionesHechas = 0;
        int exitos = 0;
        double porcentaje;
        for (int i = 0; i < listaNexoMisiones.getCantDatos(); i++) {
            Mision m = listaNexoMisiones.obtenerPorPosicion(i);
            if (m == null){
                sb.append("La lista se encuentra vacía.");
            }else{
                if(m.getResultado().equals("Exitosa")) {
                    exitos++;
                }
                misionesHechas = misionesHechas + 1;
            }
        }
        porcentaje = (double) exitos / misionesHechas;
        porcentaje *= 100;
        sb.append("El porcentaje global de misiones exitosas sobre el total de las misiones: ").append(porcentaje).append("%");
        sb.append("\n"+"===========================================================");
        return sb.toString();
    }

    /**
     * Función que identifica el/los supervivientes con mayor tasa de éxitos
     * @return un String con el/los supervivientes con mayor tasa de éxitos.
     */
    @Override
    public String supervivienteMejorExito() {
        //Superviviente con mayor tasa de exito
        String[] TopSuperviviente = new String[listaHabitantes.getCantActualHab()];
        int cantTops = 0;
        double maxTasaExito = -1.0;

        for(int i=0;i< listaHabitantes.getCantActualHab();i++){
            String rutHabitante = listaHabitantes.obtenerHabitante(i).getRut();
            int cantExitos = 0;
            int cantMisionesRealizadas = 0;

            for(int j=0;j< listaNexoMisiones.getCantDatos();j++) {
                Mision m =listaNexoMisiones.obtenerPorPosicion(j);
                String rutMision = m.getHabitante().getRut();

                if (rutMision.equals(rutHabitante)) {
                    cantMisionesRealizadas++;
                    if (m.getResultado().equalsIgnoreCase("exitosa")) {
                        cantExitos++;
                    }
                }
            }
            if(cantMisionesRealizadas>0){
                double tasaExito = (double) cantExitos/cantMisionesRealizadas;

                if(tasaExito > maxTasaExito){
                    maxTasaExito = tasaExito;
                    cantTops = 0;
                    TopSuperviviente[cantTops] = rutHabitante;
                    cantTops++;
                }
                else if(tasaExito == maxTasaExito){
                    TopSuperviviente[cantTops] = rutHabitante;
                    cantTops++;
                }
            }
        }
        if(cantTops == 0){
            return "Ningún superviviente ha realizado misiones hasta el momento...";
        }
        if(cantTops == 1){
            Habitante sup = listaHabitantes.buscarHabitantePorRut(TopSuperviviente[0]);
            return "El superviviente con mayor tasa de exitos es:" +
                    "Nombre: " + sup.getNombreCompleto() +
                    "Rol: " + sup.getRol() +
                    "Rengo: " + sup.getRango() +
                    "Con una tasa de exito de: " + (maxTasaExito * 100) + "%";

        }
        String empate = "Hubo un empate entre los supervivientes con: " + (maxTasaExito * 100) + "% de exito"+ "\n" + "Los supervivientes con mayor tasa de exitos son:" + "\n";
        for(int i=0;i<cantTops;i++){
            empate += "Nombre: "+ " " + listaHabitantes.buscarHabitantePorRut(TopSuperviviente[i]).getNombreCompleto() + ", " +
                        "Rol: "+ " " + listaHabitantes.buscarHabitantePorRut(TopSuperviviente[i]).getRol() + ", "  +
                        "Rango: "+ " " + listaHabitantes.buscarHabitantePorRut(TopSuperviviente[i]).getRango() + "\n";
        }
        return empate;
    }

    /**
     * Función que identifica el suministro más buscado del sistema
     * @return un String con el suministro más buscado del sistema.
     */
    @Override
    public String suministroMasBuscado(){
        int[] suministros = new int[listaSuministros.getCantActualSum()];
        int cantSumMasBuscados = 0;
        int posSumMasBuscado = 0;
        for(int i=0;i<listaSuministros.getCantActualSum();i++) {
            int idSum = listaSuministros.buscarSuministro(i).getId();
            int vecesBuscado = 0;
            for (int j = 0; j < listaNexoMisiones.getCantDatos(); j++) {
                Mision m = listaNexoMisiones.obtenerPorPosicion(j);
                if (m.getSuministro().getId() == idSum) {
                    vecesBuscado++;
                }
            }

            if(listaSuministros.getCantActualSum()>0){
                if (vecesBuscado > cantSumMasBuscados) {
                    cantSumMasBuscados = vecesBuscado;
                    posSumMasBuscado = 0;
                    suministros[posSumMasBuscado] = idSum;
                    posSumMasBuscado++;
                }
                else if (vecesBuscado == cantSumMasBuscados) {
                    suministros[posSumMasBuscado] = idSum;
                    posSumMasBuscado++;
                }
            }
        }
            if(cantSumMasBuscados == 0){
                return "No se ha buscado ningún suministro hasta el momento";
            }
            if(posSumMasBuscado == 1){
                return "El suministro más buscado es: \n" +
                        "Id: " + suministros[0] + ", " +
                        "Tipo: " + listaSuministros.obtenerSumPorId(suministros[0]).getTipo() + ", " +
                        "Descripción: " + listaSuministros.obtenerSumPorId(suministros[0]).getDescripcion() + "\n";
            }
            String empate = "Hubo un empate entre los suministros más buscados" + "\n" + "Los suministros más buscados son:" + "\n";
            for(int i = 0; i < posSumMasBuscado ; i++){
                empate += "Id: " + listaSuministros.obtenerSumPorId(suministros[i]).getId() + ", " +
                        "Tipo: " + listaSuministros.obtenerSumPorId(suministros[i]).getTipo() + ", "  +
                        "Descripción: " + listaSuministros.obtenerSumPorId(suministros[i]).getDescripcion() + "\n";
            }
            return empate;
        }

    /**
     * Función que carga la información del sistema dentro de los archivos txt
     */
    @Override
    public void subirDatos() throws IOException {
        ArchivoSalida archivoSalidaHabitante = new ArchivoSalida("habitantes.txt");
        for(Habitante habitante : listaHabitantes.getListaHabitantes()){
            if (habitante != null){
                Registro regSal = new Registro(7);

                regSal.agregarCampo(habitante.getRut());
                regSal.agregarCampo(habitante.getNombre());
                regSal.agregarCampo(habitante.getApellido());
                regSal.agregarCampo(habitante.getNombreUsuario());
                regSal.agregarCampo(habitante.getContrasenia());
                regSal.agregarCampo(habitante.getRol());
                regSal.agregarCampo(habitante.getRango().toString());

                archivoSalidaHabitante.writeRegistro(regSal);
            }
        }

        ArchivoSalida archivoSalidaSum = new ArchivoSalida("suministros.txt");
        for(Suministro sum : listaSuministros.getListaSuministros()){
            if (sum != null){
                Registro regSal = new Registro(6);

                regSal.agregarCampo(sum.getId());
                regSal.agregarCampo(sum.getTipo());
                regSal.agregarCampo(sum.getDescripcion());
                regSal.agregarCampo(sum.getPeso());
                regSal.agregarCampo(sum.getCantidad());
                regSal.agregarCampo(sum.getEstado());

                archivoSalidaSum.writeRegistro(regSal);
            }
        }

        ArchivoSalida archivoSalidaMisiones = new ArchivoSalida("misiones.txt");
        for(Mision mision : listaNexoMisiones.getListaMisiones()) {
            if (mision != null) {
                Registro regSal = new Registro(5);

                regSal.agregarCampo(mision.getHabitante().getRut());
                regSal.agregarCampo(mision.getSuministro().getId());
                regSal.agregarCampo(mision.getFecha());
                regSal.agregarCampo(mision.getCantidadRecuperada());
                regSal.agregarCampo(mision.getResultado());

                archivoSalidaMisiones.writeRegistro(regSal);
            }
        }
        archivoSalidaHabitante.close();
        archivoSalidaSum.close();
        archivoSalidaMisiones.close();
    }
}