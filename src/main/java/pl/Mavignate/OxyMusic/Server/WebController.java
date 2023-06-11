package pl.Mavignate.OxyMusic.Server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.Mavignate.OxyMusic.Server.Entity.Music;
import pl.Mavignate.OxyMusic.Server.Entity.Repositories.MusicRepo;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")
public class WebController {

    @Autowired
    private MusicRepo playlist;

    @GetMapping("/Musics")
    public Iterable<Music> allMusic()
    {
        return playlist.findAll();
    }

    @GetMapping("/Search/{name}")
    public Iterable<Music> SearchMusic(@PathVariable String name) throws IOException {

        YTScraper scraper = new YTScraper();
        List<Music> all = new ArrayList<>();
        List<Music> fromServer = new ArrayList<>();
        List<Music> fromYT = new ArrayList<>();

        for (Music item:
                playlist.findAll()) {
            Pattern pattern = Pattern.compile(name+"*", Pattern.CASE_INSENSITIVE);
            Matcher nameItem, author;
            nameItem = pattern.matcher(item.getName());
            author = pattern.matcher(item.getAuthor());
            if (nameItem.find() || nameItem.find()) fromServer.add(item);
        }

        if (fromServer.isEmpty())
            fromYT = scraper.getMusic(name);
        else
            for (Music YtItem:
                    scraper.getMusic(name)) {
                for (Music DbItem:
                     fromServer) {
                    if (!YtItem.getName().equals(DbItem.getName()))
                        fromYT.add(YtItem);
                }
            }
        all.addAll(fromServer);
        all.addAll(fromYT);

        Iterable <Music> out = all;
        return out;
    }

    @GetMapping(
        value = "/play/{url}",
        produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public byte[] getfile(@PathVariable String url) throws IOException {
        return Files.readAllBytes(Paths.get("./sounds/"+url+".mp3"));
    }

    @PostMapping("/Download")
    public Music downloadMusic(@RequestBody Music music) throws IOException {
        String oldUrl = music.getUrl();

        music.setUrl(String.valueOf(playlist.count() + 1));
        playlist.save(music);

        YTScraper yt = new YTScraper();
        yt.download(oldUrl, music.getId().toString());

        return music;
    }

}
