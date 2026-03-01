package org.woridge.ms_internal_users_management.mapper;

import org.woridge.ms_internal_users_management.dto.Holiday.HolidayIndexResponse;
import org.woridge.ms_internal_users_management.dto.Holiday.HolidayRequest;
import org.woridge.ms_internal_users_management.dto.Holiday.HolidayResponse;
import org.woridge.ms_internal_users_management.dto.Holiday.HolidayUpdateRequest;
import org.woridge.ms_internal_users_management.model.Holiday;

public class HolidayMapper {
    public static HolidayIndexResponse toListDto(Holiday model){
        return HolidayIndexResponse.builder()
                .id(model.getId())
                .labelFr(model.getLabelFr())
                .labelAr(model.getLabelAr())
                .date(model.getDate())
                .duration(model.getDuration())
                .isFixed(model.getIsFixed())
                .status(model.getStatus())
                .build();
    }

    public static Holiday toModel(HolidayRequest dto){
        return  Holiday.builder()
                .labelFr(dto.getLabelFr())
                .labelAr(dto.getLabelAr())
                .date(dto.getDate())
                .duration(dto.getDuration())
                .isFixed(false)
                .build();

    }

    public static HolidayResponse toHolidayResponse(Holiday model){
        return HolidayResponse.builder()
                .id(model.getId())
                .labelAr(model.getLabelAr())
                .labelFr(model.getLabelFr())
                .date(model.getDate())
                .duration(model.getDuration())
                .isFixed(model.getIsFixed())
                .status(model.getStatus())
                .build();
    }

    public static Holiday updateToModel(HolidayUpdateRequest dto){
        return Holiday.builder()
                .id(dto.getId())
                .labelAr(dto.getLabelAr())
                .labelFr(dto.getLabelFr())
                .date(dto.getDate())
                .duration(dto.getDuration())
                .status(dto.getStatus())
                .build();
    }
}
