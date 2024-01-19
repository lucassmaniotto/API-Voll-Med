package med.voll.api.controller;

import jakarta.validation.Valid;
import lombok.NonNull;
import med.voll.api.domain.patient.*;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/patients")
public class PatientController {
    @Autowired
    private PatientRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<DataPatientDetails> register(@RequestBody @Valid RegisterDataPatients data, UriComponentsBuilder uriBuilder) {
        var patient = new Patient(data);
        repository.save(patient);

        var uri = uriBuilder.path("/patients/{id}").buildAndExpand(patient.getId()).toUri();
        
        return ResponseEntity.created(uri).body(new DataPatientDetails(patient));
    }

    @GetMapping
    public ResponseEntity<Page<Patient>> list(@PageableDefault(size = 10, sort = {"name"}) Pageable pageable) {
        var page = repository.findAllByActiveTrue(pageable);

        return ResponseEntity.ok(page);
    }
    
    @PutMapping
    @Transactional
    public ResponseEntity<DataPatientDetails> update(@RequestBody @Valid UpdateDataPatients data) {
        Long id = Objects.requireNonNull(data.id());
        var patient = repository.getReferenceById(id);
        patient.updateData(data);

        return ResponseEntity.ok(new DataPatientDetails(patient));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@NonNull @PathVariable Long id) {
        Long objectId = Objects.requireNonNull(id);
        var patient = repository.getReferenceById(objectId);
        patient.delete();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataPatientDetails> details(@NonNull @PathVariable Long id) {
        Long objectId = Objects.requireNonNull(id);
        var patient = repository.getReferenceById(objectId);

        return ResponseEntity.ok(new DataPatientDetails(patient));
    }
}
