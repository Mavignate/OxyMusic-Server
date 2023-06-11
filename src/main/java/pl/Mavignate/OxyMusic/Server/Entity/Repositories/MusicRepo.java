package pl.Mavignate.OxyMusic.Server.Entity.Repositories;

import org.springframework.data.repository.CrudRepository;
import pl.Mavignate.OxyMusic.Server.Entity.Music;

public interface MusicRepo extends CrudRepository<Music, Long> {
}
