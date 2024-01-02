package TemaTest;

public class Comentariu implements Likeable{
    public static int counter = 0;
    private final int id;
    private final String text;
    private int likes;
    private final String date;
    private final Postare commentedPost;
    private Utilizator author;
    public Comentariu(String text, Postare post, String currentDate) {
        counter++;
        this.id = counter;
        this.text = text;
        this.likes = 0;
        this.date = currentDate;
        this.commentedPost = post;
    }
    public String getText() {
        return this.text;
    }
    public int getId() {
        return this.id;
    }
    public int getLikes() {
        return this.likes;
    }
    public Postare getCommentedPost() {
        return this.commentedPost;
    }
    public Utilizator getAuthor() {
        return this.author;
    }
    public String getDate() {
        return this.date;
    }
    public void setAuthor(Utilizator author) {
        this.author = author;
    }
    @Override
    public void like(Utilizator user) {
        this.likes++;
        user.getLiked().add(this);
    }
    @Override
    public void unlike(Utilizator user) {
        this.likes--;
        user.getLiked().remove(this);
    }
}
