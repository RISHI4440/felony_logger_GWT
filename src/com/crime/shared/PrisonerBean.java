/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crime.shared;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author admin
 */
public class PrisonerBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer prisonerid;
    private Date firdate;
    private String nickname;
    private String familyname;
    private String identitymark;
    private String height;
    private String weight;
    private String color;
    private int status;
    private ChargesheetBean chargesheetid;
    private Collection<HostoryBean> hostoryCollection;

    public PrisonerBean() {
    }

    public PrisonerBean(Integer prisonerid) {
        this.prisonerid = prisonerid;
    }

    public PrisonerBean(Integer prisonerid, Date firdate, int status) {
        this.prisonerid = prisonerid;
        this.firdate = firdate;
        this.status = status;
    }

    public Integer getPrisonerid() {
        return prisonerid;
    }

    public void setPrisonerid(Integer prisonerid) {
        this.prisonerid = prisonerid;
    }

    public Date getFirdate() {
        return firdate;
    }

    public void setFirdate(Date firdate) {
        this.firdate = firdate;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFamilyname() {
        return familyname;
    }

    public void setFamilyname(String familyname) {
        this.familyname = familyname;
    }

    public String getIdentitymark() {
        return identitymark;
    }

    public void setIdentitymark(String identitymark) {
        this.identitymark = identitymark;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ChargesheetBean getChargesheetid() {
        return chargesheetid;
    }

    public void setChargesheetid(ChargesheetBean chargesheetid) {
        this.chargesheetid = chargesheetid;
    }

    
    public Collection<HostoryBean> getHostoryCollection() {
        return hostoryCollection;
    }

    public void setHostoryCollection(Collection<HostoryBean> hostoryCollection) {
        this.hostoryCollection = hostoryCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prisonerid != null ? prisonerid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrisonerBean)) {
            return false;
        }
        PrisonerBean other = (PrisonerBean) object;
        if ((this.prisonerid == null && other.prisonerid != null) || (this.prisonerid != null && !this.prisonerid.equals(other.prisonerid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Prisoner[ prisonerid=" + prisonerid + " ]";
    }
    
}
