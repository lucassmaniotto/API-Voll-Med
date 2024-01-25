package med.voll.api.domain.appointment.validation.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidationException;
import med.voll.api.domain.appointment.AppointmentSchedulingData;
import med.voll.api.domain.patient.PatientRepository;

@Component
public class ActivePatientValidator implements AppointmentScheduleValidator {
    @Autowired
    private PatientRepository repository;

    public void validate (AppointmentSchedulingData data) {
        if (data.idDoctor() == null )
            return;
        
            var patientIsActive = repository.findActiveById(data.idPatient());
            if (!patientIsActive)
                throw new ValidationException("Consulta não pode ser agendada com paciente excluído");
    
    }
}
