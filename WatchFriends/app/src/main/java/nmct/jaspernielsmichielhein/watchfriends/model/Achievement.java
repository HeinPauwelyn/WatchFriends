package nmct.jaspernielsmichielhein.watchfriends.model;

import nmct.jaspernielsmichielhein.watchfriends.helper.Interfaces;

public class Achievement {
    private String name;
    private String description;
    private Status status;
    private int progress;
    private Integer next;
    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public Integer getNext() {
        return next;
    }

    public void setNext(Integer next) {
        this.next = next;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String progress() {

        if (status == Status.GOLD) {
            return "Progress completed!";
        }
        return "Progress: " + Integer.toString(progress) + "/" + Integer.toString(next);
    }

    @Override
    public String toString() {
        if (next != null) {
            return description.replace("%d", Integer.toString(next));
        }
        else {
            return description.replace("%d", Integer.toString(progress));
        }
    }
}
