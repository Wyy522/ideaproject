package experiment;

import java.util.ArrayList;
import java.util.List;

public class SRF {

    private static final float INF = 10000000.0f;

    public static void main(String[] args) {
        List<ProcessType> processlist = new ArrayList<ProcessType>();
        processlist.add(new ProcessType('1', 10.0f, 2.0f,'R'));
        processlist.add(new ProcessType('2', 10.2f, 1.0f,'R'));
        processlist.add(new ProcessType('3', 10.4f, 0.5f,'R'));
        processlist.add(new ProcessType('4', 10.5f, 0.3f,'R'));
        processlist.sort(ProcessType::compareTo);

        float lastTime = processlist.get(0).getSubmittime();
        for (int i = 0; i < processlist.size(); i++) {
            // 找到下一个将要执行的进程
            int p = findNext(processlist, lastTime);
            if (processlist.get(p).getSubmittime() < lastTime) processlist.get(p).setStarttime(lastTime);
            else processlist.get(p).setStarttime(processlist.get(p).getSubmittime());
            processlist.get(p).setEndtime(processlist.get(p).getStarttime() + processlist.get(p).getServicetime());
            processlist.get(p).state= 'F';
            lastTime = processlist.get(p).getEndtime();  // 更新lastTime
        }

        float sum1 = 0.0f, sum2 = 0.0f;
        System.out.println("\n  进程   到达时间   运行时间   开始时间   完成时间    周转时间      带权周转时间");
        for (ProcessType jcb : processlist) {
            System.out.format("%4s  %8.2f  %8.2f  ", jcb.getProcessno(), jcb.getSubmittime(), jcb.getServicetime());
            System.out.format("%8.2f  %8.2f  ", jcb.getStarttime(), jcb.getEndtime());
            System.out.format("%8.2f  ", jcb.getEndtime() - jcb.getSubmittime());
            System.out.format("%12.2f\n", (jcb.getEndtime() - jcb.getSubmittime()) / jcb.getServicetime());
            sum1 += jcb.getEndtime() - jcb.getSubmittime();
            sum2 += (jcb.getEndtime() - jcb.getSubmittime()) / jcb.getServicetime();
        }
    }

    private static int findNext(List<ProcessType> processlist, float lastTime) {
        int i, p = -1;
        float minNeedTime = INF;
        for (i = 0; i < processlist.size(); i++) {
            if (processlist.get(i).state == 'R') {
			    /* 数组arr已经按reachTime排序,当出现
			       arr[i].reachTime>lastTime时,说明在lastTime时刻无进程到达,终止循环.*/
                if (processlist.get(i).getSubmittime() > lastTime) break;
                if (processlist.get(i).getServicetime() < minNeedTime) {
                    p = i;
                    minNeedTime = processlist.get(p).getServicetime();
                }
            }
        }
        if (p != -1) return p;
        return i;
    }

}
