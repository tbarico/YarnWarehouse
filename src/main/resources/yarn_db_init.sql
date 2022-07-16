-- Creating and initalizing Yarn Database --
 
 DROP DATABASE IF EXISTS `Tarico_Yarn_Warehouse`;
 CREATE DATABASE `Tarico_Yarn_Warehouse`;
 USE `Tarico_yarn_warehouse`;
 
 -- Create the different tables --
 
 CREATE TABLE `category` (
  `category_id` int unsigned NOT NULL,
  `name` enum('Yarn','Knitting Needles') NOT NULL,
  `description` tinytext,
  PRIMARY KEY (`category_id`));

CREATE TABLE `item` (
  `item_id` int unsigned NOT NULL AUTO_INCREMENT,
  `category_id` int unsigned NOT NULL,
  `price` double NOT NULL,
  `total_quantity` int unsigned DEFAULT NULL,
  PRIMARY KEY (`item_id`),
  KEY `category_id_idx` (`category_id`),
  CONSTRAINT `category_id` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`));

CREATE TABLE `tarico_yarn_warehouse`.`needle_size` (
  `size_id` INT UNSIGNED NOT NULL,
  `mm` DECIMAL NOT NULL,
  `US` INT UNSIGNED NULL,
  `UK` INT UNSIGNED NULL,
  PRIMARY KEY (`size_id`));
  
CREATE TABLE `tarico_yarn_warehouse`.`needle` (
  `needle_item_id` INT UNSIGNED NOT NULL,
  `brand` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NULL,
  `size_id` INT UNSIGNED NOT NULL,
  `length` INT NULL,
  `type` ENUM("Straight", "Circular", "Double-Pointed") NULL,
  `price` DECIMAL NOT NULL,
  `items_per_lot` INT UNSIGNED NOT NULL,
  `material` ENUM("Plastic", "Wood", "Metal") NULL,
  PRIMARY KEY (`needle_item_id`),
  INDEX `size_id_idx` (`size_id` ASC) VISIBLE,
  CONSTRAINT `size_id`
    FOREIGN KEY (`size_id`)
    REFERENCES `tarico_yarn_warehouse`.`needle_size` (`size_id`));
    
CREATE TABLE `tarico_yarn_warehouse`.`yarn` (
  `yarn_item_id` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `brand` VARCHAR(45) NOT NULL,
  `color_way` VARCHAR(45) NULL,
  `description` TINYTEXT NULL,
  `fiber` ENUM("Wool", "SW", "Acrylic", "Cotton", "Alpaca", "Mohair", "Cashmere") NULL,
  `items_per_lot` INT UNSIGNED NOT NULL,
  `meters` INT UNSIGNED NULL,
  `grams` INT UNSIGNED NULL,
  `price` DECIMAL NOT NULL,
  `weight` ENUM("0", "1", "2", "3", "4", "5", "6", "7") NULL,
  PRIMARY KEY (`yarn_item_id`));

CREATE TABLE `tarico_yarn_warehouse`.`address` (
  `address_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `street_line_one` VARCHAR(45) NOT NULL,
  `street_line_two` VARCHAR(45) NULL,
  `state` ENUM("AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY", "MA", "MI", "MN", "MS", "MO") NOT NULL,
  `city` VARCHAR(45) NOT NULL,
  `zip` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`address_id`));
  
CREATE TABLE `location` (
  `location_id` int unsigned NOT NULL AUTO_INCREMENT,
  `type` enum('Store','Warehouse') NOT NULL,
  `curr_capacity` int unsigned DEFAULT NULL,
  `total_capacity` int unsigned NOT NULL,
  `address_id` int unsigned NOT NULL,
  `phone_number` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`location_id`),
  UNIQUE KEY `phone_number_UNIQUE` (`phone_number`),
  KEY `address_id_idx` (`address_id`),
  CONSTRAINT `address_id` FOREIGN KEY (`address_id`) REFERENCES `address` (`address_id`)
);

