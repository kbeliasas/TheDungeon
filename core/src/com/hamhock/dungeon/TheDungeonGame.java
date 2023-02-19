package com.hamhock.dungeon;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.hamhock.dungeon.managers.InputManager;
import com.hamhock.dungeon.player.Player;

public class TheDungeonGame extends ApplicationAdapter {

    Player player;
    TiledMap tiledMap;
    OrthogonalTiledMapRenderer tiledMapRenderer;
    ExtendViewport viewport;
    InputManager inputManager;
    World world;
    Box2DDebugRenderer debugRenderer;

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    @Override
    public void create() {
//        var camera = new OrthographicCamera();
//        camera.setToOrtho(false, 16, 9);
//        camera.update();

        tiledMap = new TmxMapLoader().load("path.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        viewport = new ExtendViewport(32, 18);
        Box2D.init();
        world = new World(new Vector2(0, 0), true);
        player = new Player(world);
        debugRenderer = new Box2DDebugRenderer();
        inputManager = new InputManager(player, this);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        var deltaTime = Gdx.graphics.getDeltaTime();
        inputManager.movePlayer(deltaTime);
        viewport.apply();
//        ((OrthographicCamera) viewport.getCamera()).zoom = 32f;
        tiledMapRenderer.setView((OrthographicCamera) viewport.getCamera());
        tiledMapRenderer.render();
//        spriteBatch.begin();
//        spriteBatch.draw(currentFrame, player_x, player_y); // Draw current frame at (50, 50)
//        spriteBatch.end();
        var batch = tiledMapRenderer.getBatch();
        batch.begin();
        player.render(batch, deltaTime);
        batch.end();

//        camera.position.set(player_x, player_y, 0);
        var lerp = 0.7f;
        var position = viewport.getCamera().position;
        position.x += (player.position_x - position.x) * lerp * deltaTime;
        position.y += (player.position_y - position.y) * lerp * deltaTime;
//        viewport.unproject(position);
        viewport.getCamera().update();
        debugRenderer.render(world, viewport.getCamera().combined);
        world.step(1/60f, 6, 2);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, false);
//        viewport.getCamera().position.set(player.position_x,player.position_y,0);
//        viewport.getCamera().update();
    }

    @Override
    public void dispose() {
        tiledMap.dispose();
        tiledMapRenderer.dispose();
        player.dispose();
    }
}
