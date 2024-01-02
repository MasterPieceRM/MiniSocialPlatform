/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package TemaTest;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class App {
    static ArrayList<Utilizator> users = new ArrayList<>();
    private static final Logger logger = Logger.getLogger(App.class.getName());
    public App() {/* compiled code */}
    private static Utilizator getUser(String username) {
        for (Utilizator user : users)
            if (user.getUsername().equals(username))
                return user;
        return null;
    }
    private static Postare getPost(String id, Utilizator user) {
        for (Postare post : user.getPosts())
            if (String.valueOf(post.getId()).equals(id))
                return post;
        return null;
    }
    private static Comentariu getComment(String id, Postare post) {
        for (Comentariu comment : post.getComments()) {
            if (String.valueOf(comment.getId()).equals(id))
                return comment;
        }
        return null;
    }
    public static void sortPostsDescByDateAndId(ArrayList<Postare> posts) {
        posts.sort((post1, post2) -> {
            int dateComparison = post2.getDate().compareTo(post1.getDate());
            if (dateComparison == 0) {
                return Integer.compare(post2.getId(), post1.getId());
            }
            return dateComparison;
        });
    }
    public static void sortCommsDescByDateAndId(ArrayList<Comentariu> comments) {
        comments.sort((comm1, comm2) -> {
            int dateComparison = comm2.getDate().compareTo(comm1.getDate());
            if (dateComparison == 0) {
                return Integer.compare(comm2.getId(), comm1.getId());
            }
            return dateComparison;
        });
    }
    public static void sortPostsByLikes(ArrayList<Postare> posts) {
        posts.sort((post1, post2) -> Integer.compare(post2.getLikes(), post1.getLikes()));
    }
    public static void sortPostsByComments(ArrayList<Postare> posts) {
        posts.sort((post1, post2) -> Integer.compare(post2.getComments().size(),
                post1.getComments().size()));
    }
    public static void sortUsersByFollowers(ArrayList<Utilizator> users) {
        users.sort((user1, user2) -> Integer.compare(user2.getFollowers().size(),
                user1.getFollowers().size()));
    }
    public static void sortUsersByLikes(ArrayList<Utilizator> users) {
        users.sort((user1, user2) -> Integer.compare(user2.getTotalLikes(),
                user1.getTotalLikes()));
    }
    private static void getMostLikedUsers(java.lang.String[] args) {
        boolean first = true;

        if (loginError(args))
            return;
        for (Utilizator userAux : users)
            userAux.calculateTotalLikes();
        sortUsersByLikes(users);
        System.out.print("{'status':'ok','message': [");
        for (int i = 0; i < Math.min(5, users.size()); i++) {
            if (first)
                first = false;
            else
                System.out.print(",");
            System.out.print("{'username':'" + users.get(i).getUsername() + "','number_of_likes':'"
                    + users.get(i).getTotalLikes() + "'}");
        }
        System.out.print("]}");
    }
    private static void getMostFollowedUsers(java.lang.String[] args) {
        boolean first = true;
        if (loginError(args))
            return;
        sortUsersByFollowers(users);
        System.out.print("{'status':'ok','message': [");
        for (int i = 0; i < Math.min(5, users.size()); i++) {
            if (first)
                first = false;
            else
                System.out.print(",");
            System.out.print("{'username':'" + users.get(i).getUsername() + "','number_of_followers':'"
                    + users.get(i).getFollowers().size() + "'}");
        }
        System.out.print(" ]}");
    }
    private static void getMostCommentedPosts(java.lang.String[] args) {
        ArrayList<Postare> allPosts = new ArrayList<>();
        Postare post;
        boolean first = true;
        if (loginError(args))
            return;
        for (Utilizator userAux : users)
            allPosts.addAll(userAux.getPosts());
        sortPostsByComments(allPosts);
        System.out.print("{'status':'ok','message': [");
        for (int i = 0; i < Math.min(5, allPosts.size()); i++) {
            post = allPosts.get(i);
            if (first)
                first = false;
            else
                System.out.print(",");
            System.out.print("{'post_id':'" + post.getId() + "','post_text':'" + post.getText() +
                    "','post_date':'" + post.getDate() + "','username':'" + post.getAuthor().getUsername() +
                    "','number_of_comments':'" + post.getComments().size() + "'}");
        }
        System.out.print("]}");
    }
    private static void getMostLikedPosts(java.lang.String[] args) {
        ArrayList<Postare> allPosts = new ArrayList<>();
        Postare post;
        boolean first = true;
        if (loginError(args))
            return;
        for (Utilizator userAux : users)
            allPosts.addAll(userAux.getPosts());
        sortPostsByLikes(allPosts);
        System.out.print("{'status':'ok','message': [");
        for (int i = 0; i < Math.min(5, allPosts.size()); i++) {
            post = allPosts.get(i);
            if (first)
                first = false;
            else
                System.out.print(",");
            System.out.print("{'post_id':'" + post.getId() + "','post_text':'" + post.getText() +
                    "','post_date':'" + post.getDate() + "','username':'" + post.getAuthor().getUsername() +
                    "','number_of_likes':'" + post.getLikes() + "'}");
        }
        System.out.print(" ]}");
    }
    private static void getFollowing(java.lang.String[] args) {
        String username;
        String[] extractData;

        if (loginError(args))
            return;
        extractData = args[1].split("'");
        username = extractData[1];
        Utilizator user = getUser(username);
        assert user != null;
        user.printFollowings();
    }
    private static void getFollowers(java.lang.String[] args) {
        String username;
        String[] extractData;

        if (loginError(args))
            return;
        if (!(args.length > 3 && args[3].startsWith("-username"))) {
            System.out.println("{'status':'error','message':'No username to list followers was provided'}");
            return;
        }
        extractData = args[3].split("'");
        username = extractData[1];
        Utilizator user = getUser(username);
        if (user == null) {
            System.out.println("{'status':'error','message':'The username to list followers was not valid'}");
            return;
        }
        user.printFollowers();
    }
    private static void getUserPosts(java.lang.String[] args) {
        String username;
        String usernameToList;
        String[] extractData;
        boolean first = true;

        if (loginError(args))
            return;
        if (!(args.length > 3 && args[3].startsWith("-username"))) {
            System.out.println("{'status':'error','message':'No username to list posts was provided'}");
            return;
        }
        extractData = args[3].split("'");
        usernameToList = extractData[1];
        extractData = args[1].split("'");
        username = extractData[1];
        Utilizator user = getUser(username);
        Utilizator userToList = getUser(usernameToList);
        assert user != null;
        if (userToList != null && user.getFollowing().contains(userToList)) {
            sortPostsDescByDateAndId(userToList.getPosts());
            System.out.print("{'status':'ok','message': [");
            for (Postare post : userToList.getPosts()) {
                if (first)
                    first = false;
                else
                    System.out.print(",");
                System.out.print("{'post_id':'" + post.getId() + "',");
                System.out.print("'post_text':'" + post.getText() + "',");
                System.out.print("'post_date':'" + post.getDate() + "'}");
            }
            System.out.print("]}");
        } else
            System.out.println("{'status':'error','message':'The username to list posts was not valid'}");
    }
    private static void getFollowingsPosts(java.lang.String[] args) {
        String username;
        String[] extractData;

        if (loginError(args))
            return;
        extractData = args[1].split("'");
        username = extractData[1];
        Utilizator user = getUser(username);
        assert user != null;
        user.getFeed();
    }
    private static void getPostDetails(java.lang.String[] args) {
        String[] extractData;
        String username;
        String id;

        if (loginError(args))
            return;
        extractData = args[1].split("'");
        username = extractData[1];
        if (!(args.length > 3 && args[3].startsWith("-post-id"))) {
            System.out.println("{'status':'error','message':'No post identifier was provided'}");
            return;
        }
        extractData = args[3].split("'");
        id = extractData[1];
        Postare postToPrint = null;
        Utilizator user = getUser(username);
        for (Utilizator userAux : users) {
            postToPrint = getPost(id, userAux);
            if (postToPrint != null)
                break;
        }
        assert user != null;
        if (postToPrint != null && (postToPrint.getAuthor().equals(user)
            || user.getFollowing().contains(postToPrint.getAuthor()))) {
            postToPrint.getDetails();
            return;
        }
        System.out.println("{'status':'error','message':'The post identifier was not valid'}");
    }
    private static void createPost(java.lang.String[] args) {
        String username;
        String text;
        String[] extractData;

        if (loginError(args))
            return;
        extractData = args[1].split("'");
        username = extractData[1];
        if (!(args.length > 3 && args[3].startsWith("-text"))) {
            System.out.println("{'status':'error','message':'No text provided'}");
            return;
        }
        extractData = args[3].split("'");
        text = extractData[1];
        if (text.length() > 300) {
            System.out.println("{'status':'error','message':'Post text length exceeded'}");
            return;
        }
        Utilizator userPost = getUser(username);
        if (userPost != null) {
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String currentDateAsString = currentDate.format(formatter);
            Postare post = userPost.addPost(text, currentDateAsString);
            System.out.println("{'status':'ok','message':'Post added successfully'}");
            addPostToFile(String.valueOf(post.getId()), username, text, currentDateAsString);
        }
    }
    private static void deletePost(java.lang.String[] args) {
        String[] extractData;
        String username;
        String id;

        if (loginError(args))
            return;
        extractData = args[1].split("'");
        username = extractData[1];
        if (!(args.length > 3 && args[3].startsWith("-id"))) {
            System.out.println("{'status':'error','message':'No identifier was provided'}");
            return;
        }
        extractData = args[3].split("'");
        id = extractData[1];
        Utilizator user = getUser(username);
        assert user != null;
        Postare post = getPost(id, user);
        if (post != null) {
            user.getPosts().remove(post);
            deleteFromFileById(id, "posts.csv");
            System.out.println("{'status':'ok','message':'Post deleted successfully'}");
            return;
        }
        System.out.println("{'status':'error','message':'The identifier was not valid'}");
    }
    private static void commentPost(java.lang.String[] args) {
        String[] extractData;
        String username;
        String idToComment;
        String text;

        if (loginError(args))
            return;
        extractData = args[1].split("'");
        username = extractData[1];
        if (args.length == 3) {
            System.out.println("{'status':'error','message':'No text provided'}");
            return;
        }
        if (!(args[3].startsWith("-post-id"))) {
            System.out.println("{'status':'error','message':'No post id was provided'}");
            return;
        }
        extractData = args[3].split("'");
        idToComment = extractData[1];
        if (!(args.length > 4 && args[4].startsWith("-text"))) {
            System.out.println("{'status':'error','message':'No text provided'}");
            return;
        }
        extractData = args[4].split("'");
        text = extractData[1];
        if (text.length() > 300) {
            System.out.println("{'status':'error','message':'Comment text length exceeded'}");
            return;
        }
        Utilizator user = getUser(username);
        assert user != null;
        Postare post = null;
        for (Utilizator userAux : users) {
            post = getPost(idToComment, userAux);
            if (post != null) {
                break;
           }
        }
        if (post != null) {
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String currentDateAsString = currentDate.format(formatter);
            Comentariu comment = post.addComment(text, post, currentDateAsString);
            comment.setAuthor(user);
            user.getComments().add(comment);
            System.out.println("{'status':'ok','message':'Comment added successfully'}");
            addCommentToFile(String.valueOf(comment.getId()), username,
                    text, String.valueOf(post.getId()));
            return;
        }
        System.out.println("{'status':'error','message':'The identifier was not valid'}");
    }
    private static void deleteComment(java.lang.String[] args) {
        String[] extractData;
        String username;
        String idToDelete;

        if (loginError(args))
            return;
        extractData = args[1].split("'");
        username = extractData[1];
        if (!(args.length > 3 && args[3].startsWith("-id"))) {
            System.out.println("{'status':'error','message':'No identifier was provided'}");
            return;
        }
        extractData = args[3].split("'");
        idToDelete = extractData[1];
        Utilizator user = getUser(username);
        Comentariu comment = null;
        Postare commentedPost = null;
        assert user != null;
        for (Postare post : user.getPosts()) {
            comment = getComment(idToDelete, post);
            if (comment != null) {
                commentedPost = post;
                break;
            }
        }
        if (comment == null) {
            for (Comentariu comm : user.getComments()) {
                if (String.valueOf(comm.getId()).equals(idToDelete)) {
                    comment = comm;
                    break;
                }
            }
            if (comment != null) {
                commentedPost = comment.getCommentedPost();
            }
        }
        if (comment != null) {
            commentedPost.getComments().remove(comment);
            deleteFromFileById(idToDelete, "comments.csv");
            System.out.println("{'status':'ok','message':'Operation executed successfully'}");
            return;
        }
        System.out.println("{'status':'error','message':'The identifier was not valid'}");
    }
    private static void like(java.lang.String[] args) {
        String[] extractData;
        String username;
        String idToLike;

        if (loginError(args))
            return;
        extractData = args[1].split("'");
        username = extractData[1];
        Utilizator user = getUser(username);
        if (args[0].equals("-like-post")) {
            if (!(args.length > 3 && args[3].startsWith("-post-id"))) {
                System.out.println("{'status':'error','message':'No post identifier to like was provided'}");
                return;
            }
            extractData = args[3].split("'");
            idToLike = extractData[1];
            Postare postToLike = null;
            for (Utilizator userAux : users) {
                postToLike = getPost(idToLike, userAux);
                if (postToLike != null)
                    break;
            }
            assert user != null;
            if (postToLike == null || postToLike.getAuthor().equals(user)
                    || user.getLiked().contains(postToLike)) {
                System.out.println("{'status':'error','message':'The post identifier to like was not valid'}");
                return;
            }
            postToLike.like(user);
            addLikesToFile(username, "Post", idToLike);
            System.out.println("{'status':'ok','message':'Operation executed successfully'}");
        } else if (args[0].equals("-like-comment")) {
            if (!(args.length > 3 && args[3].startsWith("-comment-id"))) {
                System.out.println("{'status':'error','message':'No comment identifier to like was provided'}");
                return;
            }
            extractData = args[3].split("'");
            idToLike = extractData[1];
            Comentariu commentToLike = null;
            for (Utilizator userAux : users)
                for (Comentariu comm : userAux.getComments()) {
                    if (String.valueOf(comm.getId()).equals(idToLike))
                        commentToLike = comm;
                    if (commentToLike != null)
                        break;
                }
            assert user != null;
            if (commentToLike == null || commentToLike.getAuthor().equals(user)
                || user.getLiked().contains(commentToLike)) {
                System.out.println("{'status':'error','message':'The comment identifier to like was not valid'}");
                return;
            }
            commentToLike.like(user);
            addLikesToFile(username, "Comment", idToLike);
            System.out.println("{'status':'ok','message':'Operation executed successfully'}");
        }
    }
    private static void unlike(java.lang.String[] args) {
        String[] extractData;
        String username;
        String idToUnlike;

        if (loginError(args))
            return;
        extractData = args[1].split("'");
        username = extractData[1];
        Utilizator user = getUser(username);
        if (args[0].equals("-unlike-post")) {
            if (!(args.length > 3 && args[3].startsWith("-post-id"))) {
                System.out.println("{'status':'error','message':'No post identifier to unlike was provided'}");
                return;
            }
            extractData = args[3].split("'");
            idToUnlike = extractData[1];
            Postare postToUnlike = null;
            for (Utilizator userAux : users) {
                postToUnlike = getPost(idToUnlike, userAux);
                if (postToUnlike != null)
                    break;
            }
            assert user != null;
            if (postToUnlike == null || postToUnlike.getAuthor().equals(user)
                    || !(user.getLiked().contains(postToUnlike))) {
                System.out.println("{'status':'error','message':'The post identifier to unlike was not valid'}");
                return;
            }
            postToUnlike.unlike(user);
            addUnlikesToFile(username, "Post", idToUnlike);
            System.out.println("{'status':'ok','message':'Operation executed successfully'}");
        } else {
            if (!(args.length > 3 && args[3].startsWith("-comment-id"))) {
                System.out.println("{'status':'error','message':'No comment identifier to unlike was provided'}");
                return;
            }
            extractData = args[3].split("'");
            idToUnlike = extractData[1];
            Comentariu commentToUnlike = null;
            for (Utilizator userAux : users)
                for (Postare postAux : userAux.getPosts()) {
                    commentToUnlike = getComment(idToUnlike, postAux);
                    if (commentToUnlike != null)
                        break;
                }
            assert user != null;
            if (commentToUnlike == null || commentToUnlike.getAuthor().equals(user)
                    || !(user.getLiked().contains(commentToUnlike))) {
                System.out.println("{'status':'error','message':'The comment identifier to unlike was not valid'}");
                return;
            }
            commentToUnlike.unlike(user);
            addUnlikesToFile(username, "Comment", idToUnlike);
            System.out.println("{'status':'ok','message':'Operation executed successfully'}");
        }
    }
    private static void addFollow(java.lang.String[] args) {
        String[] extractData;
        String usernameToFollow;
        String username;

        if (loginError(args))
            return;
        extractData = args[1].split("'");
        username = extractData[1];
        if (!(args.length > 3 && args[3].startsWith("-username"))) {
            System.out.println("{'status':'error','message':'No username to follow was provided'}");
            return;
        }
        extractData = args[3].split("'");
        usernameToFollow = extractData[1];
        Utilizator userToFollow = getUser(usernameToFollow);
        Utilizator userFollowing = getUser(username);
        if (userToFollow == null || userFollowing == null ||
                isFollowed(userToFollow, userFollowing)) {
            System.out.println("{'status':'error','message':'The username to follow was not valid'}");
            return;
        }
        userToFollow.addFollower(userFollowing);
        userFollowing.addFollowing(userToFollow);
        addFollowToFile(username, usernameToFollow);
        System.out.println("{'status':'ok','message':'Operation executed successfully'}");
    }
    private static void unfollow(java.lang.String[] args) {
        String[] extractData;
        String usernameToUnfollow;
        String username;

        if (loginError(args))
            return;
        extractData = args[1].split("'");
        username = extractData[1];
        if (!(args.length > 3 && args[3].startsWith("-username"))) {
            System.out.println("{'status':'error','message':'No username to unfollow was provided'}");
            return;
        }
        extractData = args[3].split("'");
        usernameToUnfollow = extractData[1];
        Utilizator userToUnfollow = getUser(usernameToUnfollow);
        Utilizator userUnfollowing = getUser(username);
        if (userToUnfollow == null || userUnfollowing == null ||
                !isFollowed(userToUnfollow, userUnfollowing)) {
            System.out.println("{'status':'error','message':'The username to unfollow was not valid'}");
            return;
        }
        userToUnfollow.getFollowers().remove(userUnfollowing);
        userUnfollowing.getFollowing().remove(userToUnfollow);
        addUnfollowToFile(username, usernameToUnfollow);
        System.out.println("{'status':'ok','message':'Operation executed successfully'}");
    }
    private static void createUser(java.lang.String[] args) {
        String username;
        String password;
        String[] extractData;

        if (args.length > 1 && args[1].startsWith("-u")) {
            extractData = args[1].split("'");
            username = extractData[1];
            if (args.length > 2 && args[2].startsWith("-p")) {
                extractData = args[2].split("'");
                password = extractData[1];
            } else {
                System.out.println("{'status':'error','message':'Please provide password'}");
                return;
            }
        } else {
            System.out.println("{'status':'error','message':'Please provide username'}");
            return;
        }
        if (userExists(username)) {
            System.out.println("{'status':'error','message':'User already exists'}");
            return;
        }
        addUserToFile(username, password);
        Utilizator user = new Utilizator(username, password);
        users.add(user);
        System.out.println("{'status':'ok','message':'User created successfully'}");
    }
    private static boolean isNotLoggedIn(String[] args) {
        return !((args.length > 1 && args[1].startsWith("-u") &&
                args.length > 2 && args[2].startsWith("-p")));
    }
    private static boolean loginError(java.lang.String[] args) {
        String[] extractData;
        String username;
        String password;

        if (isNotLoggedIn(args)) {
            System.out.println("{'status':'error','message':'You need to be authenticated'}");
            return true;
        }
        extractData = args[1].split("'");
        username = extractData[1];
        extractData = args[2].split("'");
        password = extractData[1];
        if (!userExists(username) || userIncorrectCredentials(username, password)) {
            System.out.println("{'status':'error','message':'Login failed'}");
            return true;
        }
        return false;
    }
    private static boolean isFollowed(Utilizator userToFollow, Utilizator userFollowing) {
        for (Utilizator user : userToFollow.getFollowers()) {
            if (user.equals(userFollowing))
                return true;
        }
        return false;
    }
    private static boolean userExists(String username) {
        String filePath = "users.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 2 && username.equals(fields[0])) {
                    return true;
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading file", e);
        }
        return false;
    }
    private static boolean userIncorrectCredentials(String username, String password) {
        String filePath = "users.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 2 && username.equals(fields[0]) && password.equals(fields[1])) {
                    return false;
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading file", e);
        }
        return true;
    }
   private static void addUserToFile(String username, String password) {
        String filePath = "users.csv";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(username + "," + password);
            bw.newLine();

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading file", e);
        }
    }
    private static void addPostToFile(String id, String username, String text, String date) {
        String filePath = "posts.csv";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(id + "," + username + "," + text + ',' + date);
            bw.newLine();

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading file", e);
        }
    }
    private static void addFollowToFile(String follower, String following) {
        String filePath = "follows.csv";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(follower + "," + following);
            bw.newLine();

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading file", e);
        }
    }
    private static void addUnfollowToFile(String unfollower, String unfollowing) {
        String filePath = "unfollows.csv";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(unfollower + "," + unfollowing);
            bw.newLine();

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading file", e);
        }
    }
    private static void addCommentToFile(String id, String username, String text, String postId) {
        String filePath = "comments.csv";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(id + "," + username + "," + text + "," + postId);
            bw.newLine();

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading file", e);
        }
    }
    private static void addLikesToFile(String username, String text, String id) {
        String filePath = "likes.csv";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(username + "," + text + "," + id);
            bw.newLine();

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading file", e);
        }
    }
    private static void addUnlikesToFile(String username, String text, String id) {
        String filePath = "unlikes.csv";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(username + "," + text + "," + id);
            bw.newLine();

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading file", e);
        }
    }
    private static void deleteFromFileById(String id, String filePath) {
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith(id + ",")) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading file", e);
        }
        PrintWriter writer;
        try {
            writer = new PrintWriter(filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        writer.print("");
        writer.close();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                if (!line.trim().isEmpty()) {
                    bw.write(line);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading file", e);
        }
    }
    private static void cleanup() {
        String[] files = {"users.csv", "comments.csv", "follows.csv", "likes.csv", "unlikes.csv", "posts.csv",
                        "unfollows.csv"};

        for (String file : files) {
            PrintWriter writer;
            try {
                writer = new PrintWriter(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            writer.print("");
            writer.close();
        }
        users.clear();
        Postare.counter = 0;
        Comentariu.counter = 0;
        addUserToFile("Username", "Password");
        addPostToFile("ID", "Username", "Text", "Date");
        addFollowToFile("Follower", "Following");
        addUnfollowToFile("Unfollower", "Unfollowing");
        addCommentToFile("Id", "Username", "Text", "PostId");
        addLikesToFile("Username" ,"Post/Comment","Id");
        addUnlikesToFile("Username" ,"Post/Comment","Id");
        System.out.println("{'status':'ok','message':'Cleanup finished successfully'}");
    }
    public static void main(java.lang.String[] strings) {

        if (strings != null && strings.length > 0) {
            switch (strings[0]) {
                case "-create-user":
                    createUser(strings);
                    break;
                case "-create-post":
                    createPost(strings);
                    break;
                case "-delete-post-by-id":
                    deletePost(strings);
                    break;
                case "-follow-user-by-username":
                    addFollow(strings);
                    break;
                case "-cleanup-all":
                    cleanup();
                    break;
                case "-unfollow-user-by-username":
                    unfollow(strings);
                    break;
                case "-comment-post":
                    commentPost(strings);
                    break;
                case "-delete-comment-by-id":
                    deleteComment(strings);
                    break;
                case "-like-post", "-like-comment":
                    like(strings);
                    break;
                case "-unlike-post", "-unlike-comment":
                    unlike(strings);
                    break;
                case "-get-followings-posts":
                    getFollowingsPosts(strings);
                    break;
                case "-get-post-details":
                    getPostDetails(strings);
                    break;
                case "-get-following":
                    getFollowing(strings);
                    break;
                case "-get-followers":
                    getFollowers(strings);
                    break;
                case "-get-most-liked-posts":
                    getMostLikedPosts(strings);
                    break;
                case "-get-most-commented-posts":
                    getMostCommentedPosts(strings);
                    break;
                case "-get-most-followed-users":
                    getMostFollowedUsers(strings);
                    break;
                case "-get-most-liked-users":
                    getMostLikedUsers(strings);
                    break;
                case "-get-user-posts":
                    getUserPosts(strings);
                    break;
                default:
                    System.out.println("Unknown arguments");
            }
        } else System.out.println("Hello world!");
    }
}