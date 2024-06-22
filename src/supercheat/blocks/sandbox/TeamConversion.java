package supercheat.blocks.sandbox;

import arc.Core;
import arc.scene.ui.*;
import arc.scene.ui.layout.Table;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.game.Team;
import mindustry.gen.Bullet;
import mindustry.ui.Styles;
import mindustry.world.Tile;
import mindustry.world.blocks.storage.CoreBlock;

/**
 * @author LYBF
 */
public class TeamConversion extends CoreBlock {

    public TeamConversion(String name) {
        super(name);
        solid = true;
        update = true;
        configurable = true;
    }

    @Override
    public void setStats() {
        super.setStats();
    }

    public boolean canPlaceOn(Tile tile, Team team, int rotation) {
        return true;//can place any way
    }

    @Override
    public boolean canBreak(Tile tile) {
        return true;
    }

    public class TeamConversionBuild extends CoreBuild {

        public boolean canDamage = false;

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

        @Override
        public void buildConfiguration(Table table) {
            super.buildConfiguration(table);
            ButtonGroup<ImageButton> buttonGroup = new ButtonGroup<>();
            Table cont = new Table().top();
            cont.defaults().size(40);
            int rows = 5;
            for (Team team1 : Team.baseTeams) {
                TextButton button = cont.button(team1.coloredName(), () -> {
                    configure(team1);
                }).tooltip(Core.bundle.get("switchTo", "切换到") + team1.coloredName()).group(buttonGroup).pad(4).get();
                button.setWidth(80);
                button.setColor(team1.color);
            }

            ScrollPane scrollPane = new ScrollPane(cont, Styles.smallPane);
            table.top().add(scrollPane).maxHeight(40 * rows);
        }

        @Override
        public void configure(Object value) {
            super.configure(value);
            if (value instanceof Team team) {
                Vars.player.team(team);
                this.team(team);
            }
        }

        @Override
        public byte version() {
            return 2;
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
        }

        @Override
        public void write(Writes write) {
            super.write(write);
        }
    }
}
