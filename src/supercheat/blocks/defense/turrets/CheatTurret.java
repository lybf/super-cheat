package supercheat.blocks.defense.turrets;

import arc.scene.ui.layout.Table;
import arc.struct.ObjectMap;
import mindustry.entities.bullet.BulletType;
import mindustry.world.blocks.defense.turrets.Turret;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatValues;

/**
 * @author LYBF
 */
public class CheatTurret extends Turret {

    public BulletType shootAmmo;

    public CheatTurret(String name) {
        super(name);
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.add(Stat.ammo, StatValues.ammo(ObjectMap.of(this, shootAmmo)));
    }

    public class CheatTurretBuild extends TurretBuild {

        @Override
        public void display(Table table) {
            super.display(table);
        }

        @Override
        protected void shoot(BulletType type) {
            super.shoot(type);
        }

        @Override
        public BulletType peekAmmo() {
            return shootAmmo;
        }

        @Override
        public boolean hasAmmo() {
            return true;
        }

        @Override
        public BulletType useAmmo() {
            return shootAmmo;
        }
    }
}
