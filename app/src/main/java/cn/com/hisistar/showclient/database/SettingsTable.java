package cn.com.hisistar.showclient.database;

import org.litepal.crud.LitePalSupport;

/**
 * @author lxj
 * @date 2018/9/4
 */
public class SettingsTable extends LitePalSupport {
    private int mouldLandscapeMode = 0;

    private int mouldPortraitMode = 0;

    private String subTitle = "";


    public int getMouldLandscapeMode() {
        return mouldLandscapeMode;
    }

    public void setMouldLandscapeMode(int mouldLandscapeMode) {
        this.mouldLandscapeMode = mouldLandscapeMode;
    }

    public int getMouldPortraitMode() {
        return mouldPortraitMode;
    }

    public void setMouldPortraitMode(int mouldPortraitMode) {
        this.mouldPortraitMode = mouldPortraitMode;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    @Override
    public String toString() {
        return "SettingsTable{" +
                "mouldLandscapeMode=" + mouldLandscapeMode +
                ", mouldPortraitMode=" + mouldPortraitMode +
                ", subTitle='" + subTitle + '\'' +
                '}';
    }
}
