/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crime.shared;

import java.io.Serializable;

/**
 *
 * @author admin
 */
public class FirdetailBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer firdid;
    private String name;
    private int age;
    private String gender;
    private String address;
    private int isregistrar;
    private FirBean firid;

    public FirdetailBean() {
    }

    public FirdetailBean(Integer firdid) {
        this.firdid = firdid;
    }

    public FirdetailBean(Integer firdid, String name, int age, String address, int isregistrar) {
        this.firdid = firdid;
        this.name = name;
        this.age = age;
        this.address = address;
        this.isregistrar = isregistrar;
    }

    public Integer getFirdid() {
        return firdid;
    }

    public void setFirdid(Integer firdid) {
        this.firdid = firdid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getIsregistrar() {
        return isregistrar;
    }

    public void setIsregistrar(int isregistrar) {
        this.isregistrar = isregistrar;
    }

    public FirBean getFirid() {
        return firid;
    }

    public void setFirid(FirBean firid) {
        this.firid = firid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (firdid != null ? firdid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FirdetailBean)) {
            return false;
        }
        FirdetailBean other = (FirdetailBean) object;
        if ((this.firdid == null && other.firdid != null) || (this.firdid != null && !this.firdid.equals(other.firdid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Firdetail[ firdid=" + firdid + " ]";
    }
    
}
