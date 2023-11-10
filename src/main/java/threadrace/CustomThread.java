package threadrace;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.function.Predicate;

@Getter
@Setter
public class CustomThread extends Thread {
    private Predicate<Integer> predicate;
    private List<Integer> subList;
    private List<Integer> destList;

    public CustomThread(Predicate<Integer> predicate, List<Integer> subList, List<Integer> destList) {
        this.predicate = predicate;
        this.subList = subList;
        this.destList = destList;
    }

    @Override
    public void run() {
        filterAndAdd();
    }

    private synchronized void filterAndAdd() {
        List<Integer> filteredList = subList.stream().filter(predicate).toList();
        destList.addAll(filteredList);
    }
}
