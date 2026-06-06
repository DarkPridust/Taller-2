/**
 * Representa a la clase Defensor extendida de la super clase Habitante
 */
public class Defensor extends Habitante{
    /**
     * Fuerza del Defensor
     */
    private int fuerza;

    /**
     * Constructor de la clase Defensor
     * @param rut           Rut del Defensor
     * @param nombre        Nombre del Defensor
     * @param apellido      Apellido del Defensor
     * @param nombreUsuario Usuario del Defensor
     * @param contrasenia   Contraseña del Defensor
     * @param rol           Rol del Defensor
     * @param rango         Rango del Defensor
     * @param fuerza        Fuerza del Defensor
     */
    public Defensor(String rut, String nombre, String apellido, String nombreUsuario, String contrasenia,
                    String rol, Rango rango, int fuerza) {
        super(rut, nombre, apellido, nombreUsuario, contrasenia, rol, rango);
        this.fuerza = fuerza;
    }

    /**
     *         Obtiene la fuerza del Defensor
     * @return un entero con la fuerza del Defensor
     */
    public int getFuerza() {
        return fuerza;
    }

    /**
     *        Establece la fuerza del Defensor
     * @param fuerza un entero con la fuerza del cliente
     */
    public void setFuerza(int fuerza) {
        this.fuerza = fuerza;
    }

    /**
     *         Obtiene el consumo de recursos del Defensor
     * @return un entero con el consumo del Defensor
     */
    @Override
    public double calcularConsumoRecursos() {
        return 0;
    }

    /**
     *         Obtiene la probabilidad de exito del Defensor
     * @return un decimal con la probabilidad de exito del Defensor
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
        return 0.5 + 0 + modRango;
    }

    /**
     *         Obtiene la descriopción de la habilidad del Defensor
     * @return un string con la descripcion de la habilidad del Defensor
     */
    @Override
    public String describirHabilidad() {
        return "Rol: " + getRol() + "   Rango: " + getRango().name().substring(0, 1).toUpperCase() + getRango().name().substring(1).toLowerCase();
    }
}
