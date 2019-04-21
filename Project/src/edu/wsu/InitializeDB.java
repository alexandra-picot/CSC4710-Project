package edu.wsu;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet("/initialize-db")
public class InitializeDB extends HttpServlet {

    private DBConnection _dbConnection = null;

    private void dropTables() throws SQLException {
        Statement createTable = _dbConnection.createStatement();

        createTable.execute("DROP TABLE IF EXISTS paper_authors");
        createTable.execute("DROP TABLE IF EXISTS reports");
        createTable.execute("DROP TABLE IF EXISTS authors");
        createTable.execute("DROP TABLE IF EXISTS papers");
        createTable.execute("DROP TABLE IF EXISTS pc_members");
        createTable.execute("DROP VIEW IF EXISTS acceptedpaper");
        createTable.execute("DROP VIEW IF EXISTS rejectedpaper");
    }

    private void createAuthorsTable() throws SQLException {
        Statement createTable = _dbConnection.createStatement();

        createTable.execute("CREATE TABLE IF NOT EXISTS authors (" +
                "email VARCHAR(255) NOT NULL," +
                "first_name VARCHAR(100) NOT NULL," +
                "last_name VARCHAR(100) NOT NULL," +
                "affiliation VARCHAR(100) NOT NULL," +
                "PRIMARY KEY (email)" +
                ")"
        );

        createTable.executeUpdate("DELETE FROM authors");

        createTable.executeUpdate("INSERT INTO authors VALUES " +
                "('jean.prevers@gmail.com', 'jean', 'prevers', 'Computer Science')," +
                "('victor.hugo@gmail.com', 'victor', 'hugo', 'Computer Science')," +
                "('christopher.paolini@gmail.com', 'christopher', 'paolini', 'Computer Science')," +
                "('florent.musse@gmail.com', 'florent', 'musse', 'Computer Science')," +
                "('jean-pierre.dupont@gmail.com', 'jean-pierre', 'dupont', 'Computer Science')," +
                "('jonathan.jean@gmail.com', 'jonathan', 'jean', 'Computer Science')," +
                "('corentin.grandmaire@gmail.com', 'corentin', 'grandmaire', 'Computer Science')," +
                "('steve.martins@gmail.com', 'steve', 'martins', 'Computer Science')," +
                "('nicolas.sarcozy@gmail.com', 'nicolas', 'sarcozy', 'Computer Science')," +
                "('francois.hollande@gmail.com', 'françois', 'hollande', 'Computer Science')," +
                "('jacques.chirac@gmail.com', 'jacques', 'chirac', 'Computer Science')," +
                "('claude.francois@gmail.com', 'claude', 'françois', 'Computer Science')," +
                "('seongwook.chae@gmail.com', 'seongwook', 'chae', 'Computer Science')," +
                "('jean-jacques.goldman@gmail.com', 'jean-jacques', 'goldman', 'Computer Science')," +
                "('mike.fotouhi@gmail.com', 'mike', 'fotouhi', 'Computer Science')," +
                "('mikeish.ishfotouhi@gmail.com', 'mikeish', 'ishfotouhi', 'Computer Science')," +
                "('ishmike.fotouhish@gmail.com', 'ishmike', 'fotouhish', 'Computer Science')," +
                "('fotouhi.mike@gmail.com', 'fotouhi', 'mike', 'Computer Science')," +
                "('ishfotouhi.mikeish@gmail.com', 'ishfotouhi', 'mikeish', 'Computer Science')," +
                "('fotouhish.ishmike@gmail.com', 'fotouhish', 'ishmike', 'Computer Science')," +
                "('yewon.lu@gmail.com', 'yewon', 'lu', 'Computer Science')," +
                "('florian.oliverez@gmail.com', 'florian', 'oliverez', 'Computer Science')"
        );
    }

