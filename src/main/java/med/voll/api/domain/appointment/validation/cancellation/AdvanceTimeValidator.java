package med.voll.api.domain.appointment.validation.cancellation;

import java.time.LocalDateTime;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.domain.appointment.AppointmentCancellationData;
import med.voll.api.domain.appointment.AppointmentRepository;

@Component("ValidateAppointmentCancellationAdvanceTime")
public class AdvanceTimeValidator implements AppointmentCancellationValidator {
    
    @Autowired
    private AppointmentRepository repository;

    @Override
    public void validate(AppointmentCancellationData data) {
        var appointment = repository.getReferenceById(data.idAppointment());
        var now = LocalDateTime.now();
        var differenceInHours = Duration.between(now, appointment.getDate()).toHours();

        if (differenceInHours < 24) {
            throw new ValidationException("A consulta deve ser cancelada com pelo menos 24 horas de antecedência");
        }
    }
}
