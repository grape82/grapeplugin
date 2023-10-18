package io.github.grape82.grapeplugin

import io.github.grape82.grapeplugin.Setting
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.block.CommandBlock
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.plugin.java.JavaPlugin
class Grapeplugin : JavaPlugin(), Listener {

    val getname = Setting()
    val resultplayername = getname.getPrivateplayer()

    val getdistance = Setting()
    val resultdistance = getdistance.getdistance()

    override fun onEnable() {
        logger.info("플러그인이 활성화 돼었습니다!")
//        server.pluginManager.registerEvents(this, this)
        server.pluginManager.registerEvents(this, this)
        CommandViewer()
    }

    override fun onDisable() {
        logger.info("플러그인이 비활성화 돼었습니다!")
    }

    private fun CommandViewer(){
        server.scheduler.scheduleSyncRepeatingTask(this, {
            server.onlinePlayers.forEach { p ->
                if (p.name == resultplayername) {
                    val block = p.getTargetBlock(null, resultdistance)
                    if (block.type in listOf(Material.COMMAND_BLOCK)) { p.sendActionBar("${ChatColor.GOLD}${(block.state as CommandBlock).command}") }
                    if (block.type in listOf(Material.CHAIN_COMMAND_BLOCK)) { p.sendActionBar("${ChatColor.AQUA}${(block.state as CommandBlock).command}") }
                    if (block.type in listOf(Material.REPEATING_COMMAND_BLOCK)) { p.sendActionBar("${ChatColor.GREEN}${(block.state as CommandBlock).command}") }
                }
            }
        }, 0L, 1L)
    }


    @EventHandler
    fun onChat(event: AsyncPlayerChatEvent) {
        event.format = "${ChatColor.AQUA}${event.player.name}${ChatColor.GRAY}: ${ChatColor.WHITE}${event.message}"
    }

}
