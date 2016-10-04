import java.io.File;
import java.io.SequenceInputStream;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;


public class MergeWave {

	public static void main(final String[] args) {
		    try {
			    final AudioInputStream clip1 = AudioSystem.getAudioInputStream(new File("merged-1.wav"));
			    final AudioInputStream clip2 = AudioSystem.getAudioInputStream(new File("dst-3.wav"));

			    final AudioInputStream appendedFiles = 
	                            new AudioInputStream(
	                                new SequenceInputStream(clip1, clip2),     
	                                clip1.getFormat(), 
	                                clip1.getFrameLength() + clip2.getFrameLength());

			    AudioSystem.write(appendedFiles, 
	                            AudioFileFormat.Type.WAVE, 
	                            new File("merged-2.wav"));
		    } catch (final Exception e) {
			    e.printStackTrace();
		    }

	}

}
