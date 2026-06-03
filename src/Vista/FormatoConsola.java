package Vista;

public class FormatoConsola {

    private static final String NEGRITA = "\033[1m";
    private static final String REINICIAR = "\033[0m";

    private FormatoConsola() {
    }

    public static void titulo(String texto) {
        System.out.println();
        System.out.println("============================================================");
        System.out.println(" " + negrita(texto));
        System.out.println("============================================================");
    }

    public static void seccion(String texto) {
        System.out.println();
        System.out.println("------------------------------------------------------------");
        System.out.println(" " + negrita(texto));
        System.out.println("------------------------------------------------------------");
    }

    public static void paso(int numero, String texto) {
        System.out.println();
        System.out.println(negrita("Paso " + numero + ". " + texto));
    }

    public static void linea() {
        System.out.println("------------------------------------------------------------");
    }

    public static String negrita(String texto) {
        return NEGRITA + texto + REINICIAR;
    }

    public static String subindice(int numero) {
        String texto = Integer.toString(numero);
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < texto.length(); i++) {
            resultado.append(subindice(texto.charAt(i)));
        }

        return resultado.toString();
    }

    private static char subindice(char caracter) {
        switch (caracter) {
            case '0':
                return '₀';
            case '1':
                return '₁';
            case '2':
                return '₂';
            case '3':
                return '₃';
            case '4':
                return '₄';
            case '5':
                return '₅';
            case '6':
                return '₆';
            case '7':
                return '₇';
            case '8':
                return '₈';
            case '9':
                return '₉';
            default:
                return caracter;
        }
    }
}
