package med.voll.api.domain.address;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String publicPlace;
    private String neighborhood;
    private String cep;
    private String city;
    private String uf;
    private String number;
    private String complement;

    public Address(AddressData data) {
        this.publicPlace = data.public_place();
        this.neighborhood = data.neighborhood();
        this.cep = data.cep();
        this.city = data.city();
        this.uf = data.uf();
        this.number = data.number();
        this.complement = data.complement();
    }

    public void updateData(AddressData data) {
        if (data.public_place() != null) this.publicPlace = data.public_place();
        if (data.neighborhood() != null) this.neighborhood = data.neighborhood();
        if (data.cep() != null) this.cep = data.cep();
        if (data.city() != null) this.city = data.city();
        if (data.uf() != null) this.uf = data.uf();
        if (data.number() != null) this.number = data.number();
        if (data.complement() != null) this.complement = data.complement();
    }
}
