/**
 * Representa la identidad Suministro.
 */
public class Suministro {
    /**
     * ID del suministro.
     */
    private int id;
    /**
     * Tipo de Suministro.
     */
    private String tipo;
    /**
     * Descripcion del Suministro.
     */
    private String descripcion;
    /**
     * Peso del Suministro.
     */
    private double peso;
    /**
     * Cantidad de Suministro.
     */
    private int cantidad;
    /**
     * Estado del Suministro.
     */
    private String estado;

    /**
     * Contructor de la clase Suministro.
     * @param id            Id del Suministro.
     * @param tipo          Tipo de Suministro.
     * @param descripcion   Descripcion del Suministro.
     * @param peso          Peso del Suministro.
     * @param cantidad      Cantidad de Suministro.
     * @param estado        Estado del Suministro.
     */
    public Suministro(int id, String tipo, String descripcion, double peso, int cantidad, String estado) {
        this.id = id;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.peso = peso;
        this.cantidad = cantidad;
        this.estado = estado;
    }

    /**
     * Obtiene el id del Suministro.
     * @return un entero con el id del Suministro.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el tipo de Sumnistro.
     * @return un string con el tipo de Sumnistro.
     */
    public String getTipo() {
        return tipo;
    }


    /**
     * Obtiene la descripción del Sumnistro.
     * @return un string con la descripción del suministro.
     */
    public String getDescripcion() {
        return descripcion;
    }


    /**
     * Obtiene el peso del Sumnistro.
     * @return un decimal con el peso del Suministro.
     */
    public double getPeso() {
        return peso;
    }


    /**
     * Obtiene la cantidad de Suministro.
     * @return un entero con la cantidad de Suministro.
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Estable la Cantidad de Suministro.
     * @param cantidad un entero con la Cantidad de Suministro.
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el estado del Suministro.
     * @return un string con el estado del Suministro.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Estable el estado del Suministro.
     * @param estado un string con el estado des Suministro.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Obtiene toda la información general del Suministro.
     * @return un string con la información general del Suministro.
     */
    @Override
    public String toString() {
        return "ID: " + getId() +
                "  | Tipo: " + getTipo() + "  | Descripción: " + getDescripcion() + "\n" +
                "       | Peso: " + getPeso() + " kg" +
                "  | Cantidad Disponible: " + getCantidad() +
                "  | Estado: " + getEstado() + "\n";
    }
}
