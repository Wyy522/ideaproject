import javax.xml.crypto.Data;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

public class Demo05 {
    //::方法引用

    public static void main(String[] args) {
        Date date =new Date();
        Supplier<Long> supplier =()->{ return date.getTime();};
        System.out.println(supplier.get());
        Supplier<Long> supplier1 =date::getTime;
        System.out.println(supplier1.get());
        List<String> a =new ArrayList();
        a.stream().forEach();

    }
}
