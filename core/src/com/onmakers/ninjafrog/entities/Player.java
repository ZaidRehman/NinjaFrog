package com.onmakers.ninjafrog.entities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;

import static com.onmakers.ninjafrog.utils.Constants.BIT_FROG;
import static com.onmakers.ninjafrog.utils.Constants.BIT_OWL;
import static com.onmakers.ninjafrog.utils.Constants.BIT_WALL;
import static com.onmakers.ninjafrog.utils.Constants.PPM;

public class Player  {

    public Body body;
    public DistanceJoint dJoint;
    String id;

    public Player(World world, String id, float x, float y, float width, float height){
        this.id = id;
        createBoxBody(world,x,y,width,height);
        body.setLinearDamping(1f);
    }
    public void createBoxBody(World world,float x,float y, float width, float hieght){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(x / PPM, y / PPM);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / PPM,hieght / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density =0.5f;
        fixtureDef.restitution = 0.0f;
        fixtureDef.filter.categoryBits = BIT_FROG;
        fixtureDef.filter.maskBits = BIT_FROG | BIT_WALL | BIT_OWL;

        this.body = world.createBody(bodyDef);
        this.body.createFixture(fixtureDef).setUserData(this);

        shape.dispose();
    }


    public void hit(){
        System.out.println(id + ": has been hit");
    }

    /*private Vector2 velocity = new Vector2();
    private static float speed = 60 * 2, gravity = 60 * 1.8f ;
    private TiledMapTileLayer collisionLayer;
    private boolean canJump= true;
    //private Sprite sprite;

    public Player(Sprite sprite, TiledMapTileLayer collisionLayer) {
        super(sprite);
       // this.sprite = sprite;
        this.collisionLayer = collisionLayer;
    }

    @Override
    public void draw(Batch batch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }

    private void update(float deltaTime) {

        //apply gravity
        velocity.y -= gravity * deltaTime;

        //clamp velocity
        if (velocity.y > speed) {
            velocity.y = speed;
        } else if (velocity.y < -speed) {
            velocity.y = -speed;
        }

        //save old values
        float oldX = getX(), oldY = getY();
        float tileWidth = collisionLayer.getTileWidth(), tileHieght = collisionLayer.getTileHeight();
        boolean collisionX = false, collisionY = false;

        //move on
        setX(getX() + velocity.x * deltaTime * 3f);
        if (velocity.x < 0) {                  //going left
            collisionX =collidesLeft();

        } else if (velocity.x > 0) {           //going right
            collisionX = collidesRight();
        }

        //react to x collision
        if(collisionX){
            setX(oldX);
            velocity.x = 0;
        }

        //move on y
        setY(getY() + velocity.y * deltaTime * 3f);
        if (velocity.y < 0) {                   //going bottom
            canJump = collisionY = collidesBottom();

        } else if (velocity.y > 0) {            //going top
            collisionY = collidesTop();
        }

        //react to y collision
        if(collisionY){
            setY(oldY);
            velocity.y = 0;
        }
    }
    public void move(float screenX,float screenY){
        float width = Gdx.graphics.getWidth();
       // velocity.x = speed * Math.signum(screenX - width / 2); // hard
         velocity.x = speed * (screenX - width / 2) / width * 2; // fade

        float height = Gdx.graphics.getHeight();
        if(canJump && screenY < height / 2) { // experiment by changing the 2 to change how high the touch has to be to jump
            velocity.y = speed * 50;
            canJump = false;
        }
    }
    public void playerStill(){
        velocity.x = 0;
    }


    public boolean isCellBlocked (float x, float y){
        TiledMapTileLayer.Cell cell = collisionLayer.getCell(
                (int) (x / collisionLayer.getTileWidth()),
                (int) (y / collisionLayer.getTileHeight()));

        return  cell != null &&
                cell.getTile() != null &&
                cell.getTile().getProperties().containsKey("blocked");
    }
    public boolean collidesRight() {
        for(float step = 0; step < getHeight(); step += collisionLayer.getTileHeight() / 2)
            if(isCellBlocked(getX() + getWidth(), getY() + step))
                return true;
        return false;
    }

    public boolean collidesLeft() {
        for(float step = 0; step < getHeight(); step += collisionLayer.getTileHeight() / 2)
            if(isCellBlocked(getX(), getY() + step))
                return true;
        return false;
    }

    public boolean collidesTop() {
        for(float step = 0; step < getWidth(); step += collisionLayer.getTileWidth() / 2)
            if(isCellBlocked(getX() + step, getY() + getHeight()))
                return true;
        return false;
    }

    public boolean collidesBottom() {
        for(float step = 0; step < getWidth(); step +=1)
            if(isCellBlocked(getX() + step, getY()))
                return true;
        return false;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public static float getSpeed() {
        return speed;
    }

    public static void setSpeed(float speed) {
        Player.speed = speed;
    }

    public static float getGravity() {
        return gravity;
    }

    public static void setGravity(float gravity) {
        Player.gravity = gravity;
    }

    public TiledMapTileLayer getCollisionLayer() {
        return collisionLayer;
    }

    public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
        this.collisionLayer = collisionLayer;
    }
*/
}
