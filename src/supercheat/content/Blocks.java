package supercheat.content;

import arc.Core;
import arc.graphics.Color;
import mindustry.entities.part.HaloPart;
import mindustry.entities.part.RegionPart;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.distribution.Conveyor;
import mindustry.world.blocks.distribution.StackConveyor;
import mindustry.world.blocks.liquid.LiquidRouter;
import mindustry.world.blocks.payloads.PayloadSource;
import mindustry.world.blocks.payloads.PayloadVoid;
import mindustry.world.blocks.sandbox.ItemSource;
import mindustry.world.blocks.sandbox.LiquidSource;
import mindustry.world.blocks.sandbox.PowerSource;
import mindustry.world.blocks.sandbox.PowerVoid;
import mindustry.world.blocks.storage.StorageBlock;
import mindustry.world.draw.DrawTurret;
import mindustry.world.meta.BuildVisibility;
import supercheat.blocks.defense.SuperForceProjector;
import supercheat.blocks.defense.invincibleWall;
import supercheat.blocks.defense.turrets.AdjustableTurret;
import supercheat.blocks.defense.turrets.CheatTurret;
import supercheat.blocks.distribution.MassDriver;
import supercheat.blocks.sandbox.TeamConversion;
import supercheat.blocks.storage.InvincibleCore;
import supercheat.entities.bullets.DestroyBulletType;


/**
 * @author LYBF
 */
public class Blocks {
    //effect
    public static Block superForceProjector;

    //storage and core
    public static Block invincibleCore, bigVault, bigVault2;

    public static Block bigLiquidTank, bigLiquidTank2;

    //turret
    public static Block entropy, dinEntropy, tiansha, exceedance;

    //special

    public static Block teamConversion;


    //wall
    public static Block wall1, wall2, wall3, wall4, wall5, wall6;


    //dis
    public static Block massdriver, quicklyConveyor, quicklyStackConveyor;

    //sources

    public static Block itemsource, liquidsource, unitsource, payloadVoidSource, powerVoidSource, powerSource;

