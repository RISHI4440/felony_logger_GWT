/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.crime.shared;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author admin
 */
public class PostmortamBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer pmid;
    private String reslt;
    private String gender;
    private Date deathdate;
    private String casedesc;
    private String homename;
    private String doctorname;
    private String policestation;
    private FirBean firid;

    public PostmortamBean() {
    }

    public PostmortamBean(Integer pmid) {
        this.pmid = pmid;
    }

    public PostmortamBean(Integer pmid, String reslt, String gender, Date deathdate, String casedesc, String doctorname) {
        this.pmid = pmid;
        this.reslt = reslt;
        this.gender = gender;
        this.deathdate = deathdate;
        this.casedesc = casedesc;
        this.doctorname = doctorname;
    }

    public Integer getPmid() {
        return pmid;
    }

    public void setPmid(Integer pmid) {
        this.pmid = pmid;
    }

    public String getReslt() {
        return reslt;
    }

    public void setReslt(String reslt) {
        this.reslt = reslt;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDeathdate() {
        return deathdate;
    }

    public void setDeathdate(Date deathdate) {
        this.deathdate = deathdate;
    }

    public String getCasedesc() {
        return casedesc;
    }

    public void setCasedesc(String casedesc) {
        this.casedesc = casedesc;
    }

    public String getHomename() {
        return homename;
    }

    public void setHomename(String homename) {
        this.homename = homename;
    }

    public String getDoctorname() {
        return doctorname;
    }

    public void setDoctorname(String doctorname) {
        this.doctorname = doctorname;
    }

    public String getPolicestation() {
        return policestation;
    }

    public void setPolicestation(String policestation) {
        this.policestation = policestation;
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
        hash += (pmid != null ? pmid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PostmortamBean)) {
            return false;
        }
        PostmortamBean other = (PostmortamBean) object;
        if ((this.pmid == null && other.pmid != null) || (this.pmid != null && !this.pmid.equals(other.pmid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Postmortam[ pmid=" + pmid + " ]";
    }
    
}
