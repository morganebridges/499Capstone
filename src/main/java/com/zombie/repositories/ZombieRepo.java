package com.zombie.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zombie.models.Zombie;
@Repository
public class ZombieRepo implements ZombieRepository{

	@Override
	public <S extends Zombie> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Zombie> Iterable<S> save(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Zombie findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Zombie> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Zombie> findAll(Iterable<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Zombie entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Iterable<? extends Zombie> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Zombie> findByGamerTag(String gamerTag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Zombie> findById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
