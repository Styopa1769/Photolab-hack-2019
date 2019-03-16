package ru.photo.db.repo;

import org.springframework.data.repository.CrudRepository;
import ru.photo.db.model.Meme;

public interface MemeRepository extends CrudRepository<Meme, Long> {
}
