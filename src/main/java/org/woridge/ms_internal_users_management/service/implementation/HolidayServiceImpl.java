package org.woridge.ms_internal_users_management.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.woridge.ms_internal_users_management.dto.Holiday.HolidayIndexResponse;
import org.woridge.ms_internal_users_management.enumeration.HolidayStatus;
import org.woridge.ms_internal_users_management.exception.holiday.FixedHolidayUpdatedException;
import org.woridge.ms_internal_users_management.exception.holiday.HolidayExistException;
import org.woridge.ms_internal_users_management.exception.holiday.HolidayNotFoundException;
import org.woridge.ms_internal_users_management.exception.holiday.InvalidHolidayDateException;
import org.woridge.ms_internal_users_management.mapper.HolidayMapper;
import org.woridge.ms_internal_users_management.model.Holiday;
import org.woridge.ms_internal_users_management.repository.HolidayRepository;
import org.woridge.ms_internal_users_management.service.HolidayService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class HolidayServiceImpl implements HolidayService {

    private final HolidayRepository repo;
    /**
     * Retrieves all holidays for a specified year.
     * If no holidays exist for the requested year (and it's the current or future year),
     * automatically generates fixed holidays based on the previous year's fixed holidays.
     *
     * @param year The year for which to retrieve holidays
     * @return List of HolidayIndexResponse DTOs containing all holidays for the specified year
     */
    public List<HolidayIndexResponse> getAllHolidays(int year){
        var holidays =repo.findAllByYear(year);
        // If no holidays found for current or future year, generate from previous year's fixed holidays

        if(holidays.isEmpty() && year >= LocalDate.now().getYear()){
            var previousHolidays = repo.findAllByYear(year - 1);
            if(!previousHolidays.isEmpty()){
                List<Holiday> newHolidays = previousHolidays.stream().map(previous->{
                    Holiday nh = new Holiday();
                    nh.setLabelFr(previous.getLabelFr());
                    nh.setLabelAr(previous.getLabelAr());
                    nh.setIsFixed(true);
                    nh.setDuration(previous.getDuration());
                    //Change just year and keep month+day
                    if(previous.getIsFixed()){
                        nh.setDate(previous.getDate().withYear(year));
                    }else{
                        nh.setStatus(HolidayStatus.TO_BE_CONFIRMED);
                        nh.setDate(previous.getDate().plusYears(1));
                    }
                    return nh;
                }).toList();
                repo.saveAll(newHolidays);
                holidays = newHolidays;
            }
        }
        return holidays.stream().map(h-> HolidayMapper.toListDto(h)).toList();
    }

    public boolean UpdateHoliday(Holiday model){
        var existing = repo.findById(model.getId()).orElseThrow(()->new HolidayNotFoundException("Holiday Not found !"));

        //Compare if RH try to change a fixed holiday date = not allowed
        if(existing.getIsFixed()){
            if(!existing.getDate().equals(model.getDate())){
                throw new FixedHolidayUpdatedException("Cannot update a fixed holiday !");
            }
        }
        //Verifying if ther's any other holidays with same date after update
        if(repo.existsByDateAndIdNot(model.getDate() , existing.getId()))
            throw new InvalidHolidayDateException("Ther's already a Holiday exist with this date");
        //Make the modification PUT :
        existing.setLabelFr(model.getLabelFr());
        existing.setLabelAr(model.getLabelAr());
        existing.setDuration(model.getDuration());
        if (!existing.getIsFixed()) {
            existing.setDate(model.getDate());
        }
        existing.setStatus(model.getStatus());
        repo.save(existing);
        return true;
    }

    /**
     *
     * @param model
     * @return
     */
    public Holiday createHoliday(Holiday model){
        //Date is old
        if(model.getDate().getYear() < LocalDate.now().getYear())
            throw new InvalidHolidayDateException("Year must be early than " + LocalDate.now().getYear());
        if(repo.existsByDate(model.getDate()))
            throw new HolidayExistException("Ther's already a Holiday exist with this date : "+ model.getDate());
        model.setIsFixed(true);
        model.setStatus(HolidayStatus.CONFIRMED);
        return repo.save(model);
    }

    public Optional<Holiday> getHolidayById(Long id){
        return repo.findById(id);

    }
}
