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
    @NamedQuery(name = "Hostory.findAll", query = "SELECT h FROM Hostory h"),
    @NamedQuery(name = "Hostory.findByHistoryid", query = "SELECT h FROM Hostory h WHERE h.historyid = :historyid"),
    @NamedQuery(name = "Hostory.findByCrimeno", query = "SELECT h FROM Hostory h WHERE h.crimeno = :crimeno"),
    @NamedQuery(name = "Hostory.findByDateofocc", query = "SELECT h FROM Hostory h WHERE h.dateofocc = :dateofocc"),
    @NamedQuery(name = "Hostory.findByPlaceofocc", query = "SELECT h FROM Hostory h WHERE h.placeofocc = :placeofocc"),
    @NamedQuery(name = "Hostory.findByDescription", query = "SELECT h FROM Hostory h WHERE h.description = :description")})
public class Hostory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer historyid;
    @Basic(optional = false)
    @Column(nullable = false)
    private int crimeno;
    @Basic(optional = false)
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateofocc;
    @Basic(optional = false)
    @Column(nullable = false, length = 50)
    private String placeofocc;
    @Basic(optional = false)
    @Column(nullable = false, length = 150)
    private String description;
    @JoinColumn(name = "PRISONERID", referencedColumnName = "PRISONERID", nullable = false)
    @ManyToOne(optional = false)
    private Prisoner prisonerid;
    @JoinColumn(name = "CRIMETYPEID", referencedColumnName = "CRIMETYPEID", nullable = false)
    @ManyToOne(optional = false)
    private Crimetype crimetypeid;

    public Hostory() {
    }

    public Hostory(Integer historyid) {
        this.historyid = historyid;
    }

    public Hostory(Integer historyid, int crimeno, Date dateofocc, String placeofocc, String description) {
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

    public Prisoner getPrisonerid() {
        return prisonerid;
    }

    public void setPrisonerid(Prisoner prisonerid) {
        this.prisonerid = prisonerid;
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
        hash += (historyid != null ? historyid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Hostory)) {
            return false;
        }
        Hostory other = (Hostory) object;
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
