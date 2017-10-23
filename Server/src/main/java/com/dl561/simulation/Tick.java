package com.dl561.simulation;

import com.dl561.rest.service.ISimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class Tick {

    private final ISimulationService simulationService;
    public static final int TICK_TIME = 30;

    @Autowired
    public Tick(ISimulationService simulationService) {
        this.simulationService = simulationService;
    }

    @PostConstruct
    public void beginTick() {
        Timer timer = new Timer();
        TimerTask timerTask = new TickTask();
        timer.scheduleAtFixedRate(timerTask, 1000, TICK_TIME);
    }

    private void tick() {
        System.out.println("Do tick");
        simulationService.doTick();
    }

    private class TickTask extends TimerTask {
        @Override
        public void run() {
            tick();
        }
    }
}
