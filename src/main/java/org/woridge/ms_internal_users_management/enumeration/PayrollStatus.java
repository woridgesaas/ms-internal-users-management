package org.woridge.ms_internal_users_management.enumeration;

public enum PayrollStatus {
    EN_ATTENTE,  // générée par cron, RH pas encore validé
    VALIDEE,     // RH a approuvé → employé peut télécharger
    REJETEE      // RH a rejeté → à recalculer
}