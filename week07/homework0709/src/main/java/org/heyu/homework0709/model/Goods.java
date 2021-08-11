package org.heyu.homework0709.model;

import java.math.BigDecimal;
import java.util.Date;

public class Goods {
    private Long id;

    private String goodCode;

    private String goodName;

    private BigDecimal price;

    private Date crearteTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoodCode() {
        return goodCode;
    }

    public void setGoodCode(String goodCode) {
        this.goodCode = goodCode;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getCrearteTime() {
        return crearteTime;
    }

    public void setCrearteTime(Date crearteTime) {
        this.crearteTime = crearteTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}