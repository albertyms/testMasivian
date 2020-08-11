package com.masivian.test.controller;

import com.masivian.test.Request.BetRequest;
import com.masivian.test.Request.RouletteRequest;
import com.masivian.test.model.Roulette;
import com.masivian.test.service.RouletteServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "/api/roulette")
public class RouletteController {
    private Logger logger = LogManager.getLogger(RouletteController.class);

    @Autowired
    RouletteServiceImpl rouletteService;

    @PostMapping
    public ResponseEntity createRoulette(@Validated @RequestBody RouletteRequest rouletteRequest) {
        JSONObject rouletteJson = new JSONObject(rouletteRequest.getRoullete().toJson());
        rouletteJson.put("createDate", new Date().getTime())
                .put("status", "created");
        Document rouletteDoc = Document.parse(rouletteJson.toString());
        Roulette roulette = new Roulette(rouletteDoc);
        roulette = rouletteService.createRoulette(roulette);
        return ResponseEntity.ok(roulette.getId());
    }

    @PutMapping("/openRoulette")
    public ResponseEntity openRoulette(@Validated @RequestBody String idStr) {
        JSONObject jsonObject = new JSONObject(idStr);
        Roulette roulette = rouletteService.getRoulette(jsonObject.getString("id"));
        roulette.getRoulette().put("status", "open");
        rouletteService.updateRoulette(roulette);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/addBet")
    public ResponseEntity addBet(@Validated @RequestBody BetRequest betRequest) {
        JSONObject betJson = new JSONObject(betRequest.getBet().toJson());
        Roulette roulette = rouletteService.getRoulette(betJson.getString("id"));
        ResponseEntity responseEntity = dataBetValidation(roulette, betJson);
        if (responseEntity == null) {
            List<Document> betsList = new ArrayList<>();
            betJson.remove("id");
            if (roulette.getRoulette().containsKey("bets")) {
                betsList.addAll((Collection<? extends Document>) roulette.getRoulette().get("bets"));
            }
            betsList.add(Document.parse(betJson.toString()));
            roulette.getRoulette().put("bets", betsList);
            rouletteService.updateRoulette(roulette);
            return ResponseEntity.ok().build();
        } else {
            return responseEntity;
        }
    }

    @PutMapping("/closeRoulette")
    public ResponseEntity closeRoulette(@Validated @RequestBody String idStr) {
        JSONObject jsonObject = new JSONObject(idStr);
        Roulette roulette = rouletteService.getRoulette(jsonObject.getString("id"));
        roulette.getRoulette().put("status", "close");
        rouletteService.updateRoulette(roulette);
        return ResponseEntity.ok(roulette);
    }

    @GetMapping
    public ResponseEntity getRouletteList() {
        List<Roulette> rouletteList = new ArrayList<>();
        rouletteList.addAll(rouletteService.getListRoulette());
        return ResponseEntity.ok(rouletteList);
    }

    private ResponseEntity dataBetValidation(Roulette roulette, JSONObject betJson) {
        if (betJson.getDouble("amountMoney") > 10000) {
            return ResponseEntity.ok("El valor de la apuesta es mayor a $10000");
        }
        if (roulette.getRoulette().getString("status").equals("close")) {
            return ResponseEntity.ok("Ya fueron cerradas las apuestas");
        }
        if (roulette.getRoulette().getString("status").equals("created")) {
            return ResponseEntity.ok("Esta reluta aún no esta abierta para apuestas");
        }
        return null;
    }
}
