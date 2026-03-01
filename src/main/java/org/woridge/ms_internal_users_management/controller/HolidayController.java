package org.woridge.ms_internal_users_management.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.woridge.ms_internal_users_management.dto.Holiday.HolidayIndexResponse;
import org.woridge.ms_internal_users_management.dto.Holiday.HolidayRequest;
import org.woridge.ms_internal_users_management.dto.Holiday.HolidayResponse;
import org.woridge.ms_internal_users_management.dto.Holiday.HolidayUpdateRequest;
import org.woridge.ms_internal_users_management.mapper.HolidayMapper;
import org.woridge.ms_internal_users_management.model.Holiday;
import org.woridge.ms_internal_users_management.service.HolidayService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/holidays")
public class HolidayController {
    private final HolidayService holidayService;

    @GetMapping
    public ResponseEntity<List<HolidayIndexResponse>> getAll(@RequestParam("year") @NotNull(message = "year cannot be null") int year){
        var list = holidayService.getAllHolidays(year);
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<HolidayResponse> create(@RequestBody @Valid HolidayRequest dto){
        Holiday createdRessource =holidayService.createHoliday(HolidayMapper.toModel(dto));
        URI location = URI.create("/api/holidays/" + createdRessource.getId());
        return ResponseEntity.created(location).body(HolidayMapper.toHolidayResponse(createdRessource));
    }

    @PutMapping
    public ResponseEntity udpate(@RequestBody @Valid HolidayUpdateRequest dto){
        boolean updatedRessource = holidayService.UpdateHoliday(HolidayMapper.updateToModel(dto));
        return ResponseEntity.noContent().build();

    }
    @GetMapping("/{id}")
    public ResponseEntity<HolidayResponse> getById(@PathVariable @NotNull(message = "ID cannot be null") Long id){
        Optional<Holiday> existing = holidayService.getHolidayById(id);
        if(existing.isPresent())
            return ResponseEntity.ok(HolidayMapper.toHolidayResponse(existing.get()));
        return ResponseEntity.notFound().build();
    }
}
