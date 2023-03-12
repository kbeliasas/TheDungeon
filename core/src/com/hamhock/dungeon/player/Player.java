package com.hamhock.dungeon.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Disposable;

public class Player implements Disposable {

    public static final int MOVE_SPEED = 3;
    private static final int FRAME_COLS = 3, FRAME_ROWS = 4;
    public float position_x;
    public float position_y;

    private Animation<TextureRegion> walkAnimation;
    private Texture texture;
    private float stateTime;
    private World world;

    public Player(World world) {
        stateTime = 0f;
        position_x = 10;
        position_y = 12;
        this.world = world;
        texture = new Texture(Gdx.files.internal("Soldier.png"));
        var walkSheet = new Texture(Gdx.files.internal("Soldier 01-1.png"));

        TextureRegion[][] tmp = TextureRegion.split(walkSheet,
                walkSheet.getWidth() / FRAME_COLS,
                walkSheet.getHeight() / FRAME_ROWS);

        TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS];
        var index = 0;

        for (var j = 0; j < FRAME_COLS; j++) {
            walkFrames[index++] = tmp[0][j];
        }
        walkAnimation = new Animation<>(0.5f, walkFrames);
        create_body();
    }

    public void render(Batch batch, float deltaTime) {
        stateTime += deltaTime;
        var currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        batch.draw(texture, position_x, position_y, 1, 1);
//        batch.draw(currentFrame, position_x, position_y);
    }

    private void create_body() {
        BodyDef bodyDef = new BodyDef();
// We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyDef.BodyType.DynamicBody;
// Set our body's starting position in the world
        bodyDef.position.set(10, 12);

// Create our body in the world using our body definition
        Body body = world.createBody(bodyDef);

// Create a circle shape and set its radius to 6
        PolygonShape box = new PolygonShape();
        box.setAsBox(1, 1);

// Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f; // Make it bounce a little bit

// Create our fixture and attach it to the body
        var fixture = body.createFixture(fixtureDef);
        box.dispose();
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
