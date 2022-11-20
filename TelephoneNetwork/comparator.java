import java.util.Comparator;
public class comparator implements Comparator<Edge> {
//    to sort in reverse way if needed
    public int compare(Edge a, Edge b) {
        return a.lens - b.lens;
    }
}