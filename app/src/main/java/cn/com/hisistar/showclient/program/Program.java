package cn.com.hisistar.showclient.program;

public class Program {
    private int id;
    private String programName;
    private int mouldImg;
    private int mouldPosition;

    public Program() {
    }

    public Program(String programName, int mouldImg) {
        this.programName = programName;
        this.mouldImg = mouldImg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public int getMouldImg() {
        return mouldImg;
    }

    public void setMouldImg(int mouldImg) {
        this.mouldImg = mouldImg;
    }

    public int getMouldPosition() {
        return mouldPosition;
    }

    public void setMouldPosition(int mouldPosition) {
        this.mouldPosition = mouldPosition;
    }

    @Override
    public String toString() {
        return "Program{" +
                "id=" + id +
                ", programName='" + programName + '\'' +
                ", mouldImg=" + mouldImg +
                ", mouldPosition=" + mouldPosition +
                '}';
    }
}
