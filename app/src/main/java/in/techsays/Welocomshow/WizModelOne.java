package in.techsays.Welocomshow;

public class WizModelOne {
    private int wizImage;
    private String headLine, description;

    public WizModelOne(int wizImage, String headLine, String description) {
        this.wizImage = wizImage;
        this.headLine = headLine;
        this.description = description;
    }

    public int getWizImage() {
        return wizImage;
    }

    public void setWizImage(int wizImage) {
        this.wizImage = wizImage;
    }

    public String getHeadLine() {
        return headLine;
    }

    public void setHeadLine(String headLine) {
        this.headLine = headLine;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
