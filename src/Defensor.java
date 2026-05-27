public class Defensor extends Habitante{
    private int fuerza;
    public Defensor(String rut, String nombre, String apellido, String nombreUsuario, String contrasenia,
                    String rol, Rango rango, int fuerza) {
        super(rut, nombre, apellido, nombreUsuario, contrasenia, rol, rango);
        this.fuerza = fuerza;
    }
    public int getFuerza() {
        return fuerza;
    }
    public void setFuerza(int fuerza) {
        this.fuerza = fuerza;
    }

    @Override
    public double calcularConsumoRecursos() {
        return 0;
    }

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

    @Override
    public String describirHabilidad() {
        return "Rol: " + getRol() + "   Rango: " + getRango().name().substring(0, 1).toUpperCase() + getRango().name().substring(1).toLowerCase();
    }
}
