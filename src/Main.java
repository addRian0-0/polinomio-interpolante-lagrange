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

        FormatoConsola.titulo("VELOCIDAD DE CRECIMIENTO DEL CUBITO");
        System.out.println("El programa analiza la relacion:");
        System.out.println("x = edad del sujeto");
        System.out.println("y = medida del cubito");
        System.out.println("La velocidad de crecimiento se obtiene con la derivada P'(x).");

        ArrayList<Individuo> muestra = MuestraDatos.crearMuestraPredefinida();

        consola.imprimirMuestra(muestra);
        consola.imprimirPuntos(muestra);

        double[] edades = UtilidadesInterpolacion.obtenerEdades(muestra);
        double[] cubitos = UtilidadesInterpolacion.obtenerMedidasCubito(muestra);

        if (UtilidadesInterpolacion.hayEdadesRepetidas(edades)) {
            System.out.println("\nNo se puede interpolar: hay edades repetidas.");
            System.out.println("Para estos metodos, cada valor x debe ser unico.");
            return;
        }

        double edadEvaluar = consola.leerDouble("\nIngrese la edad donde desea hallar la velocidad de crecimiento: ");

        FormatoConsola.seccion("SELECCION DEL METODO");
        System.out.println("Seleccione el metodo:");
        System.out.println("1. Derivada del polinomio de Lagrange");
        System.out.println("2. Derivada del polinomio de Newton");
        System.out.println("3. Ambos metodos");

        int opcion = consola.leerEntero("Opcion: ");

        switch (opcion) {
            case 1:
                mostrarResultado("Lagrange", Lagrange.calcularVelocidadCrecimiento(edades, cubitos, edadEvaluar));
                break;

            case 2:
                mostrarResultado("Newton", Newton.calcularVelocidadCrecimiento(edades, cubitos, edadEvaluar));
                break;

            case 3:
                double resultadoLagrange = Lagrange.calcularVelocidadCrecimiento(edades, cubitos, edadEvaluar);
                mostrarResultado("Lagrange", resultadoLagrange);

                double resultadoNewton = Newton.calcularVelocidadCrecimiento(edades, cubitos, edadEvaluar);
                mostrarResultado("Newton", resultadoNewton);
                break;

            default:
                System.out.println("Opcion invalida.");
        }
    }

    private static void mostrarResultado(String metodo, double resultado) {
        System.out.println();
        System.out.println("*************** RESULTADO ***************");
        System.out.println("Metodo usado: " + metodo);
        System.out.printf("Velocidad de crecimiento del cubito = %.6f cm/anio%n", resultado);
        System.out.println("*****************************************");
    }
}
