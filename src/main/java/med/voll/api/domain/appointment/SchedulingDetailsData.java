package med.voll.api.domain.appointment;

import java.time.LocalDateTime;

public record SchedulingDetailsData(Long id, Long idDoctor, Long idPatient, LocalDateTime date) {
}
