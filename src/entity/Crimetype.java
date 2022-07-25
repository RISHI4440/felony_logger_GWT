/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author admin
 */
@Entity
@Table(catalog = "", schema = "ADMIN", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"CRIMENAME"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Crimetype.findAll", query = "SELECT c FROM Crimetype c"),
    @NamedQuery(name = "Crimetype.findByCrimetypeid", query = "SELECT c FROM Crimetype c WHERE c.crimetypeid = :crimetypeid"),
    @NamedQuery(name = "Crimetype.findByCrimename", query = "SELECT c FROM Crimetype c WHERE c.crimename = :crimename")})
public class Crimetype implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer crimetypeid;
    @Basic(optional = false)
    @Column(nullable = false, length = 30)
    private String crimename;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "crimetypeid")
    private Collection<Criminal> criminalCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "crimetypeid")
    private Collection<Hostory> hostoryCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "crimetypeid")
    private Collection<Fir> firCollection;

    public Crimetype() {
    }

    public Crimetype(Integer crimetypeid) {
        this.crimetypeid = crimetypeid;
    }

    public Crimetype(Integer crimetypeid, String crimename) {
        this.crimetypeid = crimetypeid;
        this.crimename = crimename;
    }

    public Integer getCrimetypeid() {
        return crimetypeid;
    }

    public void setCrimetypeid(Integer crimetypeid) {
        this.crimetypeid = crimetypeid;
    }

    public String getCrimename() {
        return crimename;
    }

    public void setCrimename(String crimename) {
        this.crimename = crimename;
    }

    @XmlTransient
    public Collection<Criminal> getCriminalCollection() {
        return criminalCollection;
    }

    public void setCriminalCollection(Collection<Criminal> criminalCollection) {
        this.criminalCollection = criminalCollection;
    }

    @XmlTransient
    public Collection<Hostory> getHostoryCollection() {
        return hostoryCollection;
    }

    public void setHostoryCollection(Collection<Hostory> hostoryCollection) {
        this.hostoryCollection = hostoryCollection;
    }

    @XmlTransient
    public Collection<Fir> getFirCollection() {
        return firCollection;
    }

    public void setFirCollection(Collection<Fir> firCollection) {
        this.firCollection = firCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (crimetypeid != null ? crimetypeid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Crimetype)) {
            return false;
        }
        Crimetype other = (Crimetype) object;
        if ((this.crimetypeid == null && other.crimetypeid != null) || (this.crimetypeid != null && !this.crimetypeid.equals(other.crimetypeid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Crimetype[ crimetypeid=" + crimetypeid + " ]";
    }
    
}
