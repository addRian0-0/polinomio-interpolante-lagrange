import java.util.ArrayList;

import Data.Individuo;
import Data.MuestraDatos;
import Interpolacion.Lagrange;
import Interpolacion.Newton;
import Interpolacion.ProcesoCalculado;
import Interpolacion.ResultadoInterpolacion;
import Interpolacion.UtilidadesInterpolacion;
import Vista.Consola;
import Vista.FormatoConsola;
import Vista.GraficaVelocidad;
import Vista.NavegadorPasos;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Consola consola = new Consola(scanner);

        FormatoConsola.titulo("VELOCIDAD DE CRECIMIENTO DEL CUBITO");
        System.out.println(FormatoConsola.negrita("El programa analiza la relacion:"));
        System.out.println("x = edad del sujeto");
        System.out.println("y = medida del cubito");
        System.out.println(FormatoConsola.negrita("La velocidad de crecimiento se obtiene con la derivada P′(x)."));

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
        System.out.println(FormatoConsola.negrita("Seleccione el metodo:"));
        System.out.println("1. Derivada del polinomio de Lagrange");
        System.out.println("2. Derivada del polinomio de Newton");
        System.out.println("3. Ambos metodos");

        int opcion = consola.leerEntero("Opcion: ");

        switch (opcion) {
            case 1:
                ProcesoCalculado procesoLagrange = calcularProceso("Lagrange",
                        () -> Lagrange.calcularVelocidadCrecimiento(edades, cubitos, edadEvaluar));
                NavegadorPasos.mostrar("PROCESO MATEMATICO - LAGRANGE", procesoLagrange.getPasos(), scanner);
                mostrarResultado(procesoLagrange.getResultado());
                mostrarResumenFinal(procesoLagrange.getResultado(), null);
                generarGrafica("Lagrange", edades, cubitos, edadEvaluar);
                break;

            case 2:
                ProcesoCalculado procesoNewton = calcularProceso("Newton",
                        () -> Newton.calcularVelocidadCrecimiento(edades, cubitos, edadEvaluar));
                NavegadorPasos.mostrar("PROCESO MATEMATICO - NEWTON", procesoNewton.getPasos(), scanner);
                mostrarResultado(procesoNewton.getResultado());
                mostrarResumenFinal(null, procesoNewton.getResultado());
                generarGrafica("Newton", edades, cubitos, edadEvaluar);
                break;

            case 3:
                ProcesoCalculado procesoLagrangeAmbos = calcularProceso("Lagrange",
                        () -> Lagrange.calcularVelocidadCrecimiento(edades, cubitos, edadEvaluar));
                NavegadorPasos.mostrar("PROCESO MATEMATICO - LAGRANGE", procesoLagrangeAmbos.getPasos(), scanner);
                mostrarResultado(procesoLagrangeAmbos.getResultado());

                ProcesoCalculado procesoNewtonAmbos = calcularProceso("Newton",
                        () -> Newton.calcularVelocidadCrecimiento(edades, cubitos, edadEvaluar));
                NavegadorPasos.mostrar("PROCESO MATEMATICO - NEWTON", procesoNewtonAmbos.getPasos(), scanner);
                mostrarResultado(procesoNewtonAmbos.getResultado());
                mostrarResumenFinal(procesoLagrangeAmbos.getResultado(), procesoNewtonAmbos.getResultado());
                generarGrafica("Lagrange", edades, cubitos, edadEvaluar);
                break;

            default:
                System.out.println("Opcion invalida.");
        }
    }

    private static void generarGrafica(String metodo, double[] edades, double[] cubitos, double edadEvaluar) {
        try {
            GraficaVelocidad.generar(metodo, edades, cubitos, edadEvaluar);
        } catch (IOException e) {
            System.out.println();
            System.out.println(FormatoConsola.negrita("No se pudo generar el archivo de la grafica."));
            System.out.println("Detalle: " + e.getMessage());
        }
    }

    private static ProcesoCalculado calcularProceso(String metodo, CalculoProceso calculo) {
        PrintStream salidaOriginal = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream salidaTemporal = new PrintStream(buffer, true, StandardCharsets.UTF_8);
        ResultadoInterpolacion resultado;

        try {
            System.setOut(salidaTemporal);
            resultado = calculo.ejecutar();
        } finally {
            System.setOut(salidaOriginal);
            salidaTemporal.close();
        }

        String salida = buffer.toString(StandardCharsets.UTF_8);
        return new ProcesoCalculado(resultado, dividirEnPasos(metodo, salida));
    }

    private static ArrayList<String> dividirEnPasos(String metodo, String salida) {
        ArrayList<String> pasos = new ArrayList<>();
        StringBuilder pasoActual = new StringBuilder();

        for (String linea : salida.split("\\R")) {
            if (iniciaNuevaParte(linea) && pasoActual.length() > 0) {
                pasos.add(pasoActual.toString());
                pasoActual = new StringBuilder();
            }

            pasoActual.append(linea).append(System.lineSeparator());
        }

        if (pasoActual.length() > 0) {
            pasos.add(pasoActual.toString());
        }

        if (pasos.isEmpty()) {
            pasos.add("No se genero salida para el metodo " + metodo + ".");
        }

        return pasos;
    }

    private static boolean iniciaNuevaParte(String linea) {
        return linea.contains("Paso ")
                || linea.contains("RESUMEN LAGRANGE")
                || linea.contains("RESUMEN NEWTON");
    }

    private static void mostrarResultado(ResultadoInterpolacion resultado) {
        System.out.println();
        System.out.println(FormatoConsola.negrita("*************** RESULTADO ***************"));
        System.out.println(FormatoConsola.negrita("Metodo usado: ") + resultado.getMetodo());
        System.out.printf("%s %.6f años%n", FormatoConsola.negrita("Edad evaluada ="), resultado.getEdadEvaluada());
        System.out.printf("%s %.6f cm%n", FormatoConsola.negrita("Medida estimada del cubito ="), resultado.getMedidaEstimada());
        System.out.printf("%s %.6f cm/año%n", FormatoConsola.negrita("Velocidad de crecimiento del cubito ="),
                resultado.getVelocidadCrecimiento());
        System.out.println(FormatoConsola.negrita("*****************************************"));
    }

    private static void mostrarResumenFinal(ResultadoInterpolacion resultadoLagrange, ResultadoInterpolacion resultadoNewton) {
        FormatoConsola.titulo("RESUMEN FINAL PARA LEER SIN PERDERSE");
        System.out.println(FormatoConsola.negrita("El número principal es P′(x), porque representa la velocidad de crecimiento."));
        System.out.println(FormatoConsola.negrita("P(x) solo se muestra como apoyo: es la medida estimada del cubito en esa edad."));
        System.out.println();

        System.out.printf("%-12s %-16s %-20s %-20s%n",
                "Método", "Edad", "P(x) cúbito", "P′(x) velocidad");
        FormatoConsola.linea();

        if (resultadoLagrange != null) {
            imprimirFilaResumen(resultadoLagrange);
        }

        if (resultadoNewton != null) {
            imprimirFilaResumen(resultadoNewton);
        }

        ResultadoInterpolacion referencia = resultadoLagrange != null ? resultadoLagrange : resultadoNewton;

        if (resultadoLagrange != null && resultadoNewton != null) {
            double diferencia = Math.abs(resultadoLagrange.getVelocidadCrecimiento()
                    - resultadoNewton.getVelocidadCrecimiento());
            System.out.println();
            System.out.printf("%s %.10f cm/año%n", FormatoConsola.negrita("Diferencia entre velocidades ="), diferencia);
            if (diferencia < 0.000001) {
                System.out.println(FormatoConsola.negrita("Conclusion: ambos metodos coinciden en el resultado."));
            } else {
                System.out.println(FormatoConsola.negrita("Conclusion: los metodos no coinciden exactamente; revise los datos o el redondeo."));
            }
        }

        System.out.println();
        explicarVelocidad(referencia.getVelocidadCrecimiento());
    }

    private static void imprimirFilaResumen(ResultadoInterpolacion resultado) {
        System.out.printf("%-12s %-16.6f %-20.6f %-20.6f%n",
                resultado.getMetodo(),
                resultado.getEdadEvaluada(),
                resultado.getMedidaEstimada(),
                resultado.getVelocidadCrecimiento());
    }

    private static void explicarVelocidad(double velocidad) {
        if (velocidad > 0) {
            System.out.printf("%s el cúbito está creciendo a %.6f cm/año en esa edad.%n",
                    FormatoConsola.negrita("Interpretacion:"), velocidad);
        } else if (velocidad < 0) {
            System.out.printf("%s la derivada dio %.6f cm/año; en el polinomio interpolante, la tendencia local baja en esa edad.%n",
                    FormatoConsola.negrita("Interpretacion:"), velocidad);
        } else {
            System.out.println(FormatoConsola.negrita("Interpretacion:") + " la velocidad es 0 cm/año; el polinomio queda plano en esa edad.");
        }
    }

    private interface CalculoProceso {
        ResultadoInterpolacion ejecutar();
    }
}
