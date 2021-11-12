package guru.springframework.sfgpetclinic.bootstrap;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import guru.springframework.sfgpetclinic.services.VetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataLoader implements CommandLineRunner {

	private final OwnerService ownerService;
	private final VetService vetService;
	private final PetTypeService petTypeService;

	public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService) {
		this.ownerService = ownerService;
		this.vetService = vetService;
		this.petTypeService = petTypeService;
	}

	@Override
	public void run(String... args) throws Exception {
		Owner owner1 = new Owner();
		owner1.setFirstName("ownerName1");
		owner1.setLastName("ownerLastName1");
		ownerService.save(owner1);


		Owner owner2 = new Owner();
		owner2.setFirstName("ownerName2");
		owner2.setLastName("ownerLastName2");
		ownerService.save(owner2);

		System.out.println("loaded owners...");

		Vet vet1 = new Vet();
		vet1.setFirstName("vetName1");
		vet1.setLastName("vetLastName1");
		vetService.save(vet1);

		Vet vet2 = new Vet();
		vet2.setFirstName("vetName2");
		vet2.setLastName("vetLastName2");
		vetService.save(vet2);

		System.out.println("loaded vets...");

		petTypeService.save(PetType.builder().id(1L).name("Dog").build());
		petTypeService.save(PetType.builder().id(2L).name("Cat").build());

		System.out.println("loaded pet types...");

	}
}
