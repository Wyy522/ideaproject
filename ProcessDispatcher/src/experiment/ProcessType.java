package experiment;

public class ProcessType implements Comparable<ProcessType>{
    private char Processno;
    private float submittime;
    private float servicetime;
    private float starttime;
    private float endtime;
    private float roundTime;
    private float wRoundTime;

    public ProcessType(char processno, float submittime, float servicetime, float starttime, float endtime, float roundTime, float wRoundTime) {
        Processno = processno;
        this.submittime = submittime;
        this.servicetime = servicetime;
        this.starttime = starttime;
        this.endtime = endtime;
        this.roundTime = roundTime;
        this.wRoundTime = wRoundTime;
    }

    public ProcessType(char processno, float submittime, float servicetime) {
        Processno = processno;
        this.submittime = submittime;
        this.servicetime = servicetime;
    }

    public ProcessType() {
    }

    public char getProcessno() {
        return Processno;
    }

    public void setProcessno(char processno) {
        Processno = processno;
    }

    public float getSubmittime() {
        return submittime;
    }

    public void setSubmittime(float submittime) {
        this.submittime = submittime;
    }

    public float getServicetime() {
        return servicetime;
    }

    public void setServicetime(float servicetime) {
        this.servicetime = servicetime;
    }

    public float getStarttime() {
        return starttime;
    }

    public void setStarttime(float starttime) {
        this.starttime = starttime;
    }

    public float getEndtime() {
        return endtime;
    }

    public void setEndtime(float endtime) {
        this.endtime = endtime;
    }

    public float getRoundTime() {
        return roundTime;
    }

    public void setRoundTime(float roundTime) {
        this.roundTime = roundTime;
    }

    public float getwRoundTime() {
        return wRoundTime;
    }

    public void setwRoundTime(float wRoundTime) {
        this.wRoundTime = wRoundTime;
    }

    @Override
    public int compareTo( ProcessType processType ) {
        return Float.compare(this.submittime, processType.submittime);
    }

    @Override
    public String toString() {
        return "ProcessType{" +
                "Processno=" + Processno +
                ", submittime=" + submittime +
                ", servicetime=" + servicetime +
                ", starttime=" + starttime +
                ", endtime=" + endtime +
                ", roundTime=" + roundTime +
                ", wRoundTime=" + wRoundTime +
                '}';
    }
}
