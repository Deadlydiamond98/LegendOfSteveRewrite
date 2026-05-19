package net.deadlydiamond.legend_of_steve.client.animation;

import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

public class TektiteEntityAnimations {
        // LAND
        public static final Animation IDLE = idle();
        public static final Animation WALKING = walk();
        public static final Animation JUMPING = jumping();
        public static final Animation LANDING = landing();

        // WATER
//        public static final Animation WATER_IDLE = water_idle();


        // ANIMATION METHODS (DONE THIS WAY SO I CAN ACTUALLY SCROLL)

        private static Animation idle() {
                return Animation.Builder.create(4.0F).looping()
                        .addBoneAnimation("body", new Transformation(Transformation.Targets.TRANSLATE,
                                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 0.0F), Transformation.Interpolations.LINEAR),
                                new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                                new Keyframe(4.0F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 0.0F), Transformation.Interpolations.LINEAR)
                        ))
                        .addBoneAnimation("FrontRightLegUpper", new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0688F, -1.0955F, -7.1869F), Transformation.Interpolations.LINEAR),
                                new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0214F, -0.6068F, -4.0435F), Transformation.Interpolations.LINEAR),
                                new Keyframe(4.0F, AnimationHelper.createRotationalVector(0.0688F, -1.0955F, -7.1869F), Transformation.Interpolations.LINEAR)
                        ))
                        .addBoneAnimation("FrontRightLegLower", new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.createRotationalVector(-0.0758F, 12.2314F, -0.707F), Transformation.Interpolations.LINEAR),
                                new Keyframe(2.0F, AnimationHelper.createRotationalVector(-0.2469F, 10.3584F, -2.7237F), Transformation.Interpolations.LINEAR),
                                new Keyframe(4.0F, AnimationHelper.createRotationalVector(-0.0758F, 12.2314F, -0.707F), Transformation.Interpolations.LINEAR)
                        ))
                        .addBoneAnimation("BackRightLegUpper", new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0597F, -0.5436F, -12.5375F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.6667F, AnimationHelper.createRotationalVector(0.0283F, -0.3661F, -8.8553F), Transformation.Interpolations.LINEAR),
                                new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0238F, -0.3337F, -8.1479F), Transformation.Interpolations.LINEAR),
                                new Keyframe(3.6667F, AnimationHelper.createRotationalVector(0.0522F, -0.5059F, -11.7815F), Transformation.Interpolations.LINEAR),
                                new Keyframe(4.0F, AnimationHelper.createRotationalVector(0.0597F, -0.5436F, -12.5375F), Transformation.Interpolations.LINEAR)
                        ))
                        .addBoneAnimation("BackRightLegLower", new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.createRotationalVector(-0.1176F, -0.6126F, 21.7369F), Transformation.Interpolations.LINEAR),
                                new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0456F, 0.2872F, 18.0408F), Transformation.Interpolations.LINEAR),
                                new Keyframe(4.0F, AnimationHelper.createRotationalVector(-0.1176F, -0.6126F, 21.7369F), Transformation.Interpolations.LINEAR)
                        ))
                        .addBoneAnimation("FrontLeftLegUpper", new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0416F, 0.8292F, 5.7476F), Transformation.Interpolations.LINEAR),
                                new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0082F, 0.3665F, 2.5764F), Transformation.Interpolations.LINEAR),
                                new Keyframe(4.0F, AnimationHelper.createRotationalVector(0.0416F, 0.8292F, 5.7476F), Transformation.Interpolations.LINEAR)
                        ))
                        .addBoneAnimation("FrontLeftLegLower", new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.createRotationalVector(-0.0702F, -11.0254F, 0.727F), Transformation.Interpolations.LINEAR),
                                new Keyframe(2.0F, AnimationHelper.createRotationalVector(-0.225F, -9.1617F, 2.8083F), Transformation.Interpolations.LINEAR),
                                new Keyframe(4.0F, AnimationHelper.createRotationalVector(-0.0702F, -11.0254F, 0.727F), Transformation.Interpolations.LINEAR)
                        ))
                        .addBoneAnimation("BackLeftLegUpper", new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0424F, 0.3386F, 14.2715F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.5F, AnimationHelper.createRotationalVector(0.0218F, 0.2382F, 10.4688F), Transformation.Interpolations.LINEAR),
                                new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0168F, 0.2077F, 9.2548F), Transformation.Interpolations.LINEAR),
                                new Keyframe(3.5F, AnimationHelper.createRotationalVector(0.0345F, 0.3036F, 12.9769F), Transformation.Interpolations.LINEAR),
                                new Keyframe(4.0F, AnimationHelper.createRotationalVector(0.0424F, 0.3386F, 14.2715F), Transformation.Interpolations.LINEAR)
                        ))
                        .addBoneAnimation("BackLeftLegLower", new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.createRotationalVector(-0.4278F, 2.133F, -22.6799F), Transformation.Interpolations.LINEAR),
                                new Keyframe(2.0F, AnimationHelper.createRotationalVector(-0.1612F, 0.9927F, -18.4407F), Transformation.Interpolations.LINEAR),
                                new Keyframe(4.0F, AnimationHelper.createRotationalVector(-0.4278F, 2.133F, -22.6799F), Transformation.Interpolations.LINEAR)
                        ))
                        .addBoneAnimation("Front Right IK", new Transformation(Transformation.Targets.TRANSLATE,
                                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(-7.25F, -9.19F, -7.31F), Transformation.Interpolations.CUBIC)
                        ))
                        .addBoneAnimation("Back Right IK", new Transformation(Transformation.Targets.TRANSLATE,
                                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(-6.0F, -8.37F, 2.31F), Transformation.Interpolations.CUBIC)
                        ))
                        .addBoneAnimation("Front Left IK", new Transformation(Transformation.Targets.TRANSLATE,
                                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(7.25F, -9.37F, -7.12F), Transformation.Interpolations.CUBIC)
                        ))
                        .addBoneAnimation("Back Left IK", new Transformation(Transformation.Targets.TRANSLATE,
                                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(6.0F, -8.37F, 2.31F), Transformation.Interpolations.CUBIC)
                        ))
                        .build();
        }

        private static Animation walk() {
                return Animation.Builder.create(2.0F).looping()
                        .addBoneAnimation("body", new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(0.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 5.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(0.75F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -5.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(1.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 5.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(1.75F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -5.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                        ))
                        .addBoneAnimation("body", new Transformation(Transformation.Targets.TRANSLATE,
                                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, -1.0F, 0.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0.0F, -1.0F, 0.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(0.75F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, -1.0F, 0.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(1.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(1.5F, AnimationHelper.createTranslationalVector(0.0F, -1.0F, 0.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(1.75F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, -1.0F, 0.0F), Transformation.Interpolations.CUBIC)
                        ))
                        .addBoneAnimation("FrontRightLegUpper", new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0197F, -1.1591F, -1.9516F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0417F, AnimationHelper.createRotationalVector(0.0641F, -2.0152F, -3.6446F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0833F, AnimationHelper.createRotationalVector(0.1901F, -3.0359F, -7.1641F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.125F, AnimationHelper.createRotationalVector(0.2596F, -2.7202F, -10.9002F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.1667F, AnimationHelper.createRotationalVector(0.0992F, -0.8828F, -12.817F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.2083F, AnimationHelper.createRotationalVector(-0.1163F, 1.0802F, -12.2876F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.25F, AnimationHelper.createRotationalVector(-0.1986F, 2.1012F, -10.7954F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.2917F, AnimationHelper.createRotationalVector(-0.2051F, 2.4546F, -9.552F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-0.1861F, 2.5255F, -8.4284F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.375F, AnimationHelper.createRotationalVector(-0.1627F, 2.4608F, -7.5623F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.4167F, AnimationHelper.createRotationalVector(-0.1446F, 2.3613F, -7.0059F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.4583F, AnimationHelper.createRotationalVector(-0.1351F, 2.2834F, -6.7693F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.5F, AnimationHelper.createRotationalVector(-0.1349F, 2.2528F, -6.8508F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.5417F, AnimationHelper.createRotationalVector(-0.1454F, 2.2711F, -7.328F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.5833F, AnimationHelper.createRotationalVector(-0.1648F, 2.2895F, -8.2347F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.625F, AnimationHelper.createRotationalVector(-0.184F, 2.2204F, -9.4754F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-0.1864F, 1.9573F, -10.8786F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.7083F, AnimationHelper.createRotationalVector(-0.1507F, 1.412F, -12.1866F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.75F, AnimationHelper.createRotationalVector(-0.0649F, 0.5661F, -13.0818F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.7917F, AnimationHelper.createRotationalVector(0.0944F, -0.8101F, -13.294F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.8333F, AnimationHelper.createRotationalVector(0.2569F, -2.4459F, -11.992F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.875F, AnimationHelper.createRotationalVector(0.2512F, -3.2098F, -8.9473F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.9167F, AnimationHelper.createRotationalVector(0.1215F, -2.6249F, -5.301F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.9583F, AnimationHelper.createRotationalVector(0.0341F, -1.508F, -2.5885F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0197F, -1.1591F, -1.9516F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.0417F, AnimationHelper.createRotationalVector(0.0439F, -1.6783F, -2.9981F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.0833F, AnimationHelper.createRotationalVector(0.087F, -2.2236F, -4.4796F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.125F, AnimationHelper.createRotationalVector(0.1412F, -2.5804F, -6.2644F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.1667F, AnimationHelper.createRotationalVector(0.1823F, -2.5593F, -8.1459F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.2083F, AnimationHelper.createRotationalVector(0.1792F, -2.0736F, -9.8777F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.25F, AnimationHelper.createRotationalVector(0.1163F, -1.1806F, -11.2507F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.2917F, AnimationHelper.createRotationalVector(0.0049F, -0.0466F, -12.1285F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.3333F, AnimationHelper.createRotationalVector(-0.1204F, 1.1159F, -12.3176F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.375F, AnimationHelper.createRotationalVector(-0.2117F, 2.0576F, -11.7482F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.4167F, AnimationHelper.createRotationalVector(-0.238F, 2.5854F, -10.5171F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.4583F, AnimationHelper.createRotationalVector(-0.2029F, 2.6311F, -8.8183F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.5F, AnimationHelper.createRotationalVector(-0.1349F, 2.2528F, -6.8508F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.5417F, AnimationHelper.createRotationalVector(-0.0398F, 1.2884F, -3.5401F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.5833F, AnimationHelper.createRotationalVector(-0.0001F, 0.0706F, -0.1929F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.625F, AnimationHelper.createRotationalVector(-0.0212F, -0.9126F, 2.6554F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.6667F, AnimationHelper.createRotationalVector(-0.057F, -1.4129F, 4.6198F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.7083F, AnimationHelper.createRotationalVector(-0.0656F, -1.3923F, 5.3919F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.75F, AnimationHelper.createRotationalVector(-0.0393F, -0.9574F, 4.6987F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.7917F, AnimationHelper.createRotationalVector(-0.0039F, -0.2292F, 1.9399F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.8333F, AnimationHelper.createRotationalVector(0.0004F, -0.0266F, -1.7169F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.875F, AnimationHelper.createRotationalVector(0.0248F, -0.7227F, -3.9368F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.9167F, AnimationHelper.createRotationalVector(0.045F, -1.369F, -3.7636F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.9583F, AnimationHelper.createRotationalVector(0.0268F, -1.2607F, -2.4317F), Transformation.Interpolations.LINEAR)
                        ))
                        .addBoneAnimation("FrontRightLegLower", new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.createRotationalVector(-2.8419F, 29.6968F, -10.6907F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0417F, AnimationHelper.createRotationalVector(-2.2565F, 29.2754F, -8.6245F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0833F, AnimationHelper.createRotationalVector(-0.8794F, 26.0353F, -3.8023F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.125F, AnimationHelper.createRotationalVector(0.1656F, 19.6076F, 0.9581F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.1667F, AnimationHelper.createRotationalVector(0.352F, 10.6465F, 3.7767F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.2083F, AnimationHelper.createRotationalVector(0.0539F, 1.3499F, 4.5711F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.25F, AnimationHelper.createRotationalVector(-0.2023F, -5.4262F, 4.2681F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.2917F, AnimationHelper.createRotationalVector(-0.3055F, -9.4376F, 3.6999F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-0.3222F, -12.2367F, 3.0048F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.375F, AnimationHelper.createRotationalVector(-0.2836F, -13.9747F, 2.3134F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.4167F, AnimationHelper.createRotationalVector(-0.2215F, -14.8178F, 1.7036F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.4583F, AnimationHelper.createRotationalVector(-0.1594F, -14.9253F, 1.2171F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.5F, AnimationHelper.createRotationalVector(-0.1116F, -14.439F, 0.8807F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.5417F, AnimationHelper.createRotationalVector(-0.075F, -13.2279F, 0.647F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.5833F, AnimationHelper.createRotationalVector(-0.0468F, -11.1245F, 0.4801F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.625F, AnimationHelper.createRotationalVector(-0.0289F, -8.2031F, 0.4027F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-0.0157F, -4.5642F, 0.3933F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.7083F, AnimationHelper.createRotationalVector(-0.0012F, -0.3571F, 0.3939F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.75F, AnimationHelper.createRotationalVector(0.0123F, 4.2078F, 0.334F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.7917F, AnimationHelper.createRotationalVector(0.0056F, 10.2749F, 0.0627F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-0.1741F, 17.5615F, -1.1272F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.875F, AnimationHelper.createRotationalVector(-0.7899F, 23.8333F, -3.7418F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.9167F, AnimationHelper.createRotationalVector(-1.8071F, 27.8724F, -7.2732F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.9583F, AnimationHelper.createRotationalVector(-2.7004F, 29.6747F, -10.1687F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.0F, AnimationHelper.createRotationalVector(-2.8419F, 29.6968F, -10.6907F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.0417F, AnimationHelper.createRotationalVector(-2.5467F, 29.0118F, -9.8207F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.0833F, AnimationHelper.createRotationalVector(-2.0597F, 27.2979F, -8.4677F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.125F, AnimationHelper.createRotationalVector(-1.4991F, 24.6067F, -6.8659F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.1667F, AnimationHelper.createRotationalVector(-0.9694F, 20.9934F, -5.2287F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.2083F, AnimationHelper.createRotationalVector(-0.5372F, 16.5807F, -3.6852F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.25F, AnimationHelper.createRotationalVector(-0.2281F, 11.6099F, -2.2437F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.2917F, AnimationHelper.createRotationalVector(-0.0483F, 6.3936F, -0.8644F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.3333F, AnimationHelper.createRotationalVector(0.0029F, 1.1367F, 0.2973F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.375F, AnimationHelper.createRotationalVector(-0.0373F, -3.888F, 1.0992F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.4167F, AnimationHelper.createRotationalVector(-0.1073F, -8.3508F, 1.4693F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.4583F, AnimationHelper.createRotationalVector(-0.1454F, -11.9454F, 1.3895F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.5F, AnimationHelper.createRotationalVector(-0.1116F, -14.439F, 0.8807F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.5417F, AnimationHelper.createRotationalVector(0.1149F, -17.728F, -0.7366F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.5833F, AnimationHelper.createRotationalVector(0.4185F, -19.3677F, -2.452F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.625F, AnimationHelper.createRotationalVector(0.6475F, -19.6283F, -3.7415F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.6667F, AnimationHelper.createRotationalVector(0.7069F, -18.7187F, -4.287F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.7083F, AnimationHelper.createRotationalVector(0.5879F, -16.7163F, -4.0002F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.75F, AnimationHelper.createRotationalVector(0.3536F, -13.5595F, -2.9738F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.7917F, AnimationHelper.createRotationalVector(0.0798F, -7.5596F, -1.2071F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.8333F, AnimationHelper.createRotationalVector(-0.0011F, 1.7668F, -0.0741F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.875F, AnimationHelper.createRotationalVector(-0.112F, 12.2044F, -1.0478F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.9167F, AnimationHelper.createRotationalVector(-0.8149F, 21.2145F, -4.3494F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.9583F, AnimationHelper.createRotationalVector(-2.0661F, 27.138F, -8.5454F), Transformation.Interpolations.LINEAR)
                        ))
                        .addBoneAnimation("BackRightLegUpper", new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.createRotationalVector(-0.1129F, 3.2336F, -3.9997F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0417F, AnimationHelper.createRotationalVector(-0.1486F, 3.8796F, -4.3843F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0833F, AnimationHelper.createRotationalVector(-0.2783F, 5.2889F, -6.0194F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.125F, AnimationHelper.createRotationalVector(-0.3089F, 5.2906F, -6.6789F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-0.1438F, 3.1741F, -5.1875F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.2083F, AnimationHelper.createRotationalVector(-0.0234F, 0.9356F, -2.8697F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.25F, AnimationHelper.createRotationalVector(0.0001F, -0.0071F, -1.5382F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.2917F, AnimationHelper.createRotationalVector(0.0056F, -0.4785F, -1.3459F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.3333F, AnimationHelper.createRotationalVector(0.0086F, -0.8214F, -1.2017F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.375F, AnimationHelper.createRotationalVector(0.0006F, -0.2404F, -0.2686F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.4583F, AnimationHelper.createRotationalVector(0.0987F, 3.3314F, 3.3914F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.1166F, 3.4487F, 3.8705F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.5417F, AnimationHelper.createRotationalVector(0.0955F, 2.9106F, 3.7565F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.5833F, AnimationHelper.createRotationalVector(0.0606F, 2.0793F, 3.337F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.625F, AnimationHelper.createRotationalVector(0.028F, 1.2163F, 2.6338F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.6667F, AnimationHelper.createRotationalVector(0.0083F, 0.5377F, 1.7695F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.7083F, AnimationHelper.createRotationalVector(0.001F, 0.1313F, 0.8552F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.75F, AnimationHelper.createRotationalVector(0.0F, -0.0018F, -0.1844F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.7917F, AnimationHelper.createRotationalVector(-0.0027F, 0.2006F, -1.5147F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-0.0183F, 0.7626F, -2.7444F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.875F, AnimationHelper.createRotationalVector(-0.047F, 1.5172F, -3.5483F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.9167F, AnimationHelper.createRotationalVector(-0.0765F, 2.2475F, -3.8989F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.9583F, AnimationHelper.createRotationalVector(-0.0981F, 2.8273F, -3.9757F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.0F, AnimationHelper.createRotationalVector(-0.1129F, 3.2336F, -3.9997F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.0417F, AnimationHelper.createRotationalVector(-0.1486F, 3.8796F, -4.3843F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.0833F, AnimationHelper.createRotationalVector(-0.2783F, 5.2889F, -6.0194F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.125F, AnimationHelper.createRotationalVector(-0.3089F, 5.2906F, -6.6789F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.1667F, AnimationHelper.createRotationalVector(-0.1438F, 3.1741F, -5.1875F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.2083F, AnimationHelper.createRotationalVector(-0.0234F, 0.9356F, -2.8697F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.25F, AnimationHelper.createRotationalVector(0.0001F, -0.0071F, -1.5382F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.2917F, AnimationHelper.createRotationalVector(0.0056F, -0.4785F, -1.3459F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.3333F, AnimationHelper.createRotationalVector(0.0086F, -0.8214F, -1.2017F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.375F, AnimationHelper.createRotationalVector(0.0006F, -0.2404F, -0.2686F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.4583F, AnimationHelper.createRotationalVector(0.0987F, 3.3314F, 3.3914F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.5F, AnimationHelper.createRotationalVector(0.1166F, 3.4487F, 3.8705F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.5417F, AnimationHelper.createRotationalVector(0.0955F, 2.9106F, 3.7565F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.5833F, AnimationHelper.createRotationalVector(0.0606F, 2.0793F, 3.337F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.625F, AnimationHelper.createRotationalVector(0.028F, 1.2163F, 2.6338F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.6667F, AnimationHelper.createRotationalVector(0.0083F, 0.5377F, 1.7695F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.7083F, AnimationHelper.createRotationalVector(0.001F, 0.1313F, 0.8552F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.75F, AnimationHelper.createRotationalVector(0.0F, -0.0018F, -0.1844F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.7917F, AnimationHelper.createRotationalVector(-0.0027F, 0.2006F, -1.5147F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.8333F, AnimationHelper.createRotationalVector(-0.0183F, 0.7626F, -2.7444F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.875F, AnimationHelper.createRotationalVector(-0.047F, 1.5172F, -3.5483F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.9167F, AnimationHelper.createRotationalVector(-0.0765F, 2.2475F, -3.8989F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.9583F, AnimationHelper.createRotationalVector(-0.0981F, 2.8273F, -3.9757F), Transformation.Interpolations.LINEAR)
                        ))
                        .addBoneAnimation("BackRightLegLower", new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.createRotationalVector(-4.7953F, -38.0946F, 13.8299F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0417F, AnimationHelper.createRotationalVector(-3.0963F, -39.5945F, 8.5874F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0833F, AnimationHelper.createRotationalVector(-0.6417F, -37.9803F, 1.8646F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.125F, AnimationHelper.createRotationalVector(2.2617F, -31.7821F, -7.9328F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.1667F, AnimationHelper.createRotationalVector(3.5836F, -20.5205F, -19.6102F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.2083F, AnimationHelper.createRotationalVector(2.2024F, -8.4622F, -29.1293F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.25F, AnimationHelper.createRotationalVector(0.0132F, -0.0439F, -33.5231F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.2917F, AnimationHelper.createRotationalVector(-2.2216F, 7.5778F, -32.6375F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-4.3009F, 16.6027F, -28.8643F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.375F, AnimationHelper.createRotationalVector(-5.3637F, 24.4555F, -24.3928F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.4167F, AnimationHelper.createRotationalVector(-5.4843F, 29.4742F, -20.6398F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.4583F, AnimationHelper.createRotationalVector(-4.9302F, 31.8964F, -17.1344F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.5F, AnimationHelper.createRotationalVector(-3.467F, 32.249F, -11.9526F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.5417F, AnimationHelper.createRotationalVector(-2.0704F, 30.613F, -7.5545F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.5833F, AnimationHelper.createRotationalVector(-0.4676F, 27.2611F, -1.9281F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.625F, AnimationHelper.createRotationalVector(0.7748F, 22.2007F, 3.9475F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.6667F, AnimationHelper.createRotationalVector(1.274F, 15.7205F, 9.2085F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.7083F, AnimationHelper.createRotationalVector(0.9778F, 8.3282F, 13.369F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.75F, AnimationHelper.createRotationalVector(0.069F, 0.4801F, 16.3693F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.7917F, AnimationHelper.createRotationalVector(-1.2326F, -7.6086F, 18.3776F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-2.6832F, -15.6745F, 19.3125F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.875F, AnimationHelper.createRotationalVector(-3.9769F, -23.2749F, 19.1378F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.9167F, AnimationHelper.createRotationalVector(-4.8316F, -29.8869F, 17.9652F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.9583F, AnimationHelper.createRotationalVector(-5.0931F, -34.9897F, 16.063F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.0F, AnimationHelper.createRotationalVector(-4.7953F, -38.0946F, 13.8299F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.0417F, AnimationHelper.createRotationalVector(-3.0963F, -39.5945F, 8.5874F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.0833F, AnimationHelper.createRotationalVector(-0.6417F, -37.9803F, 1.8646F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.125F, AnimationHelper.createRotationalVector(2.2617F, -31.7821F, -7.9328F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.1667F, AnimationHelper.createRotationalVector(3.5836F, -20.5205F, -19.6102F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.2083F, AnimationHelper.createRotationalVector(2.2024F, -8.4622F, -29.1293F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.25F, AnimationHelper.createRotationalVector(0.0132F, -0.0439F, -33.5231F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.2917F, AnimationHelper.createRotationalVector(-2.2216F, 7.5778F, -32.6375F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.3333F, AnimationHelper.createRotationalVector(-4.3009F, 16.6027F, -28.8643F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.375F, AnimationHelper.createRotationalVector(-5.3637F, 24.4555F, -24.3928F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.4167F, AnimationHelper.createRotationalVector(-5.4843F, 29.4742F, -20.6398F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.4583F, AnimationHelper.createRotationalVector(-4.9302F, 31.8964F, -17.1344F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.5F, AnimationHelper.createRotationalVector(-3.467F, 32.249F, -11.9526F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.5417F, AnimationHelper.createRotationalVector(-2.0704F, 30.613F, -7.5545F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.5833F, AnimationHelper.createRotationalVector(-0.4676F, 27.2611F, -1.9281F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.625F, AnimationHelper.createRotationalVector(0.7748F, 22.2007F, 3.9475F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.6667F, AnimationHelper.createRotationalVector(1.274F, 15.7205F, 9.2085F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.7083F, AnimationHelper.createRotationalVector(0.9778F, 8.3282F, 13.369F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.75F, AnimationHelper.createRotationalVector(0.069F, 0.4801F, 16.3693F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.7917F, AnimationHelper.createRotationalVector(-1.2326F, -7.6086F, 18.3776F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.8333F, AnimationHelper.createRotationalVector(-2.6832F, -15.6745F, 19.3125F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.875F, AnimationHelper.createRotationalVector(-3.9769F, -23.2749F, 19.1378F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.9167F, AnimationHelper.createRotationalVector(-4.8316F, -29.8869F, 17.9652F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.9583F, AnimationHelper.createRotationalVector(-5.0931F, -34.9897F, 16.063F), Transformation.Interpolations.LINEAR)
                        ))
                        .addBoneAnimation("FrontLeftLegUpper", new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.createRotationalVector(-0.117F, -2.0762F, 6.45F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0417F, AnimationHelper.createRotationalVector(-0.1018F, -1.9948F, 5.8416F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0833F, AnimationHelper.createRotationalVector(-0.1197F, -2.0961F, 6.5359F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.125F, AnimationHelper.createRotationalVector(-0.1559F, -2.1933F, 8.1327F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-0.1758F, -1.9957F, 10.0672F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.2083F, AnimationHelper.createRotationalVector(-0.1405F, -1.3669F, 11.7385F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.25F, AnimationHelper.createRotationalVector(-0.0499F, -0.4484F, 12.7012F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.2917F, AnimationHelper.createRotationalVector(0.1004F, 0.8923F, 12.8425F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.3333F, AnimationHelper.createRotationalVector(0.2443F, 2.4391F, 11.4364F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.375F, AnimationHelper.createRotationalVector(0.2222F, 3.0616F, 8.3017F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.4167F, AnimationHelper.createRotationalVector(0.0938F, 2.3304F, 4.6087F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.4583F, AnimationHelper.createRotationalVector(0.0183F, 1.1149F, 1.88F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.008F, 0.7436F, 1.2306F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.5417F, AnimationHelper.createRotationalVector(0.0253F, 1.2835F, 2.2572F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.5833F, AnimationHelper.createRotationalVector(0.0598F, 1.8532F, 3.6948F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.625F, AnimationHelper.createRotationalVector(0.105F, 2.2277F, 5.3991F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.6667F, AnimationHelper.createRotationalVector(0.1386F, 2.2195F, 7.1463F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.7083F, AnimationHelper.createRotationalVector(0.1338F, 1.7628F, 8.6824F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.75F, AnimationHelper.createRotationalVector(0.0811F, 0.944F, 9.8179F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.7917F, AnimationHelper.createRotationalVector(-0.0051F, -0.0553F, 10.4676F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-0.0953F, -1.0358F, 10.511F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.875F, AnimationHelper.createRotationalVector(-0.1565F, -1.7977F, 9.9513F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.9167F, AnimationHelper.createRotationalVector(-0.1732F, -2.2161F, 8.9392F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.9583F, AnimationHelper.createRotationalVector(-0.1535F, -2.2813F, 7.6995F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.0F, AnimationHelper.createRotationalVector(-0.117F, -2.0762F, 6.45F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.0417F, AnimationHelper.createRotationalVector(-0.1018F, -1.9948F, 5.8416F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.0833F, AnimationHelper.createRotationalVector(-0.1197F, -2.0961F, 6.5359F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.125F, AnimationHelper.createRotationalVector(-0.1559F, -2.1933F, 8.1327F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.1667F, AnimationHelper.createRotationalVector(-0.1758F, -1.9957F, 10.0672F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.2083F, AnimationHelper.createRotationalVector(-0.1405F, -1.3669F, 11.7385F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.25F, AnimationHelper.createRotationalVector(-0.0499F, -0.4484F, 12.7012F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.2917F, AnimationHelper.createRotationalVector(0.1004F, 0.8923F, 12.8425F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.3333F, AnimationHelper.createRotationalVector(0.2443F, 2.4391F, 11.4364F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.375F, AnimationHelper.createRotationalVector(0.2222F, 3.0616F, 8.3017F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.4167F, AnimationHelper.createRotationalVector(0.0938F, 2.3304F, 4.6087F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.4583F, AnimationHelper.createRotationalVector(0.0183F, 1.1149F, 1.88F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.5F, AnimationHelper.createRotationalVector(0.008F, 0.7436F, 1.2306F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.5417F, AnimationHelper.createRotationalVector(0.0253F, 1.2835F, 2.2572F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.5833F, AnimationHelper.createRotationalVector(0.0598F, 1.8532F, 3.6948F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.625F, AnimationHelper.createRotationalVector(0.105F, 2.2277F, 5.3991F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.6667F, AnimationHelper.createRotationalVector(0.1386F, 2.2195F, 7.1463F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.7083F, AnimationHelper.createRotationalVector(0.1338F, 1.7628F, 8.6824F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.75F, AnimationHelper.createRotationalVector(0.0811F, 0.944F, 9.8179F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.7917F, AnimationHelper.createRotationalVector(-0.0051F, -0.0553F, 10.4676F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.8333F, AnimationHelper.createRotationalVector(-0.0953F, -1.0358F, 10.511F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.875F, AnimationHelper.createRotationalVector(-0.1565F, -1.7977F, 9.9513F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.9167F, AnimationHelper.createRotationalVector(-0.1732F, -2.2161F, 8.9392F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.9583F, AnimationHelper.createRotationalVector(-0.1535F, -2.2813F, 7.6995F), Transformation.Interpolations.LINEAR)
                        ))
                        .addBoneAnimation("FrontLeftLegLower", new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.createRotationalVector(-0.1227F, 14.2786F, -0.9796F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0417F, AnimationHelper.createRotationalVector(-0.0498F, 15.5449F, -0.3651F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0833F, AnimationHelper.createRotationalVector(-0.0277F, 14.1446F, -0.2236F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.125F, AnimationHelper.createRotationalVector(-0.0366F, 10.7292F, -0.3902F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-0.0295F, 5.9589F, -0.5665F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.2083F, AnimationHelper.createRotationalVector(-0.003F, 0.6025F, -0.5702F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.25F, AnimationHelper.createRotationalVector(0.0169F, -4.4811F, -0.4329F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.2917F, AnimationHelper.createRotationalVector(0.0103F, -10.5371F, -0.1119F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-0.1828F, -17.775F, 1.1689F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.375F, AnimationHelper.createRotationalVector(-0.829F, -23.9661F, 3.9043F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.4167F, AnimationHelper.createRotationalVector(-1.8795F, -27.918F, 7.5511F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.4583F, AnimationHelper.createRotationalVector(-2.7924F, -29.6532F, 10.5215F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.5F, AnimationHelper.createRotationalVector(-2.9366F, -29.6504F, 11.0629F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.5417F, AnimationHelper.createRotationalVector(-2.6205F, -28.9194F, 10.1373F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.5833F, AnimationHelper.createRotationalVector(-2.0899F, -27.0685F, 8.6666F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.625F, AnimationHelper.createRotationalVector(-1.4876F, -24.1614F, 6.9424F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-0.9348F, -20.2787F, 5.2238F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.7083F, AnimationHelper.createRotationalVector(-0.5011F, -15.5885F, 3.6598F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.75F, AnimationHelper.createRotationalVector(-0.2048F, -10.39F, 2.2523F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.7917F, AnimationHelper.createRotationalVector(-0.0413F, -5.0503F, 0.9373F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-0.0003F, 0.1898F, -0.1554F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.875F, AnimationHelper.createRotationalVector(-0.0401F, 5.0479F, -0.9086F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.9167F, AnimationHelper.createRotationalVector(-0.1029F, 9.2046F, -1.2788F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.9583F, AnimationHelper.createRotationalVector(-0.139F, 12.363F, -1.2832F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.0F, AnimationHelper.createRotationalVector(-0.1227F, 14.2786F, -0.9796F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.0417F, AnimationHelper.createRotationalVector(-0.0498F, 15.5449F, -0.3651F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.0833F, AnimationHelper.createRotationalVector(-0.0277F, 14.1446F, -0.2236F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.125F, AnimationHelper.createRotationalVector(-0.0366F, 10.7292F, -0.3902F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.1667F, AnimationHelper.createRotationalVector(-0.0295F, 5.9589F, -0.5665F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.2083F, AnimationHelper.createRotationalVector(-0.003F, 0.6025F, -0.5702F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.25F, AnimationHelper.createRotationalVector(0.0169F, -4.4811F, -0.4329F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.2917F, AnimationHelper.createRotationalVector(0.0103F, -10.5371F, -0.1119F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.3333F, AnimationHelper.createRotationalVector(-0.1828F, -17.775F, 1.1689F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.375F, AnimationHelper.createRotationalVector(-0.829F, -23.9661F, 3.9043F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.4167F, AnimationHelper.createRotationalVector(-1.8795F, -27.918F, 7.5511F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.4583F, AnimationHelper.createRotationalVector(-2.7924F, -29.6532F, 10.5215F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.5F, AnimationHelper.createRotationalVector(-2.9366F, -29.6504F, 11.0629F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.5417F, AnimationHelper.createRotationalVector(-2.6205F, -28.9194F, 10.1373F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.5833F, AnimationHelper.createRotationalVector(-2.0899F, -27.0685F, 8.6666F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.625F, AnimationHelper.createRotationalVector(-1.4876F, -24.1614F, 6.9424F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.6667F, AnimationHelper.createRotationalVector(-0.9348F, -20.2787F, 5.2238F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.7083F, AnimationHelper.createRotationalVector(-0.5011F, -15.5885F, 3.6598F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.75F, AnimationHelper.createRotationalVector(-0.2048F, -10.39F, 2.2523F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.7917F, AnimationHelper.createRotationalVector(-0.0413F, -5.0503F, 0.9373F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.8333F, AnimationHelper.createRotationalVector(-0.0003F, 0.1898F, -0.1554F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.875F, AnimationHelper.createRotationalVector(-0.0401F, 5.0479F, -0.9086F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.9167F, AnimationHelper.createRotationalVector(-0.1029F, 9.2046F, -1.2788F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.9583F, AnimationHelper.createRotationalVector(-0.139F, 12.363F, -1.2832F), Transformation.Interpolations.LINEAR)
                        ))
                        .addBoneAnimation("BackLeftLegUpper", new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.1159F, -3.3958F, -3.9081F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0417F, AnimationHelper.createRotationalVector(0.0931F, -2.835F, -3.7603F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0833F, AnimationHelper.createRotationalVector(0.057F, -1.9858F, -3.2897F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.125F, AnimationHelper.createRotationalVector(0.0249F, -1.1258F, -2.5378F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.1667F, AnimationHelper.createRotationalVector(0.0069F, -0.4746F, -1.6552F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.2083F, AnimationHelper.createRotationalVector(0.0007F, -0.105F, -0.7661F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.25F, AnimationHelper.createRotationalVector(0.0F, -0.0015F, 0.2281F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.2917F, AnimationHelper.createRotationalVector(-0.003F, -0.2278F, 1.5316F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-0.0191F, -0.8037F, 2.7254F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.375F, AnimationHelper.createRotationalVector(-0.0468F, -1.5457F, 3.4687F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.4167F, AnimationHelper.createRotationalVector(-0.0731F, -2.2334F, 3.7486F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.4583F, AnimationHelper.createRotationalVector(-0.0908F, -2.7586F, 3.7684F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.5F, AnimationHelper.createRotationalVector(-0.1028F, -3.1267F, 3.7638F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.5417F, AnimationHelper.createRotationalVector(-0.1376F, -3.7843F, 4.162F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.5833F, AnimationHelper.createRotationalVector(-0.2834F, -5.4201F, 5.9824F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.625F, AnimationHelper.createRotationalVector(-0.3331F, -5.6024F, 6.7993F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-0.1592F, -3.4416F, 5.2945F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.7083F, AnimationHelper.createRotationalVector(-0.0266F, -1.0619F, 2.8665F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.75F, AnimationHelper.createRotationalVector(-0.0008F, -0.0632F, 1.4845F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.8333F, AnimationHelper.createRotationalVector(0.0095F, 0.8392F, 1.2993F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.875F, AnimationHelper.createRotationalVector(0.0012F, 0.3456F, 0.4002F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.9583F, AnimationHelper.createRotationalVector(0.0992F, -3.3001F, -3.4439F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.1159F, -3.3958F, -3.9081F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.0417F, AnimationHelper.createRotationalVector(0.0931F, -2.835F, -3.7603F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.0833F, AnimationHelper.createRotationalVector(0.057F, -1.9858F, -3.2897F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.125F, AnimationHelper.createRotationalVector(0.0249F, -1.1258F, -2.5378F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.1667F, AnimationHelper.createRotationalVector(0.0069F, -0.4746F, -1.6552F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.2083F, AnimationHelper.createRotationalVector(0.0007F, -0.105F, -0.7661F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.25F, AnimationHelper.createRotationalVector(0.0F, -0.0015F, 0.2281F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.2917F, AnimationHelper.createRotationalVector(-0.003F, -0.2278F, 1.5316F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.3333F, AnimationHelper.createRotationalVector(-0.0191F, -0.8037F, 2.7254F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.375F, AnimationHelper.createRotationalVector(-0.0468F, -1.5457F, 3.4687F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.4167F, AnimationHelper.createRotationalVector(-0.0731F, -2.2334F, 3.7486F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.4583F, AnimationHelper.createRotationalVector(-0.0908F, -2.7586F, 3.7684F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.5F, AnimationHelper.createRotationalVector(-0.1028F, -3.1267F, 3.7638F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.5417F, AnimationHelper.createRotationalVector(-0.1376F, -3.7843F, 4.162F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.5833F, AnimationHelper.createRotationalVector(-0.2834F, -5.4201F, 5.9824F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.625F, AnimationHelper.createRotationalVector(-0.3331F, -5.6024F, 6.7993F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.6667F, AnimationHelper.createRotationalVector(-0.1592F, -3.4416F, 5.2945F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.7083F, AnimationHelper.createRotationalVector(-0.0266F, -1.0619F, 2.8665F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.75F, AnimationHelper.createRotationalVector(-0.0008F, -0.0632F, 1.4845F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.8333F, AnimationHelper.createRotationalVector(0.0095F, 0.8392F, 1.2993F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.875F, AnimationHelper.createRotationalVector(0.0012F, 0.3456F, 0.4002F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.9583F, AnimationHelper.createRotationalVector(0.0992F, -3.3001F, -3.4439F), Transformation.Interpolations.LINEAR)
                        ))
                        .addBoneAnimation("BackLeftLegLower", new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.createRotationalVector(-3.4718F, -31.453F, 12.2859F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0417F, AnimationHelper.createRotationalVector(-2.1004F, -29.807F, 7.8804F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0833F, AnimationHelper.createRotationalVector(-0.5287F, -26.4278F, 2.2516F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.125F, AnimationHelper.createRotationalVector(0.6789F, -21.3289F, -3.604F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.1667F, AnimationHelper.createRotationalVector(1.1478F, -14.8184F, -8.8096F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.2083F, AnimationHelper.createRotationalVector(0.8391F, -7.4218F, -12.8837F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.25F, AnimationHelper.createRotationalVector(-0.0563F, 0.4062F, -15.7934F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.2917F, AnimationHelper.createRotationalVector(-1.3234F, 8.4666F, -17.7369F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-2.7177F, 16.4858F, -18.5986F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.375F, AnimationHelper.createRotationalVector(-3.9264F, 24.0104F, -18.3139F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.4167F, AnimationHelper.createRotationalVector(-4.6692F, 30.5224F, -16.9968F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.4583F, AnimationHelper.createRotationalVector(-4.8125F, 35.5214F, -14.9481F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.5F, AnimationHelper.createRotationalVector(-4.4271F, 38.5474F, -12.6156F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.5417F, AnimationHelper.createRotationalVector(-2.6821F, 39.9897F, -7.3622F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.5833F, AnimationHelper.createRotationalVector(-0.3374F, 38.5339F, -0.9652F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.625F, AnimationHelper.createRotationalVector(2.5025F, 32.5604F, 8.5543F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.6667F, AnimationHelper.createRotationalVector(3.849F, 21.4243F, 20.1442F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.7083F, AnimationHelper.createRotationalVector(2.4887F, 9.3893F, 29.6319F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.75F, AnimationHelper.createRotationalVector(0.3088F, 1.0107F, 33.9806F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.7917F, AnimationHelper.createRotationalVector(-1.9376F, -6.5307F, 33.0189F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-4.0712F, -15.578F, 29.1316F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.875F, AnimationHelper.createRotationalVector(-5.1998F, -23.5489F, 24.5786F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.9167F, AnimationHelper.createRotationalVector(-5.3825F, -28.6441F, 20.8643F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.9583F, AnimationHelper.createRotationalVector(-4.8929F, -31.0832F, 17.4678F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.0F, AnimationHelper.createRotationalVector(-3.4718F, -31.453F, 12.2859F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.0417F, AnimationHelper.createRotationalVector(-2.1004F, -29.807F, 7.8804F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.0833F, AnimationHelper.createRotationalVector(-0.5287F, -26.4278F, 2.2516F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.125F, AnimationHelper.createRotationalVector(0.6789F, -21.3289F, -3.604F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.1667F, AnimationHelper.createRotationalVector(1.1478F, -14.8184F, -8.8096F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.2083F, AnimationHelper.createRotationalVector(0.8391F, -7.4218F, -12.8837F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.25F, AnimationHelper.createRotationalVector(-0.0563F, 0.4062F, -15.7934F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.2917F, AnimationHelper.createRotationalVector(-1.3234F, 8.4666F, -17.7369F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.3333F, AnimationHelper.createRotationalVector(-2.7177F, 16.4858F, -18.5986F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.375F, AnimationHelper.createRotationalVector(-3.9264F, 24.0104F, -18.3139F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.4167F, AnimationHelper.createRotationalVector(-4.6692F, 30.5224F, -16.9968F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.4583F, AnimationHelper.createRotationalVector(-4.8125F, 35.5214F, -14.9481F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.5F, AnimationHelper.createRotationalVector(-4.4271F, 38.5474F, -12.6156F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.5417F, AnimationHelper.createRotationalVector(-2.6821F, 39.9897F, -7.3622F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.5833F, AnimationHelper.createRotationalVector(-0.3374F, 38.5339F, -0.9652F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.625F, AnimationHelper.createRotationalVector(2.5025F, 32.5604F, 8.5543F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.6667F, AnimationHelper.createRotationalVector(3.849F, 21.4243F, 20.1442F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.7083F, AnimationHelper.createRotationalVector(2.4887F, 9.3893F, 29.6319F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.75F, AnimationHelper.createRotationalVector(0.3088F, 1.0107F, 33.9806F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.7917F, AnimationHelper.createRotationalVector(-1.9376F, -6.5307F, 33.0189F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.8333F, AnimationHelper.createRotationalVector(-4.0712F, -15.578F, 29.1316F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.875F, AnimationHelper.createRotationalVector(-5.1998F, -23.5489F, 24.5786F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.9167F, AnimationHelper.createRotationalVector(-5.3825F, -28.6441F, 20.8643F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.9583F, AnimationHelper.createRotationalVector(-4.8929F, -31.0832F, 17.4678F), Transformation.Interpolations.LINEAR)
                        ))
                        .addBoneAnimation("Front Right IK", new Transformation(Transformation.Targets.TRANSLATE,
                                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(-9.0F, -9.0F, -11.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(0.25F, AnimationHelper.createTranslationalVector(-6.69F, -9.62F, -4.12F), Transformation.Interpolations.CUBIC),
                                new Keyframe(0.5F, AnimationHelper.createTranslationalVector(-5.0F, -9.0F, -3.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(0.75F, AnimationHelper.createTranslationalVector(-5.0F, -6.0F, -6.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(1.0F, AnimationHelper.createTranslationalVector(-9.0F, -9.0F, -11.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(1.5F, AnimationHelper.createTranslationalVector(-5.0F, -9.0F, -3.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(1.75F, AnimationHelper.createTranslationalVector(-5.0F, -9.0F, -3.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(2.0F, AnimationHelper.createTranslationalVector(-9.0F, -9.0F, -11.0F), Transformation.Interpolations.CUBIC)
                        ))
                        .addBoneAnimation("Back Right IK", new Transformation(Transformation.Targets.TRANSLATE,
                                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(-8.0F, -8.0F, 8.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(0.25F, AnimationHelper.createTranslationalVector(-1.5F, -5.0F, 4.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(0.5F, AnimationHelper.createTranslationalVector(-3.0F, -8.0F, -3.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(1.0F, AnimationHelper.createTranslationalVector(-8.0F, -8.0F, 8.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(1.25F, AnimationHelper.createTranslationalVector(-1.5F, -5.0F, 4.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(1.5F, AnimationHelper.createTranslationalVector(-3.0F, -8.0F, -3.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(2.0F, AnimationHelper.createTranslationalVector(-8.0F, -8.0F, 8.0F), Transformation.Interpolations.CUBIC)
                        ))
                        .addBoneAnimation("Front Left IK", new Transformation(Transformation.Targets.TRANSLATE,
                                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(5.0F, -9.0F, -3.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(0.25F, AnimationHelper.createTranslationalVector(5.0F, -6.0F, -6.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(0.5F, AnimationHelper.createTranslationalVector(9.0F, -9.0F, -11.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(1.0F, AnimationHelper.createTranslationalVector(5.0F, -9.0F, -3.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(1.25F, AnimationHelper.createTranslationalVector(5.0F, -6.0F, -6.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(1.5F, AnimationHelper.createTranslationalVector(9.0F, -9.0F, -11.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(2.0F, AnimationHelper.createTranslationalVector(5.0F, -9.0F, -3.0F), Transformation.Interpolations.CUBIC)
                        ))
                        .addBoneAnimation("Back Left IK", new Transformation(Transformation.Targets.TRANSLATE,
                                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(3.0F, -8.0F, -3.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(0.5F, AnimationHelper.createTranslationalVector(8.0F, -8.0F, 8.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(0.75F, AnimationHelper.createTranslationalVector(1.5F, -5.0F, 4.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(1.0F, AnimationHelper.createTranslationalVector(3.0F, -8.0F, -3.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(1.5F, AnimationHelper.createTranslationalVector(8.0F, -8.0F, 8.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(1.75F, AnimationHelper.createTranslationalVector(1.5F, -5.0F, 4.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(2.0F, AnimationHelper.createTranslationalVector(3.0F, -8.0F, -3.0F), Transformation.Interpolations.CUBIC)
                        ))
                        .build();
        }

        private static Animation jumping() {
                return Animation.Builder.create(0.2083F)
                        .addBoneAnimation("body", new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(0.0833F, AnimationHelper.createRotationalVector(7.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(0.2083F, AnimationHelper.createRotationalVector(-15.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                        ))
                        .addBoneAnimation("body", new Transformation(Transformation.Targets.TRANSLATE,
                                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(0.0833F, AnimationHelper.createTranslationalVector(0.0F, -4.0F, 2.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(0.2083F, AnimationHelper.createTranslationalVector(0.0F, 7.0F, -1.0F), Transformation.Interpolations.CUBIC)
                        ))
                        .addBoneAnimation("FrontRightLegUpper", new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0214F, -0.6068F, -4.0435F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0417F, AnimationHelper.createRotationalVector(0.6033F, -5.4041F, -12.7317F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0833F, AnimationHelper.createRotationalVector(0.5852F, -6.5259F, -10.2374F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.125F, AnimationHelper.createRotationalVector(1.3507F, 7.2526F, 21.0729F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.1667F, AnimationHelper.createRotationalVector(2.1501F, 3.9945F, 56.57F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.2083F, AnimationHelper.createRotationalVector(-2.9172F, -3.8953F, 73.6494F), Transformation.Interpolations.LINEAR)
                        ))
                        .addBoneAnimation("FrontRightLegLower", new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.createRotationalVector(-0.2469F, 10.3584F, -2.7237F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0417F, AnimationHelper.createRotationalVector(-0.0993F, 29.5297F, -0.3768F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0833F, AnimationHelper.createRotationalVector(0.0366F, 37.7556F, 0.1069F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.125F, AnimationHelper.createRotationalVector(-0.8916F, 6.275F, -16.1589F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.1667F, AnimationHelper.createRotationalVector(9.0538F, -22.6092F, -43.2133F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.2083F, AnimationHelper.createRotationalVector(19.847F, -30.9197F, -64.6326F), Transformation.Interpolations.LINEAR)
                        ))
                        .addBoneAnimation("BackRightLegUpper", new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0238F, -0.3337F, -8.1479F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0417F, AnimationHelper.createRotationalVector(2.0421F, -6.7703F, -33.5363F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0833F, AnimationHelper.createRotationalVector(4.3491F, -9.309F, -50.0074F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.125F, AnimationHelper.createRotationalVector(-0.3209F, 2.315F, -15.7814F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-0.7129F, -5.7682F, 14.0801F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.2083F, AnimationHelper.createRotationalVector(-9.7598F, -23.4407F, 44.7375F), Transformation.Interpolations.LINEAR)
                        ))
                        .addBoneAnimation("BackRightLegLower", new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0456F, 0.2872F, 18.0408F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0417F, AnimationHelper.createRotationalVector(1.3926F, 3.8163F, 40.0839F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0833F, AnimationHelper.createRotationalVector(-0.2508F, -0.535F, 50.2274F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.125F, AnimationHelper.createRotationalVector(-2.433F, -12.1248F, 22.6134F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.1667F, AnimationHelper.createRotationalVector(1.2478F, -12.9568F, -10.9555F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.2083F, AnimationHelper.createRotationalVector(-1.7093F, 3.74F, -49.111F), Transformation.Interpolations.LINEAR)
                        ))
                        .addBoneAnimation("FrontLeftLegUpper", new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0082F, 0.3665F, 2.5764F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0417F, AnimationHelper.createRotationalVector(0.4876F, 4.7847F, 11.6304F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0833F, AnimationHelper.createRotationalVector(0.4606F, 5.7152F, 9.207F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.125F, AnimationHelper.createRotationalVector(1.5345F, -7.6164F, -22.7513F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.1667F, AnimationHelper.createRotationalVector(2.1574F, -3.849F, -58.5311F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.2083F, AnimationHelper.createRotationalVector(-3.8369F, 4.2642F, -83.9555F), Transformation.Interpolations.LINEAR)
                        ))
                        .addBoneAnimation("FrontLeftLegLower", new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.createRotationalVector(-0.225F, -9.1617F, 2.8083F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0417F, AnimationHelper.createRotationalVector(-0.0335F, -28.293F, 0.1329F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0833F, AnimationHelper.createRotationalVector(0.0813F, -36.4711F, -0.2469F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.125F, AnimationHelper.createRotationalVector(-0.7293F, -4.9774F, 16.6621F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.1667F, AnimationHelper.createRotationalVector(9.8554F, 23.4801F, 45.0641F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.2083F, AnimationHelper.createRotationalVector(24.5478F, 28.8178F, 80.515F), Transformation.Interpolations.LINEAR)
                        ))
                        .addBoneAnimation("BackLeftLegUpper", new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0168F, 0.2077F, 9.2548F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0417F, AnimationHelper.createRotationalVector(2.3611F, 6.7418F, 38.5654F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0833F, AnimationHelper.createRotationalVector(4.7718F, 8.7566F, 57.1091F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.125F, AnimationHelper.createRotationalVector(-0.441F, -2.8901F, 17.3475F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-0.918F, 6.643F, -15.7185F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.2083F, AnimationHelper.createRotationalVector(-10.5305F, 24.3978F, -46.1747F), Transformation.Interpolations.LINEAR)
                        ))
                        .addBoneAnimation("BackLeftLegLower", new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.createRotationalVector(-0.1612F, 0.9927F, -18.4407F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0417F, AnimationHelper.createRotationalVector(0.4439F, -1.119F, -43.2792F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0833F, AnimationHelper.createRotationalVector(-2.102F, 4.0764F, -54.5409F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.125F, AnimationHelper.createRotationalVector(-2.8499F, 13.6701F, -23.448F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.1667F, AnimationHelper.createRotationalVector(1.4461F, 12.5302F, 13.1157F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.2083F, AnimationHelper.createRotationalVector(-1.7862F, -3.9342F, 48.8257F), Transformation.Interpolations.LINEAR)
                        ))
                        .addBoneAnimation("Front Right IK", new Transformation(Transformation.Targets.TRANSLATE,
                                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(-7.25F, -9.19F, -7.31F), Transformation.Interpolations.CUBIC),
                                new Keyframe(0.125F, AnimationHelper.createTranslationalVector(-10.25F, -14.19F, -7.31F), Transformation.Interpolations.CUBIC)
                        ))
                        .addBoneAnimation("Back Right IK", new Transformation(Transformation.Targets.TRANSLATE,
                                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(-6.0F, -8.37F, 2.31F), Transformation.Interpolations.CUBIC),
                                new Keyframe(0.125F, AnimationHelper.createTranslationalVector(-6.0F, -8.37F, 5.31F), Transformation.Interpolations.CUBIC)
                        ))
                        .addBoneAnimation("Front Left IK", new Transformation(Transformation.Targets.TRANSLATE,
                                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(7.25F, -9.37F, -7.12F), Transformation.Interpolations.CUBIC),
                                new Keyframe(0.125F, AnimationHelper.createTranslationalVector(10.25F, -14.37F, -7.12F), Transformation.Interpolations.CUBIC)
                        ))
                        .addBoneAnimation("Back Left IK", new Transformation(Transformation.Targets.TRANSLATE,
                                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(6.0F, -8.37F, 2.31F), Transformation.Interpolations.CUBIC),
                                new Keyframe(0.125F, AnimationHelper.createTranslationalVector(6.0F, -8.37F, 5.31F), Transformation.Interpolations.CUBIC)
                        ))
                        .build();
        }

        private static Animation landing() {
                return Animation.Builder.create(0.1667F)
                        .addBoneAnimation("body", new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.createRotationalVector(-15.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(0.0833F, AnimationHelper.createRotationalVector(7.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(0.1667F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                        ))
                        .addBoneAnimation("body", new Transformation(Transformation.Targets.TRANSLATE,
                                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 7.0F, -1.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(0.0833F, AnimationHelper.createTranslationalVector(0.0F, -4.0F, 2.0F), Transformation.Interpolations.CUBIC),
                                new Keyframe(0.1667F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                        ))
                        .addBoneAnimation("FrontRightLegUpper", new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.createRotationalVector(-2.9172F, -3.8953F, 73.6494F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0417F, AnimationHelper.createRotationalVector(2.8609F, 7.6818F, 40.8044F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0833F, AnimationHelper.createRotationalVector(0.5852F, -6.5259F, -10.2374F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.125F, AnimationHelper.createRotationalVector(0.6033F, -5.4041F, -12.7317F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.1667F, AnimationHelper.createRotationalVector(0.0214F, -0.6068F, -4.0435F), Transformation.Interpolations.LINEAR)
                        ))
                        .addBoneAnimation("FrontRightLegLower", new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.createRotationalVector(19.847F, -30.9197F, -64.6326F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0417F, AnimationHelper.createRotationalVector(2.8893F, -11.0114F, -29.3239F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0833F, AnimationHelper.createRotationalVector(0.0366F, 37.7556F, 0.1069F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.125F, AnimationHelper.createRotationalVector(-0.0993F, 29.5297F, -0.3768F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-0.2469F, 10.3584F, -2.7237F), Transformation.Interpolations.LINEAR)
                        ))
                        .addBoneAnimation("BackRightLegUpper", new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.createRotationalVector(-9.7598F, -23.4407F, 44.7375F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0417F, AnimationHelper.createRotationalVector(-0.0157F, -0.7116F, 2.5201F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0833F, AnimationHelper.createRotationalVector(4.3491F, -9.309F, -50.0074F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.125F, AnimationHelper.createRotationalVector(2.0421F, -6.7703F, -33.5363F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.1667F, AnimationHelper.createRotationalVector(0.0238F, -0.3337F, -8.1479F), Transformation.Interpolations.LINEAR)
                        ))
                        .addBoneAnimation("BackRightLegLower", new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.createRotationalVector(-1.7093F, 3.74F, -49.111F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0417F, AnimationHelper.createRotationalVector(-0.3503F, -12.9007F, 3.0974F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0833F, AnimationHelper.createRotationalVector(-0.2508F, -0.535F, 50.2274F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.125F, AnimationHelper.createRotationalVector(1.3926F, 3.8163F, 40.0839F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.1667F, AnimationHelper.createRotationalVector(0.0456F, 0.2872F, 18.0408F), Transformation.Interpolations.LINEAR)
                        ))
                        .addBoneAnimation("FrontLeftLegUpper", new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.createRotationalVector(-3.8369F, 4.2642F, -83.9555F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0417F, AnimationHelper.createRotationalVector(3.0371F, -7.749F, -42.7536F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0833F, AnimationHelper.createRotationalVector(0.4606F, 5.7152F, 9.207F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.125F, AnimationHelper.createRotationalVector(0.4876F, 4.7847F, 11.6304F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.1667F, AnimationHelper.createRotationalVector(0.0082F, 0.3665F, 2.5764F), Transformation.Interpolations.LINEAR)
                        ))
                        .addBoneAnimation("FrontLeftLegLower", new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.createRotationalVector(24.5478F, 28.8178F, 80.515F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0417F, AnimationHelper.createRotationalVector(3.3529F, 12.233F, 30.5524F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0833F, AnimationHelper.createRotationalVector(0.0813F, -36.4711F, -0.2469F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.125F, AnimationHelper.createRotationalVector(-0.0335F, -28.293F, 0.1329F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-0.225F, -9.1617F, 2.8083F), Transformation.Interpolations.LINEAR)
                        ))
                        .addBoneAnimation("BackLeftLegUpper", new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.createRotationalVector(-10.5305F, 24.3978F, -46.1747F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0417F, AnimationHelper.createRotationalVector(-0.028F, 0.9803F, -3.2756F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0833F, AnimationHelper.createRotationalVector(4.7718F, 8.7566F, 57.1091F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.125F, AnimationHelper.createRotationalVector(2.3611F, 6.7418F, 38.5654F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.1667F, AnimationHelper.createRotationalVector(0.0168F, 0.2077F, 9.2548F), Transformation.Interpolations.LINEAR)
                        ))
                        .addBoneAnimation("BackLeftLegLower", new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.createRotationalVector(-1.7862F, -3.9342F, 48.8257F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0417F, AnimationHelper.createRotationalVector(-0.1898F, 13.2866F, -1.6294F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.0833F, AnimationHelper.createRotationalVector(-2.102F, 4.0764F, -54.5409F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.125F, AnimationHelper.createRotationalVector(0.4439F, -1.119F, -43.2792F), Transformation.Interpolations.LINEAR),
                                new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-0.1612F, 0.9927F, -18.4407F), Transformation.Interpolations.LINEAR)
                        ))
                        .addBoneAnimation("Front Right IK", new Transformation(Transformation.Targets.TRANSLATE,
                                new Keyframe(0.0417F, AnimationHelper.createTranslationalVector(-10.25F, -14.19F, -7.31F), Transformation.Interpolations.CUBIC),
                                new Keyframe(0.1667F, AnimationHelper.createTranslationalVector(-7.25F, -9.19F, -7.31F), Transformation.Interpolations.CUBIC)
                        ))
                        .addBoneAnimation("Back Right IK", new Transformation(Transformation.Targets.TRANSLATE,
                                new Keyframe(0.0417F, AnimationHelper.createTranslationalVector(-6.0F, -8.37F, 5.31F), Transformation.Interpolations.CUBIC),
                                new Keyframe(0.1667F, AnimationHelper.createTranslationalVector(-6.0F, -8.37F, 2.31F), Transformation.Interpolations.CUBIC)
                        ))
                        .addBoneAnimation("Front Left IK", new Transformation(Transformation.Targets.TRANSLATE,
                                new Keyframe(0.0417F, AnimationHelper.createTranslationalVector(10.25F, -14.37F, -7.12F), Transformation.Interpolations.CUBIC),
                                new Keyframe(0.1667F, AnimationHelper.createTranslationalVector(7.25F, -9.37F, -7.12F), Transformation.Interpolations.CUBIC)
                        ))
                        .addBoneAnimation("Back Left IK", new Transformation(Transformation.Targets.TRANSLATE,
                                new Keyframe(0.0417F, AnimationHelper.createTranslationalVector(6.0F, -8.37F, 5.31F), Transformation.Interpolations.CUBIC),
                                new Keyframe(0.1667F, AnimationHelper.createTranslationalVector(6.0F, -8.37F, 2.31F), Transformation.Interpolations.CUBIC)
                        ))
                        .build();
        }

        private static Animation water_idle() {
                return Animation.Builder.create(4.0F).looping()
                        .addBoneAnimation("body", new Transformation(Transformation.Targets.TRANSLATE,
                                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 2.72F, 0.0F), Transformation.Interpolations.LINEAR),
                                new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 3.22F, 0.0F), Transformation.Interpolations.LINEAR),
                                new Keyframe(4.0F, AnimationHelper.createTranslationalVector(0.0F, 2.72F, 0.0F), Transformation.Interpolations.LINEAR)
                        ))
                        .addBoneAnimation("FrontRightLegUpper", new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.createRotationalVector(-0.1736F, 0.9891F, -19.913F), Transformation.Interpolations.LINEAR),
                                new Keyframe(2.0F, AnimationHelper.createRotationalVector(-0.1284F, 0.7688F, -18.9622F), Transformation.Interpolations.LINEAR),
                                new Keyframe(4.0F, AnimationHelper.createRotationalVector(-0.1736F, 0.9891F, -19.913F), Transformation.Interpolations.LINEAR)
                        ))
                        .addBoneAnimation("FrontRightLegLower", new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.createRotationalVector(-0.4804F, 6.4974F, -8.4485F), Transformation.Interpolations.LINEAR),
                                new Keyframe(2.0F, AnimationHelper.createRotationalVector(-0.4517F, 6.6659F, -7.7451F), Transformation.Interpolations.LINEAR),
                                new Keyframe(4.0F, AnimationHelper.createRotationalVector(-0.4804F, 6.4974F, -8.4485F), Transformation.Interpolations.LINEAR)
                        ))
                        .addBoneAnimation("BackRightLegUpper", new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.createRotationalVector(1.2077F, -7.7364F, -17.7196F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.5F, AnimationHelper.createRotationalVector(1.1205F, -7.0382F, -18.0692F), Transformation.Interpolations.LINEAR),
                                new Keyframe(2.0F, AnimationHelper.createRotationalVector(1.0897F, -6.8196F, -18.1363F), Transformation.Interpolations.LINEAR),
                                new Keyframe(3.5F, AnimationHelper.createRotationalVector(1.1798F, -7.4966F, -17.8629F), Transformation.Interpolations.LINEAR),
                                new Keyframe(4.0F, AnimationHelper.createRotationalVector(1.2077F, -7.7364F, -17.7196F), Transformation.Interpolations.LINEAR)
                        ))
                        .addBoneAnimation("BackRightLegLower", new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.createRotationalVector(-0.8181F, 15.8053F, -5.8887F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.1667F, AnimationHelper.createRotationalVector(-0.5321F, 14.5668F, -4.1616F), Transformation.Interpolations.LINEAR),
                                new Keyframe(2.0F, AnimationHelper.createRotationalVector(-0.3705F, 13.7627F, -3.0695F), Transformation.Interpolations.LINEAR),
                                new Keyframe(3.3333F, AnimationHelper.createRotationalVector(-0.6452F, 15.0809F, -4.8713F), Transformation.Interpolations.LINEAR),
                                new Keyframe(4.0F, AnimationHelper.createRotationalVector(-0.8181F, 15.8053F, -5.8887F), Transformation.Interpolations.LINEAR)
                        ))
                        .addBoneAnimation("FrontLeftLegUpper", new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.createRotationalVector(-0.1338F, -0.7685F, 19.7471F), Transformation.Interpolations.LINEAR),
                                new Keyframe(2.0F, AnimationHelper.createRotationalVector(-0.0945F, -0.5714F, 18.7764F), Transformation.Interpolations.LINEAR),
                                new Keyframe(4.0F, AnimationHelper.createRotationalVector(-0.1338F, -0.7685F, 19.7471F), Transformation.Interpolations.LINEAR)
                        ))
                        .addBoneAnimation("FrontLeftLegLower", new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.createRotationalVector(-0.5088F, -7.0536F, 8.2415F), Transformation.Interpolations.LINEAR),
                                new Keyframe(2.0F, AnimationHelper.createRotationalVector(-0.4738F, -7.1736F, 7.5479F), Transformation.Interpolations.LINEAR),
                                new Keyframe(4.0F, AnimationHelper.createRotationalVector(-0.5088F, -7.0536F, 8.2415F), Transformation.Interpolations.LINEAR)
                        ))
                        .addBoneAnimation("BackLeftLegUpper", new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.createRotationalVector(1.4156F, 7.8584F, 20.3925F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.5F, AnimationHelper.createRotationalVector(1.3003F, 7.114F, 20.6907F), Transformation.Interpolations.LINEAR),
                                new Keyframe(2.0F, AnimationHelper.createRotationalVector(1.2608F, 6.883F, 20.7362F), Transformation.Interpolations.LINEAR),
                                new Keyframe(3.5F, AnimationHelper.createRotationalVector(1.378F, 7.6015F, 20.5213F), Transformation.Interpolations.LINEAR),
                                new Keyframe(4.0F, AnimationHelper.createRotationalVector(1.4156F, 7.8584F, 20.3925F), Transformation.Interpolations.LINEAR)
                        ))
                        .addBoneAnimation("BackLeftLegLower", new Transformation(Transformation.Targets.ROTATE,
                                new Keyframe(0.0F, AnimationHelper.createRotationalVector(-0.4704F, -13.85F, 3.8716F), Transformation.Interpolations.LINEAR),
                                new Keyframe(1.1667F, AnimationHelper.createRotationalVector(-0.2468F, -12.6357F, 2.2285F), Transformation.Interpolations.LINEAR),
                                new Keyframe(2.0F, AnimationHelper.createRotationalVector(-0.1242F, -11.8555F, 1.1959F), Transformation.Interpolations.LINEAR),
                                new Keyframe(3.3333F, AnimationHelper.createRotationalVector(-0.3343F, -13.138F, 2.9022F), Transformation.Interpolations.LINEAR),
                                new Keyframe(4.0F, AnimationHelper.createRotationalVector(-0.4704F, -13.85F, 3.8716F), Transformation.Interpolations.LINEAR)
                        ))
                        .addBoneAnimation("Front Right IK", new Transformation(Transformation.Targets.TRANSLATE,
                                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(-4.25F, -2.19F, -7.31F), Transformation.Interpolations.CUBIC)
                        ))
                        .addBoneAnimation("Back Right IK", new Transformation(Transformation.Targets.TRANSLATE,
                                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(-2.0F, -0.37F, 2.31F), Transformation.Interpolations.CUBIC)
                        ))
                        .addBoneAnimation("Front Left IK", new Transformation(Transformation.Targets.TRANSLATE,
                                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(4.25F, -2.19F, -7.31F), Transformation.Interpolations.CUBIC)
                        ))
                        .addBoneAnimation("Back Left IK", new Transformation(Transformation.Targets.TRANSLATE,
                                new Keyframe(0.0F, AnimationHelper.createTranslationalVector(2.0F, -0.37F, 2.31F), Transformation.Interpolations.CUBIC)
                        ))
                        .build();
        }
}
