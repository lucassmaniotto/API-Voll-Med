package med.voll.api.domain.appointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.domain.ValidationException;
import med.voll.api.domain.doctor.Doctor;
import med.voll.api.domain.doctor.DoctorRepository;
import med.voll.api.domain.patient.PatientRepository;

@Service
@SuppressWarnings("null")
public class AppointmentSchedule {
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    public void schedule(AppointmentSchedulingData data) {
        if(!patientRepository.existsById(data.idPatient())) {
            throw new ValidationException("Paciente não encontrado");
        }
        if(data.idDoctor() != null && !doctorRepository.existsById(data.idDoctor())) {
            throw new ValidationException("Médico não encontrado");
        }
        
        var patient = patientRepository.getReferenceById(data.idPatient());
        var doctor = chooseDoctor(data);
        var appointment = new Appointment(null, doctor, patient, data.date());
        appointmentRepository.save(appointment);
    }

    
    private Doctor chooseDoctor(AppointmentSchedulingData data) {
        if(data.idDoctor() != null) {
            return doctorRepository.getReferenceById(data.idDoctor());
        }

        if(data.specialty() == null) {
            throw new ValidationException("Especialidade é obrigatória quando médico não é escolhido");
        }

        return doctorRepository.chooseRandomDoctorAvailable(data.specialty(), data.date());
    }
}