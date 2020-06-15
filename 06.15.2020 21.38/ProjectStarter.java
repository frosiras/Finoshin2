import java.io.*;
import java.util.ArrayList;

public class ProjectStarter {
    public static ArrayList<MyVector> vectors = new ArrayList<>();
    public static ProjectStarter projectStarter = new ProjectStarter();
    public static DataContainer dataContainer = new DataContainer();
    public static ArrayList<Graph> graphs = new ArrayList<>();
    public final static byte TEST = 1;
    public static void main(String[] args) throws IOException {
        projectStarter.startRead(); // считывает сначала данные из файла
        projectStarter.formatDataToVectors(); // форматирует данные
        projectStarter.createGraphs(); // создает точки
        projectStarter.makeTextGraph(); // создает пнг файл
    }

    // нужно, чтобы не было повторяющихся путей из одного графа в другой
    private void createGraphs() {
        for (int vectorCounter = 0; vectorCounter < vectors.size(); vectorCounter++) {
            // берем один из векторов
            MyVector currentVector = new MyVector(vectors.get(vectorCounter));
            for (int valuesCounter = 0; valuesCounter < currentVector.values.size(); valuesCounter++) {
                // записываем граф
                Graph temp = new Graph();
                // его состояние и значение
                temp.condition = currentVector.condition;
                temp.value = currentVector.values.get(valuesCounter);
                temp.output = dataContainer.getAlphabet().get(valuesCounter)+ "/" + currentVector.output.charAt(valuesCounter);
                if (temp.value == null) continue;
                for (int i = valuesCounter+1; i < currentVector.values.size(); i++) {
                    if (currentVector.values.get(i) == null) continue;
                    if (temp.value.equals(currentVector.values.get(i))){
                        temp.output = temp.output + "; " + dataContainer.getAlphabet().get(i) + "/" + currentVector.output.charAt(i);
                        currentVector.values.set(i, null);
                    }

                }
                graphs.add(temp);
            }
            for (Graph t : graphs)
                System.out.println(t);
            System.out.println(vectorCounter);
        }
    }

    // создает список строк типа того, что у нас получается на листочке
    // цикл прочитал состояние, записал
    private void formatDataToVectors() {
        for (int inputConditionsCounter = 0; inputConditionsCounter < dataContainer.getConditions().size(); inputConditionsCounter++){
            MyVector vector = new MyVector();
            vector.condition = dataContainer.getConditions().get(inputConditionsCounter); // добавили в временный вектор состояние
            for (int inputAlphabetCounter = 0; inputAlphabetCounter < dataContainer.getAlphabet().size(); inputAlphabetCounter++){
                int i = inputAlphabetCounter + inputConditionsCounter*dataContainer.getAlphabet().size();
                vector.values.add(dataContainer.getVectorOfValues().get(inputAlphabetCounter + inputConditionsCounter*dataContainer.getAlphabet().size()));
            }
            vector.output = dataContainer.getOutputValue().get(inputConditionsCounter);
            vectors.add(vector);

        }
        if (TEST == 1){
            System.out.println("Проверяем вектора");
            for (MyVector v : vectors)
                System.out.println(v);
            System.out.println("==================================================================================");
        }
    }

    public void startRead() throws IOException {
        File data = new File("C:\\Users\\Nikita\\IdeaProjects\\Fin2\\src\\data.txt");
        BufferedReader reader = new BufferedReader(new FileReader(data));
        // первая строка файла предназначена для входного алфавита каждый символ алфавита разделен пробелом
        dataContainer.setAlphabet(reader.readLine());
        // вторая строка для состояний, каждое состояние разделено пробелом
        dataContainer.setConditions(reader.readLine());
        // третья строка для выходных значений ( правая таблица )
        dataContainer.setOutputValue(reader.readLine());
        // 4-я строка для вектора значений ( левая таблица )
        String inputVector = reader.readLine();
        while (reader.ready()) inputVector = inputVector + " " + reader.readLine();
        dataContainer.setVectorOfValues(inputVector);
    }
    public void makeTextGraph(){
        GraphViz gv = new GraphViz();
        gv.addln(gv.start_graph());
        for (Graph g : graphs){
            gv.addln((g.condition + " -> " + g.value + " [ label = " + "\"" + g.output + "\"" + " ]"));
        }
        gv.addln(gv.end_graph());
        System.out.println(gv.getDotSource());
        gv.increaseDpi();
        String type = "png";
        String repesentationType= "dot";
        File out = new File("C:\\Users\\Nikita\\Desktop\\test\\kek." + type);   // Windows
        gv.writeGraphToFile( gv.getGraph(gv.getDotSource(), type, repesentationType), out );
    }
}
