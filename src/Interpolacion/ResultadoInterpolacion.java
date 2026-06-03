package Interpolacion;

public class ResultadoInterpolacion {

    private final String metodo;
    private final double edadEvaluada;
    private final double medidaEstimada;
    private final double velocidadCrecimiento;

    public ResultadoInterpolacion(String metodo, double edadEvaluada, double medidaEstimada, double velocidadCrecimiento) {
        this.metodo = metodo;
        this.edadEvaluada = edadEvaluada;
        this.medidaEstimada = medidaEstimada;
        this.velocidadCrecimiento = velocidadCrecimiento;
    }

    public String getMetodo() {
        return metodo;
    }

    public double getEdadEvaluada() {
        return edadEvaluada;
    }

    public double getMedidaEstimada() {
        return medidaEstimada;
    }

    public double getVelocidadCrecimiento() {
        return velocidadCrecimiento;
    }
}
