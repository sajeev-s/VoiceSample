import java.io.IOException;

import javax.sound.sampled.AudioFileFormat.Type;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import com.sun.speech.freetts.audio.AudioPlayer;
import com.sun.speech.freetts.audio.SingleFileAudioPlayer;

public class TalkResource {

//    private static final String VOICENAME_kevin = "alan";
    private static final String VOICENAME_kevin = "kevin16";

    public TalkResource(final String sayText) throws IOException {
    	
    	AudioPlayer audioPlayer = null;
        Voice voice;
        final VoiceManager voiceManager = VoiceManager.getInstance();
        
        voice = voiceManager.getVoice(VOICENAME_kevin);
       
        voice.setRate(80);
        voice.setPitch(80);
        voice.setPitchRange(25);
        voice.setDurationStretch(1.5f);
        System.out.println(voice.getPitch());
//        audioPlayer = new MultiFileAudioPlayer("D:\\BT\\BarraTelefonica-DEVENV\\workspace\\VoiceSample\\test",Type.WAVE);
        audioPlayer = new SingleFileAudioPlayer("D:\\BT\\BarraTelefonica-DEVENV\\workspace\\VoiceSample\\test",Type.WAVE);
        voice.setAudioPlayer(audioPlayer);
        voice.allocate();
        
        voice.speak(sayText);
        
        voice.deallocate();
        
        
        audioPlayer.close();
    }

    public static void main(final String []args) throws IOException {
//        new TalkResource("hello how are you ? anything special ?");
        new TalkResource("one zero zero zero one nine oh two "
            + "one oh zero one eight zero three");
//        new TalkResource("hello mother how are you?");
        
        /*this.nominalRate = 150.0F;
		this.pitch = 100.0F;
		this.range = 10.0F;
		this.pitchShift = 1.0F;
		this.volume = 0.8F;
		this.durationStretch = 1.0F;*/
    }
}