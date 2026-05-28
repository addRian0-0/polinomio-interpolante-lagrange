import Data.Individuo;

import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;
import Data.Individuo;

public class Main {
    public static void main(String[] args) {

        /*
        [0] = bebe
        [1] = infante
        [2] = adolscente
        [3] = joven adulto
        [4] = adulto mayor
         */

        ArrayList<Individuo> muestra = new ArrayList<>();
        System.out.println("Los datos de la muestra son:");

        Individuo bebe = new Individuo(58.9, Individuo.Genero.HOMBRE, 6.7, 0.25);
        Individuo infante = new Individuo(146, Individuo.Genero.MUJER, 26, 10);
        Individuo adolescente = new Individuo(163, Individuo.Genero.HOMBRE, 24.4, 15);
        Individuo jovenAdulto = new Individuo(166, Individuo.Genero.HOMBRE, 28, 21);
        Individuo adultoMayor = new Individuo(161, Individuo.Genero.HOMBRE, 28.7, 44);

        muestra.add(bebe);
        muestra.add(infante);
        muestra.add(adolescente);
        muestra.add(jovenAdulto);
        muestra.add(adultoMayor);

        System.out.printf("%-10s %-10s %-15s %-10s%n",
                "Genero", "Altura", "Medida Cubit", "Edad");

        System.out.println("------------------------------------------------");

        for (Individuo individuo : muestra) {

            System.out.printf("%-10s %-10.2f %-15.2f %-10.0f%n",
                    individuo.getGenero(),
                    individuo.getAltura(),
                    individuo.getMedidacubit(),
                    individuo.getEdad());

        }
}
}