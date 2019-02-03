package com.example.mycontacts;

public class Contact {

    int id;


    String name, number, email, organisation,relationship;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Contact(String name, String number, String email, String organisation, String relationship) {
        this.name = name;
        this.number = number;
        this.email = email;
        this.organisation = organisation;
        this.relationship = relationship;
    }


    public Contact(String name, String number, String email, String organisation, String relationship, int id) {
        this.name = name;
        this.number = number;
        this.email = email;
        this.organisation = organisation;
        this.relationship = relationship;
        this.id = id;
    }



    public Contact() {

    }

}