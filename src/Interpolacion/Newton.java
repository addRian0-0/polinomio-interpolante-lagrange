package Interpolacion;

import Vista.FormatoConsola;

public class Newton {

    private Newton() {
    }

    public static double interpolar(double[] x, double[] y, double valor) {
        int n = x.length;
        double[][] tabla = new double[n][n];

        for (int i = 0; i < n; i++) {
            tabla[i][0] = y[i];
        }

        FormatoConsola.titulo("METODO DE NEWTON");
        System.out.println("Objetivo: estimar y cuando x = " + valor);
        System.out.println();
        System.out.println("Formula general:");
        System.out.println("P(x) = f[x0] + f[x0,x1](x-x0) + f[x0,x1,x2](x-x0)(x-x1) + ...");

        FormatoConsola.seccion("CALCULO DE DIFERENCIAS DIVIDIDAS");

        for (int j = 1; j < n; j++) {
            System.out.println();
            System.out.println("Orden " + j + ":");
            for (int i = 0; i < n - j; i++) {
                double numerador = tabla[i + 1][j - 1] - tabla[i][j - 1];
                double denominador = x[i + j] - x[i];
                tabla[i][j] = numerador / denominador;

                System.out.printf("  f[x%d..x%d] = (%.6f - %.6f) / (%.6f - %.6f) = %.6f%n",
                        i, i + j, tabla[i + 1][j - 1], tabla[i][j - 1],
                        x[i + j], x[i], tabla[i][j]);
            }
        }

        imprimirTabla(tabla, n);

        double resultado = tabla[0][0];
        double termino = 1.0;

        FormatoConsola.seccion("DESARROLLO DEL POLINOMIO");
        System.out.printf("P(x) inicia con f[x0] = %.6f%n", resultado);

        for (int i = 1; i < n; i++) {
            termino *= valor - x[i - 1];
            double parcial = tabla[0][i] * termino;

            FormatoConsola.paso(i, "Calcular termino " + i + " del polinomio");
            System.out.print("Producto algebraico: ");
            imprimirProducto(valor, x, i);
            System.out.printf(" = %.6f%n", termino);

            System.out.printf("Aporte = coeficiente * producto = %.6f * %.6f = %.6f%n",
                    tabla[0][i], termino, parcial);

            resultado += parcial;
            System.out.printf("Suma acumulada de P(%.6f) = %.6f%n", valor, resultado);
        }

        FormatoConsola.seccion("RESUMEN NEWTON");
        System.out.printf("P(%.6f) = %.6f%n", valor, resultado);

        return resultado;
    }

    private static void imprimirTabla(double[][] tabla, int n) {
        FormatoConsola.seccion("TABLA DE DIFERENCIAS DIVIDIDAS");

        for (int columna = 0; columna < n; columna++) {
            System.out.printf("%12s ", "Orden " + columna);
        }
        System.out.println();
        FormatoConsola.linea();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i; j++) {
                System.out.printf("%12.6f ", tabla[i][j]);
            }
            System.out.println();
        }
    }

    private static void imprimirProducto(double valor, double[] x, int cantidadFactores) {
        for (int k = 0; k < cantidadFactores; k++) {
            if (k > 0) {
                System.out.print(" * ");
            }
            System.out.printf("(%.6f - %.6f)", valor, x[k]);
        }
    }
}
