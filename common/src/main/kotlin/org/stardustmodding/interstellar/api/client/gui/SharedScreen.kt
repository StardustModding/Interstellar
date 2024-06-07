package org.stardustmodding.interstellar.api.client.gui

import dev.architectury.registry.menu.MenuRegistry
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.registry.Registries
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text

open class SharedScreen<H : ScreenHandler>(
    handler: H,
    title: Text,
    private val screenWidth: Int,
    private val screenHeight: Int
) : HandledScreen<H>(handler, PlayerInventory(null), title) {
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

    override fun drawBackground(ctx: DrawContext, delta: Float, mouseX: Int, mouseY: Int) {
        ctx.fill(0, 0, ctx.scaledWindowWidth, ctx.scaledWindowHeight, BG_SHADOW_COLOR)
    }

    companion object {
        const val BG_COLOR = 0x383B52
        const val TEXT_COLOR = 0xFFFFFF
        const val BAR_COLOR = 0xFFFFFF
        const val BG_SHADOW_COLOR = 0x000000
        const val LINE_PADDING = 2

        @JvmStatic
        fun open(player: ServerPlayerEntity, handler: ScreenHandlerType<*>) {
            MenuRegistry.openMenu(player, object : NamedScreenHandlerFactory {
                override fun createMenu(
                    syncId: Int,
                    playerInventory: PlayerInventory,
                    player: PlayerEntity
                ): ScreenHandler {
                    return handler.create(syncId, playerInventory)
                }

                override fun getDisplayName(): Text {
                    val reg = Registries.SCREEN_HANDLER
                    val key = reg.getKey(handler).get().value
                    val id = "screen.${key.namespace}.${key.path}"

                    return Text.translatable(id)
                }
            })
        }
    }
}