package test;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class ShortRuntime {

    private static String threadName;
    private static String threadSubmitTime;
    private static String threadRunTime;
    private static Date now; //记录当前时间
    private static int i = 2;

    public static void main(String[] args) throws ParseException {

        //创建线程对象的集合
        List<Process> list = new ArrayList<>();

        //手动输入太麻烦了，先自己创建几个对象调试程序
        list.add(new Process("Process1", "00:10:00", "00:02:00"));
        list.add(new Process("Process2", "00:10:20", "00:01:00"));
        list.add(new Process("Process3", "00:10:40", "00:00:50"));
        list.add(new Process("Process4", "00:10:50", "00:0:30"));
        startUp(list);

    }

    //最开始的时候先比较提交时间，在提交时间相同的情况下比较运行时间长短
    //程序开始运行后，只要提交时间小于当前时间的进程，按运行时间长短执行
    public static void startUp(List<Process> list) throws ParseException {
        int flag = 0;
        now = new Date(-8 * 60 * 60 * 1000);
        System.out.println(now);
        Collections.sort(list);
        list.get(0).setmState(1);
        list.get(0).setmBeginTime(list.get(0).getmSubmitTime());
        list.get(0).setmEndTime(new Date(((list.get(0).getmBeginTime().getTime()/1000 + 8 * 60 * 60)
                + (list.get(0).getmRunTime().getTime()/1000)) * 1000)); //我也不知为什么要这么写，简直是够了
        list.get(0).setmTurnOverTime((list.get(0).getmRunTime().getTime())/1000 + 8 * 60 * 60);
        list.get(0).setmResponseRitio(1);
        System.out.println("第1次执行进程：");
        System.out.println(list.get(0).toString());

        for (Process process : list) {
            if(process.getmState() != 1) {
                System.out.println(process.toString2());
            }
        }
        now = new Date(now.getTime() + list.get(0).getmRunTime().getTime() + 8 * 60 * 60 * 1000);
        //System.out.println(now);
        //当第一个进程执行完毕之后，移除执行完毕的进程，并询问是否要添加新的进程
        list.remove(0);
        System.out.println("是否要添加新的进程，如果是请输入1，否请输入0");
        Scanner scanner = new Scanner(System.in);
        flag = scanner.nextInt();
        if (flag == 1) {
            addProcess(list);
        }
        else {
            execute(list);
        }

    }

    public static void addProcess(List<Process> list) throws ParseException {
        int total = 0;

        System.out.println("请按如下格式输入线程名,线程提交时间，需要运行时间。 "
                + "Example: Process1, 09:13:01, 11:00:20");
        System.out.println("请输入需要创建线程个数");
        Scanner scanner = new Scanner(System.in);
        total = scanner.nextInt();

        for (int i = 0; i < total; i++) {
            //读取信息
            System.out.println("请输入线程名");
            threadName = scanner.next();
            System.out.println("请输入线程提交时间(提交时间必须大于当前时间)");
            threadSubmitTime = scanner.next();
            System.out.println("请输入需要运行时间");
            threadRunTime = scanner.next();

            Process process = new Process(threadName, threadSubmitTime, threadRunTime);
            list.add(process);
        }
        Collections.sort(list);
        execute(list);
    }

    public static void execute(List<Process> list) throws ParseException {
        while (list.isEmpty()) {
            System.out.println("进程已经执行完毕");
            System.exit(0);
        }
        double res = 0;//响应比
        int flag = 0;
        List<Process> newList = new ArrayList<>();

        for (Process process : list) {
            if((process.getmSubmitTime().getTime() + 28800000) < (now.getTime() + 28800000)) {
                newList.add(process);
            }
        }
        //System.out.println(list.get(0).getmSubmitTime().getTime() + 28800000);
        Collections.sort(newList, new sortBymRunTime());
        newList.get(0).setmState(1);

        newList.get(0).setmBeginTime(now);

        newList.get(0).setmEndTime(new Date(((now.getTime()/1000 + 8 * 60 * 60)
                + (newList.get(0).getmRunTime().getTime()/1000)) * 1000)); //我也不知为什么要这么写，简直是够了

        newList.get(0).setmTurnOverTime(newList.get(0).getmEndTime().getTime()/1000
                - newList.get(0).getmSubmitTime().getTime()/1000);

        res = (double) (newList.get(0).getmTurnOverTime()/(double)(newList.get(0).getmRunTime().getTime()/1000 + 28800));
        now = new Date(newList.get(0).getmEndTime().getTime());
        newList.get(0).setmResponseRitio(res);


        System.out.println("第" + i + "执行进程：");
        i++;

        System.out.println(newList.get(0).toString());

        String name = newList.get(0).getmName();
        Iterator<Process> iterator = list.iterator();
        while (iterator.hasNext()) {
            Process process = (Process) iterator.next();
            if (process.getmName() == name) {
                iterator.remove();
            }
        }

        System.out.println("是否要添加新的进程，如果是请输入1，否请输入0");
        Scanner scanner = new Scanner(System.in);
        flag = scanner.nextInt();
        if (flag == 1) {
            addProcess(list);
        }
        else {
            execute(list);
        }

    }

}
