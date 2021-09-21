package guru.springframework.sfgpetclinic.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Vet extends Person {

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Specialty> specialty = new HashSet<>();

	@Builder
	public Vet(Long id, String firstName, String lastName, Set<Specialty> specialty) {
		super(id, firstName, lastName);
		this.specialty = specialty;
	}
}
