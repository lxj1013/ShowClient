package cn.com.histar.showclient.mould;

public class Mould {
    private String mouldName;
    private int mouldId;

    public Mould(String mouldName, int mouldId) {
        this.mouldName = mouldName;
        this.mouldId = mouldId;
    }

    public String getMouldName() {
        return mouldName;
    }

    public void setMouldName(String mouldName) {
        this.mouldName = mouldName;
    }

    public int getMouldId() {
        return mouldId;
    }

    public void setMouldId(int mouldId) {
        this.mouldId = mouldId;
    }
}
