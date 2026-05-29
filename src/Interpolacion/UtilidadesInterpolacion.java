package Interpolacion;

import Data.Individuo;

import java.util.ArrayList;

public class UtilidadesInterpolacion {

    private UtilidadesInterpolacion() {
    }

    public static double[] obtenerEdades(ArrayList<Individuo> muestra) {
        double[] edades = new double[muestra.size()];

        for (int i = 0; i < muestra.size(); i++) {
            edades[i] = muestra.get(i).getEdad();
        }

        return edades;
    }

    public static double[] obtenerMedidasCubito(ArrayList<Individuo> muestra) {
        double[] cubitos = new double[muestra.size()];

        for (int i = 0; i < muestra.size(); i++) {
            cubitos[i] = muestra.get(i).getMedidaCubito();
        }

        return cubitos;
    }

    public static boolean hayEdadesRepetidas(double[] edades) {
        for (int i = 0; i < edades.length; i++) {
            for (int j = i + 1; j < edades.length; j++) {
                if (Double.compare(edades[i], edades[j]) == 0) {
                    return true;
                }
            }
        }

        return false;
    }
}
