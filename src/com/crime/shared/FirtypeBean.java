/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crime.shared;

import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author admin
 */
public class FirtypeBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer firtypeid;
    private String typename;
    private Collection<FirBean> firCollection;

    public FirtypeBean() {
    }

    public FirtypeBean(Integer firtypeid) {
        this.firtypeid = firtypeid;
    }

    public FirtypeBean(Integer firtypeid, String typename) {
        this.firtypeid = firtypeid;
        this.typename = typename;
    }

    public Integer getFirtypeid() {
        return firtypeid;
    }

    public void setFirtypeid(Integer firtypeid) {
        this.firtypeid = firtypeid;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
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
        hash += (firtypeid != null ? firtypeid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FirtypeBean)) {
            return false;
        }
        FirtypeBean other = (FirtypeBean) object;
        if ((this.firtypeid == null && other.firtypeid != null) || (this.firtypeid != null && !this.firtypeid.equals(other.firtypeid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Firtype[ firtypeid=" + firtypeid + " ]";
    }
    
}
