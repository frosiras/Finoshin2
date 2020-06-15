// этот класс нужен для того, чтобы хранить в себе точку и "дорожки" до других точек
// таким образом я избежал появления одинаковых дорожек, которые бы вели в одну и ту же точку
public class Graph {
    public String condition;
    public String value;
    public String output;

    @Override
    public String toString() {
        return condition + " " + value + " " + output ;
    }
}
