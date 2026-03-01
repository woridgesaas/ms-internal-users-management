package org.woridge.ms_internal_users_management.service;

import org.woridge.ms_internal_users_management.dto.Holiday.HolidayIndexResponse;
import org.woridge.ms_internal_users_management.model.Holiday;

import java.util.List;
import java.util.Optional;

public interface HolidayService {
    public List<HolidayIndexResponse> getAllHolidays(int year);

    public boolean UpdateHoliday(Holiday model);

    public Holiday createHoliday(Holiday model);

    public Optional<Holiday> getHolidayById(Long id);
}
