package supercheat.blocks.storage;

import arc.scene.ui.layout.Table;
import mindustry.Vars;
import mindustry.game.Team;
import mindustry.gen.Bullet;
import mindustry.world.Tile;
import mindustry.world.blocks.storage.CoreBlock;

/**
 * @author LYBF
 */
public class InvincibleCore extends CoreBlock {


    public InvincibleCore(String name) {
        super(name);
    }


    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation) {
        return true;//can place any way
    }

    @Override
    public boolean canBreak(Tile tile) {
        return true;
    }

    public class InvincibleCoreBuild extends CoreBuild {

        public boolean canDamage = false;

        @Override
        public void buildConfiguration(Table table) {
            if (Vars.player == null || Vars.player.team() != team) return;
            super.buildConfiguration(table);
        }


        @Override
        public void damage(float amount) {
            if (canDamage) super.damage(amount);
        }

        @Override
        public void damage(Team source, float damage) {
            if (canDamage) super.damage(source, damage);
        }

        @Override
        public void damage(float amount, boolean withEffect) {
            if (canDamage) super.damage(amount, withEffect);
        }

        @Override
        public void damage(Bullet bullet, Team source, float damage) {
            if (canDamage) super.damage(bullet, source, damage);
        }

        @Override
        public boolean damaged() {
            if (canDamage) return super.damaged();
            return false;
        }

        @Override
        public void damageContinuous(float amount) {
            if (canDamage) super.damageContinuous(amount);
        }

        @Override
        public void damageContinuousPierce(float amount) {
            if (canDamage) super.damageContinuousPierce(amount);
        }

        @Override
        public void damagePierce(float amount) {
            if (canDamage) super.damagePierce(amount);
        }

        @Override
        public void damagePierce(float amount, boolean withEffect) {
            if (canDamage) damagePierce(amount, withEffect);
        }

        @Override
        public float handleDamage(float amount) {
            if (canDamage) super.handleDamage(amount);
            return 0f;
        }

    }
}
