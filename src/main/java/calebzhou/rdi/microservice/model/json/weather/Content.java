package calebzhou.rdi.microservice.model.json.weather;

public class Content {
    private String source;
    private String title;
    private String description;

    public String getSource() {
        return source;
    }

    public Content setSource(String source) {
        this.source = source;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Content setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Content setDescription(String description) {
        this.description = description;
        return this;
    }
}
