import java.util.HashMap;

public class DataBase<T> {
    private final HashMap<Integer,T> dataArray;

    public DataBase(HashMap<Integer,T> dataArray) {
        this.dataArray = dataArray;
    }

    public HashMap<Integer,T> getDataArray() {
        return dataArray;
    }
}
