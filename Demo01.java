import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Demo01 {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("xxx");
            }
        }).start();//匿名内部类

        new Thread(()-> System.out.println("xxx")).start();//lambda表达式,只关注(参数类型 参数名称) -> {代码体；}
        // javac Demo01.java
        // javap -c -p Demo01.class

        /*
        匿名内部类锁在编译后形成一个class
        Lambda表达式是在程序运行的时候动态生成class
         */



    }
}
