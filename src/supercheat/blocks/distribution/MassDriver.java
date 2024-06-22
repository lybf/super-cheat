package supercheat.blocks.distribution;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Angles;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import arc.util.pooling.Pool;
import arc.util.pooling.Pools;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.world.Block;
import supercheat.entities.bullets.MassBulletBoltType;

import java.util.Objects;

import static mindustry.Vars.*;
import static mindustry.Vars.content;

public class MassDriver extends Block {

    public float delay = 60f;
    public float range = 8 * 30f;

    public float translation = 7f;

    public float bulletSpeed = 5.5f;
    public float bulletLifetime = 200f;
    public Effect shootEffect = Fx.shootBig2;
    public Effect smokeEffect = Fx.shootBigSmoke2;
    //    public Sound shootSound = Sounds.shootBig;
    public float shake = 3f;
    public MassBulletBoltType bullet = new MassBulletBoltType();


    public int reloadTimer = timers++;

    public MassDriver(String name) {
        super(name);
        solid = true;
        update = true;
        hasItems = true;
        configurable = true;
        clearOnDoubleTap = true;
        configClear(building -> ((MassDriverBuild) building).links.clear());
    }

    public static class DriverBulletData implements Pool.Poolable {
        public Building from, to;
        public int[] items = new int[content.items().size];

        @Override
        public void reset() {
            from = null;
            to = null;
        }
    }


    public class MassDriverBuild extends Building {

        public Seq<Integer> links = new Seq<>();
        public int index = 0;

        @Override
        public void draw() {
            super.draw();
        }

        @Override
        public void updateTile() {
            super.updateTile();
            checkBuilding();
            if (links.isEmpty()) return;
            if (timer(reloadTimer, delay)) {
                if (items.total() <= 0 || links.isEmpty()) return;
                Building target = world.build(links.get(index % links.size));
                if (target == null) return;
                fire(target);
                index++;
            }
        }


        protected void fire(Building target) {
            int totalUsed = 0;
            DriverBulletData data = Pools.obtain(DriverBulletData.class, DriverBulletData::new);
            data.from = this;
            data.to = target;
            for (int i = 0; i < content.items().size; i++) {
                int maxTransfer = Math.min(items.get(content.item(i)), tile.block().itemCapacity - totalUsed);
                data.items[i] = maxTransfer;
                totalUsed += maxTransfer;
                items.remove(content.item(i), maxTransfer);
            }

            float angle = tile.angleTo(target);

            bullet.create(this, team,
                    x + Angles.trnsx(angle, translation), y + Angles.trnsy(angle, translation),
                    angle, -1f, bulletSpeed, bulletLifetime, data);

            shootEffect.at(x + Angles.trnsx(angle, translation), y + Angles.trnsy(angle, translation), angle);
            smokeEffect.at(x + Angles.trnsx(angle, translation), y + Angles.trnsy(angle, translation), angle);
            Effect.shake(shake, shake, this);
//            shootSound.at(tile, Mathf.random(0.9f, 1.1f));

        }

        @Override
        public boolean acceptItem(Building source, Item item) {
            return items.total() < itemCapacity && linkValid();
        }


        private void checkBuilding() {
            links.removeAll(link -> Vars.world.build(link) == null);
        }

        @Override
        public void drawConfigure() {
            Drawf.dashCircle(x, y, range, Pal.accent);
            if (linkValid()) drawConf();
        }

        public void drawConf() {
            float sin = Mathf.absin(Time.time, 6f, 1f);

            Draw.color(Pal.accent);
            Lines.stroke(1f);
            Drawf.circles(x, y, (tile.block().size / 2f + 1) * tilesize + sin - 2f, Pal.accent);


            if (linkValid()) {
                links.each(pos -> {
                    Building target = world.build(pos);
                    Drawf.circles(target.x, target.y, (target.block().size / 2f + 1) * tilesize + sin - 2f, Pal.place);
                    Drawf.arrow(x, y, target.x, target.y, size * tilesize + sin, 4f + sin);
                });
            }

        }


        public boolean linkValid() {
            checkBuilding();
            return !links.isEmpty();
        }


        @Override
        public boolean onConfigureBuildTapped(Building other) {
            if (this == other) {
                deselect();
                return false;
            }

            if (other != null && other.dst(tile) < range && other.team == team() && hasItems) {
                configure(other.pos());
            }
            return false;
        }

        @Override
        public void configure(Object value) {
            if (value instanceof Integer pos) {
                boolean bool = links.remove(link -> Objects.equals(link, pos));
                if (!bool) {
                    links.add(pos);
                }
            }
            super.configure(value);
        }

        @Override
        public byte version() {
            return super.version();
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            int size = read.i();
            for (int i = 0; i < size; i++) {
                links.add(read.i());
            }
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            write.i(links.size);
            links.each(write::i);
        }
    }

}
