package in.techsays.Settings;

public class MainModel {
    private int images;
    private String names;
    private int dots;

    public MainModel() {
    }

    public MainModel(int images, String names, int dots) {
        this.images = images;
        this.names = names;
        this.dots = dots;
    }

    public int getImages() {
        return images;
    }

    public void setImages(int images) {
        this.images = images;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public int getDots() {
        return dots;
    }

    public void setDots(int dots) {
        this.dots = dots;
    }
}
