package com.cycapservers.game.database;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameEventsRepository extends CrudRepository<GameEventsEntity, String>{

}
