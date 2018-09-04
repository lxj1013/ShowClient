package cn.com.hisistar.showclient.program;

import com.luck.picture.lib.entity.LocalMedia;


import java.util.List;

import cn.com.hisistar.showclient.transfer.SettingsTransfer;

public class Program {
    private String programName;
    private int mouldImg;
    private int mouldPosition;

    private List<LocalMedia> mainList;
    private List<LocalMedia> listTwo;
    private List<LocalMedia> listThree;
    private List<LocalMedia> listFour;
    private List<LocalMedia> musicList;

    private SettingsTransfer settings;

    public Program(String programName, int mouldImg) {
        this.programName = programName;
        this.mouldImg = mouldImg;
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

    public List<LocalMedia> getMainList() {
        return mainList;
    }

    public void setMainList(List<LocalMedia> mainList) {
        this.mainList = mainList;
    }

    public List<LocalMedia> getListTwo() {
        return listTwo;
    }

    public void setListTwo(List<LocalMedia> listTwo) {
        this.listTwo = listTwo;
    }

    public List<LocalMedia> getListThree() {
        return listThree;
    }

    public void setListThree(List<LocalMedia> listThree) {
        this.listThree = listThree;
    }

    public List<LocalMedia> getListFour() {
        return listFour;
    }

    public void setListFour(List<LocalMedia> listFour) {
        this.listFour = listFour;
    }

    public List<LocalMedia> getMusicList() {
        return musicList;
    }

    public void setMusicList(List<LocalMedia> musicList) {
        this.musicList = musicList;
    }

    public SettingsTransfer getSettings() {
        return settings;
    }

    public void setSettings(SettingsTransfer settings) {
        this.settings = settings;
    }

    @Override
    public String toString() {
        return "Program{" +
                "programName='" + programName + '\'' +
                ", mouldImg=" + mouldImg +
                ", mouldPosition=" + mouldPosition +
                ", mainList=" + mainList +
                ", listTwo=" + listTwo +
                ", listThree=" + listThree +
                ", listFour=" + listFour +
                ", musicList=" + musicList +
                ", settings=" + settings +
                '}';
    }
}
