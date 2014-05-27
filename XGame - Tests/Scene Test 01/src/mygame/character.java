/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;

/**
 *
 * @author Nico
 */
public class character {
    
    private Node player;
    private Node playerNode;
    private AnimChannel channel;
    private AnimControl control;
    private AnimEventListener event;
    
    public character(AssetManager assMan, InputManager inMan) {
        playerNode = new Node("Player");
        initAnimEventListener();
        initModel(assMan);
        initKeys(inMan);
    }
    
    private void initAnimEventListener() {
        event = new AnimEventListener() {
            public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
                if (animName.equals("Walk")) {
                    channel.setAnim("stand", 0.50f);
                    channel.setLoopMode(LoopMode.DontLoop);
                    channel.setSpeed(1f);
                }
            }

            public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
                System.out.println("bo");
            }
        };
    }
    
    private void initModel(AssetManager assMan) {
        player = (Node) assMan.loadModel("Models/big_man/big_man.j3o");
        player.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        player.addControl(new AnimControl());
        
        control = player.getControl(AnimControl.class);
        control.addListener(event);
        channel = control.createChannel();
        channel.setAnim("Walk");
        
        playerNode.attachChild(player);
    }
    
    private void initKeys(InputManager inMan) {
        inMan.addMapping("Walk", new KeyTrigger(KeyInput.KEY_SPACE));
        inMan.addListener(actionListener, "Walk");
    }
      
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (name.equals("Walk") && !keyPressed) {
                if (!channel.getAnimationName().equals("Walk")) {
                  channel.setAnim("Walk", 0.50f);
                  channel.setLoopMode(LoopMode.Loop);
                }
            }
        }
    };
    
    public Node retrieveNode() {
        return playerNode;
    }
}
