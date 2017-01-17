-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema hotel
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `hotel`;

-- -----------------------------------------------------
-- Schema hotel
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `hotel`
  DEFAULT CHARACTER SET utf8
  COLLATE utf8_unicode_ci;
USE `hotel`;

-- -----------------------------------------------------
-- Table `hotel`.`ROOM_TYPE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hotel`.`ROOM_TYPE`;

CREATE TABLE IF NOT EXISTS `hotel`.`ROOM_TYPE` (
  `idRoomType` INT          NOT NULL AUTO_INCREMENT,
  `type`       VARCHAR(50)  NOT NULL,
  `descr`      VARCHAR(100) NULL,
  `price`      INT          NOT NULL,
  `maxPerson`  INT          NOT NULL,
  PRIMARY KEY (`idRoomType`),
  UNIQUE INDEX `type_UNIQUE` (`type` ASC)
)
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `hotel`.`ROOM`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hotel`.`ROOM`;

CREATE TABLE IF NOT EXISTS `hotel`.`ROOM` (
  `idRoom` INT NOT NULL AUTO_INCREMENT,
  `typeFK` INT NOT NULL,
  `number` INT NOT NULL,
  PRIMARY KEY (`idRoom`),
  INDEX `fk_ROOM_1_idx` (`typeFK` ASC),
  CONSTRAINT `fk_ROOM_1`
  FOREIGN KEY (`typeFK`)
  REFERENCES `hotel`.`ROOM_TYPE` (`idRoomType`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `hotel`.`USER_ROLE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hotel`.`USER_ROLE`;

CREATE TABLE IF NOT EXISTS `hotel`.`USER_ROLE` (
  `idUserRole` INT         NOT NULL AUTO_INCREMENT,
  `role`       VARCHAR(45) NULL,
  PRIMARY KEY (`idUserRole`),
  UNIQUE INDEX `role_UNIQUE` (`role` ASC)
)
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `hotel`.`PASSWORD`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hotel`.`PASSWORD`;

