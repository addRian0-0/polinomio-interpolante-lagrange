package Interpolacion;

import Vista.FormatoConsola;

public class Newton {

    private Newton() {
    }

    public static double calcularVelocidadCrecimiento(double[] x, double[] y, double valor) {
        int n = x.length;
        double[][] tabla = new double[n][n];

        for (int i = 0; i < n; i++) {
            tabla[i][0] = y[i];
        }

        FormatoConsola.titulo("METODO DE NEWTON");
        System.out.println("Objetivo: hallar la velocidad de crecimiento del cubito cuando x = " + valor);
        System.out.println();
        System.out.println("Formula del polinomio:");
        System.out.println("P(x) = f[x0] + f[x0,x1](x-x0) + f[x0,x1,x2](x-x0)(x-x1) + ...");
        System.out.println();
        System.out.println("Formula de la derivada:");
        System.out.println("P'(x) = derivada de cada termino del polinomio de Newton");
        System.out.println("Si T_i(x) = Producto de (x - x_k), con k desde 0 hasta i-1");
        System.out.println("Entonces T_i'(x) = Sumatoria de productos donde se omite un factor a la vez");

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

        double medidaEstimada = tabla[0][0];
        double velocidad = 0.0;
        double termino = 1.0;

        FormatoConsola.seccion("DESARROLLO DEL POLINOMIO");
        System.out.printf("P(x) inicia con f[x0] = %.6f%n", medidaEstimada);
        System.out.println("La derivada de una constante es 0, por eso P'(x) inicia en 0.");

        for (int i = 1; i < n; i++) {
            termino *= valor - x[i - 1];
            double parcial = tabla[0][i] * termino;
            double derivadaProducto = calcularDerivadaProducto(valor, x, i);
            double parcialDerivada = tabla[0][i] * derivadaProducto;

            FormatoConsola.paso(i, "Calcular termino " + i + " del polinomio");
            System.out.print("Producto algebraico: ");
            imprimirProducto(valor, x, i);
            System.out.printf(" = %.6f%n", termino);

            System.out.printf("Aporte = coeficiente * producto = %.6f * %.6f = %.6f%n",
                    tabla[0][i], termino, parcial);

            medidaEstimada += parcial;
            System.out.printf("Suma acumulada de P(%.6f) = %.6f%n", valor, medidaEstimada);

            FormatoConsola.paso(i, "Derivar termino " + i + " del polinomio");
            System.out.print("Producto original T_" + i + "(x): ");
            imprimirProducto(valor, x, i);
            System.out.println();
            System.out.println("Derivada T_" + i + "'(x): se omite un factor en cada subproducto.");
            imprimirDerivadaProducto(valor, x, i);
            System.out.printf("T_%d'(%.6f) = %.6f%n", i, valor, derivadaProducto);
            System.out.printf("Aporte a P'(x) = coeficiente * T_%d'(x) = %.6f * %.6f = %.6f%n",
                    i, tabla[0][i], derivadaProducto, parcialDerivada);

            velocidad += parcialDerivada;
            System.out.printf("Suma acumulada de P'(%.6f) = %.6f%n", valor, velocidad);
        }

        FormatoConsola.seccion("RESUMEN NEWTON");
        System.out.printf("P(%.6f) = %.6f cm%n", valor, medidaEstimada);
        System.out.printf("P'(%.6f) = %.6f cm/anio%n", valor, velocidad);

        return velocidad;
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

    private static double calcularDerivadaProducto(double valor, double[] x, int cantidadFactores) {
        double suma = 0.0;

        for (int omitido = 0; omitido < cantidadFactores; omitido++) {
            double producto = 1.0;

            for (int k = 0; k < cantidadFactores; k++) {
                if (k != omitido) {
                    producto *= valor - x[k];
                }
            }

            suma += producto;
        }

        return suma;
    }

    private static void imprimirDerivadaProducto(double valor, double[] x, int cantidadFactores) {
        double suma = 0.0;

        for (int omitido = 0; omitido < cantidadFactores; omitido++) {
            double producto = 1.0;

            System.out.print("  Subproducto omitiendo el factor contra x_" + omitido + ": ");
            boolean imprimioFactor = false;

            for (int k = 0; k < cantidadFactores; k++) {
                if (k != omitido) {
                    if (imprimioFactor) {
                        System.out.print(" * ");
                    }
                    System.out.printf("(%.6f - %.6f)", valor, x[k]);
                    producto *= valor - x[k];
                    imprimioFactor = true;
                }
            }

            if (!imprimioFactor) {
                System.out.print("1");
            }

            suma += producto;
            System.out.printf(" = %.6f%n", producto);
            System.out.printf("  suma acumulada de T_%d'(%.6f) = %.6f%n",
                    cantidadFactores, valor, suma);
        }
    }
}
