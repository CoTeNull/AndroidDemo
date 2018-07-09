package module;


public class Message {
    private int id;
    private String name;
    private String content;
    private String date;
    public Message() {

    }

    public Message(int id, String date, String name, String content) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.date = date;
    }
    public Message(String date, String name, String content) {
        this.name = name;
        this.content = content;
        this.date = date;
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String title) {
        this.name = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
