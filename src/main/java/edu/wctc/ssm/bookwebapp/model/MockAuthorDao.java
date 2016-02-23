package edu.wctc.ssm.bookwebapp.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Alternative;

/**
 *
 * @author Scott
 */
@Alternative
@SessionScoped
public class MockAuthorDao implements AuthorDaoStrategy,Serializable {
    
    private DBStrategy db;
    private List<Author> authors;

    @Override
    public List<Author> getAuthorList() throws ClassNotFoundException, SQLException {
        Author a1 = new Author(01, "David Clark", new Date(62, 4, 5));
        Author a2 = new Author(02, "Brad Bishop", new Date(62, 4, 5));
        Author a3 = new Author(03, "Volker Nostrala", new Date(62, 4, 5));
        Author a4 = new Author(04, "Matt Marks", new Date(62, 4, 5));

        List<Author> Authors = new ArrayList<>();

        Authors.add(a1);
        Authors.add(a2);
        Authors.add(a3);
        Authors.add(a4);

        return Authors;
    }

    @Override
    public int deleteAuthorById(Object id) {
        return 1;
    }
    @Override
    public int createOneAuthor(Object name, Object date) throws ClassNotFoundException, SQLException{
        return 1;
    }
    @Override 
    public int updateAuthor(Object id, Object name, Object date){
        return 1;
    }

    public DBStrategy getDb() {
        return db;
    }

    public void setDb(DBStrategy db) {
        this.db = db;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }
    
    
}
