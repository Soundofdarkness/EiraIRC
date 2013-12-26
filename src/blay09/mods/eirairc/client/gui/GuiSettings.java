// Copyright (c) 2013, Christopher "blay09" Baker
// All rights reserved.

package blay09.mods.eirairc.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import blay09.mods.eirairc.config.ScreenshotConfig;
import blay09.mods.eirairc.util.Globals;

public class GuiSettings extends GuiScreen {

	private static final int BUTTON_WIDTH = 150;
	private static final int BUTTON_HEIGHT = 20;
	
	private GuiButton btnGlobalSettings;
	private GuiButton btnServerList;
	private GuiButton btnTwitch;
	private GuiButton btnDisplaySettings;
	private GuiButton btnBotSettings;
	private GuiButton btnScreenshots;
	private GuiButton btnFriendIgnore;
	private GuiButton btnNotifications;
	private GuiButton btnKeybinds;
	private GuiButton btnClientServer;
	private GuiButton btnBack;
	
	@Override
	public void initGui() {
		int leftX = width /  2 - 152;
		int rightX = width / 2 + 2;
		
		btnGlobalSettings = new GuiButton(2, leftX, height / 2 - 90, BUTTON_WIDTH, BUTTON_HEIGHT, "Global Settings");
		buttonList.add(btnGlobalSettings);
		
		btnServerList = new GuiButton(3, leftX, height / 2 - 65, BUTTON_WIDTH, BUTTON_HEIGHT, "Server List");
		buttonList.add(btnServerList);
		
		btnFriendIgnore = new GuiButton(4, leftX, height / 2 - 40, BUTTON_WIDTH, BUTTON_HEIGHT, "Friends / Ignore List");
		btnFriendIgnore.enabled = false;
		buttonList.add(btnFriendIgnore);
		
		btnTwitch = new GuiButton(5, leftX, height / 2 - 15, BUTTON_WIDTH, BUTTON_HEIGHT, "Twitch Chat");
		buttonList.add(btnTwitch);
		
		btnScreenshots = new GuiButton(6, leftX, height / 2 + 10, BUTTON_WIDTH, BUTTON_HEIGHT, "Screenshots");
		if(!ScreenshotConfig.manageScreenshots) {
			btnScreenshots.enabled = false;
		}
		buttonList.add(btnScreenshots);
		
		btnDisplaySettings = new GuiButton(7, rightX, height / 2 - 90, BUTTON_WIDTH, BUTTON_HEIGHT, "Display Settings");
		buttonList.add(btnDisplaySettings);
		
		btnBotSettings = new GuiButton(8, rightX, height / 2 - 65, BUTTON_WIDTH, BUTTON_HEIGHT, "Bot Settings");
		buttonList.add(btnBotSettings);
		
		btnNotifications = new GuiButton(9, rightX, height / 2 - 40, BUTTON_WIDTH, BUTTON_HEIGHT, "Notifications");
		buttonList.add(btnNotifications);
		
		btnKeybinds = new GuiButton(10, rightX, height / 2 - 15, BUTTON_WIDTH, BUTTON_HEIGHT, "Keybinds");
		buttonList.add(btnKeybinds);
		
		btnClientServer = new GuiButton(1, 1, 1, 60, 20, "Client");
		btnClientServer.enabled = false;
		buttonList.add(btnClientServer);
		
		btnBack = new GuiButton(0, width / 2 - 100, height / 2 + 90, 200, 20, "Back");
		buttonList.add(btnBack);
	}
	
	@Override
	public void actionPerformed(GuiButton button) {
		if(button == btnTwitch) {
			Minecraft.getMinecraft().displayGuiScreen(new GuiTwitch(this));
		} else if(button == btnServerList) {
			Minecraft.getMinecraft().displayGuiScreen(new GuiServerList());
		} else if(button == btnGlobalSettings) {
			Minecraft.getMinecraft().displayGuiScreen(new GuiGlobalSettings());
		} else if(button == btnScreenshots) {
			Minecraft.getMinecraft().displayGuiScreen(new GuiScreenshots());
		} else if(button == btnNotifications) {
			Minecraft.getMinecraft().displayGuiScreen(new GuiNotifications());
		} else if(button == btnKeybinds) {
			Minecraft.getMinecraft().displayGuiScreen(new GuiKeybinds());
		} else if(button == btnDisplaySettings) {
			Minecraft.getMinecraft().displayGuiScreen(new GuiDisplaySettings());
		} else if(button == btnBotSettings) {
			Minecraft.getMinecraft().displayGuiScreen(new GuiBotSettings());
		} else if(button == btnBack) {
			Minecraft.getMinecraft().displayGuiScreen(null);
		}
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		drawBackground(0);
		this.drawCenteredString(fontRenderer, "EiraIRC Settings", width / 2, height / 2 - 110, Globals.TEXT_COLOR);
		super.drawScreen(par1, par2, par3);
	}
}
