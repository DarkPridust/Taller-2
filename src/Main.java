import ucn.StdOut;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args){
        try {
            SistemaRefugioExodoImpl sistema = new SistemaRefugioExodoImpl();
            sistema.iniciarSistema();
        } catch (java.io.IOException e) {
            StdOut.println("ERROR FATAL: No se pudieron cargar los archivos de inicialización. El sistema no puede comenzar. Detalle: " + e.getMessage());
        } catch (Exception e) {
            StdOut.println("ERROR INESPERADO: " + e.getMessage());
        }
    }
}