package pl.pjatk.clinic.service;

import org.springframework.stereotype.Service;
import pl.pjatk.clinic.exception.DoctorException;
import pl.pjatk.clinic.exception.PatientException;
import pl.pjatk.clinic.model.Patient;
import pl.pjatk.clinic.repository.DoctorRepository;
import pl.pjatk.clinic.repository.PatientRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private PatientRepository patientRepository;
    private DoctorRepository doctorRepository;

    public PatientService(PatientRepository patientRepository, DoctorRepository doctorRepository) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    public List<Patient> findAll() throws PatientException {
        if (patientRepository.findAll().isEmpty()) {
            throw new PatientException("There are no patients");
        }
        return patientRepository.findAll();
    }

    public Optional<Patient> findById(int id) throws PatientException {
        if (id < 0) {
            throw new PatientException("There are no negative IDs");
        } else if (patientRepository.findById(id).isEmpty()) {
            throw new PatientException("No patient with ID: " + id);
        } else {
            return patientRepository.findById(id);
        }
    }

    public Optional<Patient> findByPesel(String pesel) throws PatientException {
        if (patientRepository.findByPesel(pesel).isPresent()) {
            return patientRepository.findByPesel(pesel);
        } else {
            throw new PatientException("There is no one with this PESEL");
        }
    }

    public List<Patient> findAllByDate(LocalDate searchDate) throws PatientException {
        List<Patient> patientList = patientRepository.findAll();
        List<Patient> foundPatientList = new ArrayList<>();
        for (Patient patient : patientList) {
            if (patient.getDateOfConsultation().isAfter(searchDate)) {
                foundPatientList.add(patient);
            }
        }

        if (foundPatientList.isEmpty()) {
            throw new PatientException("There are no patients after this date");
        } else {
            return foundPatientList;
        }
    }

    public Patient update(String pesel, Patient updatedPatient) throws PatientException {
        Optional<Patient> patientOptional = patientRepository.findByPesel(pesel);
        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();
            patient.setName(updatedPatient.getName());
            patient.setSurname(updatedPatient.getSurname());
            patient.setDateOfConsultation(updatedPatient.getDateOfConsultation());
            if (doctorRepository.findById(patient.getDoctor().getId()).isEmpty()) {
                throw new PatientException("You cannot save patient without doctor in DataBase");
            } else {
                patient.setDoctor(patient.getDoctor());
                return patientRepository.save(patient);
            }
        } else {
            return null;
        }
    }

    public void deleteById(int id) throws PatientException {
        if (patientRepository.findById(id).isPresent()) {
            patientRepository.deleteById(id);
        } else {
            throw new PatientException("There is no such patient to delete");
        }
    }

    public void deleteAll() throws PatientException {
        if (patientRepository.findAll().isEmpty()) {
            throw new PatientException("Repository is empty");
        } else {
            patientRepository.deleteAll();
        }
    }

    public Patient save(Patient patient) throws PatientException {
        if (patientRepository.findByPesel(patient.getPesel()).isPresent()) {
            throw new PatientException("There is Patient with this PESEL already");
        } else if (doctorRepository.findById(patient.getDoctor().getId()).isEmpty()) {
            throw new PatientException("You cannot save patient without doctor in DataBase");
        } else {
            patientRepository.saveAndFlush(patient);
            return patientRepository.findById(patient.getId()).get();
        }
    }
}
