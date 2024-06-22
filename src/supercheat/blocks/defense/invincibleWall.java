package supercheat.blocks.defense;

import mindustry.game.Team;
import mindustry.world.blocks.defense.Wall;

/**
 * @author LYBF
 */
public class invincibleWall extends Wall {
    public invincibleWall(String name) {
        super(name);
    }

    public class invincibleWallBuild extends WallBuild {
        @Override
        public void damage(Team source, float damage) {
        }

        @Override
        public float handleDamage(float amount) {
            return 0;
        }

    }
}
