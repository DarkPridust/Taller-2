import ucn.StdOut;

/**
 * Representa a la clase Explorador extendida de la super clase Habitante
 */
public class Explorador extends Habitante implements IAccionSupervivencia {
    /**
     * Sigilo del Explorador
     */
    private int nivelSigilo;

    /**
     * Constructor de la clase Explorador
     * @param rut           Rut del Explorador
     * @param nombre        Nombre del Explorador
     * @param apellido      Apellido del Explorador
     * @param nombreUsuario Usuario del Explorador
     * @param contrasenia   Contraseña del Explorador
     * @param rol           Rol del Explorador
     * @param rango         Rango del Explorador
     * @param nivelSigilo   Sigilo del Explorador
     */
    Explorador(String rut, String nombre, String apellido, String nombreUsuario, String contrasenia,
               String rol, Rango rango,  int nivelSigilo) {
        super(rut, nombre, apellido, nombreUsuario, contrasenia, rol, rango);
        this.nivelSigilo = nivelSigilo;
    }

    /**
     *          Obtiene el nivel de sigilo del Explorador
     * @return  un entero con el nivel de sigilo del Explorador
     */
    public int getNivelSigilo() {
        return nivelSigilo;
    }

    /**
     *                      Estable el nivel de sigilo del Explorador
     * @param nivelSigilo   un entero con el nivel de sigilo del Explorador
     */
    public void setNivelSigilo(int nivelSigilo) {
        this.nivelSigilo = nivelSigilo;
    }

    /**
     *          Obtiene el consumo de recursos del Explorador
     * @return  un entero con el consumo de recursos del Explorador
     */
    @Override
    public double calcularConsumoRecursos() {
        return 0;
    }

    /**
     *          Obtiene la probabilidad de exito del Explorador
     * @return  un decimal con la probabilidad de exito del Explorador
     */
    @Override
    public double calcularProbabilidadExito() {
        double modRango = 0;
        if(getRango().name().equals("NOVATO")){
            modRango = 0;
        }else if(getRango().name().equals("VETERANO")){
            modRango = 0.1;
        }else if(getRango().name().equals("LEYENDA")){
            modRango = 0.2;
        }
        return 0.5 + 0.1 + modRango;
    }

    /**
     *          Obtiene la descripción de la habilidad del Explorador
     * @return  un string con la descripcion de la habilidad del Explorador
     */
    @Override
    public String describirHabilidad() {
        return "Rol: " + getRol() + "   Rango: " + getRango().name().substring(0, 1).toUpperCase() + getRango().name().substring(1).toLowerCase();
    }
}