    public static void load() {
        //storage

        invincibleCore = new InvincibleCore("invinciblecore") {
            {
                category = Category.effect;
                buildVisibility = BuildVisibility.shown;
                health = 1;
                itemCapacity = 114514;
                size = 3;
            }
        };

        bigVault = new StorageBlock("big-vault") {
            {
                category = Category.effect;
                buildVisibility = BuildVisibility.shown;
                size = 1;
                health = 114514;
                itemCapacity = 1000000;
            }
        };
        bigVault2 = new StorageBlock("big-vault2") {
            {
                category = Category.effect;
                buildVisibility = BuildVisibility.shown;
                size = 8;
                health = 114514;
                itemCapacity = 1000000;
            }
        };

        bigLiquidTank = new LiquidRouter("big-liquid-tank") {
            {
                category = Category.liquid;
                buildVisibility = BuildVisibility.shown;
                size = 1;
                health = 114514;
                liquidCapacity = 1000000;
                bottomRegion = Core.atlas.find(name + "-bottom");
            }
        };
        bigLiquidTank2 = new LiquidRouter("big-liquid-tank2") {
            {
                category = Category.liquid;
                buildVisibility = BuildVisibility.shown;
                size = 8;
                health = 114514;
                liquidCapacity = 1000000;
                bottomRegion = Core.atlas.find(name + "-bottom");
            }
        };
        //effect
        superForceProjector = new SuperForceProjector("superforceprojector") {
            {
                category = Category.effect;
                buildVisibility = BuildVisibility.shown;
                health = 114514;
                size = 3;
            }
        };

        //turrets
        entropy = new AdjustableTurret("entropy") {

            {
                category = Category.turret;
                buildVisibility = BuildVisibility.shown;
                size = 2;
                health = 191981;
                consumePower(1f);
            }
        };

        dinEntropy = new CheatTurret("din-entropy") {
            {
                category = Category.turret;
                buildVisibility = BuildVisibility.shown;
                size = 2;
                health = 114514;
                range = 8 * 30;
                reload = 20;
                recoil = 0.5f;
                shootY = 3f;

                shootAmmo = new DestroyBulletType();
                ((DestroyBulletType) shootAmmo).bulletColor = Color.green;
                drawer = new DrawTurret() {{
                    for (int i = 0; i < 2; i++) {
                        int f = i;
                        parts.add(new RegionPart("-barrel-" + (i == 0 ? "l" : "r")) {{
                            progress = PartProgress.recoil;
                            recoilIndex = f;
                            under = true;
                            moveY = -1.5f;
                        }});
                    }
                }};
            }
        };
        exceedance = new CheatTurret("exceedance") {
            {
                category = Category.turret;
                buildVisibility = BuildVisibility.shown;
                size = 3;
                health = 114514;
                range = 460f;
                reload = 60;
                recoil = 0.5f;
                shootY = 25f;
                shoot.firstShotDelay = reload + 20f;
                shootSound = Sounds.laserblast;
                chargeSound = Sounds.lasercharge2;
                shootAmmo = Bullets.extinction;
                drawer = new DrawTurret() {
                    {
                        for (int i = 1; i <= 3; i++) {
                            int finalI = i;
                            parts.add(new RegionPart("-" + finalI) {
                                {
                                    progress = PartProgress.warmup.delay(1 / reload);
                                    moveY = 8.2f + (finalI > 1 ? (finalI == 2 ? 8f : 21f) : 0);
                                    layerOffset = -0.0001f * finalI;
                                    turretHeatLayer = 50 - 0.0001f;
                                    moves.add(new PartMove(PartProgress.recoil.delay((float) (3 - finalI) / 8), 0, -10, 0));
                                    under = true;
                                }
                            });
                            parts.add(new HaloPart() {
                                {
                                    progress = PartProgress.warmup.delay(1 / reload);
                                    shapes = 4;
                                    haloRadius = 2;
                                    haloRadiusTo = 2.5f;
                                    haloRotateSpeed = 2f;
                                    radius = 0.5f;
                                    radiusTo = 1f;
                                    triLength = 0.5f;
                                    triLengthTo = 1f;
                                    rotateSpeed = 1f;
                                    moveY = 8.2f + 28f;
                                    color = Color.purple;
                                    colorTo = Color.red;
                                    layerOffset = -0.0001f;
                                }
                            });
                        }
                    }
                };
            }
        };
        tiansha = new CheatTurret("old-conventions") {
            {
                category = Category.turret;
                buildVisibility = BuildVisibility.shown;
                size = 2;
                health = 114514;
                range = 8 * 30;
                reload = 1;
                shootAmmo = Bullets.destroyBullet;
            }
        };


        //special
        teamConversion = new TeamConversion("teamconversion") {
            {
                category = Category.logic;
                buildVisibility = BuildVisibility.shown;
                size = 1;
                health = 1145;
            }
        };

        //wall

        wall1 = new invincibleWall("wall1") {
            {
                category = Category.defense;
                buildVisibility = BuildVisibility.shown;
                size = 1;
                health = 1919810;
            }
        };
        wall2 = new invincibleWall("wall2") {
            {
                category = Category.defense;
                buildVisibility = BuildVisibility.shown;
                size = 2;
                health = 1919810;
            }
        };
        wall3 = new invincibleWall("wall3") {
            {
                category = Category.defense;
                buildVisibility = BuildVisibility.shown;
                size = 3;
                health = 1919810;
            }
        };
        wall4 = new invincibleWall("wall4") {
            {
                category = Category.defense;
                buildVisibility = BuildVisibility.shown;
                size = 4;
                health = 1919810;
            }
        };
        wall5 = new invincibleWall("wall5") {
            {
                category = Category.defense;
                buildVisibility = BuildVisibility.shown;
                size = 5;
                health = 1919810;
            }
        };
        wall6 = new invincibleWall("wall6") {
            {
                category = Category.defense;
                buildVisibility = BuildVisibility.shown;
                size = 6;
                health = 1919810;
            }
        };


        //dis
        massdriver = new MassDriver("massdriver") {
            {
                category = Category.distribution;
                buildVisibility = BuildVisibility.shown;
                delay = 10;
                size = 2;
                health = 114514;
                itemCapacity = 200;
            }
        };
        quicklyConveyor = new Conveyor("quick-conveyor") {{
            category = Category.distribution;
            buildVisibility = BuildVisibility.shown;
            health = 1145;
            speed = 3f;
            itemCapacity = 10;
            displayedSpeed = 100f;
        }};

        quicklyStackConveyor = new StackConveyor("stack-conveyor") {{
            category = Category.distribution;
            buildVisibility = BuildVisibility.shown;
            health = 75;
            speed = 3f;
            itemCapacity = 10;
        }};

        itemsource = new ItemSource("item-source") {
            {
                category = Category.distribution;
                buildVisibility = BuildVisibility.shown;
                size = 1;
                health = 114514;
                itemsPerSecond = 300;
            }
        };
        liquidsource = new LiquidSource("liquid-source") {
            {
                category = Category.liquid;
                buildVisibility = BuildVisibility.shown;
                size = 1;
                health = 114514;
                liquidCapacity = 100000f;
            }
        };

        unitsource = new PayloadSource("payload-source") {
            {
                category = Category.units;
                buildVisibility = BuildVisibility.shown;
                size = 5;
                health = 114514;
            }
        };

        powerSource = new PowerSource("power-source") {
            {
                category = Category.power;
                buildVisibility = BuildVisibility.shown;
                size = 1;
                health = 114514;
                powerProduction = 10000000f / 60f;
                alwaysUnlocked = true;
            }
        };

        powerVoidSource = new PowerVoid("power-void") {
            {
                category = Category.power;
                buildVisibility = BuildVisibility.shown;
                size = 1;
                health = 114514;
            }
        };
        payloadVoidSource = new PayloadVoid("payload-void") {
            {
                category = Category.units;
                buildVisibility = BuildVisibility.shown;
                size = 5;
                health = 114514;
            }
        };
    }
}
