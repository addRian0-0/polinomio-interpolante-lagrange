import Data.Individuo;

import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;
import Data.Individuo;

public class Main {
    public static void main(String[] args) {

        System.out.println("\nBienvenido a plinomio interpolante de Lagrange :D...\n");
        ArrayList<Individuo> muestra = new ArrayList<>();
        System.out.println("    Los datos de la muestra son:\n");

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

        System.out.printf("%-15s %-15s %-18s %-15s%n",
                "Genero", "Altura(gcm)", "Medida Cúbito(cm)", "Edad (Años)");

        System.out.println("-------------------------------------------------------------");

        for (Individuo individuo : muestra) {

            System.out.printf("%-15s %-15.2f %-15.2f %-15.2f%n",
                    individuo.getGenero(),
                    individuo.getAltura(),
                    individuo.getMedidacubit(),
                    individuo.getEdad());

        }

        System.out.println("\n    Los puntos para el polinomio de Lagrange son: \n");

        System.out.printf("%-10s %-10s%n",
                "Edad (x)", "Medida cúbito (y)");

        System.out.println("-------------------------------------------------------------");

        for(Individuo individuo: muestra){
            System.out.printf("%-10s %-10.2f%n",
                    individuo.getEdad(),
                    individuo.getMedidacubit()
            );
        }

    }
}