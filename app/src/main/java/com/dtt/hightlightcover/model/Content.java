package com.dtt.hightlightcover.model;

import java.io.Serializable;

public class Content implements Serializable {
    private String bg;
    private String fontName;
    private String text;
    private String textSize;
    private String textColor;
    private String circleSticker;
    private String circleColor;

    private String sticker;
    private String bgColor;
    private Integer circlePercentage;
    private Boolean backgroundLock;
    private String textMaterail;
    private String materailGroup;
    private String stickerColor;
    private String circleStickerGroup;
    private String stickerGroup;
    private Double stickerPercentage;
    private String stickerMaterail;
    private String circleMaterail;
    private String circleMaterailGroup;

    public String getBg() {
        return bg;
    }
    public void setBg(String bg) {
        this.bg = bg;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTextSize() {
        return textSize;
    }

    public void setTextSize(String textSize) {
        this.textSize = textSize;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getCircleSticker() {
        return circleSticker;
    }

    public void setCircleSticker(String circleSticker) {
        this.circleSticker = circleSticker;
    }

    public String getCircleColor() {
        return circleColor;
    }

    public void setCircleColor(String circleColor) {
        this.circleColor = circleColor;
    }

    public String getSticker() {
        return sticker;
    }

    public void setSticker(String sticker) {
        this.sticker = sticker;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public Integer getCirclePercentage() {
        return circlePercentage;
    }

    public void setCirclePercentage(Integer circlePercentage) {
        this.circlePercentage = circlePercentage;
    }

    public Boolean getBackgroundLock() {
        return backgroundLock;
    }

    public void setBackgroundLock(Boolean backgroundLock) {
        this.backgroundLock = backgroundLock;
    }

    public String getTextMaterail() {
        return textMaterail;
    }

    public void setTextMaterail(String textMaterail) {
        this.textMaterail = textMaterail;
    }

    public String getMaterailGroup() {
        return materailGroup;
    }

    public void setMaterailGroup(String materailGroup) {
        this.materailGroup = materailGroup;
    }

    public String getStickerColor() {
        return stickerColor;
    }

    public void setStickerColor(String stickerColor) {
        this.stickerColor = stickerColor;
    }

    public String getCircleStickerGroup() {
        return circleStickerGroup;
    }

    public void setCircleStickerGroup(String circleStickerGroup) {
        this.circleStickerGroup = circleStickerGroup;
    }

    public String getStickerGroup() {
        return stickerGroup;
    }

    public void setStickerGroup(String stickerGroup) {
        this.stickerGroup = stickerGroup;
    }

    public Double getStickerPercentage() {
        return stickerPercentage;
    }

    public void setStickerPercentage(Double stickerPercentage) {
        this.stickerPercentage = stickerPercentage;
    }

    public String getStickerMaterail() {
        return stickerMaterail;
    }

    public void setStickerMaterail(String stickerMaterail) {
        this.stickerMaterail = stickerMaterail;
    }

    public String getCircleMaterail() {
        return circleMaterail;
    }

    public void setCircleMaterail(String circleMaterail) {
        this.circleMaterail = circleMaterail;
    }

    public String getCircleMaterailGroup() {
        return circleMaterailGroup;
    }

    public void setCircleMaterailGroup(String circleMaterailGroup) {
        this.circleMaterailGroup = circleMaterailGroup;
    }

    @Override
    public String toString() {
        return "Content{" +
                "bg='" + bg + '\'' +
                ", fontName='" + fontName + '\'' +
                ", text='" + text + '\'' +
                ", textSize='" + textSize + '\'' +
                ", textColor='" + textColor + '\'' +
                ", circleSticker='" + circleSticker + '\'' +
                ", circleColor='" + circleColor + '\'' +
                ", sticker='" + sticker + '\'' +
                ", bgColor='" + bgColor + '\'' +
                ", circlePercentage=" + circlePercentage +
                ", backgroundLock=" + backgroundLock +
                ", textMaterail='" + textMaterail + '\'' +
                ", materailGroup='" + materailGroup + '\'' +
                ", stickerColor='" + stickerColor + '\'' +
                ", circleStickerGroup='" + circleStickerGroup + '\'' +
                ", stickerGroup='" + stickerGroup + '\'' +
                ", stickerPercentage=" + stickerPercentage +
                ", stickerMaterail='" + stickerMaterail + '\'' +
                ", circleMaterail='" + circleMaterail + '\'' +
                ", circleMaterailGroup='" + circleMaterailGroup + '\'' +
                '}';
    }
}
