package pl.Mavignate.OxyMusic.Server;


import io.github.gaeqs.javayoutubedownloader.JavaYoutubeDownloader;
import io.github.gaeqs.javayoutubedownloader.decoder.MultipleDecoderMethod;
import io.github.gaeqs.javayoutubedownloader.stream.StreamOption;
import io.github.gaeqs.javayoutubedownloader.stream.YoutubeVideo;
import io.github.gaeqs.javayoutubedownloader.stream.download.StreamDownloader;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pl.Mavignate.OxyMusic.Server.Entity.Music;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YTScraper {
    public List<Music> getMusic(String search) throws IOException {

        String url= String.format("https://www.bing.com/videos/search?q=%s&FORM=HDRSC4", search);
        List<Music> playlist = new ArrayList<>();

        Connection connect = Jsoup.connect(url);
        Document document = connect.get();
        Elements alldiv = document.select("div.mc_vtvc_con_rc");
        Pattern pattern = Pattern.compile("youtube*",Pattern.CASE_INSENSITIVE);
        for (Element item: alldiv) {
            Matcher matcher = pattern.matcher(item.attr("ourl"));
            if (matcher.find())
                playlist.add(new Music(item.select("strong").text(), item.select("span.mc_vtvc_meta_row_channel").text(), item.attr("ourl")));
        }

        return playlist;
    }

    public void download(String url, String id) throws IOException {
        Runtime r = Runtime.getRuntime();
        String cmd = "./sounds/download " + url + " " + id;
        Process process = r.exec(cmd);

        BufferedReader is = new BufferedReader(
                new InputStreamReader(process.getInputStream()));
        String line;

        // reading the output
        while ((line = is.readLine()) != null)
            System.out.println(line);
    }

}
