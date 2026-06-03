package Interpolacion;

public class CalculadoraVelocidad {

    private CalculadoraVelocidad() {
    }

    public static double velocidadLagrange(double[] x, double[] y, double valor) {
        double velocidad = 0.0;

        for (int i = 0; i < x.length; i++) {
            double derivadaLi = 0.0;

            for (int m = 0; m < x.length; m++) {
                if (m != i) {
                    double producto = 1.0;
                    double factorDerivado = 1.0 / (x[i] - x[m]);

                    for (int j = 0; j < x.length; j++) {
                        if (j != i && j != m) {
                            producto *= (valor - x[j]) / (x[i] - x[j]);
                        }
                    }

                    derivadaLi += factorDerivado * producto;
                }
            }

            velocidad += y[i] * derivadaLi;
        }

        return velocidad;
    }

    public static double velocidadNewton(double[] x, double[] y, double valor) {
        double[][] tabla = calcularTablaDiferencias(x, y);
        double velocidad = 0.0;

        for (int i = 1; i < x.length; i++) {
            velocidad += tabla[0][i] * derivadaProductoNewton(valor, x, i);
        }

        return velocidad;
    }

    private static double[][] calcularTablaDiferencias(double[] x, double[] y) {
        int n = x.length;
        double[][] tabla = new double[n][n];

        for (int i = 0; i < n; i++) {
            tabla[i][0] = y[i];
        }

        for (int j = 1; j < n; j++) {
            for (int i = 0; i < n - j; i++) {
                tabla[i][j] = (tabla[i + 1][j - 1] - tabla[i][j - 1]) / (x[i + j] - x[i]);
            }
        }

        return tabla;
    }

    private static double derivadaProductoNewton(double valor, double[] x, int cantidadFactores) {
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
}
