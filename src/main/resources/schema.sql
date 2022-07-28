
--CREATE SCHEMA IF NOT EXISTS `paymybuddy` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;

USE paymybuddy;

-- -----------------------------------------------------
-- Table `paymybuddy`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paymybuddy`.`user` (
  `user_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `firstname` VARCHAR(50) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `lastname` VARCHAR(50) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `username` VARCHAR(50) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `email` VARCHAR(100) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `balance` DECIMAL(10,2) NOT NULL,
  `active` TINYINT(1) NOT NULL,
  `password` VARCHAR(255) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_as_cs' NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `paymybuddy`.`user_transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paymybuddy`.`user_transaction` (
  `user_transaction_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_destination_id` INT UNSIGNED NOT NULL,
  `user_source_id` INT UNSIGNED NOT NULL,
  `date_user_transaction` DATETIME NOT NULL,
  `comments` VARCHAR(200) NOT NULL,
  `amount` DECIMAL(10,2) NOT NULL,
  `fees` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`user_transaction_id`),
  INDEX `fk_tu_user_destination_id` USING BTREE (`user_destination_id` ASC) INVISIBLE,
  INDEX `fk_tu_user_source_id` USING BTREE (`user_source_id`) INVISIBLE,
  CONSTRAINT `fk_user_transaction_user_destination_id`
    FOREIGN KEY (`user_destination_id`)
    REFERENCES `paymybuddy`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_user_transaction_user_source_id`
    FOREIGN KEY (`user_source_id`)
    REFERENCES `paymybuddy`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `paymybuddy`.`user_connection`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paymybuddy`.`user_connection` (
  `user_source_id` INT UNSIGNED NOT NULL,
  `user_destination_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`user_source_id`, `user_destination_id`),
  INDEX `fk_user_connection_user_destination_id` USING BTREE (`user_source_id`) INVISIBLE,
  INDEX `fk_user_connection_user_destination_id_idx` USING BTREE (`user_destination_id`) INVISIBLE,
  CONSTRAINT `fk_user_connection_user_destination_id`
    FOREIGN KEY (`user_destination_id`)
    REFERENCES `paymybuddy`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_user_connection_user_source_id`
    FOREIGN KEY (`user_source_id`)
    REFERENCES `paymybuddy`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `paymybuddy`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paymybuddy`.`role` (
  `role_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `role` VARCHAR(50) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  PRIMARY KEY (`role_id`))
ENGINE = InnoDB;

REPLACE INTO `role` (`role_id`, `role`) VALUES
	(1, 'USER'),
	(2, 'ADMIN');


CREATE TABLE IF NOT EXISTS `paymybuddy`.`user_roles` (
  `user_id` INT UNSIGNED NOT NULL,
  `role_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`),
  INDEX `fk_user_roles_user_id` USING BTREE (`user_id`) INVISIBLE,
  INDEX `fk_user_roles_role_id` USING BTREE (`role_id`) INVISIBLE,
  CONSTRAINT `fk_user_roles_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `paymybuddy`.`user` (`user_id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `fk_user_roles_role`
    FOREIGN KEY (`role_id`)
    REFERENCES `paymybuddy`.`role` (`role_id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;