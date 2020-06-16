import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ProjectStarter {
    //public static AdjacencyMatrix matrix = new AdjacencyMatrix(); // матрица смежностей
    public static ArrayList<MyVector> vectors = new ArrayList<>(); // матрица векторов для представлению в таблицу
    public static ProjectStarter projectStarter = new ProjectStarter(); // запускает приложение
    public static DataContainer dataContainer = new DataContainer(); // хранит данные из файла
    public static ArrayList<GraphWithOutput> graphWithOutputs = new ArrayList<>(); // хранит точки со всеми ее направлениями и значениями
    //public static ArrayList<Graph> graphs = new ArrayList<>(); // хранит по точке с направлением без значений
    public final static byte TEST = 0;
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        projectStarter.startRead(); // считывает сначала данные из файла
        projectStarter.formatDataToVectors();
        projectStarter.createGraphsWithOutput();
        projectStarter.makeTextGraph();
        projectStarter.doKosaraju();
    }

    private void secondExercise() {
        projectStarter.formatDataToVectors();
        projectStarter.createGraphsWithOutput();
        System.out.println(graphWithOutputs);
        projectStarter.doKosaraju();
    }

    private void doKosaraju() {
        ArrayList<String> condition = new ArrayList<>();
        // заполняем таблицу состояний
        for (int i = 0; i < vectors.size(); i++)
            condition.add(vectors.get(i).condition);
        Kosaraju k = new Kosaraju();
        // размерность матрицы
        int V = vectors.size();
        List<Integer>[] g = new List[V];
        for (int i = 0; i < V; i++)
            g[i] = new ArrayList<Integer>();
        // считаем число ребер
        int edges = graphWithOutputs.size();
        if (TEST == 1)
            System.out.println(edges);
        for (int i = 0; i < edges; i++){
            int x = condition.indexOf(graphWithOutputs.get(i).condition);
            int y = condition.indexOf(graphWithOutputs.get(i).value);
            g[x].add(y);
        }
        List<List<Integer>> scComponents = k.getSCComponents(g);
        System.out.println(scComponents);
    }

    // создаем матрицу смежности
    /*private void createMatrix() {
        matrix.createMatrix();
        if (TEST == 1) {
            System.out.println("Вывели заранее пустую матрицу смежностей");
            matrix.printMatrix();
            System.out.println("==================================================================================");
        }
    }

    private void createGraphs() {
        for (MyVector v : vectors){
            Graph g = new Graph();
            g.condition = v.condition;
            g.direction = v.values;
            graphs.add(g);
        }
    }
    */
    private void firstExercise() {
        projectStarter.formatDataToVectors(); // форматирует данные
        projectStarter.createGraphsWithOutput(); // создает точки
        projectStarter.makeTextGraph(); // создает пнг файл
    }

    // нужно, чтобы не было повторяющихся путей из одного графа в другой
    private void createGraphsWithOutput() {
        for (int vectorCounter = 0; vectorCounter < vectors.size(); vectorCounter++) {
            // берем один из векторов
            MyVector currentVector = new MyVector(vectors.get(vectorCounter));
            for (int valuesCounter = 0; valuesCounter < currentVector.values.size(); valuesCounter++) {
                // записываем граф
                GraphWithOutput temp = new GraphWithOutput();
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
                graphWithOutputs.add(temp);
            }
            if (TEST == 1) {
                for (GraphWithOutput t : graphWithOutputs)
                    System.out.println(t);
                System.out.println(vectorCounter);
            }
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
        for (GraphWithOutput g : graphWithOutputs){
            gv.addln((g.condition + " -> " + g.value + " [ label = " + "\"" + g.output + "\"" + " ]"));
        }
        gv.addln(gv.end_graph());
        if (TEST == 1)
            System.out.println(gv.getDotSource());
        gv.increaseDpi();
        String type = "png";
        String repesentationType= "dot";
        File out = new File("C:\\Users\\Nikita\\Desktop\\test\\kek." + type);   // Windows
        gv.writeGraphToFile( gv.getGraph(gv.getDotSource(), type, repesentationType), out );
    }
}
