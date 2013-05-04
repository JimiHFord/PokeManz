/**
 * 
 */
package view.pokedex;

import view.PokeListener;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

/**
 * @author jimiford
 *
 * @source http://thiscouldbebetter.wordpress.com/2011/06/14/playing-an-mp3-from-java-using-jlayer/
 */
public class SoundJLayer extends PlaybackListener implements Runnable
{
	private String filePath;
	private AdvancedPlayer player;
	private Thread playerThread;
	private boolean hasExternalListener;
	private PokeListener listener;

	public SoundJLayer(String filePath, PokeListener listener) {
		this(filePath);
		this.hasExternalListener = true;
		this.listener = listener;
	}

	public SoundJLayer(String filePath)
	{
		this.hasExternalListener = false;
		this.filePath = filePath;
	}

	public void play()
	{
		try
		{
			String urlAsString = 
					"file:///" 
							+ new java.io.File(".").getCanonicalPath() 
							+ "/" 
							+ this.filePath;

			this.player = new AdvancedPlayer
					(
							new java.net.URL(urlAsString).openStream(),
							javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice()
							);
			this.player.setPlayBackListener(this);
			this.playerThread = new Thread(this, "AudioPlayerThread");
			this.playerThread.start();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	// PlaybackListener members
	public void playbackStarted(PlaybackEvent playbackEvent)
	{
		if(this.hasExternalListener) {
			listener.act(PokedexScreen.DISABLE, null);
		}
		//        System.out.println("playbackStarted()");
	}

	public void playbackFinished(PlaybackEvent playbackEvent)
	{
		if(this.hasExternalListener) {
			listener.act(PokedexScreen.ENABLE, null);
		}
		//        System.out.println("playbackEnded()");
	}    

	// Runnable members

	public void run()
	{
		try
		{
			this.player.play();
		}
		catch (javazoom.jl.decoder.JavaLayerException ex)
		{
			ex.printStackTrace();
		}

	}

}