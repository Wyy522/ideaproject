package allocation;
public class Main {

    private final static TableList list = new TableList(64);

    public static void main(String[] args) {
        list.useWF();
//        list.useBF();
//        list.useNF();
//        list.useFF();

        list.allocate(10);
        list.allocate(20);
        list.free(10);
        list.show();
        list.allocate(8);
        list.show();
        list.allocate(13);
        list.allocate(1);
        list.show();
        list.free(1);
        list.allocate(9);
        list.free(13);
        list.show();
        list.allocate(18);
        list.show();
        list.allocate(3);
        list.allocate(4);
        list.free(20);
        list.free(8);
        list.show();
        list.allocate(8);
        list.free(9);
        list.show();
        list.clear();
        list.show();
    }
}