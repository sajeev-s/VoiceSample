import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechAligner;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;


public class TranscriberDemo {
	
	 public static void main(final String[] args) throws Exception {
         System.out.println("inside");
	        final Configuration configuration = new Configuration();

	        configuration
	                .setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
	        configuration
	                .setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
	        configuration
	                .setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
	        
	        /*configuration.setGrammarPath("resource:/edu/cmu/sphinx/demo/dialog/");
	        configuration.setGrammarName("digits.grxml");
	        configuration.setUseGrammar(true);*/
//	        Microphone dfd = new Microphone(paramFloat, paramInt, paramBoolean1, paramBoolean2)

	        final StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(
	                configuration);
	        
	        
	        final SpeechAligner aligner = new SpeechAligner(configuration.getAcousticModelPath(),configuration.getDictionaryPath(),configuration.getLanguageModelPath());
	        aligner.align(new File("test.wav").toURI().toURL(), "hello how are you ? anything special ?");
	        
	       
//	        System.out.println(new File("test.wav").getAbsolutePath());
	        final InputStream stream = new FileInputStream(new File("dst.wav"));//dst.wav

	        recognizer.startRecognition(stream);
	        SpeechResult result;
	        while ((result = recognizer.getResult()) != null) {
	            System.out.format("Hypothesis: %s\n", result.getHypothesis());
	        }
	        recognizer.stopRecognition();
	    }
}
