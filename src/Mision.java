import java.time.LocalDate;

/**
 * Representa a la identidad Mision
 */
public class Mision {
    /**
     * Habitante asociado a la Mision
     */
    private Habitante habitante;
    /**
     * Suministro asociado a la Mision
     */
    private Suministro suministro;
    /**
     * Fecha de la Mision
     */
    private String fecha;
    /**
     * Cantidad de Suministro Recuperado de la Mision
     */
    private int cantidadRecuperada;
    /**
     * Resultado de la Mision
     */
    private String resultado;
    /**
     * Fecha actual de la Mision
     */
    private LocalDate fechaHoy;
    /**
     * Cantidad perdida de Suministro de la Mision
     */
    private int cantidadPerdida;

    /**
     * Contructor de la clase Mision
     * @param habitante             Habitante que participa en la Mision
     * @param suministro            Suministro Recuperado de la Mision
     * @param fecha                 Fecha en la que se realizo la Mision
     * @param cantidadRecuperada    Cantidad de Suministro recuperado de la Mision
     * @param resultado             Resultado de la Mision
     */
    public Mision(Habitante habitante, Suministro suministro, String fecha, int cantidadRecuperada, String resultado) {
        this.habitante = habitante;
        this.suministro = suministro;
        this.fecha = fecha;
        this.cantidadRecuperada = cantidadRecuperada;
        this.resultado = resultado;
        this.fechaHoy = LocalDate.now();
        this.cantidadPerdida = 0;
    }

    /**
     * Obtiene al Habitante asociado a la Mision
     * @return un objeto de tipo Habitante
     */
    public Habitante getHabitante() {
        return habitante;
    }

    /**
     * Obtiene el Suministro asociado a la Mision
     * @return un objeto de tipo Suministro
     */
    public Suministro getSuministro() {
        return suministro;
    }

    /**
     * Obtiene la fecha en la que se realizo la Mision
     * @return un string con la fecha en la que se realizo la Mision
     */
    public String getFecha() {
        return fecha;
    }


    /**
     * Obtiene la cantidad de Suministro recuperados de la Mision
     * @return un entero con la cantidad de Suministro recuperado
     */
    public int getCantidadRecuperada() {
        return cantidadRecuperada;
    }

    /**
     * Obtiene el resultado de la Mision
     * @return un string con el resultado de la Mision
     */
    public String getResultado() {
        return resultado;
    }



    /**
     * Obtiene la fecha actual del dispositivo de la Mision
     * @return un objeto de tipo LocalDate
     */
    public LocalDate getFechaHoy() {
        return fechaHoy;
    }


    /**
     * Obtiene la cantidad perdida de Suministro de la Mision
     * @return un entero con la cantidad perdida se Suministro de la Mision
     */
    public int getCantidadPerdida() {
        return cantidadPerdida;
    }
}
