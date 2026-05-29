import java.util.ArrayList;

import Data.Individuo;
import Data.MuestraDatos;
import Interpolacion.Lagrange;
import Interpolacion.Newton;
import Interpolacion.UtilidadesInterpolacion;
import Vista.Consola;
import Vista.FormatoConsola;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Consola consola = new Consola(scanner);

        FormatoConsola.titulo("CRECIMIENTO DEL CUBITO POR INTERPOLACION");
        System.out.println("El programa analiza la relacion:");
        System.out.println("x = edad del sujeto");
        System.out.println("y = medida del cubito");

        ArrayList<Individuo> muestra = seleccionarMuestra(consola);

        consola.imprimirMuestra(muestra);
        consola.imprimirPuntos(muestra);

        double[] edades = UtilidadesInterpolacion.obtenerEdades(muestra);
        double[] cubitos = UtilidadesInterpolacion.obtenerMedidasCubito(muestra);

        if (UtilidadesInterpolacion.hayEdadesRepetidas(edades)) {
            System.out.println("\nNo se puede interpolar: hay edades repetidas.");
            System.out.println("Para estos metodos, cada valor x debe ser unico.");
            return;
        }

        double edadInterpolar = consola.leerDouble("\nIngrese la edad del nuevo sujeto: ");

        FormatoConsola.seccion("SELECCION DEL METODO");
        System.out.println("Seleccione el metodo:");
        System.out.println("1. Interpolacion de Lagrange");
        System.out.println("2. Interpolacion de Newton");
        System.out.println("3. Ambos metodos");

        int opcion = consola.leerEntero("Opcion: ");

        switch (opcion) {
            case 1:
                mostrarResultado("Lagrange", Lagrange.interpolar(edades, cubitos, edadInterpolar));
                break;

            case 2:
                mostrarResultado("Newton", Newton.interpolar(edades, cubitos, edadInterpolar));
                break;

            case 3:
                double resultadoLagrange = Lagrange.interpolar(edades, cubitos, edadInterpolar);
                mostrarResultado("Lagrange", resultadoLagrange);

                double resultadoNewton = Newton.interpolar(edades, cubitos, edadInterpolar);
                mostrarResultado("Newton", resultadoNewton);
                break;

            default:
                System.out.println("Opcion invalida.");
        }
    }

    private static ArrayList<Individuo> seleccionarMuestra(Consola consola) {
        FormatoConsola.seccion("ORIGEN DE LOS DATOS");
        System.out.println("Seleccione el origen de los datos:");
        System.out.println("1. Usar datos predefinidos del programa");
        System.out.println("2. Introducir datos manualmente");

        int opcion = consola.leerEntero("Opcion: ");

        if (opcion == 2) {
            return consola.leerMuestraManual();
        }

        if (opcion != 1) {
            System.out.println("Opcion no reconocida. Se usaran los datos predefinidos.");
        }

        return MuestraDatos.crearMuestraPredefinida();
    }

    private static void mostrarResultado(String metodo, double resultado) {
        System.out.println();
        System.out.println("*************** RESULTADO ***************");
        System.out.println("Metodo usado: " + metodo);
        System.out.printf("Medida estimada del cubito = %.6f cm%n", resultado);
        System.out.println("*****************************************");
    }
}
