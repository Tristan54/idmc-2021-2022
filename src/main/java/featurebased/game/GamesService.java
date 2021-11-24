package featurebased.game;

import fr.unilorraine.idmc.gamescatalog.exceptions.PublisherNotFound;
import fr.unilorraine.idmc.gamescatalog.repositories.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GamesService {

    private final GamesRepository repo;
    private final PublisherRepository publisherRepo;
    private final GamesMapper mapper;

    public List<GameView> findAll() {
        return mapper.toDto(repo.findAll());
    }

    public GameView create(NewGame newGame) {
        var gg = mapper.toEntity(newGame);
        var publisher =
                publisherRepo
                        .findById(newGame.getPublisherId())
                        .orElseThrow(() -> PublisherNotFound.of(newGame));

        gg.setPublisher(publisher);
        gg = repo.save(gg);

        return mapper.toDto(gg);
    }
}
