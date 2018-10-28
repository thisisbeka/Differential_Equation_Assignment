package sample;

import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;


public abstract class CalculationMethod {

    protected Function function;
    protected Variables vars;

    CalculationMethod(Variables variables){
        this.vars = variables;
        this.function = new MyFunction();
    }

    public abstract Series getCalculation();

    public Series getError(CalculationMethod otherMethod){
        Series series = new Series();
        Series currentMethodSeries = this.getCalculation();
        Series otherMethodSeries = otherMethod.getCalculation();
        series.setName(currentMethodSeries.getName());

        for(int i = 0; i < currentMethodSeries.getData().size(); i++){
            Data<Double, Double> currentData = (Data<Double, Double>) currentMethodSeries.getData().get(i);
            Data<Double, Double> otherData = (Data<Double, Double>) otherMethodSeries.getData().get(i);
            double difference = Math.abs(currentData.getYValue() - otherData.getYValue());
            series.getData().add(new Data<>(currentData.getXValue(), difference));
        }

        return series;
    }

    public Series getMaxError(CalculationMethod otherMethod){
        Series series = new Series();
        series.setName(this.getCalculation().getName());

        double oldValueOfN = vars.getN();
        for(double i = vars.getN0(); i <= vars.getnN(); i++){
            vars.setN(i);
            Series error = getError(otherMethod);
            double max_error = Double.MIN_VALUE;
            for(int j = 0; j < error.getData().size(); j++){
                Data<Double, Double> currentData = (Data<Double, Double>) error.getData().get(j);
                max_error = Math.max(max_error, currentData.getYValue());
            }
            series.getData().add(new Data<>(i, max_error));
        }

        vars.setN(oldValueOfN);
        return series;
    }
}
