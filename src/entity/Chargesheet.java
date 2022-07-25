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
    @NamedQuery(name = "Chargesheet.findAll", query = "SELECT c FROM Chargesheet c"),
    @NamedQuery(name = "Chargesheet.findByChargesheetid", query = "SELECT c FROM Chargesheet c WHERE c.chargesheetid = :chargesheetid"),
    @NamedQuery(name = "Chargesheet.findByChsdate", query = "SELECT c FROM Chargesheet c WHERE c.chsdate = :chsdate"),
    @NamedQuery(name = "Chargesheet.findByInfoname", query = "SELECT c FROM Chargesheet c WHERE c.infoname = :infoname"),
    @NamedQuery(name = "Chargesheet.findByInfoadd", query = "SELECT c FROM Chargesheet c WHERE c.infoadd = :infoadd"),
    @NamedQuery(name = "Chargesheet.findByInfoocc", query = "SELECT c FROM Chargesheet c WHERE c.infoocc = :infoocc"),
    @NamedQuery(name = "Chargesheet.findByInfoperticular", query = "SELECT c FROM Chargesheet c WHERE c.infoperticular = :infoperticular"),
    @NamedQuery(name = "Chargesheet.findByAccusname", query = "SELECT c FROM Chargesheet c WHERE c.accusname = :accusname"),
    @NamedQuery(name = "Chargesheet.findByAccaddress", query = "SELECT c FROM Chargesheet c WHERE c.accaddress = :accaddress")})
public class Chargesheet implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer chargesheetid;
    @Basic(optional = false)
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date chsdate;
    @Basic(optional = false)
    @Column(nullable = false, length = 30)
    private String infoname;
    @Basic(optional = false)
    @Column(nullable = false, length = 100)
    private String infoadd;
    @Basic(optional = false)
    @Column(nullable = false, length = 100)
    private String infoocc;
    @Basic(optional = false)
    @Column(nullable = false, length = 150)
    private String infoperticular;
    @Basic(optional = false)
    @Column(nullable = false, length = 30)
    private String accusname;
    @Basic(optional = false)
    @Column(nullable = false, length = 100)
    private String accaddress;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "chargesheetid")
    private Collection<Prisoner> prisonerCollection;
    @JoinColumn(name = "FIRID", referencedColumnName = "FIRID", nullable = false)
    @ManyToOne(optional = false)
    private Fir firid;

    public Chargesheet() {
    }

    public Chargesheet(Integer chargesheetid) {
        this.chargesheetid = chargesheetid;
    }

    public Chargesheet(Integer chargesheetid, Date chsdate, String infoname, String infoadd, String infoocc, String infoperticular, String accusname, String accaddress) {
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

    @XmlTransient
    public Collection<Prisoner> getPrisonerCollection() {
        return prisonerCollection;
    }

    public void setPrisonerCollection(Collection<Prisoner> prisonerCollection) {
        this.prisonerCollection = prisonerCollection;
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
        hash += (chargesheetid != null ? chargesheetid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Chargesheet)) {
            return false;
        }
        Chargesheet other = (Chargesheet) object;
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
