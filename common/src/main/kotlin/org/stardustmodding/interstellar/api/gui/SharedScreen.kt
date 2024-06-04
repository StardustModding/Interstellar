package org.stardustmodding.interstellar.api.gui

import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text

open class SharedScreen(title: Text, private val screenWidth: Int, private val screenHeight: Int) : Screen(title) {
    private fun renderBg(ctx: DrawContext) {
        val windowWidth = ctx.scaledWindowWidth
        val windowHeight = ctx.scaledWindowHeight
        val startX = (windowWidth / 2) - screenWidth / 2
        val startY = (windowHeight / 2) - screenHeight / 2

        ctx.fill(startX, startY, screenWidth, screenHeight, BG_COLOR)
    }

    private fun renderTitle(ctx: DrawContext) {
        val client = MinecraftClient.getInstance()
        val windowWidth = ctx.scaledWindowWidth
        val windowHeight = ctx.scaledWindowHeight
        val startX = (windowWidth / 2) - screenWidth / 2
        val startY = (windowHeight / 2) - screenHeight / 2
        val textHeight = client.textRenderer.fontHeight
        val margin = textHeight / 2

        ctx.drawText(client.textRenderer, title, startX + margin, startY + margin, TEXT_COLOR, false)
        ctx.fill(startX + LINE_PADDING, startY + textHeight * 2, startX + screenWidth - LINE_PADDING, 1, BAR_COLOR)
    }

    override fun render(ctx: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        super.render(ctx, mouseX, mouseY, delta)

        renderBg(ctx)
        renderTitle(ctx)
    }

    companion object {
        const val BG_COLOR = 0x383B52
        const val TEXT_COLOR = 0xFFFFFF
        const val BAR_COLOR = 0xFFFFFF
        const val LINE_PADDING = 2
    }
}