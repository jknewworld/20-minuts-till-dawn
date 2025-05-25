package com.P3.Model.Enum;

public enum WeaponType {
    DUAL_SMG(24, 2f, 1, 8),
    SHOTGUN(2, 1f, 4, 10),
    REVOLVER(6, 1f, 1, 20);

    public int maxAmmo;
    public final float reloadTime;
    public int projectileCount;
    public final int damage;

    WeaponType(int maxAmmo, float reloadTime, int projectileCount, int damage) {
        this.maxAmmo = maxAmmo;
        this.reloadTime = reloadTime;
        this.projectileCount = projectileCount;
        this.damage = damage;
    }
}
