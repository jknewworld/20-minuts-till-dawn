package com.P3.Model;

import com.badlogic.gdx.math.Rectangle;

public class Shield {
    private Rectangle bounds;
    private boolean active;
    private float shrinkSpeed;

    public Shield(float screenWidth, float screenHeight, float shrinkSpeed) {
        this.bounds = new Rectangle(0, 0, screenWidth, screenHeight);
        this.active = true;
        this.shrinkSpeed = shrinkSpeed;
    }

    public void update(float delta) {
        if (!active) return;

        float shrinkAmount = shrinkSpeed * delta;

        bounds.x += shrinkAmount;
        bounds.y += shrinkAmount;
        bounds.width -= 2 * shrinkAmount;
        bounds.height -= 2 * shrinkAmount;

        if (bounds.width < 50 || bounds.height < 50) {
            active = false;
        }
    }

    public void deactivate() {
        active = false;
    }

    public boolean isActive() {
        return active;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
