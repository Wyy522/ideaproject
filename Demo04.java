
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.function.Consumer;

public class Demo04 {
    public static void main(String[] args) {
        printMax(new Consumer<int[]>() {
            @Override
            public void accept(int[] a) {
                Arrays.sort(a);
                System.out.println(Arrays.stream(a).max());
            }
        });
        printMax((a)->{
            System.out.println(Arrays.stream(a).max());
        });
    }
    private static void printMax(Consumer<int []> consumer){
        int a[]={1,2,3,4,5};
        consumer.accept(a);
    }
}
