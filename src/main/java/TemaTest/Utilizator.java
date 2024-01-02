package TemaTest;

import java.util.ArrayList;

public class Utilizator {
    private final String username;
    private final String password;
    private int totalLikes;
    private ArrayList<Utilizator> following;
    private ArrayList<Utilizator> followers;
    private ArrayList<Comentariu> comments;
    private ArrayList<Postare> posts;
    private ArrayList<Object> liked;
    public Utilizator(String username, String password) {
        this.username = username;
        this.password = password;
        this.totalLikes = 0;
        this.posts = new ArrayList<>();
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.liked = new ArrayList<>();
    }
    public String getUsername() {
        return this.username;
    }
    public ArrayList<Postare> getPosts() {
        return this.posts;
    }
    public Postare addPost(String text, String currentDate) {
        Postare post = new Postare(text, currentDate);
        post.setAuthor(this);
        this.posts.add(post);
        return post;
    }
    public void addFollower(Utilizator user) {
        this.followers.add(user);
    }
    public void addFollowing(Utilizator user) {
        this.following.add(user);
    }
    public ArrayList<Utilizator> getFollowing() {
        return this.following;
    }
    public ArrayList<Utilizator> getFollowers() {
        return this.followers;
    }
    public ArrayList<Comentariu> getComments() {
        return this.comments;
    }
    public ArrayList<Object> getLiked() {
        return this.liked;
    }
    public int getTotalLikes() {
        return this.totalLikes;
    }
    public void printFollowings() {
        boolean first = true;
        System.out.print("{'status':'ok','message': [");
        for (Utilizator user : this.following) {
            if (first)
                first = false;
            else
                System.out.print(",");
            System.out.print("'" + user.getUsername() + "'");
        }
        System.out.print("]}");
    }
    public void printFollowers() {
        boolean first = true;
        System.out.print("{'status':'ok','message': [");
        for (Utilizator user : this.followers) {
            if (first)
                first = false;
            else
                System.out.print(",");
            System.out.print("'" + user.getUsername() + "'");
        }
        System.out.print("]}");
    }
    public void calculateTotalLikes() {
        for (Postare post : this.getPosts())
            this.totalLikes += post.getLikes();
        for (Comentariu comm : this.getComments())
            this.totalLikes += comm.getLikes();
    }
    public void getFeed() {
        ArrayList<Postare> allPosts = new ArrayList<>();
        boolean first = true;
        System.out.print("{'status':'ok','message': [");
        for (Utilizator user : following) {
            allPosts.addAll(user.getPosts());
        }
        App.sortPostsDescByDateAndId(allPosts);
        for (Postare post : allPosts) {
            if (first)
                first = false;
            else
                System.out.print(",");
            System.out.print("{'post_id':'" + post.getId() + "',");
            System.out.print("'post_text':'" + post.getText() + "',");
            System.out.print("'post_date':'" + post.getDate() + "',");
            System.out.print("'username':'" + post.getAuthor().getUsername() + "'}");
        }
        System.out.print("]}");
    }
}
