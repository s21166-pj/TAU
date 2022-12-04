package pl.pjatk.clinic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Patient> patientList = new ArrayList<>();

    public Doctor() {
    }

    public Doctor(int id) {
        this.id = id;
    }

    public Doctor(String name, String surname, List<Patient> patientList) {
        this.name = name;
        this.surname = surname;
        this.patientList = patientList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<Patient> getPatientList() {
        return patientList;
    }

    public void setPatientList(List<Patient> patientList) {
        this.patientList = patientList;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
