/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crime.shared;

import java.io.Serializable;
import java.util.Collection;
public class CrimetypeBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer crimetypeid;
    private String crimename;
    private Collection<CriminalBean> criminalCollection;
    private Collection<HostoryBean> hostoryCollection;
    private Collection<FirBean> firCollection;

    public CrimetypeBean() {
    }

    public CrimetypeBean(Integer crimetypeid) {
        this.crimetypeid = crimetypeid;
    }

    public CrimetypeBean(Integer crimetypeid, String crimename) {
        this.crimetypeid = crimetypeid;
        this.crimename = crimename;
    }

    public Integer getCrimetypeid() {
        return crimetypeid;
    }

    public void setCrimetypeid(Integer crimetypeid) {
        this.crimetypeid = crimetypeid;
    }

    public String getCrimename() {
        return crimename;
    }

    public void setCrimename(String crimename) {
        this.crimename = crimename;
    }

   
    public Collection<CriminalBean> getCriminalCollection() {
        return criminalCollection;
    }

    public void setCriminalCollection(Collection<CriminalBean> criminalCollection) {
        this.criminalCollection = criminalCollection;
    }

    
    public Collection<HostoryBean> getHostoryCollection() {
        return hostoryCollection;
    }

    public void setHostoryCollection(Collection<HostoryBean> hostoryCollection) {
        this.hostoryCollection = hostoryCollection;
    }

   
    public Collection<FirBean> getFirCollection() {
        return firCollection;
    }

    public void setFirCollection(Collection<FirBean> firCollection) {
        this.firCollection = firCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (crimetypeid != null ? crimetypeid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CrimetypeBean)) {
            return false;
        }
        CrimetypeBean other = (CrimetypeBean) object;
        if ((this.crimetypeid == null && other.crimetypeid != null) || (this.crimetypeid != null && !this.crimetypeid.equals(other.crimetypeid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Crimetype[ crimetypeid=" + crimetypeid + " ]";
    }
    
}
