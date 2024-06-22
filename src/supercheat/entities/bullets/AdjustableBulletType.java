package supercheat.entities.bullets;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Building;
import mindustry.gen.Bullet;
import mindustry.gen.Hitboxc;
import mindustry.graphics.Drawf;

/**
 * @author LYBF
 */
public class AdjustableBulletType extends BulletType {
    public Color bulletColor = Color.pink;

    Effect hitEffect = new Effect(8, e -> {
        Draw.color(Color.purple, bulletColor, e.fin());
        Lines.stroke(0.5f + e.fout());
        Lines.circle(e.x, e.y, e.fin() * 30);
    });

    public AdjustableBulletType() {
        hitSize = 6;
        damage = 11.4f;
        shootEffect = Fx.none;
        lifetime = 55;
        speed = 6;
    }

    @Override
    public void hitTile(Bullet b, Building build, float x, float y, float initialHealth, boolean direct) {
        super.hitTile(b, build, x, y, initialHealth, direct);
        hitEffect.at(x, y);
    }

    @Override
    public void hitEntity(Bullet b, Hitboxc entity, float health) {
        super.hitEntity(b, entity, health);
        hitEffect.at(b.x, b.y);
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

}
