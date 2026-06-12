public enum Rango {
    NOVATO {
        public boolean puedeSubirRango(int misionesExitosas){
            return misionesExitosas >= 10;
        }
        public Rango rangoSiguiente(){
            return VETERANO;
        }
    },
    VETERANO{
        public boolean puedeSubirRango(int misionesExitosas){
            return misionesExitosas >= 20;
        }
        public Rango rangoSiguiente(){
            return LEYENDA;
        }
    },
    LEYENDA{
        public boolean puedeSubirRango(int misionesExitosas){
            return false;
        }
        public Rango rangoSiguiente(){
            return LEYENDA;
        }
    };

    public abstract boolean puedeSubirRango(int misionesExitosas);
    public abstract Rango rangoSiguiente();}
