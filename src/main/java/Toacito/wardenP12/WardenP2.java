package Toacito.wardenP12;

import Toacito.Sala;
import Toacito.ToacitoConfig;
import Toacito.ToacitoPlugin;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.NpcSpawned;
import net.runelite.api.events.ProjectileMoved;
import net.runelite.client.eventbus.Subscribe;

import javax.inject.Inject;

@Getter
@Slf4j
public class WardenP2 extends Sala {

	@Inject
	private Client client;

	@Inject
	private ToacitoConfig toacitoConfig;

	@Inject
	private WardenP2Overlay wardenP2Overlay;
	public static boolean enPelea= false;
	private int conta;
	public static boolean inFight = false;


	@Override
	public void load() {
		this.overlayManager.add(wardenP2Overlay);
	}

	@Override
	public void unload() {
		this.overlayManager.remove(wardenP2Overlay);
	}

	public static LocalPoint[] endPos = new LocalPoint[10];
	public static int[] tickLeft = new int[10];

	private static int nPick=0;

	private final int duracion = 5;


	@Inject
	protected WardenP2(ToacitoConfig config, ToacitoPlugin plugin) {
		super(config, plugin);
	}

	@Subscribe
	public void onProjectileMoved(ProjectileMoved event){
		if(event.getProjectile()==null){
			return;
		}

		if(event.getProjectile().getId()==2225){//2225
			//log.info("Se agrego un Pikashu");
			endPos[nPick] = event.getProjectile().getTarget();
			tickLeft[nPick] = duracion;
			nPick++;
			if(nPick==tickLeft.length-1){
				nPick=0;
			}
		}
	}

	@Subscribe
	public void onNpcSpawned(NpcSpawned event){
		if(event.getNpc() == null) return;
		if(event.getNpc().getId() == 11750) {
			enPelea = true;
		}
	}

	/*
	@Subscribe
	public void onNpcDespawned(NpcDespawned event){
		if(event.getNpc() == null) return;

		if(event.getNpc().getId() == 11750) {
			enPelea = false;
		}


	}
	*/

	@Subscribe
	public void onGameStateChanged(GameStateChanged event){
		if(event.getGameState() == GameState.LOADING){
			enPelea=false;
		}
	}

	@Subscribe
	public void onGameTick(GameTick event)throws Exception{
		//this.conta++;
		//if((this.conta)%5==4) log.info("conta: {}, pelea = {}",this.conta, enPelea);
		if(toacitoConfig.pikachusConfig() && enPelea){//npcSpawned warden - && isRoomRegion(WARDEN_P12_REGION)
			for(int c=0; c<tickLeft.length; c++){
				if (tickLeft[c] > 0) {
					log.info("diminuyo");
					tickLeft[c]--;
				}
			}
		}
	}
}
