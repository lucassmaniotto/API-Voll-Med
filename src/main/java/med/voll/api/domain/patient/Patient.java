package med.voll.api.domain.patient;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.address.Address;

@Table(name = "patients")
@Entity(name = "Patient")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Patient {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String cpf;
    @Embedded
    private Address address;
    private Boolean active;

    public Patient(RegisterDataPatients data) {
        this.name = data.name();
        this.email = data.email();
        this.phone = data.phone();
        this.cpf = data.cpf();
        this.address = new Address(data.address());
        this.active = true;
    }

    public void updateData(UpdateDataPatients data) {
        if (data.name() != null) this.name = data.name();
        if (data.phone() != null) this.phone = data.phone();
        if (data.address() != null) this.address.updateData(data.address());        
    }

    public void delete() {
        this.active = false;
    }
}
