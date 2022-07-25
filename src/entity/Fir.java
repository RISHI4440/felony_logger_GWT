/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author admin
 */
@Entity
@Table(catalog = "", schema = "ADMIN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fir.findAll", query = "SELECT f FROM Fir f"),
    @NamedQuery(name = "Fir.findByFirid", query = "SELECT f FROM Fir f WHERE f.firid = :firid"),
    @NamedQuery(name = "Fir.findByFirdate", query = "SELECT f FROM Fir f WHERE f.firdate = :firdate"),
    @NamedQuery(name = "Fir.findByFirtime", query = "SELECT f FROM Fir f WHERE f.firtime = :firtime"),
    @NamedQuery(name = "Fir.findByPlace", query = "SELECT f FROM Fir f WHERE f.place = :place"),
    @NamedQuery(name = "Fir.findByDistrict", query = "SELECT f FROM Fir f WHERE f.district = :district"),
    @NamedQuery(name = "Fir.findByPolicename", query = "SELECT f FROM Fir f WHERE f.policename = :policename"),
    @NamedQuery(name = "Fir.findByInformantname", query = "SELECT f FROM Fir f WHERE f.informantname = :informantname"),
    @NamedQuery(name = "Fir.findByRecievedtime", query = "SELECT f FROM Fir f WHERE f.recievedtime = :recievedtime"),
    @NamedQuery(name = "Fir.findByInforecieved", query = "SELECT f FROM Fir f WHERE f.inforecieved = :inforecieved"),
    @NamedQuery(name = "Fir.findByStatus", query = "SELECT f FROM Fir f WHERE f.status = :status")})
public class Fir implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer firid;
    @Basic(optional = false)
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date firdate;
    @Basic(optional = false)
    @Column(nullable = false, length = 10)
    private String firtime;
    @Column(length = 30)
    private String place;
    @Basic(optional = false)
    @Column(nullable = false, length = 30)
    private String district;
    @Basic(optional = false)
    @Column(nullable = false, length = 30)
    private String policename;
    @Column(length = 30)
    private String informantname;
    @Basic(optional = false)
    @Column(nullable = false, length = 10)
    private String recievedtime;
    @Basic(optional = false)
    @Column(nullable = false, length = 250)
    private String inforecieved;
    @Basic(optional = false)
    @Column(nullable = false)
    private int status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "firid")
    private Collection<Postmortam> postmortamCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "firid")
    private Collection<Chargesheet> chargesheetCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "firid")
    private Collection<Firdetail> firdetailCollection;
    @JoinColumn(name = "FIRTYPEID", referencedColumnName = "FIRTYPEID", nullable = false)
    @ManyToOne(optional = false)
    private Firtype firtypeid;
    @JoinColumn(name = "FIRREGIONID", referencedColumnName = "FIRREGIONID", nullable = false)
    @ManyToOne(optional = false)
    private Firregion firregionid;
    @JoinColumn(name = "CRIMETYPEID", referencedColumnName = "CRIMETYPEID", nullable = false)
    @ManyToOne(optional = false)
    private Crimetype crimetypeid;

    public Fir() {
    }

    public Fir(Integer firid) {
        this.firid = firid;
    }

    public Fir(Integer firid, Date firdate, String firtime, String district, String policename, String recievedtime, String inforecieved, int status) {
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

    @XmlTransient
    public Collection<Postmortam> getPostmortamCollection() {
        return postmortamCollection;
    }

    public void setPostmortamCollection(Collection<Postmortam> postmortamCollection) {
        this.postmortamCollection = postmortamCollection;
    }

    @XmlTransient
    public Collection<Chargesheet> getChargesheetCollection() {
        return chargesheetCollection;
    }

    public void setChargesheetCollection(Collection<Chargesheet> chargesheetCollection) {
        this.chargesheetCollection = chargesheetCollection;
    }

    @XmlTransient
    public Collection<Firdetail> getFirdetailCollection() {
        return firdetailCollection;
    }

    public void setFirdetailCollection(Collection<Firdetail> firdetailCollection) {
        this.firdetailCollection = firdetailCollection;
    }

    public Firtype getFirtypeid() {
        return firtypeid;
    }

    public void setFirtypeid(Firtype firtypeid) {
        this.firtypeid = firtypeid;
    }

    public Firregion getFirregionid() {
        return firregionid;
    }

    public void setFirregionid(Firregion firregionid) {
        this.firregionid = firregionid;
    }

    public Crimetype getCrimetypeid() {
        return crimetypeid;
    }

    public void setCrimetypeid(Crimetype crimetypeid) {
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
        if (!(object instanceof Fir)) {
            return false;
        }
        Fir other = (Fir) object;
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
