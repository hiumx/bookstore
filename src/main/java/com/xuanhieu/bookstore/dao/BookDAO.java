
package com.xuanhieu.bookstore.dao;

import com.xuanhieu.bookstore.model.Book;
import com.xuanhieu.bookstore.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookDAO {
    private static BookDAO instance;
    private Connection conn = DBUtil.makeConnection();

    //Cấm new trực tiếp BookDAO
    //Chỉ new BookDAO qua hàm static getInstance() để quản lí được số object/instance đã new - SINGLETON DESIGN PATTERN
    private BookDAO() {
    }

    public static BookDAO getInstance() {

        if (instance == null) {
            instance = new BookDAO();
        }
        return instance;
    }

    // Lấy ra tất cả sách trong kho
    public List<Book> getAll() {

        PreparedStatement stm;
        ResultSet rs;

        List<Book> bookList = new ArrayList();
        try {

            String sql = "SELECT * FROM BOOK";
            stm = conn.prepareStatement(sql);
            
            rs = stm.executeQuery();
            while (rs.next()) {
                bookList.add(new Book(rs.getString("Isbn"),
                        rs.getString("Title"),
                        rs.getString("Author"),
                        rs.getInt("Edition"),
                        rs.getInt("PublishedYear")));
            }
        } catch (Exception ex) {
            Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bookList;
    }

    // Lấy ra một cuốn sách dựa trên mã sách
    public Book getOne(String isbn) {

        PreparedStatement stm;
        ResultSet rs;

        try {

            String sql = "SELECT * FROM BOOK WHERE Isbn = ?";
            stm = conn.prepareStatement(sql);
            stm.setString(1, isbn);

            rs = stm.executeQuery();
            if (rs.next()) {
                return new Book(rs.getString("Isbn"),
                        rs.getString("Title"),
                        rs.getString("Author"),
                        rs.getInt("Edition"),
                        rs.getInt("PublishedYear"));
            }

        } catch (Exception ex) {
            Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void add(Book book) {
        PreparedStatement stm;
        
        try {
            String sql = "INSERT INTO Book VALUES(?,?,?,?,?)";
            stm = conn.prepareStatement(sql);
            stm.setString(1, book.getIsbn());
            stm.setString(2, book.getTitle());
            stm.setString(3, book.getAuthor());
            stm.setInt(4, book.getEdition());
            stm.setInt(5, book.getPublishedYear());
            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void update(Book newBook) {
        PreparedStatement stm;
        try {
            String sql = "UPDATE Book SET Title = ?, Author = ?, Edition = ?, PublishedYear = ? WHERE Isbn = ?";
            stm = conn.prepareStatement(sql);
            stm.setString(1, newBook.getTitle());
            stm.setString(2, newBook.getAuthor());
            stm.setInt(3, newBook.getEdition());
            stm.setInt(4, newBook.getPublishedYear());
            stm.setString(5, newBook.getIsbn());
            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public boolean delete(String isbn) {
        PreparedStatement stm;
        try {
            String sql = "DELETE FROM Book WHERE Isbn = ?";
            stm = conn.prepareStatement(sql);
            stm.setString(1, isbn);
            stm.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e);  
        }
        return false;
    }   
    
}
