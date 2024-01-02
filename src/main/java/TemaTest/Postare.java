package TemaTest;

import java.util.ArrayList;

public class Postare implements Likeable{
    private ArrayList<Comentariu> comments;
    private Utilizator author;
    public static int counter = 0;
    private final int id;
    private final String text;
    private final String date;
    private int likes;

    Postare(String text, String currentDate) {
        counter++;
        this.id = counter;
        this.text = text;
        this.date = currentDate;
        this.likes = 0;
        this.comments = new ArrayList<>();
    }
    public String getText() {
        return this.text;
    }
    public int getLikes() {
        return this.likes;
    }
    public int getId() {
        return this.id;
    }
    public String getDate() {
        return this.date;
    }
    public ArrayList<Comentariu> getComments() {
        return this.comments;
    }
    public Utilizator getAuthor() {
        return this.author;
    }
    public void setAuthor(Utilizator author) {
        this.author = author;
    }
    public Comentariu addComment(String text, Postare post, String currentDate) {
        Comentariu comment = new Comentariu(text, post, currentDate);
        this.comments.add(comment);
        return comment;
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
    public void getDetails() {
        boolean first = true;
        System.out.print("{'status':'ok','message': [{'post_text':'" + this.text + "','post_date':'" +
                this.date + "','username':'" + this.author.getUsername() + "','number_of_likes':'" +
                this.likes + "','comments': [");
        App.sortCommsDescByDateAndId(this.comments);
        for (Comentariu comm : this.comments) {
            if (first)
                first = false;
            else
                System.out.print(",");
            System.out.print("{'comment_id':'" + comm.getId() + "','comment_text':'" + comm.getText() +
                    "','comment_date':'" + comm.getDate() + "','username':'" + comm.getAuthor().getUsername() +
                    "','number_of_likes':'" + comm.getLikes() + "'}");
        }
        System.out.print("] }] }");
    }
}
