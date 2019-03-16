package ru.photo.db.repo;

import org.springframework.data.repository.CrudRepository;
import ru.photo.db.model.Emotion;

public interface EmotionRepository extends CrudRepository<Emotion, Long> {
    Emotion findOneById(long id);
}
