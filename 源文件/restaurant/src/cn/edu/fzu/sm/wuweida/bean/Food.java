package cn.edu.fzu.sm.wuweida.bean;

import java.sql.Blob;


public class Food {
    private String foodName;
    private double foodPrice;
    private String foodType;// 粤菜,湘菜,甜品
    private Blob foodImg;

    public int getIsPop() {
        return isPop;
    }

    public void setIsPop(int isPop) {
        this.isPop = isPop;
    }

    private int isPop;

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public double getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(double foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public Blob getFoodImg() {
        return foodImg;
    }

    public void setFoodImg(Blob foodImg) {
        this.foodImg = foodImg;
    }

}

