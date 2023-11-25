package com.xuanhieu.bookstore.resource.v1;

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
import java.util.ArrayList;
import java.util.List;

@Path("/v1/books")
public class BookResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
//    public String sayHello() {
//        return "Xin chào! Welcome to API World";
//    }
    public List<Book> getAll() {
        List<Book> list = new ArrayList<>();
        list.add(new Book("4018690253374", "Sống Thực Tế Giữa Đời Thực Dụng",
                "Dịch giả B.Nhung", 1, 2018));
        list.add(new Book("8980075329379", "Tâm Tĩnh Lặng, Miệng Mỉm Cười",
                "Dịch giả N.T.Loan", 1, 2020));

        return list;
    }
    
    @GET
    @Path("one")
    @Produces(MediaType.APPLICATION_JSON)
    public Book getABook() {
        return new Book("8980075329379", "Tâm Tĩnh Lặng, Miệng Mỉm Cười",
                "Dịch giả N.T.Loan", 1, 2022);
    }
    
//    @GET
//    @Path("{isbn}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Book getBy(@PathParam("isbn") String isbn) {
//        System.out.println("Test path-param: " + isbn);
//        return new Book(isbn, "Tuổi trẻ đáng giá bao nhiêu",
//                "Rosie", 1, 2020);
//    }
    
    //ĐỘ HTTP code
    @GET
    @Path("{isbn}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBy(@PathParam("isbn") String isbn) {
        Book book = new Book(isbn, "Tuổi trẻ đáng giá bao nhiêu",
                "Rosie", 1, 2020);
        Response msg = Response.ok(book).build();
        return  msg; //chủ động đóng gói RESPONSE MESSAGE trả về
    }
    
    //khai báo ở đây cho gần, đúng chuẩn để lên đầu class
    @Context UriInfo ui;
    
    @POST
//    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(Book book) throws URISyntaxException {
        //gọi DAO để insert cuốn sachs
        //tạm sout (toString cuốn sách)
        //nếu in đúng thì nghĩa là đã đưa info lên server được rồi
        //phần còn lại JavaWeb đã học
        
        System.out.println(book);
        
        //tôi sẽ trả về đường dẫn trỏ đến cuốn sách vừa tạo mới !!!
        //mình đã gõ, chuyền info cuốn sách lên server, gồm mã sách
        //nếu insert thành công thì .../api/books/mã-sách-vừa-chèn
        //sẽ là url để get lại cuốn sách đó
        URI url = new URI(ui.getBaseUri() + "books/" + book.getIsbn());
        
        Response msg = Response.created(url).build();
        return msg;
    }
    
    //JWT(JSON WEB TOKEN) BẢO MẬT API
    //SWAGGER: DOCUMENT UI/TEST, CLONE 1 SỐ TÍNH NĂNG CỦA POSTMAN   
}
