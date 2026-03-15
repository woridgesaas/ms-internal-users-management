INSERT INTO holidays (label_fr, label_ar, date, duration, is_fixed, status)
VALUES ('Manifeste de l’Indépendance', 'تقديم وثيقة الاستقلال', '2026-01-11', 1, true, 'CONFIRMED'),
       ('Nouvel An Amazigh', 'رأس السنة الأمازيغية', '2026-01-14', 1, true, 'CONFIRMED'),
       ('Fête du Travail', 'عيد الشغل', '2026-05-01', 1, true, 'CONFIRMED'),
       ('Fête du Trône', 'عيد العرش', '2026-07-30', 1, true, 'CONFIRMED'),
       ('Libération d’Oued Ed-Dahab', 'ذكرى استرجاع إقليم وادي الذهب', '2026-08-14', 1, true, 'CONFIRMED'),
       ('Révolution du Roi et du Peuple', 'ذكرى ثورة الملك والشعب', '2026-08-20', 1, true, 'CONFIRMED'),
       ('Fête de la Jeunesse', 'عيد الشباب', '2026-08-21', 1, true, 'CONFIRMED'),
       ('Marche Verte', 'ذكرى المسيرة الخضراء', '2026-11-06', 1, true, 'CONFIRMED'),
       ('Fête de l’Indépendance', 'عيد الاستقلال', '2026-11-18', 1, true, 'CONFIRMED');

-- Jours Religieux MOBILES (TO_BE_CONFIRMED)
INSERT INTO holidays (label_fr, label_ar, date, duration, is_fixed, status)
VALUES ('Aïd Al Fitr', 'عيد الفطر', '2026-03-20', 2, false, 'TO_BE_CONFIRMED'),
       ('Aïd Al Adha', 'عيد الأضحى', '2026-05-27', 2, false, 'TO_BE_CONFIRMED'),
       ('1er Moharram', 'فاتح محرم', '2026-06-16', 1, false, 'TO_BE_CONFIRMED'),
       ('Aïd Al Mawlid', 'عيد المولد النبوي', '2026-08-25', 2, false, 'TO_BE_CONFIRMED');