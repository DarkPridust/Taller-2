public class Mision {
    private Habitante habitante;
    private Suministro suministro;
    private String fecha;
    private int cantidadRecuperada;
    private String resultado;

    public Mision(Habitante habitante, Suministro suministro, String fecha, int cantidadRecuperada, String resultado) {
        this.habitante = habitante;
        this.suministro = suministro;
        this.fecha = fecha;
        this.cantidadRecuperada = cantidadRecuperada;
        this.resultado = resultado;
    }

    public Habitante getHabitante() {
        return habitante;
    }

    public void setHabitante(Habitante habitante) {
        this.habitante = habitante;
    }

    public Suministro getSuministro() {
        return suministro;
    }

    public void setSuministro(Suministro suministro) {
        this.suministro = suministro;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getCantidadRecuperada() {
        return cantidadRecuperada;
    }

    public void setCantidadRecuperada(int cantidadRecuperada) {
        this.cantidadRecuperada = cantidadRecuperada;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
}
