package edu.wsu;

public class PaperAuthor {
    Author author;
    String paperId;
    int contribution;

    public PaperAuthor(Author author, String paperId, int contribution) {
        this.author = author;
        this.paperId = paperId;
        this.contribution = contribution;
    }
}
