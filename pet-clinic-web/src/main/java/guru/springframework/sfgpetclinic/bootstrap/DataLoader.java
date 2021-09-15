package guru.springframework.sfgpetclinic.bootstrap;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

	private final OwnerService ownerService;
	private final VetService vetService;

	public DataLoader(OwnerService ownerService, VetService vetService) {
		this.ownerService = ownerService;
		this.vetService = vetService;
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

	}
}
