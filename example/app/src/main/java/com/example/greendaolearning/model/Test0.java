package com.example.greendaolearning.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2017/1/3.
 */

@Entity
public class Test0 {
    @Id(autoincrement = true)
    private Long id;
    private String username;
    private long datetime;
    @Generated(hash = 101919321)
    public Test0(Long id, String username, long datetime) {
        this.id = id;
        this.username = username;
        this.datetime = datetime;
    }
    @Generated(hash = 676991735)
    public Test0() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public long getDatetime() {
        return this.datetime;
    }
    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }
   

}
