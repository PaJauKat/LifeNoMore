package Toacito;

import Toacito.AkkhaPath.AkkhaPath;
import Toacito.lobby.Lobby;
import Toacito.wardenP12.WardenP2;
import Toacito.wardenP34.WardenP3;
import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.GraphicsObjectCreated;
import net.runelite.api.events.NpcDespawned;
import net.runelite.api.events.NpcSpawned;
import net.runelite.api.events.ProjectileMoved;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import javax.inject.Inject;

@Slf4j
@PluginDescriptor(name = "TOA modern", description = "Some features for Tombs of Amascut",tags = {"pajau","toa","Tombs","of","amascut"})
public class ToacitoPlugin extends Plugin {
	@Inject
	private Client client;

	@Inject
	private ToacitoConfig config;

	@Inject
	private AkkhaPath akkhaPath;

	@Inject
	private WardenP2 wardenP2;

	@Inject
	private WardenP3 wardenP3;

	@Inject
	private Lobby lobby;

	private Sala[] rooms = null;

	public static final int AKKHA_PATH_REGION = 14674;
	public static final int BABA_REGION = 15188;
	public static final int WARDEN_P12_REGION = 15184;

	@Provides
	ToacitoConfig getConfig(ConfigManager configManager) {
		return (ToacitoConfig) configManager.getConfig(ToacitoConfig.class);
	}

	@Override
	protected void startUp(){
		if (this.rooms==null){
			this.rooms=new Sala[]{ (Sala) this.akkhaPath,(Sala) this.wardenP2,(Sala) this.wardenP3};

			for(Sala wea: this.rooms){
				wea.init();
				wea.load();
			}
		}
	}

	@Override
	protected void shutDown(){
		for(Sala wea: this.rooms){
			wea.unload();
		}
	}

	@Subscribe
	void onGameStateChanged(GameStateChanged event){
		this.wardenP2.onGameStateChanged(event);
		this.lobby.onGameStateChanged(event);
	}



	@Subscribe
	void onNpcSpawned(NpcSpawned event){
		this.akkhaPath.onNpcSpawned(event);
		this.wardenP2.onNpcSpawned(event);

	}

	@Subscribe
	void onNpcDespawned(NpcDespawned event){
		this.akkhaPath.onNpcDespawned(event);
	}

	@Subscribe
	void onGameTick(GameTick event)throws Exception{
		this.akkhaPath.onGameTick(event);
		this.wardenP2.onGameTick(event);
		this.lobby.onGameTick(event);
	}
	@Subscribe
	void onGraphicsObjectCreated(GraphicsObjectCreated event){
		this.akkhaPath.onGraphicsObjectCreated(event);
	}

	@Subscribe
	void onAnimationChanged(AnimationChanged event){
		this.wardenP3.onAnimationChanged(event);
	}

	@Subscribe
	void onProjectileMoved(ProjectileMoved event){
		this.wardenP2.onProjectileMoved(event);
	}

	@Subscribe
	void onGameObjectSpawned(GameObjectSpawned event){
		this.lobby.onGameObjectSpawned(event);
	}



}
