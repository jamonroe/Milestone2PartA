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
  `description` TEXT,
  `course_link` TEXT,
  `start_date` VARCHAR(250),
  `duration` INT(11),
  `university` VARCHAR(250),
  `instructor` VARCHAR(250),
  `course_image` TEXT,
  PRIMARY KEY (`course_id`, `title`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
