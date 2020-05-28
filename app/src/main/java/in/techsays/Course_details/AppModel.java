package in.techsays.Course_details;

public class AppModel {
    private String head;
    private int rotation;

    public AppModel() {
    }

    public AppModel(String head, int rotation) {
        this.head = head;
        this.rotation = rotation;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }
}
