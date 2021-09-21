package guru.springframework.sfgpetclinic.model;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Entity
public class Pet extends BaseEntity {
	@OneToOne
	private PetType petType;

	@ManyToOne
	private Owner owner;
	private LocalDate birthDate;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pet")
	private Set<Visit> visits = new HashSet<>();

}
