package cn.com.histar.showclient.program;

public class Program {
    private String programName;
    private int programId;

    public Program(String programName, int programId) {
        this.programName = programName;
        this.programId = programId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }
}
