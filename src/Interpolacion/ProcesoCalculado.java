package Interpolacion;

import java.util.List;

public class ProcesoCalculado {

    private final ResultadoInterpolacion resultado;
    private final List<String> pasos;

    public ProcesoCalculado(ResultadoInterpolacion resultado, List<String> pasos) {
        this.resultado = resultado;
        this.pasos = pasos;
    }

    public ResultadoInterpolacion getResultado() {
        return resultado;
    }

    public List<String> getPasos() {
        return pasos;
    }
}
