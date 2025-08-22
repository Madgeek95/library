public class Book {
    private String title = "";
    private int id = 0;

    public Book(String title, int id) {
        this.title = title;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return title;
    }
}
