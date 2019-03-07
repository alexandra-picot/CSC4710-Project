package edu.wsu;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/createdb")
public class CreateDb extends HttpServlet {

    private String SQL_DRIVER = "com.mysql.jdbc.Driver";
    private String DATABASE_URL = "jdbc:mysql://localhost:3306/sampledb";
    private String USER = "john";
    private String PASSWORD = "pass1234";

    private Connection _dbConnection = null;

    private void createAuthorsTable() throws Exception {
        Statement createTable = _dbConnection.createStatement();

        createTable.execute("CREATE TABLE IF NOT EXISTS authors (" +
                "email VARCHAR(255) NOT NULL," +
                "name VARCHAR(150) NOT NULL," +
                "affiliation VARCHAR(100) NOT NULL," +
                "PRIMARY KEY (email)" +
                ")"
        );

        createTable.executeUpdate("DELETE FROM authors");

        createTable.executeUpdate("INSERT INTO authors VALUES " +
                "('jean.prevers@gmail.com', 'Jean Prevers', 'Computer Science')," +
                "('victor.hugo@gmail.com', 'Victor Hugo', 'Computer Science')," +
                "('christopher.paolini@gmail.com', 'Christopher Paolini', 'Computer Science')," +
                "('florent.musse@gmail.com', 'florent Musse', 'Computer Science')," +
                "('jean-pierre.dupont@gmail.com', 'Jean-Pierre Dupont', 'Computer Science')," +
                "('jonathan.jean@gmail.com', 'Jonathan Jean', 'Computer Science')," +
                "('corentin.grandmaire@gmail.com', 'Corentin Grandmaire', 'Computer Science')," +
                "('steve.martins@gmail.com', 'Steve Martins', 'Computer Science')," +
                "('nicolas.sarcozy@gmail.com', 'Nicolas Sarcozy', 'Computer Science')," +
                "('francois.hollande@gmail.com', 'François Hollande', 'Computer Science')," +
                "('jacques.chirac@gmail.com', 'Jacques Chirac', 'Computer Science')," +
                "('claude.francois@gmail.com', 'Claude François', 'Computer Science')," +
                "('jean-jacques.goldman@gmail.com', 'Jean-Jacques Goldman', 'Computer Science')," +
                "('seongwook.chae@gmail.com', 'Seongwook Chae', 'Computer Science')," +
                "('florian.oliverez@gmail.com', 'Florian Oliverez', 'Computer Science')"
        );
    }

    private void createPapersTable() throws Exception {
        Statement createTable = _dbConnection.createStatement();

        createTable.execute("CREATE TABLE IF NOT EXISTS papers (" +
                "paperid INTEGER NOT NULL AUTO_INCREMENT," +
                "title VARCHAR(200) NOT NULL," +
                "abstract TEXT NOT NULL," +
                "pdf VARCHAR(255) NOT NULL," +
                "PRIMARY KEY (paperid)" +
                ")"
        );

        createTable.executeUpdate("DELETE FROM papers");

        createTable.executeUpdate("INSERT INTO papers (title, abstract, pdf) VALUES " +
                "('Discussion of AIs future', " +
                "'In this article, we are going to talk about the future of AI, what we should expect of AI in the next 20 years, what domains will be the most successful and how AI will continue to improve humans life', " +
                "'home/papers/paper1')," +
                "('Universe and its mysteries', " +
                "'This paper discuss about the universe, what humans discovered about it in the past centuries and what we are looking for in the current days. It will discuss the hopes and theory that scientists have about the universe', " +
                "'home/papers/paper')," +
                "('Impact of global warning on humans', " +
                "'Global warning is real, it has been proven hundreds of times by hundreds of studies. In this paper, we will discuss about how human will have to evolve to adapt to the coming changes of our planet.', " +
                "'home/papers/paper')," +
                "('Evolution of tech in people s life', " +
                "'Technology entered in peoples life only a few decades ago, and yet it changes our life so much that nowadays, few peoples could live without all our devices. We will discuss how those technologies will continue to change our life in the near and far future.', '" +
                "home/papers/paper')," +
                "('Natural algorithms and its applications', " +
                "'We will discuss the application of natural algorithms', " +
                "'home/papers/paper')," +
                "('Cultural algorithms and its applications', " +
                "'We will discuss the application of cultural algorithms', " +
                "'home/papers/paper')," +
                "('Pathfinding algorithms and their applications', " +
                "'This paper discuss of several pathfinding algorithms such as A* or Dijkstras. We will see how those algorithms helped in a lot of situation and are used in thousands of everyday life tools.', " +
                "'home/papers/paper')," +
                "('Tesla and SpaceX, the begginning of a revolution', " +
                "'Tesla and SpaceX are two companies created by the world famous Elon Musk. In the last 5 years, those two companies transformed respectively the car and space industries. Two industry that weren t evolving or barely. We will discuss how Elon Musk was able to do that and what is the future of those revolutions.' , " +
                "'home/papers/paper')," +
                "('AI and music, the future of the music industry', " +
                "'Recently, a first time ever, a google AI was able to create its own music. In this paper, we will try to see if AI could change the music industry and how.', " +
                "'home/papers/paper')," +
                "('Warning and dangers of AI', " +
                "'AI is in our lives, we use them inconsciously everyday, we talk a lot about how it improved everyones lives. But there are potential dangers in AI and that is what this paper will discuss about.', " +
                "'home/papers/paper')"
        );
    }

