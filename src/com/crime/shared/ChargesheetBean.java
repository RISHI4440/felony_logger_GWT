/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crime.shared;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

public class ChargesheetBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer chargesheetid;
    private Date chsdate;
    private String infoname;
    private String infoadd;
    private String infoocc;
    private String infoperticular;
    private String accusname;
    private String accaddress;
    private Collection<PrisonerBean> prisonerCollection;
    private FirBean firid;

    public ChargesheetBean() {
    }

    public ChargesheetBean(Integer chargesheetid) {
        this.chargesheetid = chargesheetid;
    }

    public ChargesheetBean(Integer chargesheetid, Date chsdate, String infoname, String infoadd, String infoocc, String infoperticular, String accusname, String accaddress) {
        this.chargesheetid = chargesheetid;
        this.chsdate = chsdate;
        this.infoname = infoname;
        this.infoadd = infoadd;
        this.infoocc = infoocc;
        this.infoperticular = infoperticular;
        this.accusname = accusname;
        this.accaddress = accaddress;
    }

    public Integer getChargesheetid() {
        return chargesheetid;
    }

    public void setChargesheetid(Integer chargesheetid) {
        this.chargesheetid = chargesheetid;
    }

    public Date getChsdate() {
        return chsdate;
    }

    public void setChsdate(Date chsdate) {
        this.chsdate = chsdate;
    }

    public String getInfoname() {
        return infoname;
    }

    public void setInfoname(String infoname) {
        this.infoname = infoname;
    }

    public String getInfoadd() {
        return infoadd;
    }

    public void setInfoadd(String infoadd) {
        this.infoadd = infoadd;
    }

    public String getInfoocc() {
        return infoocc;
    }

    public void setInfoocc(String infoocc) {
        this.infoocc = infoocc;
    }

    public String getInfoperticular() {
        return infoperticular;
    }

    public void setInfoperticular(String infoperticular) {
        this.infoperticular = infoperticular;
    }

    public String getAccusname() {
        return accusname;
    }

    public void setAccusname(String accusname) {
        this.accusname = accusname;
    }

    public String getAccaddress() {
        return accaddress;
    }

    public void setAccaddress(String accaddress) {
        this.accaddress = accaddress;
    }

    
    public Collection<PrisonerBean> getPrisonerCollection() {
        return prisonerCollection;
    }

    public void setPrisonerCollection(Collection<PrisonerBean> prisonerCollection) {
        this.prisonerCollection = prisonerCollection;
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
        hash += (chargesheetid != null ? chargesheetid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChargesheetBean)) {
            return false;
        }
        ChargesheetBean other = (ChargesheetBean) object;
        if ((this.chargesheetid == null && other.chargesheetid != null) || (this.chargesheetid != null && !this.chargesheetid.equals(other.chargesheetid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Chargesheet[ chargesheetid=" + chargesheetid + " ]";
    }
    
}
