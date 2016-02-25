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
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 *
 * @author Scott
 */
//"com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/books", "root", "admin"
@Dependent
public class AuthorDao implements AuthorDaoStrategy,Serializable {

    @Inject
    private DBStrategy db;
    private String driver;
    private String url;
    private String user;
    private String pass;
    private final String TABLE = "author";
    private final String COLONE = "author_name";
    private final String COLTWO = "date_added";
    private final List COLNAMES = new ArrayList();
    private final List VALUES = new ArrayList();
    
    @Override
    public void initDao(String driver, String url, String user, String pass){
        setDRIVER(driver);
        setURL(url);
        setUSER(user);
        setPASSWORD(pass);
    }
    

    @Override
    public List<Author> getAuthorList() throws ClassNotFoundException, SQLException {
        db.openConnection(driver, url, user, pass);

        List<Map<String, Object>> rawData
                = db.findAllRecords(TABLE, 0);
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
        db.openConnection(driver, url, user, pass);
        int result = db.deleteById(TABLE, "author_id", id);
        db.closeConnection();
        return result;
    }
    
    @Override
    public int createOneAuthor(Object name, Object date) throws ClassNotFoundException, SQLException{
        db.openConnection(driver, url, user, pass);
        COLNAMES.add(COLONE);
        COLNAMES.add(COLTWO);
        VALUES.add(name);
        VALUES.add(date);
        int result = db.insertOneRecord(TABLE, COLNAMES, VALUES);
        db.closeConnection();
        return result;
    }
    
    @Override
    public int updateAuthor(Object id, Object name, Object date) throws ClassNotFoundException, SQLException{
        COLNAMES.add(COLONE);
        COLNAMES.add(COLTWO);
        VALUES.add(name);
        VALUES.add(date);
        db.openConnection(driver, url, user, pass);
        int result = db.updateRecordById(TABLE, COLNAMES, VALUES, "author_id", id);
        db.closeConnection();
        return result;
    }

    @Override
    public String getDRIVER() {
        return driver;
    }

    @Override
    public String getURL() {
        return url;
    }

    @Override
    public String getUSER() {
        return user;
    }

    @Override
    public String getPASSWORD() {
        return pass;
    }

    @Override
    public void setDRIVER(String DRIVER) {
        this.driver = DRIVER;
    }

    @Override
    public void setURL(String URL) {
        this.url = URL;
    }

    @Override
    public void setUSER(String USER) {
        this.user = USER;
    }

    @Override
    public void setPASSWORD(String PASSWORD) {
        this.pass = PASSWORD;
    }

    @Override
    public DBStrategy getDb() {
        return db;
    }

    @Override
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
