package sample;

public class MyFunction implements Function{
    @Override
    public double getFunctionValue(double x, double y) {
        return Math.cos(x) - y;
    }

    @Override
    public double getSolvedFunctionValue(double c, double x, double y) {
       double expression = c * Math.pow(Math.E, -x) + (Math.sin(x)+Math.cos(x))/2;

       return expression;
    }

    @Override
    public double getCoefficient(double x0, double y0){
        return (y0 - (Math.sin(x0)+Math.cos(x0))/2)*Math.pow(Math.E, x0) ;
    }
}
