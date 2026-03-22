package org.woridge.ms_internal_users_management.strategy.delete;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class DeleteStrategyResolver {

    private final Map<String, DeleteStrategy> strategies; // plus de générique

    public DeleteStrategy getStrategy(String strategyName) {
        DeleteStrategy strategy = strategies.get(strategyName);
        if (strategy == null) {
            throw new IllegalArgumentException("Aucune stratégie de suppression trouvée pour " + strategyName);
        }
        return strategy;
    }
}