package med.voll.api.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.NonNull;

import med.voll.api.doctor.Doctor;
import med.voll.api.doctor.DoctorRepository;
import med.voll.api.doctor.ListDataDoctors;
import med.voll.api.doctor.RegisterDataDoctors;
import med.voll.api.doctor.UpdateDataDoctors;

@RestController
@RequestMapping("/doctors")
public class DoctorController {
    @Autowired
    private DoctorRepository repository;
    
    @PostMapping
    @Transactional
    public void register(@RequestBody @Valid RegisterDataDoctors data){
        repository.save(new Doctor(data));
    }

    @GetMapping
    public Page<ListDataDoctors> list(@NonNull @PageableDefault(size=10, sort={"name"}) Pageable pageable){
        return repository.findAllByActiveTrue(pageable).map(ListDataDoctors::new);
    }

    @PutMapping
    @Transactional
    public void update(@RequestBody @Valid UpdateDataDoctors data){
        Long id = Objects.requireNonNull(data.id());
        var doctor = repository.getReferenceById(id);
        doctor.updateData(data);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void delete(@NonNull @PathVariable Long id){
        Long objectId = Objects.requireNonNull(id);
        var doctor = repository.getReferenceById(objectId);
        doctor.delete();
    }
}