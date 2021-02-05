package com.threecubed.auber.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.threecubed.auber.Difficulty;
import com.threecubed.auber.GdxTestRunner;
import com.threecubed.auber.World;
import com.threecubed.auber.entities.Infiltrator;
import com.threecubed.auber.entities.Player;
import com.threecubed.auber.entities.Projectile;
import com.threecubed.auber.entities.Projectile.CollisionActions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

@RunWith(GdxTestRunner.class)
public class DamageTest {

  @Mock
  World worldMock = mock(World.class);
  @Mock
  Player playerMock = mock(Player.class);
  @Mock
  Infiltrator infiltratorMock = mock(Infiltrator.class);
  @Mock
  Projectile projectileMock = mock(Projectile.class);
  
  @Test
  public void maxHealth() throws Exception{
    assertNotNull(worldMock);
    worldMock.player = playerMock;
    worldMock.player.health = 1f;
    projectileMock.position = new Vector2(0,0);
    playerMock.position = new Vector2(0,0);
    Difficulty.damageMultiplier = 1;
    projectileMock.update(worldMock);
    assertTrue("message", worldMock.player.health == 0.8f);

  }
}
