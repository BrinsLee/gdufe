package com.brin.gdufe.customView;

public class ListItem {

    private String  content;
    private int image;
    private int icon;
    private String version_info;
    private String cash;

    public ListItem(String content, int image,int icon) {
        this.content = content;
        this.image = image;
        this.icon = icon;
    }

    public ListItem(String content, int image,int icon,String cash) {
        this.content = content;
        this.image = image;
        this.icon = icon;
        this.cash = cash;
    }

    public ListItem(String content,int icon, String version_info){
        this.content = content;
        this.icon = icon;
        this.version_info = version_info;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setVersion_info(String version_info) {
        this.version_info = version_info;
    }

    public String getVersion_info() {
        return version_info;
    }

    public void setIcon(int icon){
        this.icon = icon;
    }
    public int getIcon(){
        return icon;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }
}
