package Vista;

public class FormatoConsola {

    private FormatoConsola() {
    }

    public static void titulo(String texto) {
        System.out.println();
        System.out.println("============================================================");
        System.out.println(" " + texto);
        System.out.println("============================================================");
    }

    public static void seccion(String texto) {
        System.out.println();
        System.out.println("------------------------------------------------------------");
        System.out.println(" " + texto);
        System.out.println("------------------------------------------------------------");
    }

    public static void paso(int numero, String texto) {
        System.out.println();
        System.out.println("Paso " + numero + ". " + texto);
    }

    public static void linea() {
        System.out.println("------------------------------------------------------------");
    }
}
