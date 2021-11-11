package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/owners")
@Controller
public class OwnerController {
	private static final String OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";


	private final OwnerService ownerService;

	public OwnerController(OwnerService ownerService) {
		this.ownerService = ownerService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder webDataBinder) {
		webDataBinder.setDisallowedFields("id");
	}

	@GetMapping({"/find"})
	public String findOwners(Model model) {
		model.addAttribute("owner", new Owner());
		return "owners/findOwners";
	}

	@GetMapping("")
	public String processFindForm(Owner owner, BindingResult result, Model model) {
		if (owner.getLastName() == null) {
			owner.setLastName("");
		}

		List<Owner> results = ownerService.findAllByLastNameLike("%" + owner.getLastName() + "%");
		if (results.isEmpty()) {
			result.rejectValue("lastName", "notFound", "not found");
			return "owners/findOwners";
		} else if (results.size() == 1) {
			owner = results.get(0);
			return "redirect:/owners/" + owner.getId();
		} else {
			model.addAttribute("selections", results);
			return "owners/ownersList";
		}
	}

	@GetMapping({"/{ownerId}"})
	public String ownerDetails(Model model, @PathVariable Long ownerId) {
		model.addAttribute("owner", ownerService.findById(ownerId));
		return "owners/ownerDetails";
	}

	@GetMapping("/new")
	public String initCreationForm(Model model) {
		model.addAttribute("owner", new Owner());
		return OWNER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/new")
	public String processCreationForm(@Validated Owner owner, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return OWNER_CREATE_OR_UPDATE_FORM;
		} else {
			owner = ownerService.save(owner);
			return "redirect:/owners/" + owner.getId();
		}
	}

	@GetMapping("/{ownerId}/edit")
	public String initUpdateForm(@PathVariable Long ownerId, Model model) {
		model.addAttribute("owner", ownerService.findById(ownerId));
		return OWNER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/{ownerId}/edit")
	public String processUpdateForm(@Validated Owner owner, BindingResult bindingResult, @PathVariable Long ownerId) {
		if (bindingResult.hasErrors()) {
			return OWNER_CREATE_OR_UPDATE_FORM;
		} else {
			owner.setId(ownerId);
			owner = ownerService.save(owner);
			return "redirect:/owners/" + owner.getId();
		}
	}


}
