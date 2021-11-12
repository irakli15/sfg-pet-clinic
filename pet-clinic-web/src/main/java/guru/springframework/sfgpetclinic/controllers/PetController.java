package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Objects;

@Controller
@RequestMapping("owners/{ownerId}")
public class PetController {

	private final PetService petService;
	private final PetTypeService petTypeService;
	private final OwnerService ownerService;

	public PetController(PetService petService, PetTypeService petTypeService, OwnerService ownerService) {
		this.petService = petService;
		this.petTypeService = petTypeService;
		this.ownerService = ownerService;
	}


	@ModelAttribute("types")
	public Collection<PetType> getPetTypes() {
		return petTypeService.findAll();
	}

	@ModelAttribute("owner")
	public Owner findOwner(@PathVariable Long ownerId) {
		return ownerService.findById(ownerId);
	}

	@InitBinder("owner")
	public void initOwnerBinder(WebDataBinder webDataBinder) {
		webDataBinder.setDisallowedFields("id");
	}

	@GetMapping("/pets/new")
	public String initCreateForm(Owner owner, Model model) {
		model.addAttribute("pet", Pet.builder().owner(owner).build());
		return "pets/createOrUpdatePetForm";
	}

	@PostMapping("/pets/new")
	public String processCreateForm(Owner owner, @Validated Pet pet, BindingResult result, Model model) {
		if (StringUtils.hasLength(pet.getName()) && pet.isNew() && owner.getPet(pet.getName(), true) != null) {
			result.rejectValue("name", "duplicate", "already exists");
		}
		pet.setOwner(owner);
		if (result.hasErrors()) {
			model.addAttribute("pet", pet);
			return "pets/createOrUpdatePetForm";
		} else {
			petService.save(pet);
			return "redirect:/owners/" + owner.getId();
		}
	}

	@GetMapping("/pets/{petId}/edit")
	public String initUpdateForm(Owner owner, @PathVariable Long petId, Model model) {
		model.addAttribute("pet", petService.findById(petId));
		return "pets/createOrUpdatePetForm";
	}

	@PostMapping("/pets/{petId}/edit")
	public String processUpdateForm(Owner owner, @Validated Pet pet, BindingResult result, Model model) {
		if (StringUtils.hasLength(pet.getName()) &&
				owner.getPets().stream().anyMatch(petInSet ->
						!Objects.equals(petInSet.getId(), pet.getId()) &&
						petInSet.getName().equals(pet.getName()))) {
			result.rejectValue("name", "duplicate", "already exists");
		}
		pet.setOwner(owner);
		if(result.hasErrors()) {
			model.addAttribute("pet", pet);
			return "pets/createOrUpdatePetForm";
		} else {
			petService.save(pet);
			return "redirect:/owners/" + owner.getId();
		}
	}
}
