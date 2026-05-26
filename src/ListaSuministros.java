public class ListaSuministros {
    private Suministro[] listaSuministros;
    private int cantActualSum;
    private int cantMaxSum;

    public ListaSuministros(int cantMaxSum) {
        this.cantMaxSum = cantMaxSum;
        this.listaSuministros = new Suministro[cantMaxSum];
        this.cantActualSum = 0;
    }

    public boolean agregarSuministro(Suministro s) {
        if(this.cantActualSum >= this.cantMaxSum) {
            System.out.println("No se puede agregar más suministros. Capacidad maxima alcanzada.");
            return false;
        }
        if(this.buscarSuministro(s.getId()) != null) {
            System.out.println("Ya existe un suministro con la id " + s.getId());
        }
        this.listaSuministros[this.cantActualSum++] = s;
        this.cantActualSum++;
        return true;
    }

    public Suministro buscarSuministro(int pos){
        if(pos < 0 || pos >= this.cantActualSum) {
            return null;
        }
        return this.listaSuministros[pos];
    }

    public Suministro obtenerSumPorId(int id){
        for(int i = 0; i < this.cantActualSum; i++){
            if (listaSuministros[i].getId() == id){
                return listaSuministros[i];
            }
        }
        return null;
    }

    public int getCantActualSum() {
        return cantActualSum;
    }
}
