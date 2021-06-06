public class User {
    String name;
    int inTime;
    int localInTime;

    public void setLocalInTime(int localInTime) {
        this.localInTime = localInTime;
    }

    public User(String name, int inTime) {
        this.name = name;
        this.inTime = inTime;
    }

    @Override
    public boolean equals(Object obj) {
        return this.name.equals(((User)obj).name);
    }
}
