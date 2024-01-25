package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.appointment.AppointmentSchedule;
import med.voll.api.domain.appointment.AppointmentSchedulingData;
import med.voll.api.domain.appointment.SchedulingDetailsData;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentSchedule appointmentSchedule;

    @PostMapping
    @Transactional
    public ResponseEntity<SchedulingDetailsData> scheduleAppointment(@RequestBody @Valid AppointmentSchedulingData data) {
        var dto = appointmentSchedule.schedule(data);
        return ResponseEntity.ok(dto);
    }

    
}
