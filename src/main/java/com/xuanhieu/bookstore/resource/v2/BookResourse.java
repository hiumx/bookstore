
package com.xuanhieu.bookstore.resource.v2;

import com.xuanhieu.bookstore.model.Book;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/v2/books")
public class BookResourse {

    private List<Book> list;
    public BookResourse() {
        list = new ArrayList<>();
        list.add(new Book("4018690253374", "Sống Thực Tế Giữa Đời Thực Dụng",
                "Dịch giả B.Nhung", 1, 2018));
        list.add(new Book("8980075329379", "Tâm Tĩnh Lặng, Miệng Mỉm Cười",
                "Dịch giả N.T.Loan", 1, 2020));
        list.add(new Book("8980075329379", "Tâm Tĩnh Lặng, Miệng Mỉm Cười",
                "Dịch giả N.T.Loan", 1, 2022));
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getAll() {
        return list;
    }
    
    @GET
    @Path("{isbn}")
    @Produces(MediaType.APPLICATION_JSON)
    public Book getABook(@PathParam("isbn") String isbn) {
        for (Book book : list) {
            if(book.getIsbn().equalsIgnoreCase(isbn))
                return book;
        }
        return null;
    }
    
    
}
