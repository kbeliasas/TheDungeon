package com.hamhock.dungeon.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.hamhock.dungeon.TheDungeonGame;
import com.hamhock.dungeon.player.Player;

public class InputManager implements InputProcessor {

    private Player player;
    private TheDungeonGame game;

    public InputManager(Player player, TheDungeonGame game) {
        this.player = player;
        this.game = game;
        Gdx.input.setInputProcessor(this);
    }

    public void movePlayer(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
//            camera.translate(-32, 0);
            player.position_x -= player.MOVE_SPEED * dt;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            player.position_x += player.MOVE_SPEED * dt;
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            player.position_y += player.MOVE_SPEED * dt;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            player.position_y -= player.MOVE_SPEED * dt;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
//        if (keycode == Input.Keys.LEFT)
////            camera.translate(-32, 0);
//            player.position_x -= 32;
//        if (keycode == Input.Keys.RIGHT)
//            player.position_x += 32;
//        if (keycode == Input.Keys.UP)
//            player.position_y += 32;
//        if (keycode == Input.Keys.DOWN)
//            player.position_y -= 32;
        if (keycode == Input.Keys.NUM_1)
            game.getTiledMap().getLayers().get(0).setVisible(!game.getTiledMap().getLayers().get(0).isVisible());
        if (keycode == Input.Keys.NUM_2)
            game.getTiledMap().getLayers().get(1).setVisible(!game.getTiledMap().getLayers().get(1).isVisible());
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
