package sample;

import javafx.scene.chart.XYChart.Series;
import javafx.scene.chart.XYChart.Data;


public class Exact extends CalculationMethod {
    Exact(Variables vars) {
        super(vars);
    }

    @Override
    public Series getCalculation() {
        Series series = new Series();
        series.setName("Exact");
        double y = vars.getY0();
        double step = (vars.getX() - vars.getX0()) / vars.getN();
        double c = function.getCoefficient(vars.getX0(), vars.getY0());

        for(int i = 0; i <= vars.getN(); i++){
            double xi = vars.getX0() + i * step;
            series.getData().add(new Data<>(xi, y));
            y = function.getSolvedFunctionValue(c, xi + step, y);
        }

        return series;
    }
}
