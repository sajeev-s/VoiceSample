import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;



public class MicroPhoneSample {
	
	static String start = "Y";	
	
	/*public String voiceGet() throws InterruptedException{
//		HelloWorld.class.getResource("helloworld.config.xml")
		 final ConfigurationManager cm = new ConfigurationManager();
//		cm = new ConfigurationManager();
		        recognizer = (Recognizer) cm.lookup("recognizer");
		        microphone = (Microphone) cm.lookup("microphone");
		        microphone.clear();
		        recognizer.allocate();
		         if(microphone.startRecording())
		        {

		                System.out.println("Speak now");
		                final Result result = recognizer.recognize();
		                if(result != null)
		                {
		                    resultString = result.getBestResultNoFiller();
		                    System.out.println(resultString);
		                } else
		                {
		                    System.out.println("nothing spoken");
		                }

		        } else
		        {
		            System.out.println("microphone not available");
		            recognizer.deallocate();
		            System.exit(1);

		        }
		            recognizer.deallocate();
		            recognizer=null;
		            microphone.stopRecording();
		            microphone.clear();
		            microphone=null;
		            cm=null;
		        return resultString;


		    }*/

	public static void main(final String[] args) throws Exception {
		
		
		/*final MicroPhoneSample microPhoneSample = new MicroPhoneSample();
		Microphone
		
	    final AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
	      final TargetDataLine microphone = AudioSystem.getTargetDataLine(format);
	      microphone.start();
	      System.out.println(microphone.isActive()+"-----"+microphone.isOpen()+"-----"+microphone.isRunning()+"-----"+microphone);
	      System.out.println("before stop");
//	      microPhoneSample.wait(3000);
	      
	      microphone.stop();*/
		
		/*final Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
		 for (final Mixer.Info info: mixerInfos){
		  final Mixer m = AudioSystem.getMixer(info);
		  Line.Info[] lineInfos = m.getSourceLineInfo();
		  for (final Line.Info lineInfo:lineInfos){
		   System.out.println ("1--->"+info.getName()+"---"+lineInfo);
		   final Line line = m.getLine(lineInfo);
		   System.out.println("\t-----"+line);
		  }
		  lineInfos = m.getTargetLineInfo();
		  for (final Line.Info lineInfo:lineInfos){
		   System.out.println ("2--->"+m+"---"+lineInfo);
		   final Line line = m.getLine(lineInfo);
		   System.out.println("\t-----"+line);
		   line.open();
		  }

		 }
		
		 final LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(null);
		// Start recognition process pruning previously cached data.
		recognizer.startRecognition(true);
		final SpeechResult result = recognizer.getResult();
		// Pause recognition process. It can be resumed then with startRecognition(false).
		recognizer.stopRecognition();
		 
		 final Microphone microphone = new Microphone();
		 System.out.println(microphone);
		 microphone.startRecording();*/

		
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
	    } catch (final LineUnavailableException e) {
	        e.printStackTrace();
	    } 
	}
	
	

}
