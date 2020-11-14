package io.github.craftablescience.windowedchat.event;

import javax.swing.ImageIcon;

import io.github.craftablescience.windowedchat.WindowedChat;
import io.github.craftablescience.windowedchat.gui.GuiOptionsNoChat;
import io.github.craftablescience.windowedchat.helpers.ChatStyleBlock;
import io.github.craftablescience.windowedchat.helpers.ImageAssets;
import io.github.craftablescience.windowedchat.helpers.PlayerHelper;
import io.github.craftablescience.windowedchat.swing.ChatWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ChatType;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;


public class WindowedChatEvents {
	
	private ChatWindow chatWindow = WindowedChat.chatWindow;

    @SubscribeEvent
    public void onPlayerLogin(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        chatWindow = new ChatWindow();
        chatWindow.activate(true);
    }

    @SubscribeEvent
    public void onPlayerLogout(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
    	chatWindow.deactivate();
    	chatWindow = null;
    }

    @SubscribeEvent
    public void onRenderGameOverlayEvent(RenderGameOverlayEvent event) {
        // Fixes most recent message still displaying
        if (!ChatWindow.SETTING_MCCHATHISTORY)
            Minecraft.getMinecraft().ingameGUI.getChatGUI().clearChatMessages(true);
    }

    @SubscribeEvent
    public void onGuiOpenEvent(GuiOpenEvent event) {
        if (event.getGui() == null) return;
        if (event.getGui() instanceof GuiOptions) {
            event.setGui(new GuiOptionsNoChat());
        }
    }

    @SubscribeEvent
    public void onClientChatReceivedEvent(ClientChatReceivedEvent event) {
        ImageIcon icon = null;

        String playerName = event.getMessage().getUnformattedText().split(" ")[0].replace("<", "").replace(">", "");
        
        WindowedChat.logger.info("Name: " + playerName);
        
        if (event.getType() == ChatType.CHAT) {
        	icon = PlayerHelper.loadImage(playerName);
        } else if (event.getType() == ChatType.GAME_INFO) {
            icon = ImageAssets.getTexture("gameinfo");
        } else if (event.getType() == ChatType.SYSTEM) {
            icon = ImageAssets.getTexture("system");
        }

        if (event.getMessage().getUnformattedText().trim().length() > 0) {
            char section = '\u00A7';
            String text = event.getMessage().getUnformattedText();

            if (text.contains(String.valueOf(section))) {
                StringBuilder output = new StringBuilder();
                output.append("<html>");
                String[] components = text.split("(?=" + section + ")");

                char color = '\0';
                boolean k = false;
                boolean l = false;
                boolean m = false;
                boolean n = false;
                boolean o = false;

                for (String component : components) {
                    boolean foundCode = false;
                    for (char c : component.toCharArray()) {
                        if (foundCode) {
                            switch (c) {
                                case '0':
                                case '1':
                                case '2':
                                case '3':
                                case '4':
                                case '5':
                                case '6':
                                case '7':
                                case '8':
                                case '9':
                                case 'a':
                                case 'b':
                                case 'c':
                                case 'd':
                                case 'e':
                                case 'f':
                                    color = c;
                                    break;
                                case 'k':
                                    k = true;
                                    break;
                                case 'l':
                                    l = true;
                                    break;
                                case 'm':
                                    m = true;
                                    break;
                                case 'n':
                                    n = true;
                                    break;
                                case 'o':
                                    o = true;
                                    break;
                                case 'r':
                                    k = l = m = n = o = false;
                                    break;
                            }
                            foundCode = false;
                        }
                        if (c == section) {
                            foundCode = true;
                        }
                    }
                    if (component.length() > 2 && component.contains(String.valueOf(section)))
                        output.append(new ChatStyleBlock(component.substring(2), color, k, l, m, n, o).getHTMLMarkup());
                    else if (component.length() == 2 && component.contains(String.valueOf(section)))
                        ;
                    else
                        output.append(new ChatStyleBlock(component, color, k, l, m, n, o).getHTMLMarkup());
                }
                output.append("</html>");
               text = output.toString();

            }
            if (event.getMessage().getStyle().getClickEvent() == null)
            	chatWindow.addItem(text, icon);
            else
            	chatWindow.addLinkItem(text, icon, event.getMessage());
        }
        String text = event.getMessage().getUnformattedText();
        if (text.split(" ").length > 1 && playerExists(text.split(" ")[0]) && text.split(" ")[1].equals("whispers")) {
            String message = text.substring("[".length() + text.split(" ")[0].length() + " whispers to you:".length());
            if (ChatWindow.DM_HASHMAP.containsKey(text.split(" ")[0])) {
                ChatWindow.DM_HASHMAP.get(text.split(" ")[0]).addItemOther(message);
            } else {
            	chatWindow.addDM(text.split(" ")[0], message, false);
            }
        }
        if (text.startsWith("You whispered to ") && event.getType() == ChatType.SYSTEM) {
            if (text.split(" ").length > 4 && playerExists(text.split(" ")[3].substring(0, text.split(" ")[3].length() - 1))) {
                StringBuilder out = new StringBuilder();
                for (int i = 4; i < text.split(" ").length; i++)
                    out.append(text.split(" ")[i]).append(" ");
                if (!ChatWindow.DM_HASHMAP.containsKey(text.split(" ")[3].substring(0, text.split(" ")[3].length() - 1)))
                	chatWindow.addDM(text.split(" ")[3].substring(0, text.split(" ")[3].length() - 1), out.toString(), true);
                else {
                    ChatWindow.DM_HASHMAP.get(text.split(" ")[3].substring(0, text.split(" ")[3].length() - 1)).addItemSelf(out.toString());
                }
            }
        }
        if (!ChatWindow.SETTING_MCCHATHISTORY)
            Minecraft.getMinecraft().ingameGUI.getChatGUI().clearChatMessages(true);
    }

    private boolean playerExists(String name) {
        for (EntityPlayer player : Minecraft.getMinecraft().world.playerEntities)
            if (player.getName().equals(name))
                return true;
        return false;
    }
}
