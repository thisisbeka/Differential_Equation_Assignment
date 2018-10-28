package sample;

import javafx.scene.chart.XYChart.Series;
import javafx.scene.chart.XYChart.Data;

public class ImprovedEuler extends CalculationMethod {
    ImprovedEuler(Variables variables) {
        super(variables);
    }

    @Override
    public Series getCalculation() {
        Series series = new Series();
        series.setName("Improved Euler");

        double y = vars.getY0();
        double lastValue = function.getFunctionValue(vars.getX0(), vars.getY0());
        double xInCalc, yInCalc;
        double step = (vars.getX() - vars.getX0()) / vars.getN();

        for(int i = 0; i <= vars.getN(); i++){
            double xi = vars.getX0() + i * step;
            series.getData().add(new Data<>(xi, y));
            xInCalc = xi + step / 2.0;
            yInCalc = y + (step / 2.0) * lastValue;
            y = y + step * function.getFunctionValue(xInCalc, yInCalc);
            lastValue = function.getFunctionValue(xi + step, y);
        }

        return series;
    }
}
