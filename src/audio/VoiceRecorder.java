package audio;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.io.ByteArrayOutputStream;

import java.io.File;

import java.io.InputStream;

import java.awt.BorderLayout;
import java.awt.Button;

import java.awt.Frame;

import java.awt.FlowLayout;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.awt.event.WindowAdapter;

import java.awt.event.WindowEvent;

import javax.sound.sampled.AudioFileFormat;

import javax.sound.sampled.AudioFormat;

import javax.sound.sampled.AudioInputStream;

import javax.sound.sampled.AudioSystem;

import javax.sound.sampled.DataLine;

import javax.sound.sampled.SourceDataLine;

import javax.sound.sampled.TargetDataLine;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 * 录音器功能类
 * @author Administrator
 *
 */
public class VoiceRecorder extends JDialog {

    private boolean stopCapture = false;       //控制录音标志
    AudioFormat audioFormat;    //录音格式

    //读取数据：从TargetDataLine写入ByteArrayOutputStream录音
   private ByteArrayOutputStream byteArrayOutputStream;
   private int totaldatasize = 0;
   private TargetDataLine targetDataLine;

    //播放数据：从AudioInputStream写入SourceDataLine播放
   private AudioInputStream audioInputStream;
   private SourceDataLine sourceDataLine;
   private JButton captureBtn = new JButton("录音");
   private JButton stopBtn = new JButton("停止");
   private JButton playBtn = new JButton("播放");
   private JButton saveBtn = new JButton("保存");

    public VoiceRecorder() {
        initComponent();
    }
    
    private void initComponent(){
    	setTitle("录音机");
    	setSize(300, 80);
		this.setLocationRelativeTo(null);
    	this.setLayout(new BorderLayout());
    	

    	captureBtn = new JButton("录音");
    	stopBtn = new JButton("停止");
    	playBtn = new JButton("播放");
    	saveBtn = new JButton("保存");
        captureBtn.setEnabled(true);
        stopBtn.setEnabled(false);
        playBtn.setEnabled(false);
        saveBtn.setEnabled(false);

        //设置窗体属性
    	JPanel panel = new JPanel();
    	panel.setLayout(new FlowLayout());
        panel.add(captureBtn);
        panel.add(stopBtn);
        panel.add(playBtn);
        panel.add(saveBtn);
        this.add(panel, "Center");
        //this.setModalityType(DEFAULT_MODALITY_TYPE);
        this.setVisible(true);

        //注册录音事件
        captureBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                captureBtn.setEnabled(false);
                System.out.print("ddd");
                stopBtn.setEnabled(true);
                playBtn.setEnabled(false);
                saveBtn.setEnabled(false);

                //开始录音
                capture();
            }
        });


        //注册停止事件
        stopBtn.addActionListener(new ActionListener() {
        	

            public void actionPerformed(ActionEvent e) {

                captureBtn.setEnabled(true);
                stopBtn.setEnabled(false);
                playBtn.setEnabled(true);
                saveBtn.setEnabled(true);

                //停止录音
                stop();
            }
        });


        //注册播放事件
        playBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                //播放录音
                play();
            }
        });


        //注册保存事件
        saveBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                //保存录音
                //save();
            }
        });
    }
    public JButton getSaveButton()
    {
        return saveBtn;
    }

    //录音事件，保存到ByteArrayOutputStream中
    private void capture() {

        try {

            //打开录音

            audioFormat = getAudioFormat();

            DataLine.Info dataLineInfo = new DataLine.Info(

                    TargetDataLine.class, audioFormat);

            targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);

            targetDataLine.open(audioFormat);

            targetDataLine.start();

            //创建独立线程进行录音

            Thread captureThread = new Thread(new CaptureThread());

            captureThread.start();

        } catch (Exception e) {

            e.printStackTrace();
            this.dispose();

        }

    }

    //（2）播放ByteArrayOutputStream中的数据
    private void play() {

        try {

            //取得录音数据

            byte audioData[] = byteArrayOutputStream.toByteArray();

            //转换成输入流

            InputStream byteArrayInputStream = new ByteArrayInputStream(

                    audioData);

           

            audioInputStream = new AudioInputStream(byteArrayInputStream,

                    audioFormat, audioData.length / audioFormat.getFrameSize());

            DataLine.Info dataLineInfo = new DataLine.Info(

                    SourceDataLine.class, audioFormat);

            sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);

            sourceDataLine.open(audioFormat);

            sourceDataLine.start();

            //创建独立线程进行播放

            Thread playThread = new Thread(new PlayThread());

            playThread.start();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    //（3）停止录音

    public void stop() {
        stopCapture = true;
    }

    //（4）保存文件
    public byte [] save() {

        //取得录音输入流

        AudioFormat audioFormat = getAudioFormat();
        return byteArrayOutputStream.toByteArray();
    }

    //取得AudioFormat

    private AudioFormat getAudioFormat() {

        float sampleRate = 16000.0F;
        int sampleSizeInBits = 16;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = true;

        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed,

                bigEndian);

    }

    
    class CaptureThread extends Thread {

    //临时数组

    byte tempBuffer[] = new byte[10000];

    public void run() {

        byteArrayOutputStream = new ByteArrayOutputStream();

        totaldatasize = 0;

        stopCapture = false;

        try {//循环执行，直到按下停止录音按钮

            while (!stopCapture) {

                //读取10000个数据

                int cnt = targetDataLine.read(tempBuffer, 0,

                        tempBuffer.length);

                if (cnt > 0) {

                    //保存该数据

                    byteArrayOutputStream.write(tempBuffer, 0, cnt);

                    totaldatasize += cnt;

                }

            }

            byteArrayOutputStream.close();

        } catch (Exception e) {

            e.printStackTrace();

            System.exit(0);

        }

    }

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
