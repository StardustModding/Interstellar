package org.stardustmodding.interstellar.api.client.gui

import dev.architectury.registry.menu.MenuRegistry
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.MenuType

open class SharedScreen<H : AbstractContainerMenu>(
    handler: H,
    title: Component,
    private val screenWidth: Int,
    private val screenHeight: Int
) : AbstractContainerScreen<H>(handler, Inventory(null), title) {
    private fun renderBg(ctx: GuiGraphics) {
        val windowWidth = ctx.guiWidth()
        val windowHeight = ctx.guiHeight()
        val startX = (windowWidth / 2) - screenWidth / 2
        val startY = (windowHeight / 2) - screenHeight / 2

        ctx.fill(startX, startY, screenWidth, screenHeight, BG_COLOR)
    }

    private fun renderTitle(ctx: GuiGraphics) {
        val client = Minecraft.getInstance()
        val windowWidth = ctx.guiWidth()
        val windowHeight = ctx.guiHeight()
        val startX = (windowWidth / 2) - screenWidth / 2
        val startY = (windowHeight / 2) - screenHeight / 2
        val textHeight = client.font.lineHeight
        val margin = textHeight / 2

        ctx.drawString(client.font, title, startX + margin, startY + margin, TEXT_COLOR, false)
        ctx.fill(startX + LINE_PADDING, startY + textHeight * 2, startX + screenWidth - LINE_PADDING, 1, BAR_COLOR)
    }

    override fun render(ctx: GuiGraphics, mouseX: Int, mouseY: Int, delta: Float) {
        super.render(ctx, mouseX, mouseY, delta)

        renderBg(ctx)
        renderTitle(ctx)
    }

    override fun renderBg(ctx: GuiGraphics, delta: Float, mouseX: Int, mouseY: Int) {
        ctx.fill(0, 0, ctx.guiWidth(), ctx.guiHeight(), BG_SHADOW_COLOR)
    }

    companion object {
        const val BG_COLOR = 0x383B52
        const val TEXT_COLOR = 0xFFFFFF
        const val BAR_COLOR = 0xFFFFFF
        const val BG_SHADOW_COLOR = 0x000000
        const val LINE_PADDING = 2

        @JvmStatic
        fun open(player: ServerPlayer, handler: MenuType<*>) {
            MenuRegistry.openMenu(player, object : MenuProvider {
                override fun createMenu(
                    syncId: Int,
                    playerInventory: Inventory,
                    player: Player
                ): AbstractContainerMenu {
                    return handler.create(syncId, playerInventory)
                }

                override fun getDisplayName(): Component {
                    val reg = BuiltInRegistries.MENU
                    val key = reg.getResourceKey(handler).get().location()
                    val id = "screen.${key.namespace}.${key.path}"

                    return Component.translatable(id)
                }
            })
        }
    }
}