package supercheat.ui.dialogs;

import arc.Core;
import arc.func.Floatc;
import arc.scene.event.Touchable;
import arc.scene.ui.Label;
import arc.scene.ui.Slider;
import arc.scene.ui.layout.Table;
import mindustry.Vars;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Icon;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;
import supercheat.blocks.defense.turrets.AdjustableTurret;

import static arc.Core.bundle;

/**
 * @author LYBF
 */
public class AdjustTurretDialog extends BaseDialog {
    public AdjustTurretDialog(String title) {
        super(title);
    }

    public void show(AdjustableTurret.AdjustableTurretBuild build) {
        cont.clear();
        buttons.clear();
        BulletType bulletType = build.adjustShootType;
        float[] range = {bulletType.range};
        float[] damage = {bulletType.damage};
        float[] speed = {bulletType.speed};
        float[] reload = {build.getReload()};
        cont.add(bundle.get("bullet-damage")).left().row();
        cont.area("" + bulletType.damage, str -> {
            try {
                float dam = Float.parseFloat(str);
                damage[0] = dam;
            } catch (Exception ignored) {

            }
        }).width(260f).left().row();

        cont.add(bundle.get("bullet-speed")).left().row();
        cont.area("" + bulletType.speed, str -> {
            try {
                speed[0] = Float.parseFloat(str);
            } catch (Exception ignored) {

            }
        }).width(260f).left().row();

        cont.add(bundle.get("bullet-reload")).left().row();
        cont.area("" + reload[0], value -> {
            try {
                reload[0] = Float.parseFloat(value);
            } catch (Exception ignored) {
            }
        }).width(260f).left().row();

        cont.add(bundle.get("bullet-range")).left().row();

        slider(cont, 0, Math.max(Vars.state.map.width, Vars.state.map.height) / 2, (int) bulletType.range / 8,
                value -> range[0] = value * 8);

        buttons.button(Icon.saveSmall, () -> {
            bulletType.damage = damage[0];
            bulletType.range = range[0];

            bulletType.rangeChange = range[0];
            bulletType.speed = speed[0];
            float realRange = bulletType.rangeChange + build.adjustRange;
            bulletType.lifetime = (realRange + 10) / bulletType.speed;

            build.setReload(Math.abs(reload[0]));
            hide();
        }).width(120f);
        buttons.button(Icon.cancelSmall, this::hide).width(120f);
        show();
    }

    public void slider(Table table1, int min, int max, int def, Floatc listener) {
        Table table = new Table();
        Slider slider = new Slider(min, max, 1, false);
        slider.setValue(def);
        Label value = new Label("", Styles.outlineLabel);
        Table content = new Table();
        content.add(bundle.get("super-cheat.adjustRadius"), Styles.outlineLabel).left().growX().wrap();
        content.add(value).padLeft(10f).right();
        content.margin(3f, 33f, 3f, 33f);
        content.touchable = Touchable.disabled;
        slider.changed(() -> {
            int value1 = (int) slider.getValue();
            if (listener != null) listener.get(value1);
            value.setText("" + value1);
        });
        slider.change();
        table.stack(slider, content).width(Math.min(Core.graphics.getWidth() / 1.2f, 460f)).left().padTop(4f).get();
        table.row();
        table1.add(table);
    }


}