    private void createPapersTable() throws SQLException {
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
                "('Written by fotouhi single author', " +
                "'This is a test paper to prove the capability of the advanced search. This paper should appear with a search for fotouhi (lastname), single author', " +
                "'home/papers/paper')," +
                "('Written by fotouhi single author 2', " +
                "'This is a test paper to prove the capability of the advanced search. This paper should appear with a search for fotouhi (lastname), single author', " +
                "'home/papers/paper')," +
                "('Written by fotouhi and lu', " +
                "'This is a test paper to prove the capability of the advanced search. This paper should appear with a search for co-author fotouhi and lu', " +
                "'home/papers/paper')," +
                "('Written by fotouhi and oliverez', " +
                "'This is a test paper to prove the capability of the advanced search. This paper should appear with a search for fotouhi first author', " +
                "'home/papers/paper')," +
                "('Written by fotouhish', " +
                "'This is a test paper to prove the capability of the advanced search. This paper should appear with a search for fotouhi (lastname) single other non-exact search', " +
                "'home/papers/paper')," +
                "('Written by fotouhish (firstname)', " +
                "'This is a test paper to prove the capability of the advanced search. This paper should appear with a search for fotouhi (firstname) single other non-exact search', " +
                "'home/papers/paper')," +
                "('Warning and dangers of AI', " +
                "'AI is in our lives, we use them inconsciously everyday, we talk a lot about how it improved everyones lives. But there are potential dangers in AI and that is what this paper will discuss about.', " +
                "'home/papers/paper')," +
                "('Rejected and reviewed by matt (firstname)', " +
                "'This is a test paper to prove the capability of the advanced search. This paper should appear with a search for rejected paper with Matt as reviewer', " +
                "'home/papers/paper')," +
                "('Accepted and reviewed by matt (firstname)', " +
                "'This is a test paper to prove the capability of the advanced search. This paper should appear with a search for accepted paper with Matt as reviewer', " +
                "'home/papers/paper')," +
                "('Rejected by matt (firstname) and john (firstname)', " +
                "'This is a test paper to prove the capability of the advanced search. This paper should appear with a search for rejected by both matt and john', " +
                "'home/papers/paper')," +
                "('Rejected and reviewed by Taylor (firstname)', " +
                "'This is a test paper to prove the capability of the advanced search. This paper should appear with a search for rejected paper with Taylor as reviewer', " +
                "'home/papers/paper')"
        );
    }



    private void createPaperAuthorsJunctionTable() throws SQLException {
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
                "(16, 'corentin.grandmaire@gmail.com', 3)," +
                "(16, 'victor.hugo@gmail.com', 2)," +
                "(16, 'francois.hollande@gmail.com', 1)," +
                "(8, 'victor.hugo@gmail.com', 1)," +
                "(8, 'corentin.grandmaire@gmail.com', 2)," +
                "(8, 'florent.musse@gmail.com', 4)," +
                "(10, 'mike.fotouhi@gmail.com', 1)," +
                "(11, 'mike.fotouhi@gmail.com', 1)," +
                "(12, 'mike.fotouhi@gmail.com', 2)," +
                "(12, 'yewon.lu@gmail.com', 1)," +
                "(13, 'mike.fotouhi@gmail.com', 1)," +
                "(13, 'florian.oliverez@gmail.com', 2)," +
                "(14, 'ishmike.fotouhish@gmail.com', 1)," +
                "(15, 'fotouhish.ishmike@gmail.com', 1)," +
                "(17, 'fotouhi.mike@gmail.com', 1)," +
                "(18, 'jacques.chirac@gmail.com', 1)," +
                "(19, 'steve.martins@gmail.com', 1)," +
                "(20, 'fotouhi.mike@gmail.com', 1)," +
                "(6, 'jean-pierre.dupont@gmail.com', 1)"
        );
    }

    private void createPCMembersTable() throws SQLException {
        Statement createTable = _dbConnection.createStatement();

        createTable.execute("CREATE TABLE IF NOT EXISTS pc_members (" +
                "email VARCHAR(255) NOT NULL," +
                "first_name VARCHAR(100) NOT NULL," +
                "last_name VARCHAR(100) NOT NULL," +
                "PRIMARY KEY (email)" +
                ")"
        );

        createTable.executeUpdate("DELETE FROM pc_members");

        createTable.executeUpdate("INSERT INTO pc_members VALUES " +
                "('robert.reynold@gmail.com', 'robert', 'reynolds')," +
                "('charles.degaulle@gmail.com', 'charles', 'de-gaulle')," +
                "('emmanuel.macron@gmail.com', 'emmanuel', 'macron')," +
                "('luc.besson@gmail.com', 'luc', 'besson')," +
                "('taylor.swift@gmail.com', 'taylor', 'swift')," +
                "('eli.semoun@gmail.com', 'eli', 'semoun')," +
                "('mimi.mathie@gmail.com', 'mimi', 'mathie')," +
                "('j.k.rowling@gmail.com', 'j.k.', 'rowling')," +
                "('neil.degrasse.tyson@gmail.com', 'neil', 'degrasse tyson')," +
                "('matt.bank@gmail.com', 'matt', 'bank')," +
                "('john.cownie@gmail.com', 'john', 'cownie')," +
                "('julie.dupuit@gmail.com', 'julie', 'dupuit')"
        );
    }

    private void createReportsTable() throws SQLException {
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

        createTable.executeUpdate("INSERT INTO reports (recommendation, paper_id, pc_member_id) VALUES " +
                "('P', 3, 'robert.reynold@gmail.com')," +
                "('R', 3, 'luc.besson@gmail.com')," +
                "('P', 3, 'julie.dupuit@gmail.com')," +
                "('R', 17, 'robert.reynold@gmail.com')," +
                "('R', 17, 'matt.bank@gmail.com')," +
                "('R', 18, 'matt.bank@gmail.com')," +
                "('A', 18, 'luc.besson@gmail.com')," +
                "('A', 18, 'taylor.swift@gmail.com')," +
                "('P', 19, 'eli.semoun@gmail.com')," +
                "('R', 19, 'matt.bank@gmail.com')," +
                "('R', 19, 'john.cownie@gmail.com')," +
                "('A', 20, 'matt.bank@gmail.com')," +
                "('R', 20, 'john.cownie@gmail.com')," +
                "('R', 20, 'taylor.swift@gmail.com')"
        );
    }


    private void createAcceptedPaperTable() throws SQLException {
        Statement createView = _dbConnection.createStatement();

        createView.execute("CREATE VIEW AcceptedPaper(paper_id) AS " +
                "SELECT paper_id FROM reports WHERE recommendation = 'A' GROUP BY paper_id HAVING COUNT(*) >= 2;");
    }

    private void createRejectedPaperTable() throws SQLException {
        Statement createView = _dbConnection.createStatement();

        createView.execute("CREATE VIEW RejectedPaper(paper_id) AS " +
                "SELECT paper_id FROM reports WHERE recommendation = 'R' GROUP BY paper_id HAVING COUNT(*) >= 2;");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        _dbConnection = new DBConnection();

        try {
            dropTables();
            createAuthorsTable();
            createPapersTable();
            createPaperAuthorsJunctionTable();
            createPCMembersTable();
            createReportsTable();
            createAcceptedPaperTable();
            createRejectedPaperTable();
        } catch (Exception e) {
            System.out.println(e);
        }

        _dbConnection.closeConnection();

        req.getRequestDispatcher("/initialize-db.jsp").forward(req, resp);
    }
}