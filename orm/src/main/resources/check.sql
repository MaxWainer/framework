SELECT *
FROM `example`
         JOIN `other` AS `other` ON `example`.`person_id` = `other`.`person_id`
WHERE `uuid` = ?