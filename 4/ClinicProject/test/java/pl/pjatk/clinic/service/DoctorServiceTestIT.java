package pl.pjatk.clinic.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pl.pjatk.clinic.exception.DoctorException;
import pl.pjatk.clinic.exception.PatientException;
import pl.pjatk.clinic.model.Doctor;
import pl.pjatk.clinic.model.Patient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class DoctorServiceTestIT {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @AfterEach
    void afterEach() {
        try {
            doctorService.deleteAll();
        } catch (DoctorException dex) {
            dex.printStackTrace();
        }
    }

    @Test
    void shouldFindAllBeEmpty() {
        assertThrows(DoctorException.class, () -> doctorService.findAll(), "There are no doctors");
    }

    @Test
    void shouldFindAll() throws DoctorException {
        Doctor doctor = new Doctor("Jacek", "Soplica", new ArrayList<>());
        doctorService.save(doctor);
        List<Doctor> listOfDoctors = doctorService.findAll();
        assertThat(listOfDoctors).isNotEmpty();
    }

    @Test
    void shouldListListOfPatients() throws DoctorException, PatientException {
        Doctor doctor = new Doctor("Jacek", "Soplica", new ArrayList<>());
        Patient patient = new Patient("Pablo", "Badyliano", "92092363677", LocalDate.parse("2021-02-13"), doctor);
        Patient patient2 = new Patient("Piotro", "Badolio", "95042614625", LocalDate.parse("2021-02-27"), doctor);
//        patientService.save(patient);
//        patientService.save(patient2);

        doctor.getPatientList().add(patient);
        doctor.getPatientList().add(patient2);
        doctorService.save(doctor);

        List<Patient> patientList = doctorService.listOfPatients(doctor.getId()).get();

        assertThat(patientList).isNotEmpty();
        assertThat(patientList.size()).isEqualTo(doctor.getPatientList().size());
    }

    @Test
    void shouldlistOfPatientsThrowException() {
        Doctor doctor = new Doctor("Jacek", "Soplica", new ArrayList<>());
        doctorService.save(doctor);
        assertThrows(DoctorException.class,
                () -> doctorService.listOfPatients(doctor.getId()),
                "There are no patients assigned to a doctor!");
    }

    @Test
    void shouldFindById() throws DoctorException {
        Doctor doctor = new Doctor("Jacek", "Soplica", new ArrayList<>());
        doctorService.save(doctor);
        Doctor doctorById = doctorService.findById(doctor.getId()).get();
        assertThat(doctorById.getId()).isEqualTo(doctor.getId());
    }

    @Test
    void shouldFindByIdNegativeIdThrowException() {
        int negativeId = -1;
        assertThrows(DoctorException.class,
                () -> doctorService.findById(negativeId), "There are no negative IDs");
    }

    @Test
    void shouldFindByIdNoFoundMatchThrowException() {
        int notDoctorId = 101;
        assertThrows(DoctorException.class,
                () -> doctorService.findById(notDoctorId), "No doctor with ID: " + notDoctorId);
    }

    @Test
    void shouldUpdateCorrectly() throws DoctorException {
        Doctor doctor = new Doctor("Jacek", "Soplica", new ArrayList<>());
        doctorService.save(doctor);
        Doctor expected = new Doctor("Testowy", "Tester", new ArrayList<>());
        doctorService.update(doctor.getId(), expected);

        assertThat(expected.getName()).isEqualTo(doctor.getName());
        assertThat(expected.getSurname()).isEqualTo(doctor.getSurname());
    }

    @Test
    void shoudDeleteByIdCorrectly() throws DoctorException {
        Doctor doctor = new Doctor("Jacek", "Soplica", new ArrayList<>());
        Doctor doctorSecond = new Doctor("Tomasz", "Zgrzyb", new ArrayList<>());
        doctorService.save(doctor);
        doctorService.save(doctorSecond);
        doctorService.deleteById(doctorSecond.getId());

        assertThrows(DoctorException.class,
                () -> doctorService.findById(doctorSecond.getId()), "No doctor with ID: " + doctorSecond.getId());
    }

    @Test
    void shouldDeleteByIdNotMatchThrowException() {
        int notDoctorId = 100;
        assertThrows(DoctorException.class,
                () -> doctorService.deleteById(notDoctorId), "No doctor with ID: " + notDoctorId);
    }

    @Test
    void shouldDeleteAllCorrectly() throws DoctorException {
        Doctor doctor = new Doctor("Jacek", "Soplica", new ArrayList<>());
        Doctor doctor2 = new Doctor("Tomasz", "Zgrzyb", new ArrayList<>());
        doctorService.save(doctor);
        doctorService.save(doctor2);

        doctorService.deleteAll();
        assertThrows(DoctorException.class,
                () -> doctorService.deleteAll(), "Repository is empty");
    }
}