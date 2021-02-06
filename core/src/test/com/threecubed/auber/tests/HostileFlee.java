package com.threecubed.auber.tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.threecubed.auber.GdxTestRunner;
import com.threecubed.auber.World;
import com.threecubed.auber.entities.Infiltrator;
import com.threecubed.auber.entities.Npc;
import com.threecubed.auber.entities.Player;
import com.threecubed.auber.pathfinding.NavigationMesh;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;


@RunWith(GdxTestRunner.class)
public class HostileFlee {

  @Mock
  World worldMock = mock(World.class);
  @Mock
  Player playerMock = mock(Player.class);

  @Test
  public void validFlee() throws Exception {
    worldMock.atlas = new TextureAtlas("auber.atlas");
    worldMock.navigationMesh = new NavigationMesh((TiledMapTileLayer) World.map.getLayers().get("navigation_layer"));
    worldMock.randomNumberGenerator = new Random();
    Infiltrator i = new Infiltrator(0,0,worldMock);
    playerMock.position = new Vector2(0,0);
    worldMock.player = playerMock;
    
    i.handleTeleporterShot(worldMock);
    assertTrue("Infiltrator should be exposed", i.exposed);
    assertEquals("Infiltrator should be fleeing", Npc.States.FLEEING, i.state);
  }

}
