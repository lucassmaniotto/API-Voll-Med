package med.voll.api.domain.doctor;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import lombok.NonNull;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Page<Doctor> findAllByActiveTrue(@NonNull Pageable pageable);

    @Query("""
                select d from Doctor d
                where 
                d.active = true
                and 
                d.specialty = :specialty
                and 
                d.id not in (
                    select a.doctor.id from Appointment a
                    where 
                    a.date = :date
                )
                order by rand()
                limit 1
        """)
    Doctor chooseAvailableRandomDoctorAtDate(Specialty specialty, LocalDateTime date);

    @Query("""
                select d.active
                from Doctor d
                where 
                d.id = :id
            """)
    Boolean findActiveById(Long id);

}
