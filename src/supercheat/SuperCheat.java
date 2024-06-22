package supercheat;

import mindustry.mod.Mod;
import supercheat.content.Blocks;
import supercheat.content.Bullets;

/**
 * @author LYBF
 */
public class SuperCheat extends Mod {
    @Override
    public void loadContent() {
        super.loadContent();
        Bullets.load();
        Blocks.load();
    }

    @Override
    public void init() {
        super.init();
    }

}
