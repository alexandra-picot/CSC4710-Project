CSC 4710 Project, part 2

Group members:

gr9185	Vincent PICOT		gr9185@wayne.edu
ft7834	Mrinnal Muralidhar	mrinnal.muralidhar@wayne.edu


Work separation Part1:

Vincent:

		- Setup of both our development environments
		- Design of the ER diagram 			(SQLTables.png, projectP1.png)
		- Initialization of the database - Creation of the tables and population of the tables, SQL statements and Java/Html code - (InitializeDB.java, initialize-db.jsp)
		- Creation of the class DBConnection, global java class to connect to the database		(DBConnection.java)
		- Creation of a JSTL tag - base_site - to have common site template for every webpage		(base_site.tag)
		- Add boostrap4 (https://getbootstrap.com) to the project, especially in the base_site tag	(base_site.tag)
		- Conversion of all HTML and JSP files into JSP files that implements the base_site JSTL tag	(*.html, *.jsp
		- Conversion and all Java Scriptlet (Java code inside JSP files) into JSTL code			(*.jsp)
		- All the advanced and standard search in the paper-list page (points 4/5/6/9/10 of the subject)	(PaperList.java, paper-list.jsp, PaperListFilterHandling.js)
		- Add/Edit/Delete a paper from the list of papers							(AddPaper.java, EditPaper.java, DeletePaper.java, Author.java, Person.java, PaperAuthor.java, AddEditPaperCommon.java, add-paper.jsp, edit-paper.jsp, add_edit_paper_form.tag, successful-paper-add.jsp, successful-paper-edit.jsp)
		- Add test data into the Database to test part2 requirements					(InitializeDB.java)


		- !HAS BEEN DELETED! Save reviewers into the database + form validation in JavaScript on paper-details.jsp


Mrinnal and Vincent:
		- Display of the paper list, Files: PaperList.java, paper-list.jsp, paperlist.css


		- !HAS BEEN DELETED! Display of the papers' details, Files: PaperDetails.java, paper-details.jsp




HOW TO INSTALL:

    - Be sure to have MySql installed and running on your platform

    - Create a database "sampledb", and a user 'john' with password 'pass1234' in MySql, and give 'john' access to the newly created database
      (If you want to connect to another database, and/or change the user, you can go in the DBConnection class and change the global variables)

    - Be sure to have Tomcat (version 9+) installed and Java SDK/JRE 8+

    - Put the .war file (csc4710_gr9185ft7834_part1.war) inside the webapps folder of tomcat

    - Start Tomcat

    - Go to: localhost:8080/csc4710_gr9185ft7834_part1/

    - You're all set, have fun!


