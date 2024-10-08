package com.vmwavie.backend.service;

import com.vmwavie.backend.constants.CostumerMessage;
import com.vmwavie.backend.constants.GlobalMessage;
import com.vmwavie.backend.dto.CostumerDto;
import com.vmwavie.backend.model.Costumer;
import com.vmwavie.backend.repository.CostumerRepository;
import com.vmwavie.backend.security.SecurityValidation;
import com.vmwavie.backend.utils.Validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Service
public class CostumerService {

    @Autowired
    private SecurityValidation securityValidation;

    @Autowired
    private CostumerRepository costumerRepository;

    public ResponseEntity<CostumerDto.CostumerResponseListAllDto> getAllCostumers(@RequestParam int pageNumber, @RequestParam int itemsPerPage) {
        try {
            Pageable pageable = PageRequest.of(pageNumber, itemsPerPage);
            Page<Costumer> costumerPage = costumerRepository.findAll(pageable);
            List<Costumer> costumers = costumerPage.getContent();

            CostumerDto.CostumerResponseListAllDto responseDto = new CostumerDto.CostumerResponseListAllDto(costumers);

            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            String errorMessage = Boolean.TRUE.equals(Validations.isNativeError(e.getMessage())) ? GlobalMessage.COMMON_ERRORS.UNDEFINED : e.getMessage();
            CostumerDto.CostumerResponseListAllDto errorResponse = new CostumerDto.CostumerResponseListAllDto(errorMessage);

            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    public ResponseEntity<CostumerDto.CostumerResponseListOneDto> getCostumerById(Long id) {
        try {
            Optional<Costumer> costumer = costumerRepository.findById(id);
            if (costumer.isPresent()) {
                CostumerDto.CostumerResponseListOneDto responseDto = new CostumerDto.CostumerResponseListOneDto(costumer.get());
                return ResponseEntity.ok(responseDto);
            } else {
                CostumerDto.CostumerResponseListOneDto errorResponse = new CostumerDto.CostumerResponseListOneDto(CostumerMessage.ERROR_MESSAGES.COSTUMER_NOT_FOUND);
                return ResponseEntity.status(404).body(errorResponse);
            }
        } catch (Exception e) {
            String errorMessage = Boolean.TRUE.equals(Validations.isNativeError(e.getMessage())) ? GlobalMessage.COMMON_ERRORS.UNDEFINED : e.getMessage();
            CostumerDto.CostumerResponseListOneDto errorResponse = new CostumerDto.CostumerResponseListOneDto(errorMessage);
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    public ResponseEntity<CostumerDto.CostumerResponseSaveDto> saveCostumer(Costumer costumer) {
        try {
            validateCostumerFields(costumer);

            Optional<Costumer> existingCostumer = costumerRepository.findByCpf(costumer.getCpf());

            if (existingCostumer.isPresent()) {
                CostumerDto.CostumerResponseSaveDto errorResponse = new CostumerDto.CostumerResponseSaveDto();
                errorResponse.setErrorMessage(CostumerMessage.ERROR_MESSAGES.COSTUMER_ALREADY_EXISTS);
                return ResponseEntity.status(400).body(errorResponse);
            }

            Costumer savedCostumer = costumerRepository.save(costumer);
            CostumerDto.CostumerResponseSaveDto responseDto = new CostumerDto.CostumerResponseSaveDto();
            responseDto.setCostumer(savedCostumer);

            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            String errorMessage = Boolean.TRUE.equals(Validations.isNativeError(e.getMessage())) ? GlobalMessage.COMMON_ERRORS.UNDEFINED : e.getMessage();
            CostumerDto.CostumerResponseSaveDto errorResponse = new CostumerDto.CostumerResponseSaveDto();
            errorResponse.setErrorMessage(errorMessage);

            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    public ResponseEntity<CostumerDto.CostumerResponseUpdateDto> updateCostumer(Costumer requestCostumer) {
        try {
            Costumer costumerToUpdate = costumerRepository.findById(requestCostumer.getId())
                    .orElseThrow(() -> new IllegalArgumentException(CostumerMessage.ERROR_MESSAGES.COSTUMER_NOT_FOUND));

            validateCostumerFields(requestCostumer);

            costumerToUpdate.setName(requestCostumer.getName());
            costumerToUpdate.setCpf(requestCostumer.getCpf());
            costumerToUpdate.setWhatsapp(requestCostumer.getWhatsapp());

            Costumer updatedCostumer = costumerRepository.save(costumerToUpdate);
            CostumerDto.CostumerResponseUpdateDto responseDto = new CostumerDto.CostumerResponseUpdateDto();
            responseDto.setCostumer(updatedCostumer);

            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            String errorMessage = Boolean.TRUE.equals(Validations.isNativeError(e.getMessage())) ? GlobalMessage.COMMON_ERRORS.UNDEFINED : e.getMessage();
            CostumerDto.CostumerResponseUpdateDto errorResponse = new CostumerDto.CostumerResponseUpdateDto();
            errorResponse.setErrorMessage(errorMessage);

            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    public ResponseEntity<CostumerDto.CostumerResponseListAllDto> getCostumerByText(String filter) {
        List<Costumer> costumers = new ArrayList<>();

        try {
            securityValidation.xss(filter);

            boolean isName = filter.matches("^[a-zA-Z\\s]+$");
            boolean isCpfOrWhatsapp = filter.matches("^\\d{11}$");

            if (isName) {
                costumers = costumerRepository.findByNameContainingIgnoreCase(filter);
            }

            if (isCpfOrWhatsapp || (costumers.isEmpty())) {
                costumers = costumerRepository.findByCpfContaining(filter);
            }

            if (isCpfOrWhatsapp && costumers.isEmpty()) {
                String cleanedWhatsapp = filter.replaceAll("[^0-9]", "");
                Long whatsappFilter = Long.parseLong(cleanedWhatsapp);
                costumers = costumerRepository.findByWhatsappContaining(whatsappFilter);
            }


            costumers.sort(Comparator.comparing(Costumer::getName));

            CostumerDto.CostumerResponseListAllDto responseDto = new CostumerDto.CostumerResponseListAllDto(costumers);

            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            String errorMessage = costumers.isEmpty() ? CostumerMessage.ERROR_MESSAGES.COSTUMER_NOT_FOUND : GlobalMessage.COMMON_ERRORS.UNDEFINED;
            CostumerDto.CostumerResponseListAllDto errorResponse = new CostumerDto.CostumerResponseListAllDto(errorMessage);

            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    public ResponseEntity<Map<String, String>> deleteCostumer(Long id) {
        try {
            Optional<Costumer> costumer = costumerRepository.findById(id);

            if (!costumer.isPresent()) {
                return ResponseEntity.status(404).body(Collections.singletonMap("error", CostumerMessage.ERROR_MESSAGES.COSTUMER_NOT_FOUND));
            }

            costumerRepository.deleteById(id);

            return ResponseEntity.ok(Collections.singletonMap("success", CostumerMessage.SUCCESS_MESSAGES.COSTUMER_DELETED_SUCCESSFULLY));
        } catch (Exception e) {
            String errorMessage = Boolean.TRUE.equals(Validations.isNativeError(e.getMessage())) ? GlobalMessage.COMMON_ERRORS.UNDEFINED : e.getMessage();
            return ResponseEntity.status(500).body(Collections.singletonMap("error", errorMessage));
        }
    }

    private void validateCostumerFields(Costumer costumer) {
        List<String> fieldsToValidate = Arrays.asList(costumer.getName(), costumer.getCpf(), String.valueOf(costumer.getWhatsapp()));

        for (String field : fieldsToValidate) {
            securityValidation.xss(field);
        }

        boolean isValidName = Validations.isValidName(costumer.getName());
        boolean isValidCpf = Validations.isValidCPF(costumer.getCpf());
        boolean isValidWhatsappPhoneNumber = Validations.isValidWhatsappPhoneNumber(costumer.getWhatsapp());

        if (!isValidName) {
            throw new IllegalArgumentException(CostumerMessage.ERROR_MESSAGES.COSTUMER_INVALID_NAME);
        }

        if (!isValidCpf) {
            throw new IllegalArgumentException(CostumerMessage.ERROR_MESSAGES.COSTUMER_INVALID_CPF);
        }

        if (!isValidWhatsappPhoneNumber) {
            throw new IllegalArgumentException(CostumerMessage.ERROR_MESSAGES.COSTUMER_INVALID_WHATSAPP);
        }
    }
}