package allocation;


public class Table {

    private int address;

    private int size;
    private boolean free;
    private boolean lastAllocated;

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public boolean isLastAllocated() {
        return lastAllocated;
    }

    public void setLastAllocated(boolean lastAllocated) {
        this.lastAllocated = lastAllocated;
    }

    public Table() {
    }

    public Table(int address, int size, boolean free, boolean lastAllocated) {
        this.address = address;
        this.size = size;
        this.free = free;
        this.lastAllocated = lastAllocated;
    }

    public static Table freeTable(int address, int size)
    {
        return new Table(address,size,true,false);
    }

    public static Table allocatedTable(int address,int size)
    {
        return new Table(address,size,false,false);
    }

    public boolean isFree()
    {
        return free;
    }

    public boolean isAllocated()
    {
        return !isFree();
    }

    public void setFree()
    {
        free = true;
    }
}
