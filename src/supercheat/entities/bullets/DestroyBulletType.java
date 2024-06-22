package supercheat.entities.bullets;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.Units;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.*;
import mindustry.graphics.Drawf;

/**
 * @author LYBF
 */
public class DestroyBulletType extends BasicBulletType {
    public Color bulletColor = Color.pink;

    Effect hitEffect = new Effect(8, e -> {
        Draw.color(Color.purple, bulletColor, e.fin());
        Lines.stroke(0.5f + e.fout());
        Lines.circle(e.x, e.y, e.fin() * 30);
    });

    public DestroyBulletType() {
        hitSize = 6;
        damage = 114514f;
        shootEffect = Fx.none;
        lifetime = 55;
        speed = 6;
        fragBullets = 3;
        fragOnHit = false;
        fragBullet = new DestroyFragBullet() {
            {
                hitSize = 6;
                damage = 1919810000f;
                homingPower = 0.3f;
                homingRange = 240f;
                shootEffect = Fx.none;
                lifetime = 110;
                speed = 3.5f;
            }
        };
    }

    @Override
    public void hitTile(Bullet b, Building build, float x, float y, float initialHealth, boolean direct) {
        super.hitTile(b, build, x, y, initialHealth, direct);
        if (b.team() != build.team() && build.isValid()) {
            build.kill();
            hitEffect.at(x, y);
        }
    }

    @Override
    public void hitEntity(Bullet b, Hitboxc entity, float health) {
        super.hitEntity(b, entity, health);
        if (entity instanceof Unit unit) {
            unit.health = 0;
            Call.unitDestroy(entity.id());
            hitEffect.at(b.x, b.y);
        }
    }


    @Override
    public void draw(Bullet b) {
        super.draw(b);
        Draw.color(bulletColor);
        Drawf.tri(b.x, b.y, 4, 6, b.rotation());
        Drawf.tri(b.x, b.y, 4, 8, b.rotation() - 180);
        Draw.reset();
    }

    @Override
    public void update(Bullet b) {
        super.update(b);
    }


    public class DestroyFragBullet extends BasicBulletType {

        @Override
        public void hitTile(Bullet b, Building build, float x, float y, float initialHealth, boolean direct) {
            super.hitTile(b, build, x, y, initialHealth, direct);
            if (b.team() != build.team() && build.isValid()) {
                build.kill();
                hitEffect.at(x, y);
            }
        }

        @Override
        public void hitEntity(Bullet b, Hitboxc entity, float health) {
            super.hitEntity(b, entity, health);
            if (entity != null) {
                Call.unitDestroy(entity.id());
                hitEffect.at(b.x, b.y);
            }
        }


        @Override
        public void draw(Bullet b) {
            super.draw(b);
            Draw.color(bulletColor);
            Drawf.tri(b.x, b.y, 4, 6, b.rotation());
            Drawf.tri(b.x, b.y, 4, 8, b.rotation() - 180);
            Draw.reset();
        }

        @Override
        public void update(Bullet b) {
            super.update(b);
            if (this.homingPower > 0.0001 && b.time > 12) {
                var target = Units.closestTarget(b.team, b.x, b.y, this.homingRange,
                        e -> (e.isGrounded() && this.collidesGround) || e.isFlying() && this.collidesAir);
                if (target != null) {
                    b.vel.setAngle(Mathf.slerpDelta(b.rotation(), b.angleTo(target), this.homingPower));
                }
            }
        }

    }
}
