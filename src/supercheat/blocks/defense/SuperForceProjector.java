package supercheat.blocks.defense;

import arc.func.Cons;
import arc.math.geom.Intersector;
import arc.scene.event.Touchable;
import arc.scene.ui.Image;
import arc.scene.ui.Label;
import arc.scene.ui.Slider;
import arc.scene.ui.layout.Table;
import arc.util.Reflect;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.gen.Bullet;
import mindustry.gen.Groups;
import mindustry.gen.Icon;
import mindustry.gen.Unit;
import mindustry.ui.Styles;
import mindustry.world.blocks.defense.ForceProjector;

import static arc.Core.bundle;

/**
 * <p>infinite shield,deflect bullets,kill unit</p>
 *
 * @author LYBF
 */
public class SuperForceProjector extends ForceProjector {
    public SuperForceProjector(String name) {
        super(name);
        configurable = true;
    }


    protected static SuperForceProjectorBuild paramEntity;
    protected static final Cons<Bullet> destroyAttacker = bullet -> {
        if (bullet.team != paramEntity.team && bullet.type.absorbable &&
                Intersector.isInRegularPolygon(((SuperForceProjector) (paramEntity.block)).sides, paramEntity.x, paramEntity.y, paramEntity.realRadius(), ((SuperForceProjector) (paramEntity.block)).shieldRotation, bullet.x, bullet.y)) {
            bullet.absorb();
            paramEffect.at(bullet);
            paramEntity.hit = 1f;
            //paramEntity.buildup += bullet.damage;
        }
    };

    public class SuperForceProjectorBuild extends ForceBuild {
        public float dynamicRadius = radius;
        public boolean killUnit = false;
        public boolean killSpecialUnit = false;

        public int killSpecialUnitTimer = timers++;

        @Override
        public void updateTile() {
            super.updateTile();
            if (killUnit) {
                unitKiller();
            }
            if (killSpecialUnit) {
                if (timer(killSpecialUnitTimer, 10)) {
                    killSpecialUnit();
                }
            }
        }

        private void killSpecialUnit() {
            try {
                Class<?> empathyDamage = Class.forName("flame.unit.empathy.EmpathyDamage");
                Reflect.invoke(empathyDamage, "reset");
            } catch (Exception ignored) {

            }
        }

        @Override
        public void deflectBullets() {
            float realRadius = realRadius();
            if (realRadius > 0 && !broken) {
                paramEntity = this;
                paramEffect = absorbEffect;
                Groups.bullet.intersect(x - realRadius, y - realRadius, realRadius * 2f, realRadius * 2f, destroyAttacker);
            }
        }

        private final Cons<Unit> unitKiller = unit -> {
            if (unit.team != paramEntity.team &&
                    Intersector.isInRegularPolygon(((SuperForceProjector) (paramEntity.block)).sides, paramEntity.x, paramEntity.y, paramEntity.realRadius(), ((SuperForceProjector) (paramEntity.block)).shieldRotation, unit.x, unit.y)) {
                unit.remove();
            }
        };

        public void unitKiller() {
            float realRadius = realRadius();
            if (realRadius > 0 && !broken) {
                Groups.unit.intersect(x - realRadius, y - realRadius, realRadius * 2f, realRadius * 2f, unitKiller);
            }
        }

        public float realRadius() {
            return (dynamicRadius + phaseHeat * phaseRadiusBoost) * radscl;
        }


        @Override
        public void buildConfiguration(Table table) {
            if (Vars.player != null && Vars.player.team() != team) return;
            table.setWidth(300f);
//            table.background(Styles.black9);
            showControl(table);
            super.buildConfiguration(table);
        }

        public void showControl(Table table) {
            table.button(bundle.get("super-cheat.unit-killer"), Icon.eyeOffSmall, Styles.togglet, () -> killUnit = !killUnit)
                    .width(table.getWidth() / 2)
                    .checked(killUnit)
                    .update(b -> {
                        b.setChecked(killUnit);
                        ((Image) b.getChildren().get(1)).setDrawable(killUnit ? Icon.eyeSmall : Icon.eyeOffSmall);
                    }).left().row();
            table.button(bundle.get("super-cheat.kill-special-unit"), Icon.eyeOffSmall, Styles.togglet, () -> killSpecialUnit = !killSpecialUnit)
                    .width(table.getWidth() / 2)
                    .checked(killSpecialUnit)
                    .update(b -> {
                        b.setChecked(killSpecialUnit);
                        ((Image) b.getChildren().get(1)).setDrawable(killSpecialUnit ? Icon.eyeSmall : Icon.eyeOffSmall);
                    }).left().row();
            adjustRadius(table);

        }

        public void adjustRadius(Table table1) {
            Table table = new Table();
            int max_radius = Math.min(Vars.state.map.width, Vars.state.map.height) / 2;
            max_radius += (int) (max_radius * 1.2f);
            Slider slider = new Slider(1, max_radius, 1, false);

            slider.setWidth(table1.getWidth());
            slider.setValue(dynamicRadius / 8);

            Label value = new Label("", Styles.outlineLabel);
            Table content = new Table();
            content.setWidth(table1.getWidth());
            content.add(bundle.get("super-cheat.adjustRadius"), Styles.outlineLabel).left().growX().wrap();
            content.add(value).padLeft(10f).right();
//            content.margin(3f, 33f, 3f, 33f);
            content.touchable = Touchable.disabled;
            slider.changed(() -> {
                int value1 = (int) slider.getValue();
                dynamicRadius = value1 * 8;
                value.setText("" + value1);
            });
            table.stack(slider, content).width(table1.getWidth()).left().padTop(4f).get();
            table.row();
            table1.add(table);

        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            dynamicRadius = read.f();
            killUnit = read.bool();
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            write.f(dynamicRadius);
            write.bool(killUnit);
        }
    }

}
