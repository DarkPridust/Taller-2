/**
 * Representa a la clase Médico extendida de la super clase habitante
 */
public class Medico extends Habitante{
    /**
     * Capacidad medica del Médico
     */
    private int capacidadMedica;

    /**
     * Contructor de la clase Médico
     * @param rut               Rut del Médico
     * @param nombre            Nombre del Médico
     * @param apellido          Apellido del Médico
     * @param nombreUsuario     Usuario del Médico
     * @param contrasenia       Contraseña del Médico
     * @param rol               Rol del Médico
     * @param rango             Rango del Médico
     * @param capacidadMedica   Capacidad medica del Médico
     */
    public Medico(String rut, String nombre, String apellido, String nombreUsuario, String contrasenia,
                  String rol, Rango rango, int capacidadMedica) {
        super(rut, nombre, apellido, nombreUsuario, contrasenia, rol, rango);
        this.capacidadMedica = capacidadMedica;
    }

    /**
     * Obtiene la Capacidad medica del Médico
     * @return un entero con la capacidad medica del Médico
     */
    public int getCapacidadMedica() {
        return capacidadMedica;
    }

    /**
     * Establece la capacidad medica del Médico
     * @param capacidadMedica un entero con la capacidad medica del Médico
     */
    public void setCapacidadMedica(int capacidadMedica) {
        this.capacidadMedica = capacidadMedica;
    }

    /**
     * Calcula el consumo de recursos del Medico
     * @return un entero con el consumo de recursos del Médico
     */
    @Override
    public double calcularConsumoRecursos() {
        return 0;
    }

    /**
     * Calcula la probabilidad de exito del Médico
     * @return un decimal con la probabilidad de exito
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
        return 0.5 + 0.05 + modRango;
    }

    /**
     * Obtiene la descripcion de la habilidad del Médico
     * @return un String con la descripción de la habilidad del Médico
     */
    @Override
    public String describirHabilidad() {
        return "Rol: " + getRol() + "   Rango: " + getRango().name().substring(0, 1).toUpperCase() + getRango().name().substring(1).toLowerCase();
    }
}
