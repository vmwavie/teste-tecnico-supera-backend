package com.vmwavie.backend;

import com.vmwavie.backend.constants.CostumerMessage;
import com.vmwavie.backend.dto.CostumerDto;
import com.vmwavie.backend.model.Costumer;
import com.vmwavie.backend.repository.CostumerRepository;
import com.vmwavie.backend.security.SecurityValidation;
import com.vmwavie.backend.service.CostumerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class CostumerTests {

	@Mock
	private SecurityValidation securityValidation;

	@Mock
	private CostumerRepository costumerRepository;

	@InjectMocks
	private CostumerService costumerService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetAllCostumers() {
		List<Costumer> costumers = Arrays.asList(new Costumer(), new Costumer());
		when(costumerRepository.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(costumers));

		int pageNumber = 0;
		int itemsPerPage = 10;

		ResponseEntity<CostumerDto.CostumerResponseListAllDto> response = costumerService.getAllCostumers(pageNumber, itemsPerPage);

		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertNotNull(response.getBody());
		assertEquals(2, response.getBody().getCostumers().size());
	}

	@Test
	void testGetCostumerById() {
		Costumer costumer = new Costumer();
		when(costumerRepository.findById(1L)).thenReturn(Optional.of(costumer));

		ResponseEntity<CostumerDto.CostumerResponseListOneDto> response = costumerService.getCostumerById(1L);

		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertNotNull(response.getBody());
		assertEquals(costumer, response.getBody().getCostumer());
	}

	@Test
	void testSaveCostumer() {
		Costumer costumer = new Costumer();
		costumer.setName("Matheus Fernandes");
		costumer.setCpf("97023289003");
		costumer.setWhatsapp(46999829389L);

		when(costumerRepository.save(any(Costumer.class))).thenReturn(costumer);

		ResponseEntity<CostumerDto.CostumerResponseSaveDto> response = costumerService.saveCostumer(costumer);

		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertNotNull(response.getBody());
		assertEquals(costumer, response.getBody().getCostumer());
	}

	@Test
	void testDeleteCostumer() {
		doNothing().when(costumerRepository).deleteById(1L);

		ResponseEntity<Map<String, String>> response = costumerService.deleteCostumer(1L);

		assertNotNull(response);
		assertEquals(404, response.getStatusCodeValue());
		assertNotNull(response.getBody());
		assertEquals(CostumerMessage.ERROR_MESSAGES.COSTUMER_NOT_FOUND, response.getBody().get("error"));
	}
}