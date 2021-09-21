package guru.springframework.sfgpetclinic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Visit extends BaseEntity {
	private LocalDate date;
	private String description;

	@ManyToOne
	private Pet pet;
}
