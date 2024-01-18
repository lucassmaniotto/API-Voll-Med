package med.voll.api.controller;

import jakarta.validation.Valid;
import lombok.NonNull;
import med.voll.api.patient.*;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
public class PatientController {
    @Autowired
    private PatientRepository repository;

    @PostMapping
    @Transactional
    public void register(@RequestBody @Valid RegisterDataPatients data) {
        repository.save(new Patient(data));
    }

    @GetMapping
    public Page<Patient> list(@PageableDefault(size = 10, sort = {"name"}) Pageable pageable) {
        return repository.findAllByActiveTrue(pageable);
    }
    
    @PutMapping
    @Transactional
    public void update(@RequestBody @Valid UpdateDataPatients data) {
        Long id = Objects.requireNonNull(data.id());
        var patient = repository.getReferenceById(id);
        patient.updateData(data);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void delete(@NonNull @PathVariable Long id) {
        Long objectId = Objects.requireNonNull(id);
        var patient = repository.getReferenceById(objectId);
        patient.delete();
    }
}
