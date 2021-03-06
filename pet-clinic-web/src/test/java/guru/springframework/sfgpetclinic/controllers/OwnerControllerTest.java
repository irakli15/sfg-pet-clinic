package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

	@Mock
	OwnerService ownerService;

	@InjectMocks
	OwnerController controller;

	Set<Owner> owners;

	MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		owners = new HashSet<>();
		owners.add(Owner.builder().id(1L).build());
		owners.add(Owner.builder().id(2L).build());
		mockMvc = MockMvcBuilders.
				standaloneSetup(controller)
				.build();
	}

	@Test
	void ownerDetails() throws Exception {
		Owner owner = Owner.builder().id(1L).build();
		when(ownerService.findById(anyLong())).thenReturn(owner);

		mockMvc.perform(get("/owners/1"))
				.andExpect(status().isOk())
				.andExpect(view().name("owners/ownerDetails"))
				.andExpect(model().attribute("owner", Matchers.hasProperty("id", Matchers.is(1L))));
	}

	@Test
	void findOwners() throws Exception {
		mockMvc.perform(get("/owners/find"))
				.andExpect(status().isOk())
				.andExpect(view().name("owners/findOwners"));

	}

	@Test
	void processFindFormReturnMany() throws Exception {
		when(ownerService.findAllByLastNameLike(anyString())).thenReturn(new ArrayList<>(owners));

		mockMvc.perform(get("/owners"))
				.andExpect(status().isOk())
				.andExpect(view().name("owners/ownersList"));
	}

	@Test
	void processFindFormReturnOne() throws Exception {
		when(ownerService.findAllByLastNameLike(anyString()))
				.thenReturn(Collections.singletonList(Owner.builder().id(1L).build()));

		mockMvc.perform(get("/owners"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/owners/1"));

	}

	@Test
	void initCreateForm() throws Exception {
		mockMvc.perform(get("/owners/new"))
				.andExpect(status().isOk())
				.andExpect(view().name("owners/createOrUpdateOwnerForm"));
		verifyNoInteractions(ownerService);
	}

	@Test
	void processCreationForm() throws Exception {
		when(ownerService.save(any())).thenReturn(Owner.builder().id(1L).build());
		mockMvc.perform(post("/owners/new"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/owners/1"))
				.andExpect(model().attributeExists("owner"));
		verify(ownerService).save(any());
	}

	@Test
	void initUpdateForm() throws Exception {
		when(ownerService.findById(1L)).thenReturn(Owner.builder().id(1L).build());
		mockMvc.perform(get("/owners/1/edit"))
				.andExpect(status().isOk())
				.andExpect(view().name("owners/createOrUpdateOwnerForm"));
		verify(ownerService, times(1)).findById(anyLong());
		verifyNoMoreInteractions(ownerService);
	}

	@Test
	void processUpdateForm() throws Exception {
		when(ownerService.save(any())).thenReturn(Owner.builder().id(1L).build());
		mockMvc.perform(post("/owners/1/edit"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/owners/1"))
				.andExpect(model().attributeExists("owner"));
		verify(ownerService).save(any());
	}
}