package supercheat.entities.bullets;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Angles;
import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Bullet;
import mindustry.graphics.Pal;
import supercheat.blocks.distribution.MassDriver;

import static mindustry.Vars.content;

public class MassBulletBoltType extends BulletType {

    public MassBulletBoltType() {
        super(1f, 75);
        collidesTiles = false;
        lifetime = 1f;
        despawnEffect = Fx.smeltsmoke;
        hitEffect = Fx.hitBulletBig;
    }

    @Override
    public void draw(Bullet b) {
        float w = 11f, h = 13f;
        Draw.color(Pal.bulletYellowBack);
        Draw.rect("shell-back", b.x, b.y, w, h, b.rotation() + 90);

        Draw.color(Pal.bulletYellow);
        Draw.rect("shell", b.x, b.y, w, h, b.rotation() + 90);
        Draw.reset();
    }

    @Override
    public void update(Bullet b) {
        if (!(b.data() instanceof MassDriver.DriverBulletData data)) {
            hit(b);
            return;
        }
        float hitDst = 7f;
        if (data.to.dead()) {
            return;
        }

        float baseDst = data.from.dst(data.to);
        float dst1 = b.dst(data.from);
        float dst2 = b.dst(data.to);

        boolean intersect = false;

        if (dst1 > baseDst) {
            float angleTo = b.angleTo(data.to);
            float baseAngle = data.to.angleTo(data.from);
            if (Angles.near(angleTo, baseAngle, 2f)) {
                intersect = true;
                b.set(data.to.x + Angles.trnsx(baseAngle, hitDst), data.to.y + Angles.trnsy(baseAngle, hitDst));
            }
        }

        if (Math.abs(dst1 + dst2 - baseDst) < 4f && dst2 <= hitDst) {
            intersect = true;
        }

        if (intersect) {
            drop(b, data);
        }
    }

    private void drop(Bullet bullet, MassDriver.DriverBulletData data) {
        int totalItems = data.to.items.total();
        for (int i = 0; i < data.items.length; i++) {
            int maxAdd = Math.min(data.items[i], data.to.block.itemCapacity * 2 - totalItems);
            for (int j = 0; j < maxAdd; j++) {
                data.to.handleItem(data.from, content.item(i));
            }
        }
        Fx.mineBig.at(bullet);
        bullet.remove();
    }

    @Override
    public void despawned(Bullet b) {
        super.despawned(b);

        if (!(b.data() instanceof MassDriver.DriverBulletData data)) return;
        for (int i = 0; i < data.items.length; i++) {
            int amountDropped = Mathf.random(0, data.items[i]);
            if (amountDropped > 0) {
                float angle = b.rotation() + Mathf.range(100f);
                Fx.dropItem.at(b.x, b.y, angle, Color.white, content.item(i));
            }
        }
    }

    @Override
    public void hit(Bullet b, float hitx, float hity) {
        super.hit(b, hitx, hity);
        despawned(b);
    }
}
