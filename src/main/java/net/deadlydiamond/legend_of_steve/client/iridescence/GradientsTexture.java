package net.deadlydiamond.legend_of_steve.client.iridescence;

import net.deadlydiamond.legend_of_steve.LegendOfSteve;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;

// CREDITS TO Phanastrae
// THIS CLASS IS FROM OPERATION STARCLEAVE, WHICH IS LICENSED UNDER MIT
// https://github.com/Phanastrae/operation_starcleave

public class GradientsTexture implements AutoCloseable {
    public static final Identifier LOCATION = LegendOfSteve.id("gradients_texture");
    public static final double TAU = 2 * Math.PI;
    private static final int WIDTH = 128;
    private static final int GRADIENTS = 3;

    private final NativeImageBackedTexture texture;
    private final NativeImage pixels;
    private final Identifier textureLocation;

    // TODO: Might just stop depending on this and move to just a texture later

    public GradientsTexture(MinecraftClient minecraft) {
        this.texture = new NativeImageBackedTexture(WIDTH, GRADIENTS, false);

        this.textureLocation = LOCATION;
        minecraft.getTextureManager().registerTexture(this.textureLocation, this.texture);

        this.pixels = this.texture.getImage();

        updateTexture();
    }

    @Override
    public void close() {
        this.texture.close();
    }

    public void updateTexture() {
        for (int j = 0; j < GRADIENTS; j++) {
            for (int i = 0; i < WIDTH; i++) {
                float progress = i / (float) WIDTH;

                int color;
                if (j == 1) {
                    color = getBismuthIridescenceColorABGR(progress);
                } else {
                    // black and magenta missing gradient
                    color = (((i / 4) & 0x1) == 0) ? 0x000000FF : 0xFF00FFFF;
                }

                this.pixels.setColor(i, j, color);
            }
        }

        this.texture.upload();
    }

    private static int getBismuthIridescenceColorABGR(float progress) {
        double dot = getDotFromProgress(progress);
        double colorAngle = TAU * -2. * (1. + (dot < 0 ? dot : dot * -0.125));

        double r = wave(colorAngle, 0.0, 0.2, 1.0);
        double g = wave(colorAngle, 1.0 / 3.0, 0.2, 1.0);
        double b = wave(colorAngle, -1.0 / 3.0, 0.2, 1.0);

        double a = (1. + Math.min(0., dot)); // dot = 1 => a = 0, dot <= 0 => a = 1

        return packABGR(a, b, g, r);
    }

    private static int getOpalIridescenceColorABGR(float progress) {
        double dot = getDotFromProgress(progress);
        double colorAngle = TAU * -2. * (1. + (dot < 0 ? dot : dot * -0.125));

        double a = (1. + Math.min(0., dot)); // dot = 1 => a = 0, dot <= 0 => a = 1

        double r = wave(colorAngle, 0.0, 0.65, 1.0) * (0.65 + 0.35 * a);
        double g = wave(colorAngle, 4.0 / 9.0, 0.65, 1.0) * (0.65 + 0.35 * a);
        double b = wave(colorAngle, -1.0 / 9.0, 0.65, 1.0) * (1.0 - 0.4 * a);

        a = Math.sqrt(a);

        return packABGR(a, b, g, r);
    }

    private static double getDotFromProgress(float progress) {
        return 2 * progress - 1; // progress = 0 => dot = -1, progress = 1 => dot = +1
    }

    private static double wave(double colorAngle, double periodOffset, double min, double max) {
        double average = (max + min) * 0.5;
        double maxDifFromAverage = (max - min) * 0.5;
        return Math.sin(colorAngle + TAU * periodOffset) * maxDifFromAverage + average;
    }

    private static int packABGR(double a, double b, double g, double r) {
        return ColorHelper.Argb.getArgb(
                (int) (255 * a),
                (int) (255 * b),
                (int) (255 * g),
                (int) (255 * r)
        );
    }
}