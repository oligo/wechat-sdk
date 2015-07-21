package net.rogers1b.wechat.message.upstream;

/**
 * 图文消息
 *
 * @author Rogers
 */
public class Article {

    private String title;
    private String description;
    private String picUrl;
    private String url;

    public Article(String title, String description, String picUrl, String url) {
        this.title = title;
        this.description = description;
        this.picUrl = picUrl;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public String getUrl() {
        return url;
    }
}
