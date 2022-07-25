/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crime.shared;

import java.io.Serializable;
import java.util.Date;

public class HostoryBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer historyid;
    private int crimeno;
    private Date dateofocc;
    private String placeofocc;
    private String description;
    private PrisonerBean prisonerid;
    private CrimetypeBean crimetypeid;

    public HostoryBean() {
    }

    public HostoryBean(Integer historyid) {
        this.historyid = historyid;
    }

    public HostoryBean(Integer historyid, int crimeno, Date dateofocc, String placeofocc, String description) {
        this.historyid = historyid;
        this.crimeno = crimeno;
        this.dateofocc = dateofocc;
        this.placeofocc = placeofocc;
        this.description = description;
    }

    public Integer getHistoryid() {
        return historyid;
    }

    public void setHistoryid(Integer historyid) {
        this.historyid = historyid;
    }

    public int getCrimeno() {
        return crimeno;
    }

    public void setCrimeno(int crimeno) {
        this.crimeno = crimeno;
    }

    public Date getDateofocc() {
        return dateofocc;
    }

    public void setDateofocc(Date dateofocc) {
        this.dateofocc = dateofocc;
    }

    public String getPlaceofocc() {
        return placeofocc;
    }

    public void setPlaceofocc(String placeofocc) {
        this.placeofocc = placeofocc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PrisonerBean getPrisonerid() {
        return prisonerid;
    }

    public void setPrisonerid(PrisonerBean prisonerid) {
        this.prisonerid = prisonerid;
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
        hash += (historyid != null ? historyid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HostoryBean)) {
            return false;
        }
        HostoryBean other = (HostoryBean) object;
        if ((this.historyid == null && other.historyid != null) || (this.historyid != null && !this.historyid.equals(other.historyid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Hostory[ historyid=" + historyid + " ]";
    }
    
}
