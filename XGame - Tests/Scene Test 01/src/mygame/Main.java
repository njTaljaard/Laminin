package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
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
        
        
        //BulletAppState bulletAppState = new BulletAppState();
        //bulletAppState.setThreadingType(BulletAppState.ThreadingType.PARALLEL);
        //stateManager.attach(bulletAppState);
        
        initBox();
        initScene();
        initLight();
        initPPcWater();
        character player = new character(assetManager, inputManager);
        rootNode.attachChild(player.retrieveNode());
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
        CollisionShape sceneShape = CollisionShapeFactory.createMeshShape(sceneModel);
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
