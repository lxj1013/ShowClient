package cn.com.hisistar.showclient.database;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.List;

/**
 * @author lxj
 * @date 2018/9/4
 */
public class ProgramTable extends LitePalSupport {
    private int id;
    private String programName;
    private int mouldImg;
    private int mouldPosition;

    private List<LocalMediaTable> mainList;
    private List<LocalMediaTable> listTwo;
    private List<LocalMediaTable> listThree;
    private List<LocalMediaTable> listFour;
    private List<LocalMediaTable> musicList;

    private SettingsTable settings;

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

    public List<LocalMediaTable> getMainList() {
        return LitePal.where("programtable_id=? and screen=?", String.valueOf(id), "main").find(LocalMediaTable.class);
    }

    public void setMainList(List<LocalMediaTable> mainList) {
        this.mainList = mainList;
        if (mainList != null) {
            LitePal.saveAll(mainList);
        }
    }

    public List<LocalMediaTable> getListTwo() {
        return LitePal.where("programtable_id=? and screen=?", String.valueOf(id), "two").find(LocalMediaTable.class);
    }

    public void setListTwo(List<LocalMediaTable> listTwo) {
        this.listTwo = listTwo;
        if (listTwo != null) {
            LitePal.saveAll(listTwo);
        }
    }

    public List<LocalMediaTable> getListThree() {
        return LitePal.where("programtable_id=? and screen=?", String.valueOf(id), "three").find(LocalMediaTable.class);
    }

    public void setListThree(List<LocalMediaTable> listThree) {
        this.listThree = listThree;
        if (listThree != null) {
            LitePal.saveAll(listThree);
        }
    }

    public List<LocalMediaTable> getListFour() {
        return LitePal.where("programtable_id=? and screen=?", String.valueOf(id), "four").find(LocalMediaTable.class);
    }

    public void setListFour(List<LocalMediaTable> listFour) {
        this.listFour = listFour;
        if (listFour != null) {
            LitePal.saveAll(listFour);
        }
    }

    public List<LocalMediaTable> getMusicList() {
        return LitePal.where("programtable_id=? and screen=?", String.valueOf(id), "music").find(LocalMediaTable.class);
    }

    public void setMusicList(List<LocalMediaTable> musicList) {
        this.musicList = musicList;
        if (musicList != null) {
            LitePal.saveAll(musicList);
        }
    }

    public SettingsTable getSettings() {
        return LitePal.find(SettingsTable.class, id);
    }

    public void setSettings(SettingsTable settings) {
        this.settings = settings;
        if (settings != null) {
            settings.save();
        }
    }

    @Override
    public String toString() {
        return "ProgramTable{" +
                "id=" + id +
                ", programName='" + programName + '\'' +
                ", mouldImg=" + mouldImg +
                ", mouldPosition=" + mouldPosition +
                '}';
    }
}
