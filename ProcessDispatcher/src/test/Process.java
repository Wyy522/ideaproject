package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class Process implements Comparable<Process> {
    private String mName; //线程名 user
    private int mState; //0代表就绪 //1代表执行 //2代表阻塞
    private Date mSubmitTime; //提交时间 user
    private Date mRunTime; //运行需要时间 user
    private Date mBeginTime; //开始运行时间
    private Date mEndTime; //运行结束时间
    private long  mTurnOverTime; //周转时间
    private double  mResponseRitio; //响应比

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

    public Process(String name, String submitTime, String runTime) throws ParseException {

        this.mName = name;
        this.mSubmitTime = simpleDateFormat.parse(submitTime);
        this.mRunTime = simpleDateFormat.parse(runTime);
    }



    public String getmName() {
        return mName;
    }
    public void setmName(String mName) {
        this.mName = mName;
    }


    public int getmState() {
        return mState;
    }
    public void setmState(int mState) {
        this.mState = mState;
    }


    public Date getmSubmitTime() {
        return mSubmitTime;
    }
    public void setmSubmitTime(Date mSubmitTime) {
        this.mSubmitTime = mSubmitTime;
    }


    public Date getmRunTime() {
        return mRunTime;
    }
    public void setmRunTime(Date mRunTime) {
        this.mRunTime = mRunTime;
    }


    public Date getmBeginTime() {
        return mBeginTime;
    }
    public void setmBeginTime(Date mBeginTime) {
        this.mBeginTime = mBeginTime;
    }


    public Date getmEndTime() {
        return mEndTime;
    }
    public void setmEndTime(Date mEndTime) {
        this.mEndTime = mEndTime;
    }


    public long getmTurnOverTime() {
        return mTurnOverTime;
    }
    public void setmTurnOverTime(long l) {
        this.mTurnOverTime = l;
    }


    public double getmResponseRitio() {
        return mResponseRitio;
    }
    public void setmResponseRitio(double mResponseRitio) {
        this.mResponseRitio = mResponseRitio;
    }


    @Override
    public String toString() {
        return " 线程名" + this.getmName() + " 状态符" + this.getmState()
                + " 提交时间" + simpleDateFormat.format(this.getmSubmitTime())
                + " 运行时间" + simpleDateFormat.format(this.getmRunTime())
                + " 开始运行时间" + simpleDateFormat.format(this.getmBeginTime())
                + " 运行结束时间" + simpleDateFormat.format(this.getmEndTime())
                + " 周转时间" + this.getmTurnOverTime()
                + " 响应比" + this.getmResponseRitio();
    }

    public String toString2() {
        return " 线程名" + this.getmName() + " 状态符" + this.getmState()
                + " 提交时间" + simpleDateFormat.format(this.getmSubmitTime())
                + " 运行时间" + simpleDateFormat.format(this.getmRunTime())
                + " 开始运行时间" + this.getmBeginTime()
                + " 运行结束时间" + this.getmEndTime()
                + " 周转时间" + this.getmTurnOverTime()
                + " 响应比" + this.getmResponseRitio();
    }


    @Override
    public int compareTo(Process o) {
        // TODO Auto-generated method stub

        int y = (int) (o.mSubmitTime.getTime()/1000 + 8 * 60 * 60);
        int x = (int) (this.mSubmitTime.getTime()/1000 + 8 * 60 * 60);
        if (x != y) {
            return (int)(x - y);

        }
        else {
            return (int) ((this.mRunTime.getTime()/1000 + 8 * 60 * 60)
                    - (o.mRunTime.getTime()/1000 + 8 * 60 * 60));
        }

    }

}

//先按照提交时间排序，再按照运行时间排序
class sortByAll implements Comparator<Process>{

    @Override
    public int compare(Process o1, Process o2) {
        int y = (int) (o2.getmSubmitTime().getTime()/1000 + 8 * 60 * 60);
        int x = (int) (o1.getmSubmitTime().getTime()/1000 + 8 * 60 * 60);
        if (x != y) {
            return (int)(x - y);

        }
        else {
            return (int) ((o1.getmRunTime().getTime()/1000 + 8 * 60 * 60)
                    - (o2.getmRunTime().getTime()/1000 + 8 * 60 * 60));
        }
    }

}

class sortBymRunTime implements Comparator<Process>{

    @Override
    public int compare(Process o1, Process o2) {

        return (int) ((o1.getmRunTime().getTime()/1000 + 8 * 60 * 60)
                - (o2.getmRunTime().getTime()/1000 + 8 * 60 * 60));
    }

}
