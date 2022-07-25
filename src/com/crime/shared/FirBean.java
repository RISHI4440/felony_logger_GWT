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
public class FirBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer firid;
    private Date firdate;
    private String firtime;
    private String place;
    private String district;
    private String policename;
    private String informantname;
    private String recievedtime;
    private String inforecieved;
    private int status;
    private Collection<PostmortamBean> postmortamCollection;
    private Collection<ChargesheetBean> chargesheetCollection;
    private Collection<FirdetailBean> firdetailCollection;
    private FirtypeBean firtypeid;
    private FirregionBean firregionid;
    private CrimetypeBean crimetypeid;

    public FirBean() {
    }

    public FirBean(Integer firid) {
        this.firid = firid;
    }

    public FirBean(Integer firid, Date firdate, String firtime, String district, String policename, String recievedtime, String inforecieved, int status) {
        this.firid = firid;
        this.firdate = firdate;
        this.firtime = firtime;
        this.district = district;
        this.policename = policename;
        this.recievedtime = recievedtime;
        this.inforecieved = inforecieved;
        this.status = status;
    }

    public Integer getFirid() {
        return firid;
    }

    public void setFirid(Integer firid) {
        this.firid = firid;
    }

    public Date getFirdate() {
        return firdate;
    }

    public void setFirdate(Date firdate) {
        this.firdate = firdate;
    }

    public String getFirtime() {
        return firtime;
    }

    public void setFirtime(String firtime) {
        this.firtime = firtime;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPolicename() {
        return policename;
    }

    public void setPolicename(String policename) {
        this.policename = policename;
    }

    public String getInformantname() {
        return informantname;
    }

    public void setInformantname(String informantname) {
        this.informantname = informantname;
    }

    public String getRecievedtime() {
        return recievedtime;
    }

    public void setRecievedtime(String recievedtime) {
        this.recievedtime = recievedtime;
    }

    public String getInforecieved() {
        return inforecieved;
    }

    public void setInforecieved(String inforecieved) {
        this.inforecieved = inforecieved;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    
    public Collection<PostmortamBean> getPostmortamCollection() {
        return postmortamCollection;
    }

    public void setPostmortamCollection(Collection<PostmortamBean> postmortamCollection) {
        this.postmortamCollection = postmortamCollection;
    }

   
    public Collection<ChargesheetBean> getChargesheetCollection() {
        return chargesheetCollection;
    }

    public void setChargesheetCollection(Collection<ChargesheetBean> chargesheetCollection) {
        this.chargesheetCollection = chargesheetCollection;
    }

    public Collection<FirdetailBean> getFirdetailCollection() {
        return firdetailCollection;
    }

    public void setFirdetailCollection(Collection<FirdetailBean> firdetailCollection) {
        this.firdetailCollection = firdetailCollection;
    }

    public FirtypeBean getFirtypeid() {
        return firtypeid;
    }

    public void setFirtypeid(FirtypeBean firtypeid) {
        this.firtypeid = firtypeid;
    }

    public FirregionBean getFirregionid() {
        return firregionid;
    }

    public void setFirregionid(FirregionBean firregionid) {
        this.firregionid = firregionid;
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
        hash += (firid != null ? firid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FirBean)) {
            return false;
        }
        FirBean other = (FirBean) object;
        if ((this.firid == null && other.firid != null) || (this.firid != null && !this.firid.equals(other.firid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Fir[ firid=" + firid + " ]";
    }
    
}
