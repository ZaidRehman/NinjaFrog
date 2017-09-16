package com.onmakers.ninjafrog.utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.onmakers.ninjafrog.entities.Coin;
import com.onmakers.ninjafrog.entities.Enemy;

import static com.onmakers.ninjafrog.utils.Constants.BIT_FROG;
import static com.onmakers.ninjafrog.utils.Constants.BIT_OWL;
import static com.onmakers.ninjafrog.utils.Constants.BIT_WALL;
import static com.onmakers.ninjafrog.utils.Constants.coins;
import static com.onmakers.ninjafrog.utils.Constants.flyingOwls;
import static com.onmakers.ninjafrog.utils.Constants.mapPixelHeight;
import static com.onmakers.ninjafrog.utils.Constants.mapPixelWidth;

public class TiledObjectUtil {
    public static void parseTiledObjetLayer(World world, MapObjects objects) {
        int i = 0, j =0 ;
        for (MapObject object: objects ) {
            Shape shape;


            if(object instanceof PolylineMapObject) {
                shape = createPolyline( (PolylineMapObject) object);
            } else {
                continue;
            }
            if(object.getProperties().containsKey("coin")){
                createCoin(world,(PolylineMapObject) object,i);
                i ++;
            }else if(object.getProperties().containsKey("flyingowl")){
                createFlyingOwl(world,(PolylineMapObject) object,j);
                j++;
            }else if(object.getProperties().containsKey("dead")){
                Body body;
                BodyDef bdef = new BodyDef();
                bdef.type = BodyDef.BodyType.StaticBody;
                body = world.createBody(bdef);

                FixtureDef fd = new FixtureDef();
                fd.shape = shape;
                fd.density = 1.0f;
                fd.isSensor = true;

                body.createFixture(fd).setUserData("SPIKES");
            }else{
                Body body;
                BodyDef bdef = new BodyDef();
                bdef.type = BodyDef.BodyType.StaticBody;
                body = world.createBody(bdef);

                FixtureDef fd = new FixtureDef();
                fd.shape = shape;
                fd.density = 1.0f;
                fd.filter.categoryBits = Constants.BIT_WALL;
                fd.filter.maskBits = Constants.BIT_WALL | BIT_FROG | BIT_OWL;
                fd.filter.groupIndex = 1;
                //fd.filter.categoryBits = BIT_WALL ;
                //fd.filter.maskBits = BIT_FROG;

                body.createFixture(fd).setUserData("WALL");
            }

            shape.dispose();
        }
    }
    private static void createCoin(World world,PolylineMapObject polyline,int i){
        float[] vertices = polyline.getPolyline().getTransformedVertices();
        coins.add(new Coin(world, "COIN" + i, vertices[0], vertices[1], 32, 32));
    }
    private  static void createFlyingOwl(World world,PolylineMapObject polyline,int j){
        float[] vertices = polyline.getPolyline().getTransformedVertices();
        flyingOwls.add(new Enemy(world, "flyingOwl"+j,vertices[0], vertices[1], 30, 30));
    }
    private static ChainShape createPolyline(PolylineMapObject polyline) {
        float[] vertices = polyline.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for(int i = 0; i < worldVertices.length; i++){
            worldVertices[i] = new Vector2(vertices[i * 2] / Constants.PPM , vertices[i * 2 + 1] / Constants.PPM);
        }
        ChainShape cs = new ChainShape();
        cs.createChain(worldVertices);
        return cs;
    }
}
