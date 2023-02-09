package com.chenghan.keepaccounts.bean;

public class LabelType {
    private Integer id;
    private String typeName;
    private Integer kind;
    private String image;

    public LabelType() {

    }

    public LabelType(String typeName, Integer kind, String image) {
        this.typeName = typeName;
        this.kind = kind;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "LabelType{" +
                "id=" + id +
                ", typeName='" + typeName + '\'' +
                ", kind=" + kind +
                ", image='" + image + '\'' +
                '}';
    }
}
