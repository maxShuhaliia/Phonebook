# Phonebook

phonebook is a spring web project which combine such operation as: create a new contact, sorting, search, edit existing item and remove them.

Besides application support authorization(login, logout, register), deatails about user and contacts store in database using JDBC.

For each user shown only their own list of contacts,

Both front-end and back-end were integrated with check on validity(check on correct data, check on existing login for registration). 



In development process i used annotaion and java configuration approach and technologies:

front-end: HTML, CSS, JavaScript, JQuery, Ajax, Bootstrap;

back-end: Spring(MVC, Security, DAO, Boot), MySql(JDBC);

unit-testing : (JUnit, Mockito);

project management and comprehension tool: Maven;

Container: Tomcat;

IDEs : Spring Tool Suite (Eclipse), WebStorm;



                                                  scripts for MySQL:

CREATE TABLE `user` (
   `login` varchar(15) NOT NULL,
   `name` varchar(15) DEFAULT NULL,
   `surname` varchar(15) DEFAULT NULL,
   `middlename` varchar(15) DEFAULT NULL,
   `password` varchar(15) DEFAULT NULL,
   `role` varchar(15) DEFAULT NULL,
   PRIMARY KEY (`login`)
 );
 
CREATE TABLE `contacts` (
   `id_contact` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
   `login` varchar(15) NOT NULL,
   `firstname` varchar(15) NOT NULL,
   `lastname` varchar(15) NOT NULL,
   `middlename` varchar(15) NOT NULL,
   `mobilephone` varchar(20) NOT NULL,
   `homephone` varchar(15) DEFAULT NULL,
   `address` varchar(50) DEFAULT NULL,
   `email` varchar(30) DEFAULT NULL,
   PRIMARY KEY (`id_contact`),
   KEY `login_fk_user` (`login`),
   CONSTRAINT `login_fk_user` FOREIGN KEY (`login`) REFERENCES `user` (`login`)
 );
 
 


