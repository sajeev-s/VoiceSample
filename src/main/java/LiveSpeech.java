import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;


public class LiveSpeech {

	static String start = "Y";
	
	
	 final static Configuration configuration = new Configuration();
		
    
	public static void main(final String[] args) throws Exception{
		
			 configuration
	         .setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
	 configuration
	         .setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
	 configuration
	         .setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
	 
	 
		final AudioFormat format = new AudioFormat(16000.0f, 16, 1, true, true);
	    TargetDataLine microphone;
	    SourceDataLine speakers;
	    final File distinationFile = new File("dst.wav");
	    
	    new Thread(){

			@Override
			public void run() {
				System.out.println("inside");
				 final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
					System.out.println("Stop :");
					try {
						start = reader.readLine();
					} catch (final IOException e) {
						e.printStackTrace();
					}
			}
	    	
	    }.start();
	    try {
	        microphone = AudioSystem.getTargetDataLine(format);

	        final DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
	        microphone = (TargetDataLine) AudioSystem.getLine(info);
	        microphone.open(format);

	        final ByteArrayOutputStream out = new ByteArrayOutputStream();
	        int numBytesRead;
	        final int CHUNK_SIZE = 1024;
	        final byte[] data = new byte[microphone.getBufferSize() / 5];
	        microphone.start();

	        int bytesRead = 0;
	        final DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
	        speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
	        speakers.open(format);
	        speakers.start();
	        System.out.println("stop -->"+start);
	        while (start.equals("Y")) { //bytesRead < 100000 //true && !
	            numBytesRead = microphone.read(data, 0, CHUNK_SIZE);
	            bytesRead += numBytesRead;
//	            System.out.println(data);
	            // write the mic data to a stream for use later
	            out.write(data, 0, numBytesRead); 
	            // write mic data to stream for immediate playback
	            speakers.write(data, 0, numBytesRead);
	            
	        }
	        speakers.drain();
	        speakers.close();
	        microphone.close();
	        
	        final byte[] audioData = out.toByteArray();
	        final ByteArrayInputStream bais = new ByteArrayInputStream(audioData);
	        final AudioInputStream audioInputStream = new AudioInputStream(bais, format,
	                audioData.length / format.getFrameSize());
	 
	        
	        AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, distinationFile);
	        audioInputStream.close();
	        out.close();
	        transcriber();
	        
	    } catch (final LineUnavailableException e) {
	        e.printStackTrace();
	    } 

	}
	
	static void transcriber() throws Exception{
			System.out.println("inside");
	       
	        
	//        Microphone dfd = new Microphone(paramFloat, paramInt, paramBoolean1, paramBoolean2)
	
	        final StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(
	                configuration);
	        
	      /*  final SpeechAligner aligner = new SpeechAligner(configuration.getAcousticModelPath(),configuration.getDictionaryPath(),configuration.getLanguageModelPath());
	        aligner.align(new URL("file:\\D:\\BT\\BarraTelefonica-DEVENV\\workspace\\VoiceSample\\test.wav"), "hello how are you ? anything special ?");
	      */  
	        
	//        System.out.println(new File("test.wav").getAbsolutePath());
	        final InputStream stream = new FileInputStream(new File("dst.wav"));//dst.wav
	
	        recognizer.startRecognition(stream);
	        SpeechResult result;
	        while ((result = recognizer.getResult()) != null) {
	            System.out.format("Hypothesis: %s\n", result.getHypothesis());
	        }
	        recognizer.stopRecognition();
	    }

}
