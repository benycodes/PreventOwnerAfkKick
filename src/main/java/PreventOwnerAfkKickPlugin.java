import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.messages.outgoing.generic.alerts.GenericAlertComposer;
import com.eu.habbo.plugin.EventHandler;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.HabboPlugin;
import com.eu.habbo.plugin.events.users.UserExitRoomEvent;
import java.util.logging.Level;

/**
 * This plugin prevents the room owner from being auto kicked due to AFK.
 */
public class PreventOwnerAfkKickPlugin extends HabboPlugin implements EventListener
{
    /**
     * This is triggered whenever the plugin gets loaded by the emulator.
     *
     * Use it as your entry point for your plugin.
     */
    @Override
    public void onEnable()
    {
        /**
         * You can use the Arcturus system.
         */
        Emulator.getLogging().logStart("PreventOwnerAfkKickPlugin by Beny. loaded!");

        /**
         * To register events, you call this function.
         * Make sure to implement the EventListener interface.
         */
        Emulator.getPluginManager().registerEvents(this, this);
    }

    /**
     * This is triggered whenever the plugin is going to be disposed.
     * You can still use the full Arcturus system as system integrity is
     * guaranteed.
     */
    @Override
    public void onDisable()
    {
        Emulator.getLogging().logShutdownLine("PreventOwnerAfkKickPlugin by Beny. has been disabled!");
    }

    /**
     * To register an event implement the interface EventListener
     * @param event The event to listen for.
     */
    @EventHandler
    public static void onUserExitRoomEvent(UserExitRoomEvent event)
    {
        if(event.habbo.getHabboInfo().getCurrentRoom().isOwner(event.habbo) && (event.reason == UserExitRoomEvent.UserExitRoomReason.KICKED_IDLE || (event.reason == UserExitRoomEvent.UserExitRoomReason.KICKED_HABBO && event.habbo.getRoomUnit().getIdleTimer() >= Emulator.getConfig().getInt("hotel.roomuser.idle.cycles.kick", 480))))
        {
            event.setCancelled(true);
        }
    }

    @Override
    public boolean hasPermission(Habbo habbo, String key) {
        return true;
    }
}
