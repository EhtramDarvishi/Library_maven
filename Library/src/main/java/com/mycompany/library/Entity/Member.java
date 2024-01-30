package com.mycompany.library.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String memberName;
    private String phoneNumber;
    private String dateMember;

    // Constructors, getters, and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return memberName;
    }

    public void setUsername(String username) {
        this.memberName = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phonenumber) {
        this.phoneNumber = phonenumber;
    }

    /**
     * @return the dateMember
     */
    public String getDateMember() {
        return dateMember;
    }

    /**
     * @param dateMember the dateMember to set
     */
    public void setDateMember(String dateMember) {
        this.dateMember = dateMember;
    }
}
