import java.util.ArrayList;
// здесь я решил представить данные в виде таблицы, которая есть в тетради
public class MyVector {
    String condition;
    ArrayList<String> values = new ArrayList<>();
    String output;

    public MyVector(MyVector myVector) {
        condition = myVector.condition;
        values = myVector.values;
        output = myVector.output;
    }

    public MyVector() {

    }

    @Override
    public String toString() {
        return "MyVector{" +
                "condition='" + condition + '\'' +
                ", values=" + values +
                ", output='" + output + '\'' +
                '}';
    }
}
