package cn.meiqu.lainmonitor.bean;

/**
 * Created by Fatel on 16-5-24.
 */
public class HomePage {
    public HomePage(String name, int number) {
        this.name = name;
        this.number = number;
    }

    /**
     * id : 1
     * isShow : 1
     * name : 主页
     * number : 1
     */

    private int id;
    private int isShow;
    private String name;
    private int number;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsShow() {
        return isShow;
    }

    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
