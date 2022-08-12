
CREATE SCHEMA IF NOT EXISTS `paymybuddy` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `paymybuddy` ;

-- -----------------------------------------------------
-- Table `paymybuddy`.`persistent_logins`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paymybuddy`.`persistent_logins` (
  `username` VARCHAR(64) NOT NULL,
  `series` VARCHAR(64) NOT NULL,
  `token` VARCHAR(64) NOT NULL,
  `last_used` TIMESTAMP NOT NULL,
  PRIMARY KEY (`series`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `paymybuddy`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paymybuddy`.`role` (
  `role_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `role` VARCHAR(50) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  PRIMARY KEY (`role_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

INSERT INTO `role` (`role`) VALUES
	('USER'),
	('ADMIN');


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
  UNIQUE INDEX `username_UNIQUE` (`username` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

INSERT INTO `user` (`firstname`, `lastname`, `username`, `email`, `balance`, `active`, `password`) VALUES
('aimen', 'jerbi','akira', 'aimenjerbi@gmail.com', 0, 1, '$2a$10$1CqRTrB8yOLXVmAMXCHbAu08ameoCePTPenJ7Zhr1E6/.GdnbRn.u'),
('nicolas', 'lietard','gimme', 'gimme@gmail.com', 0, 1, '$2a$10$1CqRTrB8yOLXVmAMXCHbAu08ameoCePTPenJ7Zhr1E6/.GdnbRn.u');


-- -----------------------------------------------------
-- Table `paymybuddy`.`transactions_bank`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paymybuddy`.`transactions_bank` (
  `bank_transaction_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `datetime` DATETIME NOT NULL,
  `amount` INT NOT NULL,
  `user_id` INT UNSIGNED NULL DEFAULT NULL,
  `bank_account_number` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`bank_transaction_id`),
  INDEX `fk_transactions_bank_user_idx` (`user_id` ASC),
  CONSTRAINT `fk_transactions_bank_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `paymybuddy`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `paymybuddy`.`user_connection`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paymybuddy`.`user_connection` (
  `user_source_id` INT UNSIGNED NOT NULL,
  `user_destination_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`user_source_id`, `user_destination_id`),
  INDEX `fk_user_connection_user_destination_id` USING BTREE (`user_source_id`),
  INDEX `fk_user_connection_user_destination_id_idx` USING BTREE (`user_destination_id`),
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
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

INSERT INTO `user_connection` (`user_source_id`, `user_destination_id`) VALUES
(1, 2);



-- -----------------------------------------------------
-- Table `paymybuddy`.`user_roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `paymybuddy`.`user_roles` (
  `user_id` INT UNSIGNED NOT NULL,
  `role_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`),
  INDEX `fk_user_roles_user_id` USING BTREE (`user_id`) ,
  INDEX `fk_user_roles_role_id` USING BTREE (`role_id`),
  CONSTRAINT `fk_user_roles_role`
    FOREIGN KEY (`role_id`)
    REFERENCES `paymybuddy`.`role` (`role_id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
  CONSTRAINT `fk_user_roles_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `paymybuddy`.`user` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


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
  INDEX `fk_tu_user_destination_id` USING BTREE (`user_destination_id`) ,
  INDEX `fk_tu_user_source_id` USING BTREE (`user_source_id`),
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
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;