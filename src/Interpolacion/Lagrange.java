package Interpolacion;

import Vista.FormatoConsola;

public class Lagrange {

    private Lagrange() {
    }

    public static double calcularVelocidadCrecimiento(double[] x, double[] y, double valor) {
        int n = x.length;
        double medidaEstimada = 0.0;
        double velocidad = 0.0;

        FormatoConsola.titulo("METODO DE LAGRANGE");
        System.out.println("Objetivo: hallar la velocidad de crecimiento del cubito cuando x = " + valor);
        System.out.println();
        System.out.println("Formula del polinomio:");
        System.out.println("P(x) = Sumatoria de y_i * L_i(x)");
        System.out.println("L_i(x) = Producto de ((x - x_j) / (x_i - x_j)), con i != j");
        System.out.println();
        System.out.println("Formula de la derivada:");
        System.out.println("P'(x) = Sumatoria de y_i * L_i'(x)");
        System.out.println("L_i'(x) = Sumatoria, para m != i, de:");
        System.out.println("          (1 / (x_i - x_m)) * Producto de ((x - x_j) / (x_i - x_j)), con j != i y j != m");

        FormatoConsola.seccion("DESARROLLO DE L_i(x) Y L_i'(x)");

        for (int i = 0; i < n; i++) {
            double li = 1.0;
            double derivadaLi = 0.0;

            FormatoConsola.paso(i + 1, "Calcular L_" + i + "(x)");
            System.out.printf("Punto base: x_%d = %.6f, y_%d = %.6f%n", i, x[i], i, y[i]);
            System.out.println();
            System.out.println("Factores de L_" + i + "(" + valor + "):");

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

            double aporteMedida = y[i] * li;

            System.out.println();
            System.out.printf("Resultado de L_%d(%.6f) = %.6f%n", i, valor, li);
            System.out.printf("Aporte al polinomio = y_%d * L_%d(x) = %.6f * %.6f = %.6f%n",
                    i, i, y[i], li, aporteMedida);

            FormatoConsola.paso(i + 1, "Calcular L_" + i + "'(x)");
            for (int m = 0; m < n; m++) {
                if (m != i) {
                    double producto = 1.0;
                    double factorDerivado = 1.0 / (x[i] - x[m]);

                    System.out.printf("Termino derivado cuando se deriva el factor contra x_%d:%n", m);
                    System.out.printf("  1 / (x_%d - x_%d) = 1 / (%.6f - %.6f) = %.6f%n",
                            i, m, x[i], x[m], factorDerivado);

                    for (int j = 0; j < n; j++) {
                        if (j != i && j != m) {
                            double fraccion = (valor - x[j]) / (x[i] - x[j]);
                            System.out.printf("  factor restante contra x_%d: ((%.6f - %.6f) / (%.6f - %.6f)) = %.6f%n",
                                    j, valor, x[j], x[i], x[j], fraccion);
                            producto *= fraccion;
                        }
                    }

                    double aporteDerivadaLi = factorDerivado * producto;
                    derivadaLi += aporteDerivadaLi;
                    System.out.printf("  aporte a L_%d'(%.6f) = %.6f * %.6f = %.6f%n",
                            i, valor, factorDerivado, producto, aporteDerivadaLi);
                    System.out.printf("  suma acumulada de L_%d'(%.6f) = %.6f%n", i, valor, derivadaLi);
                }
            }

            double aporteVelocidad = y[i] * derivadaLi;
            System.out.println();
            System.out.printf("Resultado de L_%d'(%.6f) = %.6f%n", i, valor, derivadaLi);
            System.out.printf("Aporte a P'(x) = y_%d * L_%d'(x) = %.6f * %.6f = %.6f%n",
                    i, i, y[i], derivadaLi, aporteVelocidad);

            medidaEstimada += aporteMedida;
            velocidad += aporteVelocidad;
            System.out.printf("Suma acumulada de P(%.6f) = %.6f%n", valor, medidaEstimada);
            System.out.printf("Suma acumulada de P'(%.6f) = %.6f%n", valor, velocidad);
        }

        FormatoConsola.seccion("RESUMEN LAGRANGE");
        System.out.printf("P(%.6f) = %.6f cm%n", valor, medidaEstimada);
        System.out.printf("P'(%.6f) = %.6f cm/anio%n", valor, velocidad);

        return velocidad;
    }
}
