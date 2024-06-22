package supercheat.content;

import arc.graphics.Color;
import mindustry.content.Fx;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.LaserBulletType;
import mindustry.graphics.Pal;
import supercheat.entities.bullets.*;

/**
 * @author LYBF
 */
public class Bullets {

    public static BulletType destroyBullet, extinction, adjustableBullet;

    public static void load() {
        destroyBullet = new DestroyBulletType();
        extinction = new LaserBulletType() {{
            length = 460f;
            damage = 1145141.91f;
            width = 25f;

            lifetime = 65f;

            lightningSpacing = 35f;
            lightningLength = 10;
            lightningDelay = 1.1f;
            lightningLengthRand = 20;
            lightningDamage = 114;
            lightningAngleRand = 40f;
            largeHit = true;
            lightColor = lightningColor = Pal.heal;

            chargeEffect = Fx.greenLaserChargeSmall;

            healPercent = 25f;
            collidesTeam = true;

            sideAngle = 15f;
            sideWidth = 0f;
            sideLength = 0f;
            colors = new Color[]{Pal.heal.cpy().a(0.4f), Pal.heal, Color.white};
        }};

        adjustableBullet = new AdjustableBulletType() {
            {
                bulletColor = Color.orange;
            }
        };
    }


}

