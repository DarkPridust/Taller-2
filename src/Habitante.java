/**
 *Representa a la super clase Habitante
 */
public abstract class Habitante implements IAccionSupervivencia{
    /**
     * Rut del Habitante
     */
    protected String rut;
    /**
     * Nombre del Habitante
     */
    protected String nombre;
    /**
     * Apellido del Habitante
     */
    protected String apellido;
    /**
     * Usuario del Habitante
     */
    protected String nombreUsuario;
    /**
     * contraseña del Habitante
     */
    protected String contrasenia;
    /**
     * Rol del Habitante
     */
    protected String rol;
    /**
     * Rango del Habitante
     */
    protected Rango rango;

    /**
     * Constructor de la super clase habitante
     * @param rut           Rut del Habitante
     * @param nombre        Nombre del Habitante
     * @param apellido      Apellido del Habitante
     * @param nombreUsuario Usuario del Habitante
     * @param contrasenia   Contraseña del Habitante
     * @param rol           Rol del Habitante
     * @param rango         Rango del Habitante
     */
    public Habitante(String rut, String nombre, String apellido, String nombreUsuario, String contrasenia, String rol, Rango rango) {
        this.rut = rut;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.rol = rol;
        this.rango = rango;
    }

    /**
     *          Obtiene el rut del Habitante
     * @return  un string con el rut del Habitante
     */
    public String getRut() {
        return rut;
    }

    /**
     *          Obtiene el nombre del Habitante
     * @return  un string con el nombre del Habitante
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *          Obtiene el Apellido del Habitante
     * @return  un string con el apellido del Habitante
     */
    public String getApellido() {
        return apellido;
    }

    /**
     *          Obtiene el usuario del Habitante
     * @return  un string con el usuario del Habitante
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     *          Obtiene la contraseña del Habitanter
     * @return  un string con la contraseña del Habitante
     */
    public String getContrasenia() {
        return contrasenia;
    }

    /**
     *          Obtiene el rol del Habitante
     * @return  un String con el rol del Habitante
     */
    public String getRol() {
        return rol;
    }

    /**
     *          Obtiene el rango del Habitante
     * @return  un String con el rango del Habitante
     */
    public Enum getRango() {
        return rango;
    }

    /**
     *              Establece el rango del habitante
     * @param rango un string con el rango del Habitante
     */
    public void setRango(Rango rango) {
        this.rango = rango;
    }

    /**
     * Obtiene el nombre completo del Habitante
     * @return un string con el nombre del Habitante
     */
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }
}
