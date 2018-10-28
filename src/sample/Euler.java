package sample;

import javafx.scene.chart.XYChart.Series;
import javafx.scene.chart.XYChart.Data;

public class Euler extends CalculationMethod {
    Euler(Variables variables) {
        super(variables);
    }

    @Override
    public Series getCalculation() {
        Series series = new Series();
        series.setName("Euler");

        double y = vars.getY0();
        double lastValue;
        double step = (vars.getX() - vars.getX0()) / vars.getN();

        for(int i = 0; i <= vars.getN(); i++){
            double xi = vars.getX0() + i * step;
            series.getData().add(new Data<>(xi, y));
            lastValue = function.getFunctionValue(xi, y);
            y = y + step * lastValue;
        }

        return series;
    }
}
