CSC 4710 Project, part 1

Group members:

gr9185	Vincent PICOT		gr9185@wayne.edu
ft7834	Mrinnal Muralidhar	mrinnal.muralidhar@wayne.edu


Work separation Part1:

Vincent:    - Setup of both our development environments
		    - Design of the ER diagram, Files: SQLTables.png, projectP1.png
		    - Initialization of the database (Creation of the tables and population of the tables) (SQL statements and Java/Html code) Files: InitializeDB.java, initialize-db.jsp
		    - Creation of the class DBConnection, global java class to connect to the database


Mrinnal and Vincent:
            - Display of the paper list, Files: PaperList.java, paper-list.jsp, paperlist.css
            - Display of the papers' details, Files: PaperDetails.java, paper-details.jsp


HOW TO INSTALL:

    - Be sure to have MySql installed and running on your platform

    - Create a database "sampledb", and a user 'john' with password 'pass1234' in MySql, and give 'john' access to the newly created database
      (If you want to connect to another database, and/or change the user, you can go in the DBConnection class and change the global variables)

    - Be sure to have Tomcat (version 9+) installed and Java SDK/JRE 8+

    - Put the .war file (csc4710_gr9185ft7834_part1.war) inside the webapps folder of tomcat

    - Start Tomcat

    - Go to: localhost:8080/csc4710_gr9185ft7834_part1/

    - You're all set, have fun!


