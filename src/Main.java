public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        library.addBook("blablabla");
        library.addBook("aaaaaaaa");
        library.getBook(1);
        System.out.println("------------------");
        library.deleteBook(2);
        System.out.println("------------------");
        library.getBooks();
    }
}
