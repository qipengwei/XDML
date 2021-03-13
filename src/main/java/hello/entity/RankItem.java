package hello.entity;

public class RankItem {
    private int score;
    private User user;

    public RankItem() {
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "RankItem{" +
                "score=" + score +
                ", user=" + user +
                '}';
    }
}
