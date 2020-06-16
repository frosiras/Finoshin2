import java.util.ArrayList;
import java.util.Arrays;

public class DataContainer {
    private ArrayList<String> alphabet; // словарь
    private ArrayList<String> conditions; // 000 001 010
    private ArrayList<String> outputValue; // правая часть таблицы
    private ArrayList<String> vectorOfValues; // левая часть таблицы

    public ArrayList<String> getAlphabet() {
        return alphabet;
    }

    public ArrayList<String> getConditions() {
        return conditions;
    }

    public ArrayList<String> getOutputValue() {
        return outputValue;
    }

    public ArrayList<String> getVectorOfValues() {
        return vectorOfValues;
    }

    public void setOutputValue(String outputValue) {
        this.outputValue =  new ArrayList<>(Arrays.asList(outputValue.split(" ")));
    }

    public void setVectorOfValues(String vectorOfValues) {
        this.vectorOfValues = new ArrayList<>(Arrays.asList(vectorOfValues.split(" ")));
    }

    public void setAlphabet(String input){
        alphabet = new ArrayList<>(Arrays.asList(input.split(" ")));
    }
    public void setConditions(String input){
        conditions = new ArrayList<>(Arrays.asList(input.split(" ")));
    }

}
