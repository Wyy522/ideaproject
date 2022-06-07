package allocation;
import java.util.List;

public class Print {
    public void doNothing()
    {
        System.out.println("Do nothing.");
    }

    public void notSetAlgorithm()
    {
        doNothing();
        System.out.println("Please set an algorithm before allocating.");
    }

    public void outOfMemory()
    {
        doNothing();
        System.out.println("Allocated failed, out of memory");
    }

    public void show(List<Table> list)
    {
        list.forEach(t->{
            System.out.print((t.isFree()?"Free           :      ":"Allocated      :      "));
            System.out.println(t.getAddress()+"-"+(t.getAddress()+t.getSize())+"MB");
        });
        System.out.println("\n----------------------------------------------------------------\n");
    }

    public void freeFailed(int size)
    {
        doNothing();
        System.out.println("Free "+size+"MB memory failed");
        System.out.println("Please ensure that having allocated "+size+"MB memory.");
    }
}
