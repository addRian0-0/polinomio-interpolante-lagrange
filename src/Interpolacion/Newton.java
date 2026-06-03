package Interpolacion;

import Vista.FormatoConsola;

public class Newton {

    private Newton() {
    }

    public static ResultadoInterpolacion calcularVelocidadCrecimiento(double[] x, double[] y, double valor) {
        int n = x.length;
        double[][] tabla = new double[n][n];

        for (int i = 0; i < n; i++) {
            tabla[i][0] = y[i];
        }

        FormatoConsola.titulo("METODO DE NEWTON");
        System.out.println(FormatoConsola.negrita("Objetivo:") + " hallar la velocidad de crecimiento del cubito cuando x = " + valor);
        System.out.println();
        System.out.println(FormatoConsola.negrita("Fórmula del polinomio:"));
        System.out.println("P(x) = f[x₀] + f[x₀,x₁](x − x₀) + f[x₀,x₁,x₂](x − x₀)(x − x₁) + ⋯");
        System.out.println();
        System.out.println(FormatoConsola.negrita("Fórmula de la derivada:"));
        System.out.println("P′(x) = derivada de cada término del polinomio de Newton");
        System.out.println("Si Tᵢ(x) = Π (x − xₖ), con k desde 0 hasta i−1");
        System.out.println("Entonces Tᵢ′(x) = Σ de productos donde se omite un factor a la vez");

        FormatoConsola.seccion("CALCULO DE DIFERENCIAS DIVIDIDAS");

        for (int j = 1; j < n; j++) {
            System.out.println();
            System.out.println(FormatoConsola.negrita("Orden " + j + ":"));
            for (int i = 0; i < n - j; i++) {
                double numerador = tabla[i + 1][j - 1] - tabla[i][j - 1];
                double denominador = x[i + j] - x[i];
                tabla[i][j] = numerador / denominador;

                System.out.printf("  f[x%s..x%s] = (%.6f − %.6f) / (%.6f − %.6f) = %.6f%n",
                        FormatoConsola.subindice(i), FormatoConsola.subindice(i + j), tabla[i + 1][j - 1], tabla[i][j - 1],
                        x[i + j], x[i], tabla[i][j]);
            }
        }

        imprimirTabla(tabla, n);

        double medidaEstimada = tabla[0][0];
        double velocidad = 0.0;
        double termino = 1.0;

        FormatoConsola.seccion("DESARROLLO DEL POLINOMIO");
        System.out.printf("%s f[x₀] = %.6f%n", FormatoConsola.negrita("P(x) inicia con"), medidaEstimada);
        System.out.println(FormatoConsola.negrita("La derivada de una constante es 0, por eso P′(x) inicia en 0."));

        for (int i = 1; i < n; i++) {
            termino *= valor - x[i - 1];
            double parcial = tabla[0][i] * termino;
            double derivadaProducto = calcularDerivadaProducto(valor, x, i);
            double parcialDerivada = tabla[0][i] * derivadaProducto;

            FormatoConsola.paso(i, "Calcular termino " + i + " del polinomio");
            System.out.print(FormatoConsola.negrita("Producto algebraico:") + " ");
            imprimirProducto(valor, x, i);
            System.out.printf(" = %.6f%n", termino);

            System.out.printf("%s %.6f · %.6f = %.6f%n",
                    FormatoConsola.negrita("Aporte = coeficiente * producto ="), tabla[0][i], termino, parcial);

            medidaEstimada += parcial;
            System.out.printf("%s P(%.6f) = %.6f%n", FormatoConsola.negrita("Suma acumulada de"), valor, medidaEstimada);
            System.out.println();

            FormatoConsola.paso(i, "Derivar termino " + i + " del polinomio");
            String iSub = FormatoConsola.subindice(i);
            System.out.print(FormatoConsola.negrita("Producto original T" + iSub + "(x):") + " ");
            imprimirProducto(valor, x, i);
            System.out.println();
            System.out.println(FormatoConsola.negrita("Derivada T" + iSub + "′(x):") + " se omite un factor en cada subproducto.");
            imprimirDerivadaProducto(valor, x, i);
            System.out.printf("%s T%s′(%.6f) = %.6f%n", FormatoConsola.negrita("Resultado:"), iSub, valor, derivadaProducto);
            System.out.printf("%s coeficiente · T%s′(x) = %.6f · %.6f = %.6f%n",
                    FormatoConsola.negrita("Aporte a P′(x) ="), iSub, tabla[0][i], derivadaProducto, parcialDerivada);

            velocidad += parcialDerivada;
            System.out.printf("%s P′(%.6f) = %.6f%n", FormatoConsola.negrita("Suma acumulada de"), valor, velocidad);
            System.out.println();
        }

        FormatoConsola.seccion("RESUMEN NEWTON");
        System.out.printf("P(%.6f) = %.6f cm%n", valor, medidaEstimada);
        System.out.printf("P′(%.6f) = %.6f cm/año%n", valor, velocidad);

        return new ResultadoInterpolacion("Newton", valor, medidaEstimada, velocidad);
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
                System.out.print(" · ");
            }
            System.out.printf("(%.6f − %.6f)", valor, x[k]);
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

            System.out.print("  " + FormatoConsola.negrita("Subproducto omitiendo el factor contra x" + FormatoConsola.subindice(omitido) + ":") + " ");
            boolean imprimioFactor = false;

            for (int k = 0; k < cantidadFactores; k++) {
                if (k != omitido) {
                    if (imprimioFactor) {
                        System.out.print(" · ");
                    }
                    System.out.printf("(%.6f − %.6f)", valor, x[k]);
                    producto *= valor - x[k];
                    imprimioFactor = true;
                }
            }

            if (!imprimioFactor) {
                System.out.print("1");
            }

            suma += producto;
            System.out.printf(" = %.6f%n", producto);
            System.out.printf("  %s T%s′(%.6f) = %.6f%n",
                    FormatoConsola.negrita("suma acumulada de"), FormatoConsola.subindice(cantidadFactores), valor, suma);
            System.out.println();
        }
    }
}
