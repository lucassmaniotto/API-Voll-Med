package med.voll.api.domain.doctor;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Page<Doctor> findAllByActiveTrue(@NonNull Pageable pageable);

    @Query("""
            SELECT d FROM doctors d
            WHERE d.active = 1
            AND d.specialty = :
            AND NOT IN (
                SELECT a.id_doctor FROM appointments a
                WHERE a.date = :date
            )
            ORDER BY RAND()
            LIMIT 1
            """)
    Doctor chooseRandomDoctorAvailable(Specialty specialty, @NotNull @Future LocalDateTime date);

}
