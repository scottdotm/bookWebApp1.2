/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.ssm.bookwebapp.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author Scott
 */
//"com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/books", "root", "admin"
@SessionScoped
public class AuthorDao implements AuthorDaoStrategy,Serializable {

    @Inject
    private DBStrategy db;
    private final String DRIVER = "com.mysql.jdbc.Driver";
    private final String URL = "jdbc:mysql://localhost:3306/book";
    private final String USER = "root";
    private final String PASSWORD = "admin";
    private final String col1 = "author_name";
    private final String col2 = "date_added";
    private final List colNames = new ArrayList();
    private final List values = new ArrayList();
    

    @Override
    public List<Author> getAuthorList() throws ClassNotFoundException, SQLException {
        db.openConnection(DRIVER, URL, USER, PASSWORD);

        List<Map<String, Object>> rawData
                = db.findAllRecords("author", 0);
        List<Author> authors = new ArrayList<>();

        for (Map rec : rawData) {
            Author author = new Author();
            Integer id = new Integer(rec.get("author_id").toString());
            author.setAuthorId(id);
            String name = rec.get("author_name") == null ? "" : rec.get("author_name").toString();
            author.setAuthorName(name);
            Date date = rec.get("date_added") == null ? null : (Date) rec.get("date_added");
            author.setDateAdded(date);
            authors.add(author);
        }

        db.closeConnection();

        return authors;
    }

    @Override
    public int deleteAuthorById(Object id) throws ClassNotFoundException, SQLException {
        db.openConnection(DRIVER, URL, USER, PASSWORD);
        int result = db.deleteById("author", "author_id", id);
        db.closeConnection();
        return result;
    }
    
    @Override
    public int createOneAuthor(Object name, Object date) throws ClassNotFoundException, SQLException{
        db.openConnection(DRIVER, URL, USER, PASSWORD);
//        String col1 = "author_name";
//        String col2 = "date_added";
//        List colNames = new ArrayList();
        List values = new ArrayList();
        colNames.add(col1);
        colNames.add(col2);
        values.add(name);
        values.add(date);
        int result = db.insertOneRecord("author", colNames, values);
        db.closeConnection();
        return result;
    }
    
    @Override
    public int updateAuthor(Object id, Object name, Object date) throws ClassNotFoundException, SQLException{
        colNames.add(col1);
        colNames.add(col2);
        values.add(name);
//        if(date != null){
            values.add(date);
//        } else {
//            values.add(new Date());
//        }
        //db.updateRecordById("author", colNames, colValues, "author_id", 33);
        db.openConnection(DRIVER, URL, USER, PASSWORD);
        int result = db.updateRecordById("author", colNames, values, "author_id", id);
        db.closeConnection();
        return result;
    }

    public DBStrategy getDb() {
        return db;
    }

    public void setDb(DBStrategy db) {
        this.db = db;
    }

//    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        AuthorDaoStrategy dao = new AuthorDao();
//        Date date = new Date();
//        String name = "John Doe";
//        dao.createOneAuthor(name, date);
//        dao.updateAuthor(28, "Jim L.", 0);
//        List <Author> authors = dao.getAuthorList();
//        System.out.println(authors);
//    }
}
