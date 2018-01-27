package com.dl561.simulation.hud;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class Hud {

    private List<TextHud> hudList;

    public Hud() {
        hudList = new LinkedList<>();
    }

    public List<TextHud> getHudList() {
        return hudList;
    }

    public void setHudList(List<TextHud> hudList) {
        this.hudList = hudList;
    }
}
