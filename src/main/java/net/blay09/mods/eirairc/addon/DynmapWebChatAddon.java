package net.blay09.mods.eirairc.addon;

import net.blay09.mods.eirairc.api.EiraIRCAPI;
import net.blay09.mods.eirairc.api.event.IRCChannelChatEvent;
import net.blay09.mods.eirairc.api.event.IRCUserJoinEvent;
import net.blay09.mods.eirairc.api.event.IRCUserLeaveEvent;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.dynmap.DynmapCommonAPI;
import org.dynmap.DynmapCommonAPIListener;

@Optional.Interface(iface = "org.dynmap.DynmapCommonAPIListener", modid = "Dynmap")
@SuppressWarnings("unused")
public class DynmapWebChatAddon extends DynmapCommonAPIListener {

	public static class WebChatSender implements ICommandSender {
		public final String source;
		public final String name;

		public WebChatSender(String source, String name) {
			this.source = source;
			this.name = name;
		}

		@Override
		public String getName() {
			return "[" + source + "]" + ((name != null && !name.isEmpty()) ? " " + name : "");
		}

		@Override
		public IChatComponent getDisplayName() {
			return new ChatComponentText(getName());
		}

		@Override
		public void addChatMessage(IChatComponent p_145747_1_) {}

		@Override
		public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
			return false;
		}

		@Override
		public BlockPos getPosition() {
			return new BlockPos(0, 0, 0);
		}

		@Override
		public Vec3 getPositionVector() {
			return new Vec3(0, 0, 0);
		}

		@Override
		public World getEntityWorld() {
			return MinecraftServer.getServer().getEntityWorld();
		}

		@Override
		public Entity getCommandSenderEntity() {
			return null;
		}

		@Override
		public boolean sendCommandFeedback() {
			return false;
		}

		@Override
		public void setCommandStat(CommandResultStats.Type type, int amount) {}

	}

	private DynmapCommonAPI api;

	public DynmapWebChatAddon() {
		DynmapCommonAPIListener.register(this);
	}

	@Override
	@Optional.Method(modid = "Dynmap")
	public void apiEnabled(DynmapCommonAPI api) {
		this.api = api;
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	@Optional.Method(modid = "Dynmap")
	public void apiDisabled(DynmapCommonAPI api) {
		this.api = null;
		MinecraftForge.EVENT_BUS.unregister(this);
	}

	@SubscribeEvent
	@Optional.Method(modid = "Dynmap")
	public void onChannelChat(IRCChannelChatEvent event) {
		api.postPlayerMessageToWeb(event.sender.getName(), event.sender.getName(), event.message);
	}

	@SubscribeEvent
	@Optional.Method(modid = "Dynmap")
	public void onIRCUserJoin(IRCUserJoinEvent event) {
		api.postPlayerJoinQuitToWeb(event.user.getName(), event.user.getName(), true);
	}

	@SubscribeEvent
	@Optional.Method(modid = "Dynmap")
	public void onIRCUserLeave(IRCUserLeaveEvent event) {
		api.postPlayerJoinQuitToWeb(event.user.getName(), event.user.getName(), false);
	}

	@Optional.Method(modid = "Dynmap")
	@SubscribeEvent
	public void onIRCUserQuit(IRCUserLeaveEvent event) {
		api.postPlayerJoinQuitToWeb(event.user.getName(), event.user.getName(), false);
	}

	@Override
	@Optional.Method(modid = "Dynmap")
	public boolean webChatEvent(String source, String name, String message) {
		EiraIRCAPI.relayChat(new WebChatSender(source, name), message, false, false, null);
		return true;
	}
}
