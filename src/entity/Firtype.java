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
    @UniqueConstraint(columnNames = {"TYPENAME"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Firtype.findAll", query = "SELECT f FROM Firtype f"),
    @NamedQuery(name = "Firtype.findByFirtypeid", query = "SELECT f FROM Firtype f WHERE f.firtypeid = :firtypeid"),
    @NamedQuery(name = "Firtype.findByTypename", query = "SELECT f FROM Firtype f WHERE f.typename = :typename")})
public class Firtype implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer firtypeid;
    @Basic(optional = false)
    @Column(nullable = false, length = 30)
    private String typename;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "firtypeid")
    private Collection<Fir> firCollection;

    public Firtype() {
    }

    public Firtype(Integer firtypeid) {
        this.firtypeid = firtypeid;
    }

    public Firtype(Integer firtypeid, String typename) {
        this.firtypeid = firtypeid;
        this.typename = typename;
    }

    public Integer getFirtypeid() {
        return firtypeid;
    }

    public void setFirtypeid(Integer firtypeid) {
        this.firtypeid = firtypeid;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
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
        hash += (firtypeid != null ? firtypeid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Firtype)) {
            return false;
        }
        Firtype other = (Firtype) object;
        if ((this.firtypeid == null && other.firtypeid != null) || (this.firtypeid != null && !this.firtypeid.equals(other.firtypeid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Firtype[ firtypeid=" + firtypeid + " ]";
    }
    
}
