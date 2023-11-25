
package com.xuanhieu.bookstore.resource.v3;

import com.xuanhieu.bookstore.dao.BookDAO;
import com.xuanhieu.bookstore.model.Book;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Path("/v3/books")
public class BookResource {
    private BookDAO dao = BookDAO.getInstance();
    @Context UriInfo ui;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getAll() {
        return dao.getAll();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{isbn}")
    public Book getABook(@PathParam("isbn") String isbn) {
        for (Book book : dao.getAll()) {
            if(book.getIsbn().equalsIgnoreCase(isbn))
                return book;
        }
        return null;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addABook(Book book) throws URISyntaxException {
        dao.add(book);
        URI url = new URI(ui.getBaseUri() + "/v3/books/" + book.getIsbn());
        return Response.created(url).build();
    }
    
}
