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
    @NamedQuery(name = "Criminal.findAll", query = "SELECT c FROM Criminal c"),
    @NamedQuery(name = "Criminal.findByCriminalid", query = "SELECT c FROM Criminal c WHERE c.criminalid = :criminalid"),
    @NamedQuery(name = "Criminal.findByName", query = "SELECT c FROM Criminal c WHERE c.name = :name"),
    @NamedQuery(name = "Criminal.findByNickname", query = "SELECT c FROM Criminal c WHERE c.nickname = :nickname"),
    @NamedQuery(name = "Criminal.findByAge", query = "SELECT c FROM Criminal c WHERE c.age = :age"),
    @NamedQuery(name = "Criminal.findByGender", query = "SELECT c FROM Criminal c WHERE c.gender = :gender"),
    @NamedQuery(name = "Criminal.findByOccupation", query = "SELECT c FROM Criminal c WHERE c.occupation = :occupation"),
    @NamedQuery(name = "Criminal.findByAddress", query = "SELECT c FROM Criminal c WHERE c.address = :address"),
    @NamedQuery(name = "Criminal.findByDescription", query = "SELECT c FROM Criminal c WHERE c.description = :description"),
    @NamedQuery(name = "Criminal.findByIsmostwanted", query = "SELECT c FROM Criminal c WHERE c.ismostwanted = :ismostwanted")})
public class Criminal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer criminalid;
    @Basic(optional = false)
    @Column(nullable = false, length = 30)
    private String name;
    @Basic(optional = false)
    @Column(nullable = false, length = 30)
    private String nickname;
    @Basic(optional = false)
    @Column(nullable = false)
    private int age;
    @Basic(optional = false)
    @Column(nullable = false, length = 6)
    private String gender;
    @Column(length = 30)
    private String occupation;
    @Basic(optional = false)
    @Column(nullable = false, length = 100)
    private String address;
    @Column(length = 150)
    private String description;
    @Basic(optional = false)
    @Column(nullable = false)
    private int ismostwanted;
    @JoinColumn(name = "CRIMETYPEID", referencedColumnName = "CRIMETYPEID", nullable = false)
    @ManyToOne(optional = false)
    private Crimetype crimetypeid;

    public Criminal() {
    }

    public Criminal(Integer criminalid) {
        this.criminalid = criminalid;
    }

    public Criminal(Integer criminalid, String name, String nickname, int age, String gender, String address, int ismostwanted) {
        this.criminalid = criminalid;
        this.name = name;
        this.nickname = nickname;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.ismostwanted = ismostwanted;
    }

    public Integer getCriminalid() {
        return criminalid;
    }

    public void setCriminalid(Integer criminalid) {
        this.criminalid = criminalid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIsmostwanted() {
        return ismostwanted;
    }

    public void setIsmostwanted(int ismostwanted) {
        this.ismostwanted = ismostwanted;
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
        hash += (criminalid != null ? criminalid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Criminal)) {
            return false;
        }
        Criminal other = (Criminal) object;
        if ((this.criminalid == null && other.criminalid != null) || (this.criminalid != null && !this.criminalid.equals(other.criminalid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Criminal[ criminalid=" + criminalid + " ]";
    }
    
}
