package org.woridge.ms_internal_users_management.service;

import org.woridge.ms_internal_users_management.dto.promotiom.*;

import java.util.List;
import java.util.UUID;

public interface PromotionService {

    CreatePromotionResponse promoteUser(CreatePromotionRequest createPromotionRequest);

    void updatePromotion(UUID id, UpdatePromotionRequest updatePromotionRequest);

    List<GetAllPromotionsResponse> getAllPromotions();

    UserPromotionInfoResponse getUserPromotionInfo(String CIN);

    List<SalaryHistoryResponse> getSalaryHistory(String CIN);
}