package com.chenghan.keepaccounts.bean;

import java.io.Serializable;

public class DisburseIncome implements Serializable {
    private Integer id;
    private Integer isDisburse;
    private String title;
    private String remark;
    private Double price;
    private String image;
    private Long writeTime;
    private User user;

    public DisburseIncome() {

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public DisburseIncome(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsDisburse() {
        return isDisburse;
    }

    public void setIsDisburse(Integer isDisburse) {
        this.isDisburse = isDisburse;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getWriteTime() {
        return writeTime;
    }

    public void setWriteTime(Long writeTime) {
        this.writeTime = writeTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "DisburseIncome{" +
                "id=" + id +
                ", isDisburse=" + isDisburse +
                ", title='" + title + '\'' +
                ", remark='" + remark + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", writeTime=" + writeTime +
                ", user=" + user +
                '}';
    }
}
