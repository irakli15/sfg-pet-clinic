package guru.springframework.sfgpetclinic.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Vet extends Person {

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Specialty> specialty = new HashSet<>();

	public Set<Specialty> getSpecialty() {
		return specialty;
	}

	public void setSpecialty(Set<Specialty> specialty) {
		this.specialty = specialty;
	}
}
