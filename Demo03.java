import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;
import entiy.Person;
import sun.dc.path.PathError;

import java.util.*;

public class Demo03 {
    public static void main(String[] args) {

        List<Person> list1= new ArrayList<>();
//        Collections.sort(list1, new Comparator<Person>() {
//            @Override
//            public int compare(Person o1, Person o2) {
//                return o1.getId() - o2.getId();
//            }
//        });//匿名内部类

        Collections.sort(list1,(Person o1, Person o2)->{
            return o1.getId() - o2.getId();
        });//lambda
    }
}
