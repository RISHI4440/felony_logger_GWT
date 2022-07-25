/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author admin
 */
@Entity
@Table(catalog = "", schema = "ADMIN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Postmortam.findAll", query = "SELECT p FROM Postmortam p"),
    @NamedQuery(name = "Postmortam.findByPmid", query = "SELECT p FROM Postmortam p WHERE p.pmid = :pmid"),
    @NamedQuery(name = "Postmortam.findByReslt", query = "SELECT p FROM Postmortam p WHERE p.reslt = :reslt"),
    @NamedQuery(name = "Postmortam.findByGender", query = "SELECT p FROM Postmortam p WHERE p.gender = :gender"),
    @NamedQuery(name = "Postmortam.findByDeathdate", query = "SELECT p FROM Postmortam p WHERE p.deathdate = :deathdate"),
    @NamedQuery(name = "Postmortam.findByCasedesc", query = "SELECT p FROM Postmortam p WHERE p.casedesc = :casedesc"),
    @NamedQuery(name = "Postmortam.findByHomename", query = "SELECT p FROM Postmortam p WHERE p.homename = :homename"),
    @NamedQuery(name = "Postmortam.findByDoctorname", query = "SELECT p FROM Postmortam p WHERE p.doctorname = :doctorname"),
    @NamedQuery(name = "Postmortam.findByPolicestation", query = "SELECT p FROM Postmortam p WHERE p.policestation = :policestation")})
public class Postmortam implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer pmid;
    @Basic(optional = false)
    @Column(nullable = false, length = 100)
    private String reslt;
    @Basic(optional = false)
    @Column(nullable = false, length = 6)
    private String gender;
    @Basic(optional = false)
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date deathdate;
    @Basic(optional = false)
    @Column(nullable = false, length = 100)
    private String casedesc;
    @Column(length = 30)
    private String homename;
    @Basic(optional = false)
    @Column(nullable = false, length = 30)
    private String doctorname;
    @Column(length = 50)
    private String policestation;
    @JoinColumn(name = "FIRID", referencedColumnName = "FIRID", nullable = false)
    @ManyToOne(optional = false)
    private Fir firid;

    public Postmortam() {
    }

    public Postmortam(Integer pmid) {
        this.pmid = pmid;
    }

    public Postmortam(Integer pmid, String reslt, String gender, Date deathdate, String casedesc, String doctorname) {
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

    public Fir getFirid() {
        return firid;
    }

    public void setFirid(Fir firid) {
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
        if (!(object instanceof Postmortam)) {
            return false;
        }
        Postmortam other = (Postmortam) object;
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