CREATE TABLE IF NOT EXISTS `hotel`.`PASSWORD` (
  `idPassword` INT         NOT NULL AUTO_INCREMENT,
  `pwdHash`    VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idPassword`)
)
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `hotel`.`USER`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hotel`.`USER`;

CREATE TABLE IF NOT EXISTS `hotel`.`USER` (
  `idUser`   INT         NOT NULL AUTO_INCREMENT,
  `name`     VARCHAR(45) NOT NULL,
  `surname`  VARCHAR(45) NOT NULL,
  `email`    VARCHAR(45) NOT NULL,
  `phoneNum` CHAR(12)    NOT NULL,
  `roleFK`   INT         NOT NULL,
  `pwdFK`    INT         NOT NULL,
  PRIMARY KEY (`idUser`),
  INDEX `fk_USER_1_idx` (`roleFK` ASC),
  INDEX `fk_USER_2_idx` (`pwdFK` ASC),
  CONSTRAINT `fk_USER_1`
  FOREIGN KEY (`roleFK`)
  REFERENCES `hotel`.`USER_ROLE` (`idUserRole`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_USER_2`
  FOREIGN KEY (`pwdFK`)
  REFERENCES `hotel`.`PASSWORD` (`idPassword`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `hotel`.`BOOK_REQUEST`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hotel`.`BOOK_REQUEST`;

CREATE TABLE IF NOT EXISTS `hotel`.`BOOK_REQUEST` (
  `idRequest`        INT      NOT NULL AUTO_INCREMENT,
  `creationDateTime` DATETIME NOT NULL,
  `userFK`           INT      NOT NULL,
  `persons`          INT      NOT NULL,
  `roomTypeFK`       INT      NOT NULL,
  `checkIn`          DATE     NOT NULL,
  `checkOut`         DATE     NOT NULL,
  `processed`        BIT      NULL     DEFAULT 0,
  PRIMARY KEY (`idRequest`),
  INDEX `fk_FORM_1_idx` (`userFK` ASC),
  INDEX `fk_FORM_2_idx` (`roomTypeFK` ASC),
  CONSTRAINT `fk_FORM_1`
  FOREIGN KEY (`userFK`)
  REFERENCES `hotel`.`USER` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_FORM_2`
  FOREIGN KEY (`roomTypeFK`)
  REFERENCES `hotel`.`ROOM_TYPE` (`idRoomType`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `hotel`.`BILL`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hotel`.`BILL`;

CREATE TABLE IF NOT EXISTS `hotel`.`BILL` (
  `idBill`           INT           NOT NULL AUTO_INCREMENT,
  `creationDateTime` DATETIME      NOT NULL,
  `bookRequestFK`    INT           NOT NULL,
  `roomFK`           INT           NOT NULL,
  `price`            DECIMAL(7, 2) NOT NULL,
  PRIMARY KEY (`idBill`),
  INDEX `fk_BILL_1_idx` (`bookRequestFK` ASC),
  INDEX `fk_BILL_2_idx` (`roomFK` ASC),
  CONSTRAINT `fk_BILL_1`
  FOREIGN KEY (`bookRequestFK`)
  REFERENCES `hotel`.`BOOK_REQUEST` (`idRequest`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_BILL_2`
  FOREIGN KEY (`roomFK`)
  REFERENCES `hotel`.`ROOM` (`idRoom`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `hotel`.`RESERVED_ROOM`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hotel`.`RESERVED_ROOM`;

CREATE TABLE IF NOT EXISTS `hotel`.`RESERVED_ROOM` (
  `idReservedRoom` INT  NOT NULL AUTO_INCREMENT,
  `roomFK`         INT  NULL,
  `checkIn`        DATE NOT NULL,
  `checkOut`       DATE NOT NULL,
  PRIMARY KEY (`idReservedRoom`),
  INDEX `roomFk_idx` (`roomFK` ASC),
  CONSTRAINT `roomFk`
  FOREIGN KEY (`roomFK`)
  REFERENCES `hotel`.`ROOM` (`idRoom`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
  ENGINE = InnoDB;

-- -----------------------------------------------------
-- Data for table `hotel`.`ROOM_TYPE`
-- -----------------------------------------------------
START TRANSACTION;
USE `hotel`;
INSERT INTO `hotel`.`ROOM_TYPE` (`idRoomType`, `type`, `descr`, `price`, `maxPerson`)
VALUES (1, 'Standard', 'Simple standard with 2 windows', 20, 1);
INSERT INTO `hotel`.`ROOM_TYPE` (`idRoomType`, `type`, `descr`, `price`, `maxPerson`)
VALUES (2, 'Lux', 'President Lux', 1000, 5);

COMMIT;

-- -----------------------------------------------------
-- Data for table `hotel`.`ROOM`
-- -----------------------------------------------------
START TRANSACTION;
USE `hotel`;
INSERT INTO `hotel`.`ROOM` (`idRoom`, `typeFK`, `number`) VALUES (1, 1, 666);
INSERT INTO `hotel`.`ROOM` (`idRoom`, `typeFK`, `number`) VALUES (2, 2, 404);
INSERT INTO `hotel`.`ROOM` (`idRoom`, `typeFK`, `number`) VALUES (3, 2, 220);

COMMIT;

-- -----------------------------------------------------
-- Data for table `hotel`.`USER_ROLE`
-- -----------------------------------------------------
START TRANSACTION;
USE `hotel`;
INSERT INTO `hotel`.`USER_ROLE` (`idUserRole`, `role`) VALUES (1, 'Admin');
INSERT INTO `hotel`.`USER_ROLE` (`idUserRole`, `role`) VALUES (2, 'Client');

COMMIT;

-- -----------------------------------------------------
-- Data for table `hotel`.`PASSWORD`
-- -----------------------------------------------------
START TRANSACTION;
USE `hotel`;
INSERT INTO `hotel`.`PASSWORD` (`idPassword`, `pwdHash`) VALUES (1, '4dcbac61c808f45d77a7bf56c1a5ca30');
INSERT INTO `hotel`.`PASSWORD` (`idPassword`, `pwdHash`) VALUES (2, '4dcbac61c808f45d77a7bf56c1a5ca30');

COMMIT;

-- -----------------------------------------------------
-- Data for table `hotel`.`USER`
-- -----------------------------------------------------
START TRANSACTION;
USE `hotel`;
INSERT INTO `hotel`.`USER` (`idUser`, `name`, `surname`, `email`, `phoneNum`, `roleFK`, `pwdFK`)
VALUES (1, 'Taras', 'Ignatenko', 'ignatenko@gmail.com', '380985563310', 1, 1);
INSERT INTO `hotel`.`USER` (`idUser`, `name`, `surname`, `email`, `phoneNum`, `roleFK`, `pwdFK`)
VALUES (2, 'Yurii', 'Onufreiv', 'yuriionufreiv@gmail.com', '380977767760', 2, 2);

COMMIT;

-- -----------------------------------------------------
-- Data for table `hotel`.`BOOK_REQUEST`
-- -----------------------------------------------------
START TRANSACTION;
USE `hotel`;
INSERT INTO `hotel`.`BOOK_REQUEST` (`idRequest`, `creationDateTime`, `userFK`, `persons`, `roomTypeFK`, `checkIn`, `checkOut`, `processed`)
VALUES (1, '2017-01-15 12:51:07', 2, 2, 1, '2017-01-15', '2017-01-21', 1);
INSERT INTO `hotel`.`BOOK_REQUEST` (`idRequest`, `creationDateTime`, `userFK`, `persons`, `roomTypeFK`, `checkIn`, `checkOut`, `processed`)
VALUES (2, '2017-01-16 19:34:08', 2, 3, 1, '2017-01-16', '2017-01-21', 0);
INSERT INTO `hotel`.`BOOK_REQUEST` (`idRequest`, `creationDateTime`, `userFK`, `persons`, `roomTypeFK`, `checkIn`, `checkOut`, `processed`)
VALUES (3, '2017-01-17 19:15:57', 2, 5, 1, '2017-01-13', '2017-01-21', 0);

COMMIT;

-- -----------------------------------------------------
-- Data for table `hotel`.`BILL`
-- -----------------------------------------------------
START TRANSACTION;
USE `hotel`;
INSERT INTO `hotel`.`BILL` (`idBill`, `creationDateTime`, `bookRequestFK`, `roomFK`, `price`)
VALUES (1, '2017-01-16 19:34:08', 1, 1, 120);

COMMIT;


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;
