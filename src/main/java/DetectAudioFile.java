import java.io.File;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;


public class DetectAudioFile {

	public static void main(final String[] args) throws Exception {
		final AudioFileFormat format = AudioSystem.getAudioFileFormat(new File("dst.wav"));
		
		System.out.println(format.getFormat().toString());
		
		final Map<String, Object> maps = format.getFormat().properties();
		final Set<Entry<String, Object>> sets = maps.entrySet();
		System.out.println(sets.size());
		for (final Entry<String, Object> entry : sets) {
			System.out.println(entry.getKey() +" -- "+entry.getValue());
		}

	}

}
