/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author admin
 */
@Entity
@Table(catalog = "", schema = "ADMIN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Firdetail.findAll", query = "SELECT f FROM Firdetail f"),
    @NamedQuery(name = "Firdetail.findByFirdid", query = "SELECT f FROM Firdetail f WHERE f.firdid = :firdid"),
    @NamedQuery(name = "Firdetail.findByName", query = "SELECT f FROM Firdetail f WHERE f.name = :name"),
    @NamedQuery(name = "Firdetail.findByAge", query = "SELECT f FROM Firdetail f WHERE f.age = :age"),
    @NamedQuery(name = "Firdetail.findByGender", query = "SELECT f FROM Firdetail f WHERE f.gender = :gender"),
    @NamedQuery(name = "Firdetail.findByAddress", query = "SELECT f FROM Firdetail f WHERE f.address = :address"),
    @NamedQuery(name = "Firdetail.findByIsregistrar", query = "SELECT f FROM Firdetail f WHERE f.isregistrar = :isregistrar")})
public class Firdetail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer firdid;
    @Basic(optional = false)
    @Column(nullable = false, length = 30)
    private String name;
    @Basic(optional = false)
    @Column(nullable = false)
    private int age;
    @Column(length = 6)
    private String gender;
    @Basic(optional = false)
    @Column(nullable = false, length = 100)
    private String address;
    @Basic(optional = false)
    @Column(nullable = false)
    private int isregistrar;
    @JoinColumn(name = "FIRID", referencedColumnName = "FIRID", nullable = false)
    @ManyToOne(optional = false)
    private Fir firid;

    public Firdetail() {
    }

    public Firdetail(Integer firdid) {
        this.firdid = firdid;
    }

    public Firdetail(Integer firdid, String name, int age, String address, int isregistrar) {
        this.firdid = firdid;
        this.name = name;
        this.age = age;
        this.address = address;
        this.isregistrar = isregistrar;
    }

    public Integer getFirdid() {
        return firdid;
    }

    public void setFirdid(Integer firdid) {
        this.firdid = firdid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getIsregistrar() {
        return isregistrar;
    }

    public void setIsregistrar(int isregistrar) {
        this.isregistrar = isregistrar;
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
        hash += (firdid != null ? firdid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Firdetail)) {
            return false;
        }
        Firdetail other = (Firdetail) object;
        if ((this.firdid == null && other.firdid != null) || (this.firdid != null && !this.firdid.equals(other.firdid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Firdetail[ firdid=" + firdid + " ]";
    }
    
}
