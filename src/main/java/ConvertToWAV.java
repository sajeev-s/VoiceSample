import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.sound.sampled.AudioFileFormat.Type;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;


public class ConvertToWAV {
	
	public static void UploadFiles(final byte[] bFile) throws Exception
	{
		System.out.println(bFile.length);
	    AudioInputStream source;
	    AudioInputStream pcm;
	    final InputStream b_in = new ByteArrayInputStream(bFile);
//	    source = AudioSystem.getAudioInputStream(new BufferedInputStream(b_in));
	    source = AudioSystem.getAudioInputStream(new File("D:\\BT\\Nasso Tella\\Apr 11 12.26 [contatto client with tella]\\Audio\\Audio1.wmv"));
	    pcm = AudioSystem.getAudioInputStream(AudioFormat.Encoding.PCM_SIGNED, source);
	    final File newFile = new File("ConvertedFile");
	    AudioSystem.write(pcm, Type.WAVE, newFile);

	    source.close();
	    pcm.close();
	}

	public static void main(final String[] args) throws  Exception {
		
//		File file = new File();
		final Path path = Paths.get("D:\\BT\\Nasso Tella\\Apr 11 12.26 [contatto client with tella]\\Audio\\Audio1.wmv");
		UploadFiles(Files.readAllBytes(path));

	}

}