CREATE TABLE `department` (
  `department_id` int unsigned NOT NULL,
  `location_id` int unsigned NOT NULL,
  `dept_name` varchar(45) NOT NULL,
  `head` int unsigned DEFAULT NULL,
  `phone_number` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`department_id`),
  UNIQUE KEY `phone_number_UNIQUE` (`phone_number`),
  KEY `location_id_idx` (`location_id`),
  CONSTRAINT `location_id` FOREIGN KEY (`location_id`) REFERENCES `location` (`location_id`)
);

CREATE TABLE `employee` (
  `employee_id` int unsigned NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `deptartment_id` int unsigned DEFAULT NULL,
  `phone_numnber` varchar(45) DEFAULT NULL,
  `supervisor` int unsigned NOT NULL,
  `employee_location` int unsigned NOT NULL,
  PRIMARY KEY (`employee_id`),
  KEY `location_id_idx` (`employee_location`),
  KEY `supervisor_idx` (`supervisor`),
  CONSTRAINT `employee_location` FOREIGN KEY (`employee_location`) REFERENCES `location` (`location_id`),
  CONSTRAINT `supervisor` FOREIGN KEY (`supervisor`) REFERENCES `employee` (`employee_id`)
);

ALTER TABLE `tarico_yarn_warehouse`.`location` 
ADD COLUMN `manager_id` INT UNSIGNED NOT NULL AFTER `phone_number`,
ADD INDEX `manager_id_idx` (`manager_id` ASC) VISIBLE;
;
ALTER TABLE `tarico_yarn_warehouse`.`location` 
ADD CONSTRAINT `manager_id`
  FOREIGN KEY (`manager_id`)
  REFERENCES `tarico_yarn_warehouse`.`employee` (`employee_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

CREATE TABLE `orders` (
  `order_id` int unsigned NOT NULL AUTO_INCREMENT,
  `time_ordered` datetime NOT NULL,
  `item_id` int unsigned NOT NULL,
  `orderer` int unsigned NOT NULL,
  `shipped` tinyint DEFAULT '0',
  `quantity` int unsigned NOT NULL,
  `date_ordered` datetime NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `item_id_idx` (`item_id`),
  KEY `employee_id_idx` (`orderer`),
  CONSTRAINT `employee_id` FOREIGN KEY (`orderer`) REFERENCES `employee` (`employee_id`),
  CONSTRAINT `item_id` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`)
);

CREATE TABLE `shipments` (
  `shipment_id` int unsigned NOT NULL AUTO_INCREMENT,
  `order_id` int unsigned NOT NULL,
  `time_shipped` datetime NOT NULL,
  `date_shipped` datetime NOT NULL,
  `time_arrived` datetime DEFAULT NULL,
  `date_arrived` datetime DEFAULT NULL,
  `dest_id` int unsigned NOT NULL,
  `orig_id` int unsigned NOT NULL,
  `total_price` double NOT NULL,
  PRIMARY KEY (`shipment_id`),
  KEY `order_id_idx` (`order_id`),
  KEY `dest_id_idx` (`dest_id`),
  KEY `orig_id_idx` (`orig_id`),
  CONSTRAINT `dest_id` FOREIGN KEY (`dest_id`) REFERENCES `location` (`location_id`),
  CONSTRAINT `order_id` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  CONSTRAINT `orig_id` FOREIGN KEY (`orig_id`) REFERENCES `location` (`location_id`)
);

CREATE TABLE `inventory` (
  `inventory_id` int unsigned NOT NULL AUTO_INCREMENT,
  `locat_id` int unsigned NOT NULL,
  `item_num` int unsigned NOT NULL,
  `quantity` int unsigned DEFAULT '0',
  PRIMARY KEY (`inventory_id`),
  KEY `item_id_idx` (`item_num`),
  KEY `locat_id_idx` (`locat_id`),
  CONSTRAINT `item_num` FOREIGN KEY (`item_num`) REFERENCES `item` (`item_id`),
  CONSTRAINT `locat_id` FOREIGN KEY (`locat_id`) REFERENCES `location` (`location_id`)
);
