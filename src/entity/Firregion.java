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
    @UniqueConstraint(columnNames = {"REGIONNAME"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Firregion.findAll", query = "SELECT f FROM Firregion f"),
    @NamedQuery(name = "Firregion.findByFirregionid", query = "SELECT f FROM Firregion f WHERE f.firregionid = :firregionid"),
    @NamedQuery(name = "Firregion.findByRegionname", query = "SELECT f FROM Firregion f WHERE f.regionname = :regionname")})
public class Firregion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer firregionid;
    @Basic(optional = false)
    @Column(nullable = false, length = 30)
    private String regionname;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "firregionid")
    private Collection<Fir> firCollection;

    public Firregion() {
    }

    public Firregion(Integer firregionid) {
        this.firregionid = firregionid;
    }

    public Firregion(Integer firregionid, String regionname) {
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
        hash += (firregionid != null ? firregionid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Firregion)) {
            return false;
        }
        Firregion other = (Firregion) object;
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
