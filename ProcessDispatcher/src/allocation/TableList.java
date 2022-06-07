package allocation;
import java.util.ArrayList;
import java.util.List;

public class TableList {
    private final List<Table> list = new ArrayList<>();
    private final int totalSize;
    private boolean ff = false;
    private boolean nf = false;
    private boolean bf = false;
    private boolean wf = false;
    private boolean first = true;
    private final static Print print = new Print();

    public TableList(int initSize)
    {
        totalSize = initSize;
        list.add(Table.freeTable(0,initSize));
    }

    public void useFF(){
        ff = true;
        nf = bf = wf = false;
    }

    public void useNF(){
        nf = true;
        ff = bf = wf = false;
    }

    public void useBF(){
        bf = true;
        ff = nf = wf = false;
    }

    public void useWF()
    {
        wf = true;
        ff = nf = bf = false;
    }

    public void clear()
    {
        list.clear();
        first = true;
        nf = bf = ff = wf = false;
        list.add(Table.freeTable(0,totalSize));
    }

    public void show()
    {
        print.show(list);
    }

    private int findLastAllocated()
    {
        int i;
        for (i = 0; i < list.size(); ++i) {
            Table table = list.get(i);
            if(table.isFree() && table.isLastAllocated())
                return i;
        }
        return 0;
    }

    public void allocate(int size)
    {
        if(!nf && !bf && !ff && !wf)
            print.notSetAlgorithm();
        if(first)
        {
            Table table = Table.allocatedTable(0,size);
            Table free = list.get(0);
            free.setAddress(size);
            free.setSize(totalSize-size);
            if(nf)
                free.setLastAllocated(true);
            list.add(0,table);
            first = false;
            return;
        }
        else
        {
            if (ff)
            {
                for (int i = 0; i < list.size(); i++) {
                    Table table = list.get(i);
                    if(table.isFree() && table.getSize() >= size)
                    {
                        int address = table.getAddress();
                        Table allocated = Table.allocatedTable(address,size);
                        table.setAddress(address+size);
                        table.setSize(table.getSize()-size);
                        list.add(i,allocated);
                        return;
                    }
                }
            }
            else if (nf)
            {
                int lastNFIndex = findLastAllocated();
                int i = lastNFIndex;
                do
                {
                    if(i == list.size())
                        i = 0;
                    Table table = list.get(i);
                    if(table.isFree() && table.getSize() >= size)
                    {
                        int address = table.getAddress();
                        Table allocated = Table.allocatedTable(address,size);
                        table.setAddress(address+size);
                        table.setSize(table.getSize()-size);
                        list.get(lastNFIndex).setLastAllocated(false);
                        table.setLastAllocated(true);
                        list.add(i,allocated);
                        return;
                    }
                    ++i;
                }
                while (i != lastNFIndex);
            }
            else
            {
                int i;
                int target = -1;
                for (i = 0; i < list.size(); i++) {
                    Table table = list.get(i);
                    if(table.isFree())
                    {
                        if(table.getSize() >= size)
                        {
                            if(target == -1)
                                target = i;
                            else
                            {
                                if(bf)
                                {
                                    if(list.get(target).getSize() > table.getSize())
                                        target = i;
                                }
                                else
                                {
                                    if(list.get(target).getSize() < table.getSize())
                                        target = i;
                                }
                            }
                        }
                    }
                }
                if(target != -1)
                {
                    Table table = list.get(target);
                    int address = table.getAddress();
                    table.setAddress(address+size);
                    table.setSize(table.getSize()-size);
                    list.add(target,Table.allocatedTable(address,size));
                    return;
                }
            }
        }
        print.outOfMemory();
    }

    public void free(int size)
    {
        int index = 0;
        while(index < list.size())
        {
            if(list.get(index).isAllocated() && list.get(index).getSize() == size)
                break;
            ++index;
        }
        if(index >= list.size())
        {
            print.freeFailed(size);
            return;
        }
        int address = list.get(index).getAddress();
        if(index == 0)
        {
            list.get(0).setFree();
            if(index+1 < list.size())
            {
                Table nextTable = list.get(index+1);
                if(nextTable.isFree())
                {
                    list.get(0).setSize(nextTable.getSize()+size);
                    list.remove(index+1);
                }
            }
        }
        else if(index == list.size()-1)
        {
            list.get(index).setFree();
            Table lastTable = list.get(index-1);
            if(lastTable.isFree())
            {
                lastTable.setSize(lastTable.getSize()+size);
                list.remove(index);
            }
        }
        else
        {
            Table before = list.get(index-1);
            Table after = list.get(index+1);

            if(before.isFree() && after.isFree())
            {
                before.setSize(before.getSize()+size+after.getSize());
                list.remove(index+1);
                list.remove(index);
            }
            else if(before.isFree() && after.isAllocated())
            {
                before.setSize(before.getSize()+size);
                list.remove(index);
            }
            else if(before.isAllocated() && after.isFree())
            {
                after.setSize(after.getSize()+size);
                after.setAddress(address);
                list.remove(index);
            }
            else
            {
                list.get(index).setFree();
            }
        }
    }
}
