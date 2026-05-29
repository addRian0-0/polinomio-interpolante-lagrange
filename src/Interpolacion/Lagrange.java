package Interpolacion;

import Vista.FormatoConsola;

public class Lagrange {

    private Lagrange() {
    }

    public static double interpolar(double[] x, double[] y, double valor) {
        int n = x.length;
        double resultado = 0.0;

        FormatoConsola.titulo("METODO DE LAGRANGE");
        System.out.println("Objetivo: estimar y cuando x = " + valor);
        System.out.println();
        System.out.println("Formula general:");
        System.out.println("P(x) = Sumatoria de y_i * L_i(x)");
        System.out.println("L_i(x) = Producto de ((x - x_j) / (x_i - x_j)), con i != j");

        FormatoConsola.seccion("DESARROLLO DE LOS POLINOMIOS L_i(x)");

        for (int i = 0; i < n; i++) {
            double termino = y[i];
            double li = 1.0;

            FormatoConsola.paso(i + 1, "Calcular L_" + i + "(x)");
            System.out.printf("Punto base: x_%d = %.6f, y_%d = %.6f%n", i, x[i], i, y[i]);
            System.out.println();
            System.out.println("Factores:");

            for (int j = 0; j < n; j++) {
                if (i != j) {
                    double numerador = valor - x[j];
                    double denominador = x[i] - x[j];
                    double fraccion = numerador / denominador;

                    System.out.printf("  contra x_%d: ((%.6f - %.6f) / (%.6f - %.6f)) = %.6f / %.6f = %.6f%n",
                            j,
                            valor, x[j], x[i], x[j], numerador, denominador, fraccion);

                    li *= fraccion;
                }
            }

            termino *= li;

            System.out.println();
            System.out.printf("Resultado de L_%d(%.6f) = %.6f%n", i, valor, li);
            System.out.printf("Aporte al polinomio = y_%d * L_%d(x) = %.6f * %.6f = %.6f%n",
                    i, i, y[i], li, termino);

            resultado += termino;
            System.out.printf("Suma acumulada de P(%.6f) = %.6f%n", valor, resultado);
        }

        FormatoConsola.seccion("RESUMEN LAGRANGE");
        System.out.printf("P(%.6f) = %.6f%n", valor, resultado);

        return resultado;
    }
}
