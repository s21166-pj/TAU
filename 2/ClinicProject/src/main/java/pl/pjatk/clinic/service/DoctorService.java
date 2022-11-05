package pl.pjatk.clinic.service;


import org.springframework.stereotype.Service;
import pl.pjatk.clinic.exception.DoctorException;
import pl.pjatk.clinic.model.Doctor;
import pl.pjatk.clinic.model.Patient;
import pl.pjatk.clinic.repository.DoctorRepository;
import pl.pjatk.clinic.repository.PatientRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    DoctorRepository doctorRepository;
    PatientRepository patientRepository;

    public DoctorService(DoctorRepository doctorRepository, PatientRepository patientRepository) {
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    public List<Doctor> findAll() throws DoctorException {
        if (doctorRepository.findAll().isEmpty()) {
            throw new DoctorException("There are no doctors");
        }
        return doctorRepository.findAll();
    }

    public Optional<List<Patient>> listOfPatients(int id) throws DoctorException {
        Optional<Doctor> doctorId = this.findById(id);
        if (!doctorId.get().getPatientList().isEmpty()) {
            return Optional.ofNullable(doctorId.get().getPatientList());
        } else {
            throw new DoctorException("There are no patients assigned to a doctor!");
        }
    }

    public Optional<Doctor> findById(int id) throws DoctorException {
        if (id < 1) {
            throw new DoctorException("There are no negative IDs");
        } else if (doctorRepository.findById(id).isEmpty()) {
            throw new DoctorException("No doctor with ID: " + id);
        } else {
            return doctorRepository.findById(id);
        }
    }

    public Doctor update(int id, Doctor updatedDoctor) throws DoctorException {
        Optional<Doctor> doctorOptional = this.findById(id);
        if (doctorOptional.isPresent()) {
            Doctor doctor = doctorOptional.get();
            doctor.setName(updatedDoctor.getName());
            doctor.setSurname(updatedDoctor.getSurname());
            return doctorRepository.save(doctor);
        } else {
            return null;
        }
    }

    public void deleteById(int id) throws DoctorException {
        if (this.findById(id).isPresent()) {
            doctorRepository.deleteById(id);
        } else {
            throw new DoctorException("There is no such Doctor to delete");
        }
    }

    public void deleteAll() throws DoctorException {
        if (doctorRepository.findAll().isEmpty()) {
            throw new DoctorException("Repository is empty");
        } else {
            doctorRepository.deleteAll();
        }
    }

    public Doctor save(Doctor doctor) {
        return doctorRepository.save(doctor);
    }
}
