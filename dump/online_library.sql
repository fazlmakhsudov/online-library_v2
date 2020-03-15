create database new_db;
use new_db;

CREATE TABLE `author` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `second_name` varchar(45) NOT NULL,
  `date_of_birth` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;



INSERT INTO `author` VALUES (1,'Samoa','Petrov','1989-12-01'),(2,'Hayrullo','Gafurov','1978-03-12'),(3,'Rustam','Bobrov','2012-01-23'),(4,'Jabor Rasulov','Fayzullo','2013-08-12'),(5,'Umed','Zafar Bob','2008-02-04'),(6,'Shamsullo','Haydar Sheralli','1954-08-25'),(7,'Sharif','Emomali','1989-09-03');



CREATE TABLE `book` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `description` varchar(150) DEFAULT NULL,
  `date_of_publish` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

INSERT INTO `book` VALUES (5,'Gamon','i have met many friends frin Toronto city. I thought that we can grow fast and have been very succussful. So now I thing','2012-11-02'),(6,'Dumen','i have met many friends frin Toronto city. I thought that we can grow fast and have been very succussful. So now I thing','1987-01-11'),(7,'Bid todat','somethinkg there to happends','2012-05-03'),(8,'Hash tag','I so wondered man','2015-09-12'),(9,'Uni conr','california table sell somethidns','2018-11-13'),(10,'There happend','Wonderful world is gonna to be drawn','2019-04-16'),(11,'Here we go','yes here we go people man somethid there to happer','2008-07-16');


CREATE TABLE `relation_table` (
  `book_id` int(11) NOT NULL,
  `author_id` int(11) NOT NULL,
  `relation_tablecol` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`relation_tablecol`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;

INSERT INTO `relation_table` VALUES (5,3,1),(5,2,2),(6,1,3),(6,1,4),(7,3,5),(8,3,6),(8,2,7),(9,2,8),(10,1,9),(11,1,10),(6,4,11),(6,5,12),(6,6,13),(6,7,14),(8,2,15),(8,5,16),(8,6,17),(9,1,18),(9,5,19),(9,6,20),(10,7,21),(10,7,22),(11,6,23),(11,3,24);
