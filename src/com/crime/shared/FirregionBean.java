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
public class FirregionBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer firregionid;
    private String regionname;
    private Collection<FirBean> firCollection;

    public FirregionBean() {
    }

    public FirregionBean(Integer firregionid) {
        this.firregionid = firregionid;
    }

    public FirregionBean(Integer firregionid, String regionname) {
        this.firregionid = firregionid;
        this.regionname = regionname;
    }

    public Integer getFirregionid() {
        return firregionid;
    }

    public void setFirregionid(Integer firregionid) {
        this.firregionid = firregionid;
    }

    public String getRegionname() {
        return regionname;
    }

    public void setRegionname(String regionname) {
        this.regionname = regionname;
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
        hash += (firregionid != null ? firregionid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FirregionBean)) {
            return false;
        }
        FirregionBean other = (FirregionBean) object;
        if ((this.firregionid == null && other.firregionid != null) || (this.firregionid != null && !this.firregionid.equals(other.firregionid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Firregion[ firregionid=" + firregionid + " ]";
    }
    
}
