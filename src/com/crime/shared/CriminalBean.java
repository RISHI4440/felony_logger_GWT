/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crime.shared;

import java.io.Serializable;

public class CriminalBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer criminalid;
    private String name;
    private String nickname;
    private int age;
    private String gender;
    private String occupation;
    private String address;
    private String description;
    private int ismostwanted;
    private CrimetypeBean crimetypeid;

    public CriminalBean() {
    }

    public CriminalBean(Integer criminalid) {
        this.criminalid = criminalid;
    }

    public CriminalBean(Integer criminalid, String name, String nickname, int age, String gender, String address, int ismostwanted) {
        this.criminalid = criminalid;
        this.name = name;
        this.nickname = nickname;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.ismostwanted = ismostwanted;
    }

    public Integer getCriminalid() {
        return criminalid;
    }

    public void setCriminalid(Integer criminalid) {
        this.criminalid = criminalid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIsmostwanted() {
        return ismostwanted;
    }

    public void setIsmostwanted(int ismostwanted) {
        this.ismostwanted = ismostwanted;
    }

    public CrimetypeBean getCrimetypeid() {
        return crimetypeid;
    }

    public void setCrimetypeid(CrimetypeBean crimetypeid) {
        this.crimetypeid = crimetypeid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (criminalid != null ? criminalid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CriminalBean)) {
            return false;
        }
        CriminalBean other = (CriminalBean) object;
        if ((this.criminalid == null && other.criminalid != null) || (this.criminalid != null && !this.criminalid.equals(other.criminalid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Criminal[ criminalid=" + criminalid + " ]";
    }
    
}
