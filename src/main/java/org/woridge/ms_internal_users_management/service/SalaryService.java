package org.woridge.ms_internal_users_management.service;

import org.woridge.ms_internal_users_management.model.Post;
import org.woridge.ms_internal_users_management.model.SalaryHistory;
import org.woridge.ms_internal_users_management.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface SalaryService {

        void closeCurrentSalary(User user);

        void createNewSalary(User user, Post post, BigDecimal salary);

        SalaryHistory getCurrentSalary(User user);

        List<SalaryHistory> getSalaryHistory(User user);
    }

