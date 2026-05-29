package Data;

import java.util.ArrayList;

public class MuestraDatos {

    private MuestraDatos() {
    }

    public static ArrayList<Individuo> crearMuestraPredefinida() {
        ArrayList<Individuo> muestra = new ArrayList<>();

        muestra.add(new Individuo(58.9, Individuo.Genero.HOMBRE, 6.7, 0.25));
        muestra.add(new Individuo(146, Individuo.Genero.MUJER, 26, 10));
        muestra.add(new Individuo(163, Individuo.Genero.HOMBRE, 24.4, 15));
        muestra.add(new Individuo(166, Individuo.Genero.HOMBRE, 28, 21));
        muestra.add(new Individuo(161, Individuo.Genero.HOMBRE, 28.7, 44));

        return muestra;
    }
}
