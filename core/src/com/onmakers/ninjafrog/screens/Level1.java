package com.onmakers.ninjafrog.screens;

public class Level1 { // implements Screen, InputProcessor {
    /*private SpriteBatch batch;

    //camera
    private OrthographicCamera camera;
    private Viewport viewport;

    //tile
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer renderer;
    int mapPixelHeight;
    int mapPixelWidth;

    //player
    private Sprite sprite;
    private Player player;

    //stage
    private Stage stage;
    private Skin skin;
    private TextureAtlas atlas;
    private Table table, tableFoot;
    private BitmapFont white, black;

    //private Image Button buttonPlay, buttonExit;
    private ImageButton buttonLeft,buttonRight;
    private Label heading;
    //drawable
    Drawable leftup,leftdown,rightup,rightdown;

    //variables
    float width = Gdx.graphics.getWidth(), height = Gdx.graphics.getHeight();


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        player.move(screenX, screenY);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        player.playerStill();
        return true;
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
    public boolean scrolled(int amount) {
        return false;
    }


    @Override
    public void show() {
        batch = new SpriteBatch();

        //Create camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, width, height);
        camera.update();
        viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),camera);
        viewport.apply();


        //input
        Gdx.input.setInputProcessor(this);

        //tile
        tiledMap = new TmxMapLoader().load("maps/lvl1.tmx");
        renderer = new OrthogonalTiledMapRenderer(tiledMap);
        MapProperties prop = tiledMap.getProperties();

        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);
        int tilePixelWidth = prop.get("tilewidth", Integer.class);
        int tilePixelHeight = prop.get("tileheight", Integer.class);

        mapPixelWidth = mapWidth * tilePixelWidth;
        mapPixelHeight = mapHeight * tilePixelHeight;

        //create player
        sprite = new Sprite(new Texture("images/frog_1.png"));
        sprite.setPosition(Gdx.graphics.getWidth() * 0.2f, Gdx.graphics.getHeight() * 3);
        sprite.setSize(Gdx.graphics.getWidth() * 0.2f, Gdx.graphics.getHeight() * 0.2f);
       // player = new Player(sprite, (TiledMapTileLayer) tiledMap.getLayers().get(0));
        //player.setPosition(1000, Gdx.graphics.getHeight() * 2);


        //stage
        stage = new Stage();

        atlas = new TextureAtlas("ui/atlas.pack");
        skin = new Skin(atlas);

        //font
        white = new BitmapFont(Gdx.files.internal("font/white32.fnt"), false);
        black = new BitmapFont(Gdx.files.internal("font/black32.fnt"), false);

        //table
        table = new Table(skin);
        tableFoot = new Table(skin);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //Button Style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("button.up");
        textButtonStyle.down = skin.getDrawable("button.down");
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = black;

        //Left Button
         leftup = new TextureRegionDrawable(new TextureRegion(new Texture("images/button/arrowleft.up.png")));
       //  leftdown = new TextureRegionDrawable(new TextureRegion(new Texture("images/button/arrowleft.dowm.png")));
        buttonLeft = new ImageButton(leftup);
        *//*buttonLeft.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }
        });*//*

        //Right Button
         rightup = new TextureRegionDrawable(new TextureRegion(new Texture("images/button/arrowright.up.png")));
   //      rightdown = new TextureRegionDrawable(new TextureRegion(new Texture("images/button/arrowright.down.png")));
        buttonRight = new ImageButton(rightup);
       *//* buttonRight.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }
        });
*//*
        //Label Style
        Label.LabelStyle headingStyle = new Label.LabelStyle(white, Color.WHITE);
        heading = new Label("Ninja Frog "+Gdx.graphics.getWidth() +" "+ Gdx.graphics.getHeight(), headingStyle);
        heading.setFontScale(3);

        //Add in table foot
        tableFoot.add(buttonLeft).padLeft(100).height(100).width(100);
        tableFoot.add(buttonRight).padLeft(100).height(100).width(100);
        tableFoot.align(Align.bottomLeft);
        tableFoot.debug();// TODO remove later

        //Add all stuff to Stage
        table.add(heading).top().center().align(Align.topLeft).height(height * 0.1f);
        table.row().space(height * 0.8f);
        table.add(tableFoot).left().align(Align.bottomLeft).height(height * 0.1f);

        table.debug();// TODO remove later
        stage.addActor(table.align(Align.left));


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

       float halfWidth = player.getX() + player.getWidth() / 2;
        float halfHeight = player.getY() + player.getHeight() / 2;

        //for x axis
        if ((camera.viewportWidth/2 )> halfWidth)
            camera.position.x=camera.viewportWidth/2;
        else if((mapPixelWidth - camera.viewportWidth/2)< halfWidth)
            camera.position.x=mapPixelWidth -camera.viewportWidth/2;
        else
            camera.position.x=halfWidth;

        //for y axis
        if(camera.viewportHeight/2 > halfHeight)
            camera.position.y= camera.viewportHeight/2;
        else if((mapPixelHeight-camera.viewportHeight/2) < halfHeight)
            camera.position.y = mapPixelHeight-camera.viewportHeight/2;
        else
            camera.position.y = halfHeight;

        camera.update();

        //tile
        renderer.setView(camera);
        renderer.render();

        //Draw
        renderer.getBatch().begin();
        player.draw(renderer.getBatch());
        renderer.getBatch().end();

        //draw stage
        stage.act();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        camera.viewportHeight = height;
        camera.viewportWidth = width;
        viewport.update(width, height);
        stage.getViewport().update(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
        tiledMap.dispose();
        renderer.dispose();
        player.getTexture().dispose();
        sprite.getTexture().dispose();
        stage.dispose();
        stage.dispose();
        skin.dispose();
        atlas.dispose();
        white.dispose();
        black.dispose();
    }*//* private SpriteBatch batch;

    //camera
    private OrthographicCamera camera;
    private Viewport viewport;

    //tile
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer renderer;
    int mapPixelHeight;
    int mapPixelWidth;

    //player
    private Sprite sprite;
    private Player player;

    //stage
    private Stage stage;
    private Skin skin;
    private TextureAtlas atlas;
    private Table table, tableFoot;
    private BitmapFont white, black;

    //private Image Button buttonPlay, buttonExit;
    private ImageButton buttonLeft,buttonRight;
    private Label heading;
    //drawable
    Drawable leftup,leftdown,rightup,rightdown;

    //variables
    float width = Gdx.graphics.getWidth(), height = Gdx.graphics.getHeight();


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        player.move(screenX, screenY);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        player.playerStill();
        return true;
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
    public boolean scrolled(int amount) {
        return false;
    }


    @Override
    public void show() {
        batch = new SpriteBatch();

        //Create camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, width, height);
        camera.update();
        viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),camera);
        viewport.apply();


        //input
        Gdx.input.setInputProcessor(this);

        //tile
        tiledMap = new TmxMapLoader().load("maps/lvl1.tmx");
        renderer = new OrthogonalTiledMapRenderer(tiledMap);
        MapProperties prop = tiledMap.getProperties();

        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);
        int tilePixelWidth = prop.get("tilewidth", Integer.class);
        int tilePixelHeight = prop.get("tileheight", Integer.class);

        mapPixelWidth = mapWidth * tilePixelWidth;
        mapPixelHeight = mapHeight * tilePixelHeight;

        //create player
        sprite = new Sprite(new Texture("images/frog_1.png"));
        sprite.setPosition(Gdx.graphics.getWidth() * 0.2f, Gdx.graphics.getHeight() * 3);
        sprite.setSize(Gdx.graphics.getWidth() * 0.2f, Gdx.graphics.getHeight() * 0.2f);
       // player = new Player(sprite, (TiledMapTileLayer) tiledMap.getLayers().get(0));
        //player.setPosition(1000, Gdx.graphics.getHeight() * 2);


        //stage
        stage = new Stage();

        atlas = new TextureAtlas("ui/atlas.pack");
        skin = new Skin(atlas);

        //font
        white = new BitmapFont(Gdx.files.internal("font/white32.fnt"), false);
        black = new BitmapFont(Gdx.files.internal("font/black32.fnt"), false);

        //table
        table = new Table(skin);
        tableFoot = new Table(skin);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //Button Style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("button.up");
        textButtonStyle.down = skin.getDrawable("button.down");
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = black;

        //Left Button
         leftup = new TextureRegionDrawable(new TextureRegion(new Texture("images/button/arrowleft.up.png")));
       //  leftdown = new TextureRegionDrawable(new TextureRegion(new Texture("images/button/arrowleft.dowm.png")));
        buttonLeft = new ImageButton(leftup);
        *//*buttonLeft.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }
        });*//*

        //Right Button
         rightup = new TextureRegionDrawable(new TextureRegion(new Texture("images/button/arrowright.up.png")));
   //      rightdown = new TextureRegionDrawable(new TextureRegion(new Texture("images/button/arrowright.down.png")));
        buttonRight = new ImageButton(rightup);
       *//* buttonRight.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {

            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }
        });
*//*
        //Label Style
        Label.LabelStyle headingStyle = new Label.LabelStyle(white, Color.WHITE);
        heading = new Label("Ninja Frog "+Gdx.graphics.getWidth() +" "+ Gdx.graphics.getHeight(), headingStyle);
        heading.setFontScale(3);

        //Add in table foot
        tableFoot.add(buttonLeft).padLeft(100).height(100).width(100);
        tableFoot.add(buttonRight).padLeft(100).height(100).width(100);
        tableFoot.align(Align.bottomLeft);
        tableFoot.debug();// TODO remove later

        //Add all stuff to Stage
        table.add(heading).top().center().align(Align.topLeft).height(height * 0.1f);
        table.row().space(height * 0.8f);
        table.add(tableFoot).left().align(Align.bottomLeft).height(height * 0.1f);

        table.debug();// TODO remove later
        stage.addActor(table.align(Align.left));


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

       float halfWidth = player.getX() + player.getWidth() / 2;
        float halfHeight = player.getY() + player.getHeight() / 2;

        //for x axis
        if ((camera.viewportWidth/2 )> halfWidth)
            camera.position.x=camera.viewportWidth/2;
        else if((mapPixelWidth - camera.viewportWidth/2)< halfWidth)
            camera.position.x=mapPixelWidth -camera.viewportWidth/2;
        else
            camera.position.x=halfWidth;

        //for y axis
        if(camera.viewportHeight/2 > halfHeight)
            camera.position.y= camera.viewportHeight/2;
        else if((mapPixelHeight-camera.viewportHeight/2) < halfHeight)
            camera.position.y = mapPixelHeight-camera.viewportHeight/2;
        else
            camera.position.y = halfHeight;

        camera.update();

        //tile
        renderer.setView(camera);
        renderer.render();

        //Draw
        renderer.getBatch().begin();
        player.draw(renderer.getBatch());
        renderer.getBatch().end();

        //draw stage
        stage.act();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        camera.viewportHeight = height;
        camera.viewportWidth = width;
        viewport.update(width, height);
        stage.getViewport().update(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
        tiledMap.dispose();
        renderer.dispose();
        player.getTexture().dispose();
        sprite.getTexture().dispose();
        stage.dispose();
        stage.dispose();
        skin.dispose();
        atlas.dispose();
        white.dispose();
        black.dispose();
    }*/
}


