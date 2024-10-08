package com.vmwavie.backend.controller;

import com.vmwavie.backend.dto.CostumerDto;
import com.vmwavie.backend.model.Costumer;
import com.vmwavie.backend.service.CostumerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/costumers")
@Api(value = "Api para gerenciamento de clientes")
public class CostumerController {

    private final CostumerService costumerService;

    @Autowired
    public CostumerController(CostumerService costumerService) {
        this.costumerService = costumerService;
    }

    @GetMapping("/get-all")
    @ApiOperation(value = "Retorna todos os clientes cadastrados")
    public ResponseEntity<CostumerDto.CostumerResponseListAllDto> getAllCostumers(@RequestParam Map<String, String> paginationParams) {
        int pageNumber = Integer.parseInt(paginationParams.getOrDefault("pageNumber", "0"));
        int itemsPerPage = Integer.parseInt(paginationParams.getOrDefault("itemsPerPage", "10"));
        return costumerService.getAllCostumers(pageNumber, itemsPerPage);
    }

    @GetMapping("/get-by-id/{id}")
    @ApiOperation(value = "Retorna um cliente específico pelo ID")
    public ResponseEntity<CostumerDto.CostumerResponseListOneDto> getCostumerById(@PathVariable Long id) {
        return costumerService.getCostumerById(id);
    }

    @GetMapping("/get-by-text/{filter}")
    @ApiOperation(value = "Retorna um cliente específico pelo texto de busca")
    public ResponseEntity<CostumerDto.CostumerResponseListAllDto> getCostumerByText(@PathVariable String filter) {
        return costumerService.getCostumerByText(filter);
    }

    @PostMapping("/create")
    @ApiOperation(value = "Cadastra um novo cliente")
    public ResponseEntity<CostumerDto.CostumerResponseSaveDto> saveCostumer(@RequestBody Costumer costumer) {
        return costumerService.saveCostumer(costumer);
    }

    @PutMapping("/update")
    @ApiOperation(value = "Atualiza um cliente existente")
    public ResponseEntity<CostumerDto.CostumerResponseUpdateDto> updateCostumer(@RequestBody Costumer requestCostumer) {
        return costumerService.updateCostumer(requestCostumer);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Deleta um cliente existente")
    public ResponseEntity<Map<String, String>> deleteCostumer(@PathVariable Long id) {
        return costumerService.deleteCostumer(id);
    }
}
