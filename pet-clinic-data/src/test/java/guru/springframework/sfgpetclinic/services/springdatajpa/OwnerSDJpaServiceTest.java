package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

	@Mock
	OwnerRepository ownerRepository;

	@InjectMocks
	OwnerSDJpaService service;

	Owner returnOwner;

	String LAST_NAME = "Smith";


	@BeforeEach
	void setUp() {
		returnOwner = Owner.builder().id(1L).lastName(LAST_NAME).build();
	}

	@Test
	void findAll() {
		Set<Owner> owners = new HashSet<>();
		owners.add(Owner.builder().id(1L).build());
		owners.add(Owner.builder().id(2L).build());

		when(ownerRepository.findAll()).thenReturn(owners);

		Set<Owner> result = service.findAll();
		assertNotNull(result);
		assertEquals(2, result.size());
	}

	@Test
	void findById() {

		when(ownerRepository.findById(any())).thenReturn(Optional.of(returnOwner));

		Owner owner = service.findById(1L);

		assertNotNull(owner);
	}

	@Test
	void findByIdNotFound() {

		when(ownerRepository.findById(any())).thenReturn(Optional.empty());

		Owner owner = service.findById(1L);

		assertNull(owner);
	}

	@Test
	void save() {

		Owner ownerToSave = Owner.builder().id(1L).build();

		when(ownerRepository.save(any())).thenReturn(returnOwner);

		Owner savedOwner = service.save(ownerToSave);

		assertNotNull(savedOwner);
	}

	@Test
	void delete() {
		service.delete(returnOwner);

		verify(ownerRepository).delete(any());
	}

	@Test
	void deleteById() {
		service.deleteById(1L);

		verify(ownerRepository).deleteById(anyLong());
	}

	@Test
	void findByLastName() {

		when(ownerRepository.findByLastName(any())).thenReturn(returnOwner);

		assertEquals(LAST_NAME, service.findByLastName(LAST_NAME).getLastName());

		verify(ownerRepository).findByLastName(anyString());
	}
}