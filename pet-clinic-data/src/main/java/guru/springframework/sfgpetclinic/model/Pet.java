package guru.springframework.sfgpetclinic.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Pet extends BaseEntity {
	@OneToOne
	private PetType petType;
	private String name;

	@ManyToOne
	private Owner owner;
	private LocalDate birthDate;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pet")
	private Set<Visit> visits = new HashSet<>();

	@Builder
	public Pet(Long id, PetType petType, String name, Owner owner, LocalDate birthDate, Set<Visit> visits) {
		super(id);
		this.petType = petType;
		this.name = name;
		this.owner = owner;
		this.birthDate = birthDate;
		this.visits = visits;
	}
}
