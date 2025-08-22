import java.util.ArrayList;

public class Library {
//    Book book;
    ArrayList<Book> books;

    public Library() {
        books = new ArrayList<>();
    }

    public void addBook(String title) {
        if(books.isEmpty()){
            books.add(new Book(title,1));
        } else {
            int lastId = books.getLast().getId();
            books.add(new Book(title, lastId + 1));
        }
    }

    public void getBooks() {
        books.forEach(book -> {
            System.out.println(book.toString());
        });
    }

    public void getBook(int id) {
        books.forEach(book -> {if(book.getId()==id) System.out.println(book.toString());});
    }

    public void deleteBook(int id) {
        signal:for(Book book:books) {
            if(book.getId()==id) {
                String deletedBook = book.toString();
                books.remove(book);
                System.out.println(deletedBook);
                break signal;
            }
        };
    }
}
