package med.voll.api.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.NonNull;
import med.voll.api.domain.doctor.DataDoctorDetails;
import med.voll.api.domain.doctor.Doctor;
import med.voll.api.domain.doctor.DoctorRepository;
import med.voll.api.domain.doctor.ListDataDoctors;
import med.voll.api.domain.doctor.RegisterDataDoctors;
import med.voll.api.domain.doctor.UpdateDataDoctors;

@RestController
@RequestMapping("/doctors")
public class DoctorController {
    @Autowired
    private DoctorRepository repository;
    
    @PostMapping
    @Transactional
    public ResponseEntity<DataDoctorDetails> register(@RequestBody @Valid RegisterDataDoctors data, UriComponentsBuilder uriBuilder){
        var doctor = new Doctor(data);
        repository.save(doctor);

        var uri = uriBuilder.path("/doctors/{id}").buildAndExpand(doctor.getId()).toUri();

        return ResponseEntity.created(uri).body(new DataDoctorDetails(doctor));
    }

    @GetMapping
    public ResponseEntity<Page<ListDataDoctors>> list(@NonNull @PageableDefault(size=10, sort={"name"}) Pageable pageable){
        var page = repository.findAllByActiveTrue(pageable).map(ListDataDoctors::new);

        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DataDoctorDetails> update(@RequestBody @Valid UpdateDataDoctors data){
        Long id = Objects.requireNonNull(data.id());
        var doctor = repository.getReferenceById(id);
        doctor.updateData(data);

        return ResponseEntity.ok(new DataDoctorDetails(doctor));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@NonNull @PathVariable Long id){
        Long objectId = Objects.requireNonNull(id);
        var doctor = repository.getReferenceById(objectId);
        doctor.delete();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataDoctorDetails> details(@NonNull @PathVariable Long id){
        Long objectId = Objects.requireNonNull(id);
        var doctor = repository.getReferenceById(objectId);
        
        return ResponseEntity.ok(new DataDoctorDetails(doctor));
    }
}