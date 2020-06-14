import java.util.ArrayList;
import java.util.Arrays;

public class DataContainer {
    private ArrayList<String> inputAlphabet;
    private ArrayList<String> inputConditions;
    private ArrayList<String> outputValue;
    private ArrayList<String> vectorOfValues;

    public void setOutputValue(String outputValue) {
        this.outputValue =  new ArrayList<>(Arrays.asList(outputValue.split(" ")));
    }

    public void setVectorOfValues(String vectorOfValues) {
        this.vectorOfValues = new ArrayList<>(Arrays.asList(vectorOfValues.split(" ")));
    }

    public void setInputAlphabet(String input){
        inputAlphabet = new ArrayList<>(Arrays.asList(input.split(" ")));
    }
    public void setInputConditions(String input){
        inputConditions = new ArrayList<>(Arrays.asList(input.split(" ")));
    }

}
