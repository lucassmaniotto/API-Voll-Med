package med.voll.api.domain.appointment.validation.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidationException;
import med.voll.api.domain.appointment.AppointmentSchedulingData;
import med.voll.api.domain.doctor.DoctorRepository;

@Component
public class ActiveDoctorValidator implements AppointmentScheduleValidator {
    @Autowired
    private DoctorRepository repository;

    public void validate (AppointmentSchedulingData data) {
        if (data.idDoctor() == null )
            return;
        
            var doctorIsActive = repository.findActiveById(data.idDoctor());
            if (!doctorIsActive)
                throw new ValidationException("Consulta não pode ser agendada com médico inativo");
    
    }
}
