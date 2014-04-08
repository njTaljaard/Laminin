package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture2D;
import com.jme3.water.WaterFilter;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    private Spatial sceneModel;
    private WaterFilter water;
    private Vector3f lightDir = new Vector3f(-4.0f,-1.0f,-5.0f);
    
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        flyCam.setMoveSpeed(100f);
        initBox();
        initScene();
        initLight();
        initPPcWater();
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }    
    
    private void initBox() {
        Box b = new Box(1, 1, 1);
        Geometry blue = new Geometry("Box", b);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        blue.setMaterial(mat);
         
        /** Attach the two boxes to the *pivot* node. (And transitively to the root node.) */
        rootNode.attachChild(blue);
    }
    
    private void initScene() {
        sceneModel = assetManager.loadModel("Scenes/Scene.j3o");
        if (sceneModel == null)
            System.out.println("not loaded");
        rootNode.attachChild(sceneModel);
    }
    
    private void initLight() {    /** A white, directional light source */ 
        /** A white ambient light source. */
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        rootNode.addLight(ambient); 
    }
    /*
    public void initSimpleWater() { 
        SimpleWaterProcessor waterProcessor = new SimpleWaterProcessor(assetManager);
        waterProcessor.setReflectionScene(sceneModel); 
        Vector3f waterLocation = new Vector3f(0,-6,0); 
        waterProcessor.setPlane(new Plane(Vector3f.UNIT_Y, waterLocation.dot(Vector3f.UNIT_Y))); 
        viewPort.addProcessor(waterProcessor); 
        waterProcessor.setWaterDepth(10); 
        // transparency of water 
        waterProcessor.setDistortionScale(0.05f); 
        // strength of waves 
        waterProcessor.setWaveSpeed(0.05f); 
        // speed of waves 
        Quad quad = new Quad(800,800); 
        quad.scaleTextureCoordinates(new Vector2f(6f,6f)); 
        Geometry water = new Geometry("water", quad); 
        water.setLocalRotation(new Quaternion().fromAngleAxis(-FastMath.HALF_PI, Vector3f.UNIT_X)); 
        water.setLocalTranslation(-400, 0.32f, 400); 
        water.setShadowMode(RenderQueue.ShadowMode.Receive); 
        water.setMaterial(waterProcessor.getMaterial()); 
        rootNode.attachChild(water); 
    }     */
    
    public void initPPcWater() { 
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager); 
        water = new WaterFilter(rootNode, lightDir); 
        water.setUseHQShoreline(true);
        water.setUseRefraction(true);
        water.setUseRipples(true);
        water.setUseSpecular(true);
        water.setShoreHardness(0.05f);
        water.setUnderWaterFogDistance(80);
        water.setWindDirection(new Vector2f(0.7f, 0.2f));
        water.setCenter(Vector3f.ZERO); 
        water.setRadius(2600); 
        water.setWaveScale(0.003f); 
        water.setMaxAmplitude(6.0f); 
        water.setFoamExistence(new Vector3f(1.0f, 4.0f, 0.5f)); 
        water.setFoamTexture((Texture2D) assetManager.loadTexture("Common/MatDefs/Water/Textures/foam2.jpg")); 
        water.setRefractionStrength(0.2f); water.setWaterHeight(5.0f); 
        fpp.addFilter(water); 
        viewPort.addProcessor(fpp); 
    }
}
