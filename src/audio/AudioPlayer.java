package audio;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

public class AudioPlayer{
    private boolean stopCapture = false;       //控制录音标志
    private AudioFormat audioFormat;           //录音格式

    //读取数据：从TargetDataLine写入ByteArrayOutputStream录音
   private ByteArrayOutputStream byteArrayOutputStream;
   private int totaldatasize = 0;
   private TargetDataLine targetDataLine;

   
    //播放数据：从AudioInputStream写入SourceDataLine播放
   private AudioInputStream audioInputStream;
   private SourceDataLine sourceDataLine;

   byte[] audioData;
   

   public AudioPlayer()
   {

   }

   //（2）播放ByteArrayOutputStream中的数据
   public void play(byte[] voice) {

       try {

           audioData=voice;

           if(audioData==null)
        	   return;

           //转换成输入流

           InputStream byteArrayInputStream = new ByteArrayInputStream(audioData);

           AudioFormat audioFormat = getAudioFormat();

           audioInputStream = new AudioInputStream(byteArrayInputStream,audioFormat, audioData.length / audioFormat.getFrameSize());

           DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);

           sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);

           sourceDataLine.open(audioFormat);

           sourceDataLine.start();

           //创建独立线程进行播放

           Thread playThread = new Thread(new PlayThread());

           playThread.start();

       } catch (Exception e) {

           e.printStackTrace();

           System.exit(0);

       }

   }

   private AudioFormat getAudioFormat() {

       float sampleRate = 16000.0F;
       int sampleSizeInBits = 16;
       int channels = 1;
       boolean signed = true;
       boolean bigEndian = false;

       return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed,

               bigEndian);

   }
      
   class PlayThread extends Thread {

	    byte tempBuffer[] = new byte[10000];

	    public void run() {

	        try {

	            int cnt;

	            //读取数据到缓存数据

	            while ((cnt = audioInputStream.read(tempBuffer, 0,

	                    tempBuffer.length)) != -1) {

	                if (cnt > 0) {

	                    //写入缓存数据

	                    sourceDataLine.write(tempBuffer, 0, cnt);

	                }

	            }

	            //Block等待临时数据被输出为空

	            sourceDataLine.drain();

	            sourceDataLine.close();

	        } catch (Exception e) {

	            e.printStackTrace();
	            System.exit(0);
	        }
	    }
	}
}
