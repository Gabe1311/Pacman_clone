package Entities;

import Control.Game;

public class Effect {
    private final Game game;
    private final int powerUpId;
    private final Thread effectThread;
    public Effect(Game game, int powerUpId){
        this.game = game;
        this.powerUpId = powerUpId;
        effectThread = new Thread(new EffectCooldown());
        effectThread.start();
    }
    private class EffectCooldown implements Runnable{
        @Override
        public void run() {
            int i = 0;
            while (true){
                try {
                    if(i >0){
                        switch (powerUpId){
                            case 0:
                                game.getPlayer().addOneLifeCounter();
                                break;
                            case 1:
                            case 2:
                            case 3:
                                game.getPlayer().setSpeedMultiplier(1.0f);
                                break;
                            case 4:
                            case 5:
                                game.getPlayer().setImmortal(false);
                                break;
                        }
                        effectThread.interrupt();
                        break;
                    }
                    switch (powerUpId){
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                            game.getPlayer().setSpeedMultiplier(1.5f);
                            break;
                        case 4:
                        case 5:
                            game.getPlayer().setImmortal(true);
                    }
                    i++;
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.getSuppressed();
                }

            }
        }
    }
}