package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    LineChart<Number, Number> solution_chart;
    @FXML
    LineChart<Number, Number> error_chart;
    @FXML
    LineChart<Number, Number> max_error_chart;
    @FXML
    TextField x0;
    @FXML
    TextField y0;
    @FXML
    TextField x;
    @FXML
    TextField n;
    @FXML
    TextField n0;
    @FXML
    TextField nN;
    @FXML
    CheckBox exactCB;
    @FXML
    CheckBox eulerCB;
    @FXML
    CheckBox improvedEulerCB;
    @FXML
    CheckBox rangeKuttaCB;
    @FXML
    Button calculate;

    private CalculationMethod exact;
    private CalculationMethod euler;
    private CalculationMethod improvedEuler;
    private CalculationMethod rungeKutta;
    private Variables vars;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vars = new Variables();
        exact = new Exact(vars);
        euler = new Euler(vars);
        improvedEuler = new ImprovedEuler(vars);
        rungeKutta = new RungeKutta(vars);

        setTextFields();
        setCheckBoxes();
        setChartNames();
        showData();
        setCalculateListener();
    }

    private void setChartNames() {
        solution_chart.setTitle("Solution chart");
        error_chart.setTitle("Error chart");
        max_error_chart.setTitle("Maximum Error chart");
    }

    private void setCalculateListener() {
        calculate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                update();
            }
        });
    }

    private void setTextFields() {
        x0.setText(String.valueOf(vars.getX0()));
        y0.setText(String.valueOf(vars.getY0()));
        x.setText(String.valueOf(vars.getX()));
        n.setText(String.valueOf(vars.getN()));
        n0.setText(String.valueOf(vars.getN0()));
        nN.setText(String.valueOf(vars.getnN()));
    }

    private void setCheckBoxes(){
        exactCB.setSelected(true);
        eulerCB.setSelected(true);
        improvedEulerCB.setSelected(true);
        rangeKuttaCB.setSelected(true);
    }

    private void showData() {
        solution_chart.getData().clear();
        error_chart.getData().clear();
        max_error_chart.getData().clear();

        if(exactCB.isSelected())
            solution_chart.getData().add(exact.getCalculation());
        if(eulerCB.isSelected()) {
            solution_chart.getData().add(euler.getCalculation());
            error_chart.getData().add(euler.getError(exact));  // get Error
            max_error_chart.getData().add(euler.getMaxError(exact)); // get Max Error
        }
        if(improvedEulerCB.isSelected()) {
            solution_chart.getData().add(improvedEuler.getCalculation());
            error_chart.getData().add(improvedEuler.getError(exact));
            max_error_chart.getData().add(improvedEuler.getMaxError(exact));
        }
        if(rangeKuttaCB.isSelected()) {
            solution_chart.getData().add(rungeKutta.getCalculation());
            error_chart.getData().add(rungeKutta.getError(exact));
            max_error_chart.getData().add(rungeKutta.getMaxError(exact));
        }
    }

    private void update(){
        if(!handleErrors()){
            vars.setX0(Double.valueOf(x0.getText()));
            vars.setY0(Double.valueOf(y0.getText()));
            vars.setX(Double.valueOf(x.getText()));
            vars.setN(Double.valueOf(n.getText()));
            vars.setN0(Double.valueOf(n0.getText()));
            vars.setnN(Double.valueOf(nN.getText()));
            showData();
        } else{
            setTextFields();
        }
    }

    private boolean handleErrors() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        String s = "";
        String textFields[] = new String[] {x0.getText(), y0.getText(), x.getText(), n.getText(), n0.getText(), nN.getText()};

        if(isEmpty(textFields)){
            s = "Fields should not be empty";
        } else if(!isNumbers(textFields)){
            s = "Fields should doubles";
        } else if(Double.valueOf(x0.getText()) > Double.valueOf(x.getText())){
            s = "x0 can not be bigger than X";
        } else if(Double.valueOf(x.getText()) > 10){
            s = "X can not be bigger than 15";
        } else if(Double.valueOf(y0.getText()) < 0){
            s = "y0 can not be lower than 0";
        } else if(Double.valueOf(y0.getText()) > 100000){
            s = "y0 can not be bigger than 100000";
        } else if(Double.valueOf(x0.getText()) > 10){
            s = "x0 can not be bigger than 8";
        } else if(Double.valueOf(x0.getText()) < 0){
            s = "x0 can not be lower than -12";
        } else if(Double.valueOf(n.getText()) < 0){
            s = "N (number of steps) can not be lower than 0";
        } else if(Double.valueOf(n0.getText()) > Double.valueOf(nN.getText())){
            s = "n0 can not be more than nN";
        } else if(Double.valueOf(nN.getText()) - Double.valueOf(n0.getText()) > 100){
            s = "Difference between n0 and nN can not be more than 100";
        } else if(Double.valueOf(n.getText()) > 1000){
            s = "N can not be more than 1000";
        }

        if(s.length() > 0){
            alert.setContentText(s);
            alert.showAndWait();
            return true;
        } else
            return false;
    }

    private boolean isNumbers(String[] strings){
        for(String s: strings){
            try{
                double d = Double.valueOf(s);
            } catch (NumberFormatException e){
                return false;
            }
        }
        return true;
    }

    private boolean isEmpty(String[] strings){
        for(String s: strings){
            if(s.isEmpty())
                return true;
        }
        return false;
    }

}
