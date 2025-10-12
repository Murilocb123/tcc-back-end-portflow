package br.com.murilocb123.portflow.controller;

import br.com.murilocb123.portflow.domain.entities.TransactionEntity;
import br.com.murilocb123.portflow.dto.TransactionDTO;
import br.com.murilocb123.portflow.mapper.TransactionMapper;
import br.com.murilocb123.portflow.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;


    @PostMapping
    public TransactionDTO create(@RequestBody TransactionDTO dto) {
        TransactionEntity entity = TransactionMapper.toEntity(dto);
        TransactionEntity saved = transactionService.create(entity);
        return TransactionMapper.toDTO(saved);
    }

    @GetMapping("/{id}")
    public TransactionDTO getById(@PathVariable UUID id) {
        return TransactionMapper.toDTO(transactionService.getById(id));
    }

    @PutMapping("/{id}")
    public TransactionDTO update(@PathVariable UUID id, @RequestBody TransactionDTO dto) {
        TransactionEntity entity = TransactionMapper.toEntity(dto);
        TransactionEntity updated = transactionService.update(id, entity);
        return TransactionMapper.toDTO(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        transactionService.delete(id);
    }

    @GetMapping
    public Page<TransactionDTO> list(Pageable pageable) {
        Page<TransactionEntity> page = transactionService.list(pageable);
        List<TransactionDTO> dtos = page.getContent().stream().map(TransactionMapper::toDTO).toList();
        return new PageImpl<>(dtos, pageable, page.getTotalElements());
    }
}
