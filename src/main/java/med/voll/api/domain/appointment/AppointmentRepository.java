package med.voll.api.domain.appointment;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Boolean existsByPatientIdAndDateBetween(Long idPatient, LocalDateTime start, LocalDateTime end);

    Boolean existsByDoctorIdAndDateAndCancelReasonIsNull(Long idDoctor, LocalDateTime date);
}
