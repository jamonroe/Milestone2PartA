SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `coursecamp` DEFAULT CHARACTER SET utf8 ;
USE `coursecamp` ;

-- -----------------------------------------------------
-- Table `coursecamp`.`course_details`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `coursecamp`.`course_details` (
  `course_id` INT(11) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(250) NOT NULL,
  `description` TEXT NOT NULL,
  `course_link` TEXT NOT NULL,
  `start_date` DATE NOT NULL,
  `duration` INT(11) NOT NULL,
  `category` VARCHAR(250) NOT NULL,
  `university` VARCHAR(250) NOT NULL,
  `instructor` VARCHAR(250) NOT NULL,
  PRIMARY KEY (`course_id`, `title`, `university`, `instructor`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
