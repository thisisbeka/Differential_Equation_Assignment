package sample;

import javafx.scene.chart.XYChart.Series;
import javafx.scene.chart.XYChart.Data;

public class RungeKutta extends CalculationMethod {
    RungeKutta(Variables variables) {
        super(variables);
    }

    @Override
    public Series getCalculation() {
        Series series = new Series();
        series.setName("Runge-Kutta");

        double y = vars.getY0();
        double lastValue = function.getFunctionValue(vars.getX0(), vars.getY0());
        double step = (vars.getX() - vars.getX0()) / vars.getN();

        for(int i = 0; i <= vars.getN(); i++){
            double xi = vars.getX0() + i * step;
            series.getData().add(new Data<>(xi, y));
            double k1 = lastValue;
            double k2 = function.getFunctionValue(xi + step / 2.0, y + (step * k1) / 2.0);
            double k3 = function.getFunctionValue(xi + step / 2.0, y + (step * k2) / 2.0);
            double k4 = function.getFunctionValue(xi + step, y + step * k3);
            y = y + step / 6.0 * (k1 + 2.0 * k2 + 2.0 * k3 + k4);
            lastValue = function.getFunctionValue(xi + step, y);
        }

        return series;
    }
}
