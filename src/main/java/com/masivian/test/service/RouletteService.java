package com.masivian.test.service;

import com.masivian.test.model.Roulette;

import java.util.List;

public interface RouletteService {

    Roulette createRoulette(Roulette roulette);

    Roulette getRoulette(String id);

    Roulette updateRoulette(Roulette roulette);

    List<Roulette> getListRoulette();

}