    private void createPaperAuthorsJunctionTable() throws Exception {
        Statement createTable = _dbConnection.createStatement();

        createTable.execute("CREATE TABLE IF NOT EXISTS paper_authors (" +
                "paper_id INTEGER NOT NULL," +
                "author_id VARCHAR(255) NOT NULL," +
                "contribution_significance INTEGER NOT NULL," +
                "PRIMARY KEY (paper_id, author_id)," +
                "FOREIGN KEY (paper_id) REFERENCES papers (paperid) " +
                "ON UPDATE CASCADE " +
                "ON DELETE CASCADE," +
                "FOREIGN KEY (author_id) REFERENCES authors (email) " +
                "ON UPDATE CASCADE " +
                "ON DELETE CASCADE" +
                ")"
        );

        createTable.executeUpdate("DELETE FROM paper_authors");

        createTable.executeUpdate("INSERT INTO paper_authors VALUES " +
                "(1, 'jean.prevers@gmail.com', 1)," +
                "(1, 'nicolas.sarcozy@gmail.com', 2)," +
                "(2, 'christopher.paolini@gmail.com', 1)," +
                "(3, 'steve.martins@gmail.com', 1)," +
                "(4, 'jean.prevers@gmail.com', 2)," +
                "(4, 'corentin.grandmaire@gmail.com', 1)," +
                "(4, 'christopher.paolini@gmail.com', 3)," +
                "(4, 'florian.oliverez@gmail.com', 4)," +
                "(5, 'jonathan.jean@gmail.com', 1)," +
                "(6, 'florian.oliverez@gmail.com', 2)," +
                "(7, 'claude.francois@gmail.com', 1)," +
                "(7, 'jacques.chirac@gmail.com', 2)," +
                "(8, 'seongwook.chae@gmail.com', 3)," +
                "(9, 'jean-jacques.goldman@gmail.com', 1)," +
                "(10, 'corentin.grandmaire@gmail.com', 3)," +
                "(10, 'victor.hugo@gmail.com', 2)," +
                "(10, 'francois.hollande@gmail.com', 1)," +
                "(8, 'victor.hugo@gmail.com', 1)," +
                "(8, 'corentin.grandmaire@gmail.com', 2)," +
                "(8, 'florent.musse@gmail.com', 4)," +
                "(6, 'jean-pierre.dupont@gmail.com', 1)"
        );
    }

    private void createPCMembersTable() throws Exception {
        Statement createTable = _dbConnection.createStatement();

        createTable.execute("CREATE TABLE IF NOT EXISTS pc_members (" +
                "email VARCHAR(255) NOT NULL," +
                "name VARCHAR(150) NOT NULL," +
                "PRIMARY KEY (email)" +
                ")"
        );

        createTable.executeUpdate("DELETE FROM pc_members");

        createTable.executeUpdate("INSERT INTO pc_members VALUES " +
                "('robert.reynold@gmail.com', 'Robert Reynolds')," +
                "('charles.degaulle@gmail.com', 'Charles De-Gaulle')," +
                "('emmanuel.macron@gmail.com', 'Emmanuel Macron')," +
                "('luc.besson@gmail.com', 'Luc Besson')," +
                "('tylor.swift@gmail.com', 'Tylor Swift')," +
                "('eli.semoun@gmail.com', 'Eli Semoun')," +
                "('mimi.mathie@gmail.com', 'Mimi Mathie')," +
                "('j.k.rowling@gmail.com', 'J.K. Rowling')," +
                "('neil.degrasse.tyson@gmail.com', 'Neil deGrasse Tyson')," +
                "('julie.dupuit@gmail.com', 'Julie Dupuit')"
        );
    }

    private void createReportsTable() throws Exception {
        Statement createTable = _dbConnection.createStatement();

        createTable.execute("CREATE TABLE IF NOT EXISTS reports (" +
                "reportid INTEGER NOT NULL AUTO_INCREMENT," +
                "comment TEXT NULL DEFAULT NULL," +
                "recommendation CHAR(1) NOT NULL DEFAULT 'P'," +
                "submission_date DATE NULL DEFAULT NULL," +
                "paper_id INTEGER NOT NULL," +
                "pc_member_id VARCHAR(255) NOT NULL," +
                "PRIMARY KEY (reportid)," +
                "FOREIGN KEY (paper_id) REFERENCES papers (paperid) " +
                "ON UPDATE CASCADE " +
                "ON DELETE CASCADE," +
                "FOREIGN KEY (pc_member_id) REFERENCES pc_members (email)" +
                "ON UPDATE CASCADE " +
                "ON DELETE CASCADE," +
                "CHECK ( recommendation IN ('A', 'R', 'P'))" +
                ")"
        );

        createTable.executeUpdate("DELETE FROM reports");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            Class.forName(SQL_DRIVER);

            _dbConnection = DriverManager.getConnection(
                    DATABASE_URL,
                    USER,
                    PASSWORD);

            createAuthorsTable();
            createPapersTable();
            createPaperAuthorsJunctionTable();
            createPCMembersTable();
            createReportsTable();
            _dbConnection.close();
        } catch(Exception e) {
            System.out.println(e);
        }

        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        writer.println("<html>");
        writer.println("<head>");
        writer.println("<title>You've created the database and filled it with data</title>");
        writer.println("</head>");
        writer.println("<body bgcolor=white>");
        writer.println("<h1>You've created the database and filled it with data</h1>");
        writer.println("<p>");
        writer.println("<a href=\"index.html\">Go back to home</a>");
        writer.println("</body>");
        writer.println("</html>");
    }
}
