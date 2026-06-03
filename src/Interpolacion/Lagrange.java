package Interpolacion;

import Vista.FormatoConsola;

public class Lagrange {

    private Lagrange() {
    }

    public static ResultadoInterpolacion calcularVelocidadCrecimiento(double[] x, double[] y, double valor) {
        int n = x.length;
        double medidaEstimada = 0.0;
        double velocidad = 0.0;

        FormatoConsola.titulo("METODO DE LAGRANGE");
        System.out.println(FormatoConsola.negrita("Objetivo:") + " hallar la velocidad de crecimiento del cubito cuando x = " + valor);
        System.out.println();
        System.out.println(FormatoConsola.negrita("Fórmula del polinomio:"));
        System.out.println("P(x) = Σ yᵢ · Lᵢ(x)");
        System.out.println("Lᵢ(x) = Π [(x − xⱼ) / (xᵢ − xⱼ)], con i ≠ j");
        System.out.println();
        System.out.println(FormatoConsola.negrita("Fórmula de la derivada:"));
        System.out.println("P′(x) = Σ yᵢ · Lᵢ′(x)");
        System.out.println("Lᵢ′(x) = Σ, para m ≠ i, de:");
        System.out.println("          [1 / (xᵢ − xₘ)] · Π [(x − xⱼ) / (xᵢ − xⱼ)], con j ≠ i y j ≠ m");

        FormatoConsola.seccion("DESARROLLO DE Lᵢ(x) Y Lᵢ′(x)");

        for (int i = 0; i < n; i++) {
            double li = 1.0;
            double derivadaLi = 0.0;
            String iSub = FormatoConsola.subindice(i);

            FormatoConsola.paso(i + 1, "Calcular L" + iSub + "(x)");
            System.out.printf("%s x%s = %.6f, y%s = %.6f%n", FormatoConsola.negrita("Punto base:"), iSub, x[i], iSub, y[i]);
            System.out.println();
            System.out.println(FormatoConsola.negrita("Factores de L" + iSub + "(" + valor + "):"));

            for (int j = 0; j < n; j++) {
                if (i != j) {
                    String jSub = FormatoConsola.subindice(j);
                    double numerador = valor - x[j];
                    double denominador = x[i] - x[j];
                    double fraccion = numerador / denominador;

                    System.out.printf("  contra x%s: [(%.6f − %.6f) / (%.6f − %.6f)] = %.6f / %.6f = %.6f%n",
                            jSub,
                            valor, x[j], x[i], x[j], numerador, denominador, fraccion);

                    li *= fraccion;
                }
            }

            double aporteMedida = y[i] * li;

            System.out.println();
            System.out.printf("%s L%s(%.6f) = %.6f%n", FormatoConsola.negrita("Resultado de"), iSub, valor, li);
            System.out.printf("%s y%s · L%s(x) = %.6f · %.6f = %.6f%n",
                    FormatoConsola.negrita("Aporte al polinomio ="),
                    iSub, iSub, y[i], li, aporteMedida);

            FormatoConsola.paso(i + 1, "Calcular L" + iSub + "′(x)");
            for (int m = 0; m < n; m++) {
                if (m != i) {
                    String mSub = FormatoConsola.subindice(m);
                    double producto = 1.0;
                    double factorDerivado = 1.0 / (x[i] - x[m]);

                    System.out.printf("%s x%s:%n", FormatoConsola.negrita("Término derivado cuando se deriva el factor contra"), mSub);
                    System.out.printf("  1 / (x%s − x%s) = 1 / (%.6f − %.6f) = %.6f%n",
                            iSub, mSub, x[i], x[m], factorDerivado);

                    for (int j = 0; j < n; j++) {
                        if (j != i && j != m) {
                            String jSub = FormatoConsola.subindice(j);
                            double fraccion = (valor - x[j]) / (x[i] - x[j]);
                            System.out.printf("  factor restante contra x%s: [(%.6f − %.6f) / (%.6f − %.6f)] = %.6f%n",
                                    jSub, valor, x[j], x[i], x[j], fraccion);
                            producto *= fraccion;
                        }
                    }

                    double aporteDerivadaLi = factorDerivado * producto;
                    derivadaLi += aporteDerivadaLi;
                    System.out.printf("  %s L%s′(%.6f) = %.6f · %.6f = %.6f%n",
                            FormatoConsola.negrita("aporte a"), iSub, valor, factorDerivado, producto, aporteDerivadaLi);
                    System.out.printf("  %s L%s′(%.6f) = %.6f%n", FormatoConsola.negrita("suma acumulada de"), iSub, valor, derivadaLi);
                    System.out.println();
                }
            }

            double aporteVelocidad = y[i] * derivadaLi;
            System.out.println();
            System.out.printf("%s L%s′(%.6f) = %.6f%n", FormatoConsola.negrita("Resultado de"), iSub, valor, derivadaLi);
            System.out.printf("%s y%s · L%s′(x) = %.6f · %.6f = %.6f%n",
                    FormatoConsola.negrita("Aporte a P′(x) ="),
                    iSub, iSub, y[i], derivadaLi, aporteVelocidad);

            medidaEstimada += aporteMedida;
            velocidad += aporteVelocidad;
            System.out.printf("%s P(%.6f) = %.6f%n", FormatoConsola.negrita("Suma acumulada de"), valor, medidaEstimada);
            System.out.printf("%s P′(%.6f) = %.6f%n", FormatoConsola.negrita("Suma acumulada de"), valor, velocidad);
            System.out.println();
        }

        FormatoConsola.seccion("RESUMEN LAGRANGE");
        System.out.printf("P(%.6f) = %.6f cm%n", valor, medidaEstimada);
        System.out.printf("P′(%.6f) = %.6f cm/año%n", valor, velocidad);

        return new ResultadoInterpolacion("Lagrange", valor, medidaEstimada, velocidad);
    }
}
