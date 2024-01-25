package med.voll.api.domain.appointment.validation.scheduling;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import java.time.Duration;

import med.voll.api.domain.ValidationException;
import med.voll.api.domain.appointment.AppointmentSchedulingData;

@Component("ValidateAppointmentSchedulingAdvanceTime")
public class AdvanceTimeValidator implements AppointmentScheduleValidator {
    public void validate (AppointmentSchedulingData data) {
        var appointmentDate = data.date();
        var now = LocalDateTime.now();
        var differenceInMinutes = Duration.between(now, appointmentDate).toMinutes();

        if (differenceInMinutes < 30) {
            throw new ValidationException("A consulta deve ser agendada com pelo menos 30 minutos de antecedência");
        }
    }
}
