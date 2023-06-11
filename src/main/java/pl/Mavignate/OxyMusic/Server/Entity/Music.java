package pl.Mavignate.OxyMusic.Server.Entity;

import jakarta.persistence.*;

@Entity
public class Music {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String author;
    @Column
    private String url;

    public Music()
    {
        super();
    }

    public Music(String name, String author, String url)
    {
        this.name = name;
        this.author = author;
        this.url = url;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
    this.url = url;
}

    public Long getId() {
        return id;
    }
}
