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
    @NamedQuery(name = "Prisoner.findAll", query = "SELECT p FROM Prisoner p"),
    @NamedQuery(name = "Prisoner.findByPrisonerid", query = "SELECT p FROM Prisoner p WHERE p.prisonerid = :prisonerid"),
    @NamedQuery(name = "Prisoner.findByFirdate", query = "SELECT p FROM Prisoner p WHERE p.firdate = :firdate"),
    @NamedQuery(name = "Prisoner.findByNickname", query = "SELECT p FROM Prisoner p WHERE p.nickname = :nickname"),
    @NamedQuery(name = "Prisoner.findByFamilyname", query = "SELECT p FROM Prisoner p WHERE p.familyname = :familyname"),
    @NamedQuery(name = "Prisoner.findByIdentitymark", query = "SELECT p FROM Prisoner p WHERE p.identitymark = :identitymark"),
    @NamedQuery(name = "Prisoner.findByHeight", query = "SELECT p FROM Prisoner p WHERE p.height = :height"),
    @NamedQuery(name = "Prisoner.findByWeight", query = "SELECT p FROM Prisoner p WHERE p.weight = :weight"),
    @NamedQuery(name = "Prisoner.findByColor", query = "SELECT p FROM Prisoner p WHERE p.color = :color"),
    @NamedQuery(name = "Prisoner.findByStatus", query = "SELECT p FROM Prisoner p WHERE p.status = :status")})
public class Prisoner implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer prisonerid;
    @Basic(optional = false)
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date firdate;
    @Column(length = 30)
    private String nickname;
    @Column(length = 30)
    private String familyname;
    @Column(length = 30)
    private String identitymark;
    @Column(length = 10)
    private String height;
    @Column(length = 10)
    private String weight;
    @Column(length = 20)
    private String color;
    @Basic(optional = false)
    @Column(nullable = false)
    private int status;
    @JoinColumn(name = "CHARGESHEETID", referencedColumnName = "CHARGESHEETID", nullable = false)
    @ManyToOne(optional = false)
    private Chargesheet chargesheetid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prisonerid")
    private Collection<Hostory> hostoryCollection;

    public Prisoner() {
    }

    public Prisoner(Integer prisonerid) {
        this.prisonerid = prisonerid;
    }

    public Prisoner(Integer prisonerid, Date firdate, int status) {
        this.prisonerid = prisonerid;
        this.firdate = firdate;
        this.status = status;
    }

    public Integer getPrisonerid() {
        return prisonerid;
    }

    public void setPrisonerid(Integer prisonerid) {
        this.prisonerid = prisonerid;
    }

    public Date getFirdate() {
        return firdate;
    }

    public void setFirdate(Date firdate) {
        this.firdate = firdate;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFamilyname() {
        return familyname;
    }

    public void setFamilyname(String familyname) {
        this.familyname = familyname;
    }

    public String getIdentitymark() {
        return identitymark;
    }

    public void setIdentitymark(String identitymark) {
        this.identitymark = identitymark;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Chargesheet getChargesheetid() {
        return chargesheetid;
    }

    public void setChargesheetid(Chargesheet chargesheetid) {
        this.chargesheetid = chargesheetid;
    }

    @XmlTransient
    public Collection<Hostory> getHostoryCollection() {
        return hostoryCollection;
    }

    public void setHostoryCollection(Collection<Hostory> hostoryCollection) {
        this.hostoryCollection = hostoryCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prisonerid != null ? prisonerid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Prisoner)) {
            return false;
        }
        Prisoner other = (Prisoner) object;
        if ((this.prisonerid == null && other.prisonerid != null) || (this.prisonerid != null && !this.prisonerid.equals(other.prisonerid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Prisoner[ prisonerid=" + prisonerid + " ]";
    }
    
}
