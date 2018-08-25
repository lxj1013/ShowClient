package cn.com.hisistar.showclient.transfer;

import java.io.Serializable;

/**
 * @author lxj
 * @date on 2018/8/14
 */
public class SettingsTransfer implements Serializable {

    private int mouldLandscapeMode = 0;

    private int mouldPortraitMode = 0;

    public SettingsTransfer() {
    }

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

    @Override
    public String toString() {
        return "SettingsTransfer{" +
                "mouldLandscapeMode=" + mouldLandscapeMode +
                ", mouldPortraitMode=" + mouldPortraitMode +
                '}';
    }
}
