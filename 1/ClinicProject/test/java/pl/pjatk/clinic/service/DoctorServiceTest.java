package pl.pjatk.clinic.service;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjatk.clinic.exception.DoctorException;
import pl.pjatk.clinic.model.Doctor;
import pl.pjatk.clinic.model.Patient;
import pl.pjatk.clinic.repository.DoctorRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private DoctorService doctorService;

    Doctor doctor;
    Doctor doctor2;
    Patient patient;
    Patient patient2;
    Patient patient3;

    @BeforeEach
    void createObjects() {
        doctor = new Doctor("Jacek", "Soplica", new ArrayList<>());
        doctor.setId(1);
        doctor2 = new Doctor("Tomasz", "Zgrzyb", new ArrayList<>());
        doctor2.setId(2);

        patient = new Patient("Pablo", "Badyliano", "92092363677", LocalDate.parse("2021-02-13"), doctor);
        patient.setId(1);
        patient2 = new Patient("Piotro", "Badolio", "95042614625", LocalDate.parse("2021-02-27"), doctor);
        patient2.setId(2);
        patient3 = new Patient("Agacia", "Wszebcia", "93042233482", LocalDate.parse("2021-05-29"), doctor2);
        patient3.setId(3);

        List<Patient> firstPatients = new ArrayList<>();
        List<Patient> secondPatient = new ArrayList<>();

        firstPatients.add(patient);
        firstPatients.add(patient2);
        secondPatient.add(patient3);

        doctor.setPatientList(firstPatients);
        doctor2.setPatientList(secondPatient);
    }

    @AfterEach
    void afterEach() {
        doctorRepository.deleteAll();
    }

    @Test
    void testFindAllIsEmpty() throws DoctorException {
        Assertions.assertThrows(DoctorException.class, () -> doctorService.findAll());
    }

    @Test
    void testFindAllIsNotEmpty() throws DoctorException {
        //Given
        when(doctorRepository.findAll()).thenReturn(List.of(doctor));
        //When
        List<Doctor> doctorList = doctorService.findAll();
        //Then
        assertThat(doctorList).isNotEmpty();
    }

    @Test
    void testListOfPatientsNotEmpty() throws DoctorException {
        //Given
        when(doctorRepository.findById(doctor.getId())).thenReturn(Optional.of((doctor)));
        List<Patient> patients = doctor.getPatientList();
        //When
        Optional<List<Patient>> patientList = doctorService.listOfPatients(doctor.getId());
        //Then
        assertThat(patientList).isNotEmpty();
        assertThat(patients).isNotEmpty();
        assertThat(patients.size()).isEqualTo(2);
    }

    @Test
    void listOfPatientsNoDoctorThrowException() {
        Assertions.assertThrows(DoctorException.class, () -> doctorService.listOfPatients(patient.getId()));
    }

    @Test
    void testFindByIdWorksCorrectly() throws DoctorException {
        //Given
        when(doctorRepository.findById(doctor.getId())).thenReturn(Optional.of(doctor));
        //When
        Optional<Doctor> doctorById = doctorService.findById(doctor.getId());
        //Then
        assertThat(doctorById).isNotEmpty();
        assertThat(doctorById).isNotNull();
        assertThat(doctorById.get().getId()).isEqualTo(1);
    }

    @Test
    void testFindByIdThrowsException() {
        Assertions.assertThrows(DoctorException.class, () -> doctorService.findById(-1));
    }

    @Test
    void testUpdateCorrectly() throws DoctorException {
        //Given
        when(doctorRepository.findById(doctor.getId())).thenReturn(Optional.of(doctor));
        when(doctorRepository.save(doctor)).thenReturn(doctor);
        //When
        Doctor expected = new Doctor("test", "test2",new ArrayList<>());
        Doctor updated = doctorService.update(doctor.getId(), expected);
        //Then
        assertThat(updated.getName()).isEqualTo(expected.getName());
        assertThat(updated.getSurname()).isEqualTo(expected.getSurname());
    }

    @Test
    void testDeleteByIdThrowsException() {
        Assertions.assertThrows(DoctorException.class, () -> doctorService.deleteById(119));
    }

    @Test
    void testDeleteByIdSuccess() throws DoctorException {
        //Given
        when(doctorRepository.findById(doctor.getId())).thenReturn(Optional.of(doctor));
        //when
        doctorService.deleteById(doctor.getId());
        //Then
        assertThat(doctor);
    }
}
