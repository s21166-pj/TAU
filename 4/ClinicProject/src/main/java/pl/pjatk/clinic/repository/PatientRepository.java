package pl.pjatk.clinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pjatk.clinic.model.Patient;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    Optional<Patient> findByPesel(String pesel);
}
