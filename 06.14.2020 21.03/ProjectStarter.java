import java.io.*;

public class ProjectStarter {
    public static ProjectStarter projectStarter = new ProjectStarter();
    public static DataContainer dataContainer = new DataContainer();
    public static void main(String[] args) throws IOException {
        projectStarter.startRead();

    }
    public void startRead() throws IOException {
        File data = new File("C:\\Users\\Nikita\\IdeaProjects\\Fin2\\src\\data.txt");
        BufferedReader reader = new BufferedReader(new FileReader(data));
        // первая строка файла предназначена для входного алфавита каждый символ алфавита разделен пробелом
        dataContainer.setInputAlphabet(reader.readLine());
        // вторая строка для состояний, каждое состояние разделено пробелом
        dataContainer.setInputConditions(reader.readLine());
        // третья строка для выходных значений ( правая таблица )
        dataContainer.setOutputValue(reader.readLine());
        // 4-я строка для вектора значений ( левая таблица )
        String inputVector = "";
        while (reader.ready()) inputVector = inputVector + " " + reader.readLine();
        dataContainer.setVectorOfValues(inputVector);
    }
}
