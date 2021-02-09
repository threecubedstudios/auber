package com.threecubed.auber.tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.threecubed.auber.GdxTestRunner;
import com.threecubed.auber.World;
import com.threecubed.auber.entities.GameEntity;
import com.threecubed.auber.entities.Infiltrator;
import com.threecubed.auber.entities.Player;
import com.threecubed.auber.entities.Npc.States;
import com.threecubed.auber.pathfinding.NavigationMesh;
import com.threecubed.auber.screens.GameScreen;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;

@RunWith(GdxTestRunner.class)
public class ArrestTest {
  
  @Mock
  World worldMock = mock(World.class);
  @Mock
  Infiltrator infiltratorMock = mock(Infiltrator.class);
  @Mock
  Player playerMock = mock(Player.class); 
  @Mock
  NavigationMesh nav = mock(NavigationMesh.class);

  @Test
  public void validRayCollisionTest() throws Exception {
    worldMock.atlas = new TextureAtlas("auber.atlas");
    Whitebox.setInternalState(worldMock, "randomNumberGenerator", new Random());
    worldMock.navigationMesh = nav;
    when(nav.getFurthestPointFromEntity(worldMock.player)).thenReturn(new Vector2(0,0));
    Player p = new Player(0,0,worldMock);
    Infiltrator i = new Infiltrator(0,0,worldMock);
    ArrayList<GameEntity> entities = new ArrayList<GameEntity>();
    entities.add(i);
    when(worldMock.getEntities()).thenReturn(entities);
    worldMock.camera = new OrthographicCamera();
    worldMock.auberTeleporterCharge = 1;
    worldMock.medbay = new RectangleMapObject();
    worldMock.player = p;
    GameScreen.enemyTrack = new HashMap<Infiltrator,Integer>();
    p.update(worldMock);
    assertTrue("Infiltrator should be exposed", i.exposed);
    assertSame("Infiltrator should be fleeing", i.state, States.FLEEING);
  }

  @Test
  public void validArrestTest() throws Exception {
    worldMock.atlas = new TextureAtlas("auber.atlas");
    Whitebox.setInternalState(worldMock, "randomNumberGenerator", new Random());
    worldMock.navigationMesh = nav;
    when(nav.getFurthestPointFromEntity(worldMock.player)).thenReturn(new Vector2(0,0));
    
    Player p = new Player(0,0,worldMock);
    Infiltrator i = new Infiltrator(0,0,worldMock);

    ArrayList<GameEntity> entities = new ArrayList<GameEntity>();
    entities.add(i);
    when(worldMock.getEntities()).thenReturn(entities);
    
    worldMock.camera = new OrthographicCamera();
    worldMock.auberTeleporterCharge = 1;
    worldMock.medbay = new RectangleMapObject();
    worldMock.player = p;
    i.exposed = true;

    GameScreen.enemyTrack = new HashMap<Infiltrator,Integer>();

    p.update(worldMock);
    assertTrue("Infiltrator should be arrested", i.arrested);    
  }

  @Test
  public void invalidRayCollisionTest() throws Exception {
    worldMock.atlas = new TextureAtlas("auber.atlas");
    Whitebox.setInternalState(worldMock, "randomNumberGenerator", new Random());
    worldMock.navigationMesh = nav;
    when(nav.getFurthestPointFromEntity(worldMock.player)).thenReturn(new Vector2(0,0));
    
    Player p = new Player(0,0,worldMock);
    Infiltrator i = new Infiltrator(0,0,worldMock);

    ArrayList<GameEntity> entities = new ArrayList<GameEntity>();
    entities.add(i);
    when(worldMock.getEntities()).thenReturn(entities);
    
    worldMock.camera = new OrthographicCamera();
    worldMock.auberTeleporterCharge = 1;
    worldMock.medbay = new RectangleMapObject();
    worldMock.player = p;
    i.position = new Vector2(96f, 640f);
    i.sprite.setPosition(i.position.x, i.position.y);

    GameScreen.enemyTrack = new HashMap<Infiltrator,Integer>();

    p.update(worldMock);
    assertFalse("Infiltrator should not be exposed", i.exposed);    
    assertFalse("Infiltrator should not be fleeing", i.state == States.FLEEING);
  }
}
