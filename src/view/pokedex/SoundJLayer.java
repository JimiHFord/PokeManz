/**
 * SoundJLayer.java
 */
package view.pokedex;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Port;
import javax.sound.sampled.Port.Info;

import view.PokeListener;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

/**
 * Implements a Sound Layer for the Pokedex panel
 * @author Jimi Ford
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
//			String urlAsString = 
//					"file:///" 
//							+ new java.io.File(".").getCanonicalPath() 
//							+ "/" 
//							+ this.filePath;
			java.net.URL url = this.getClass().getResource("/data/resources/sounds/"+filePath);
			this.player = new AdvancedPlayer
					(
							url.openStream(),
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
	
	public static void main(String[] args) 
	{        
	    Info source = Port.Info.SPEAKER;
	    //        source = Port.Info.LINE_OUT;
	    //        source = Port.Info.HEADPHONE;

	        if (AudioSystem.isLineSupported(source)) 
	        {
	            try 
	            {
	                Port outline = (Port) AudioSystem.getLine(source);
	                outline.open();                
	                FloatControl volumeControl = (FloatControl) outline.getControl(FloatControl.Type.VOLUME);                
	                System.out.println("       volume: " + volumeControl.getValue() );
	                float v = 0.33F;
	                volumeControl.setValue(v);
	                System.out.println("   new volume: " + volumeControl.getValue() );
	                v = 0.73F;
	                volumeControl.setValue(v); 
	                System.out.println("newest volume: " + volumeControl.getValue() );
	            } 
	            catch (LineUnavailableException ex) 
	            {
	                System.err.println("source not supported");
	                ex.printStackTrace();
	            }            
	        }
	    } 

}
