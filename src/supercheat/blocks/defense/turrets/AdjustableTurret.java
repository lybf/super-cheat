package supercheat.blocks.defense.turrets;

import arc.scene.ui.layout.Table;
import mindustry.Vars;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Icon;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import supercheat.content.Bullets;
import supercheat.ui.UI;

/**
 * <p>Adjustable radius,damage,and bullet speed</p>
 *
 * @author LYBF
 */
public class AdjustableTurret extends PowerTurret {
    public AdjustableTurret(String name) {
        super(name);
        configurable = true;
        shootType = Bullets.adjustableBullet;
    }

    @Override
    public void setStats() {
        super.setStats();
    }

    public class AdjustableTurretBuild extends PowerTurretBuild {
        public float adjustRange = range;
        public BulletType adjustShootType = shootType;

        @Override
        public void buildConfiguration(Table table) {
            if (Vars.player != null && Vars.player.team() != team) return;
            super.buildConfiguration(table);
            table.button("", Icon.settingsSmall, () -> UI.adjustTurretDialog.show(this));
        }

        public void setReload(float reloadV) {
            reload = reloadV;
        }

        public float getReload() {
            return reload;
        }

        @Override
        public float range() {
            return adjustRange + peekAmmo().rangeChange;
        }

        @Override
        public BulletType useAmmo() {
            return adjustShootType;
        }

        @Override
        public boolean hasAmmo() {
            return true;
        }

        @Override
        public BulletType peekAmmo() {
            return adjustShootType;
        }
    }
}

