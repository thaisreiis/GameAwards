package me.dio.diogameawards.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.dio.diogameawards.domain.model.Game;
import me.dio.diogameawards.domain.model.GameRepository;
import me.dio.diogameawards.service.GameService;
import me.dio.diogameawards.service.exception.BusinessException;
import me.dio.diogameawards.service.exception.NoContentException;

@Service
public class GameServiceImpl implements GameService {
	
	@Autowired
	private GameRepository repository;

	@Override
	public List<Game> findAll() {
		return repository.findAll();
	}

	@Override
	public Game findById(Long id) {
		Optional<Game> games = repository.findById(id);
		return games.orElseThrow(() -> new NoContentException());
	}

	@Override
	public void insert(Game game) {
		if (game.getId() != null) {
			throw new BusinessException("Game já existe!");
		}
		repository.save(game);
	}

	@Override
	public void update(Long id, Game game) {
		Game gameDb = findById(id);
		if(gameDb.getId().equals(game.getId())) {
			repository.save(game);
		} else {
			throw new BusinessException("Os id's para alteração são divergentes");
		}
	}

	@Override
	public void delete(Long id) {
		Game gameDb = findById(id);
		repository.delete(gameDb);		
	}
}
