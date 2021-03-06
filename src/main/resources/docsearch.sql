-- MySQL Script generated by MySQL Workbench
-- Wed Nov 20 21:33:53 2019
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema docsearch
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema docsearch
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `docsearch` DEFAULT CHARACTER SET utf8 ;
USE `docsearch` ;

-- -----------------------------------------------------
-- Table `docsearch`.`document`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `docsearch`.`document` (
  `id` INT(11) NOT NULL DEFAULT 1,
  `url` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `docsearch`.`word`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `docsearch`.`word` (
  `word` VARCHAR(30) NOT NULL,
  `documentId` INT(11) NOT NULL,
  `b` DOUBLE(11,5) NOT NULL,
  PRIMARY KEY (`word`, `documentId`),
  INDEX `documentId_idx` (`documentId` ASC) VISIBLE,
  CONSTRAINT `documentId`
    FOREIGN KEY (`documentId`)
    REFERENCES `docsearch`.`document` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `docsearch`.`document`
-- -----------------------------------------------------
START TRANSACTION;
USE `docsearch`;
INSERT INTO `docsearch`.`document` (`id`, `url`) VALUES (1, 'https://en.wikipedia.org/wiki/Cd_(command)');
INSERT INTO `docsearch`.`document` (`id`, `url`) VALUES (2, 'https://en.wikipedia.org/wiki/Touch_(command)');
INSERT INTO `docsearch`.`document` (`id`, `url`) VALUES (3, 'https://en.wikipedia.org/wiki/Ls');
INSERT INTO `docsearch`.`document` (`id`, `url`) VALUES (4, 'https://en.wikipedia.org/wiki/Dir_(command)');

COMMIT;
