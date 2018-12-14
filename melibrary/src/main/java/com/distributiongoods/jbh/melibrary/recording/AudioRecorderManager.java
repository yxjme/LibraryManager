package com.distributiongoods.jbh.melibrary.recording;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by zbsdata on 2018/9/12.
 */

public class AudioRecorderManager {

    // 指定音频源 这个和MediaRecorder是相同的 MediaRecorder.AudioSource.MIC指的是麦克风
    private static final int mAudioSource = MediaRecorder.AudioSource.MIC;
    // 指定采样率 （MediaRecoder 的采样率通常是8000Hz AAC的通常是44100Hz。
    // 设置采样率为44100，目前为常用的采样率，官方文档表示这个值可以兼容所有的设置）
    private static final int mSampleRateInHz=44100 ;
    //指定捕获音频的声道数目。在AudioFormat类中指定用于此的常量
    private static final int mChannelConfig= AudioFormat.CHANNEL_CONFIGURATION_MONO;
    // 单声道 //指定音频量化位数 ,在AudioFormaat类中指定了以下各种可能的常量。
    // 通常我们选择ENCODING_PCM_16BIT和ENCODING_PCM_8BIT PCM代表的是脉冲编码调制，
    // 它实际上是原始音频样本。 //因此可以设置每个样本的分辨率为16位或者8位，
    // 16位将占用更多的空间和处理能力,表示的音频也更加接近真实。
    private static final int mAudioFormat=AudioFormat.ENCODING_PCM_16BIT;
    //指定缓冲区大小。调用AudioRecord类的getMinBufferSize方法可以获得。
    //计算最小缓冲区
    private int mBufferSizeInBytes= AudioRecord.getMinBufferSize(mSampleRateInHz,mChannelConfig, mAudioFormat);
    private AudioRecord audioRecord;
    private boolean isRecording;
    private String currentFilePath;
    private AudioTrack audioTrack;
    public static AudioRecorderManager audiaRecorderManager;
    /**子线程对象*/
    private Thread  recordingThread;


    public static AudioRecorderManager newInstance(){
        if (audiaRecorderManager==null){
            synchronized (AudioRecorderManager.class){
                if (audiaRecorderManager==null){
                    audiaRecorderManager=new AudioRecorderManager();
                }
            }
        }
        return audiaRecorderManager;
    }


    /**
     * 开始录音采数据
     */
    public void startAudiaRecorder(){

        audioRecord=new AudioRecord(mAudioSource,mSampleRateInHz,mChannelConfig ,mAudioFormat,mBufferSizeInBytes);

        if (audioRecord!=null){
            /**
             *   AudioRecord.STATE_INITIALIZED 初始化完毕
             *   AudioRecord.STATE_UNINITIALIZED 未初始化
             */
            if (audioRecord.getState()==AudioRecord.STATE_INITIALIZED){
                recordingThread  = new  Thread(new MyRunnable());
                recordingThread.start();
                audioRecord.setRecordPositionUpdateListener(new AudioRecord.OnRecordPositionUpdateListener() {
                    @Override
                    public void onMarkerReached(AudioRecord recorder) {
                    }
                    @Override
                    public void onPeriodicNotification(AudioRecord recorder) {
                    }
                });
            }
        }
    }


    private class MyRunnable implements Runnable{

        @Override
        public void run() {
            try {
                isRecording=true ;
                File file=new File(MediaRecorderManager.newinstance().getFileDir(),System.currentTimeMillis()+".pcm");
                currentFilePath=file.getPath();
                BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(new FileOutputStream(file));
                DataOutputStream outputStream = new DataOutputStream(bufferedOutputStream);

                byte[] buffer = new byte[mBufferSizeInBytes] ;
                //开始录音。
                audioRecord.startRecording();
                while (isRecording && audioRecord.getRecordingState()==AudioRecord.RECORDSTATE_RECORDING){
                    int bufferData = audioRecord.read(buffer, 0, mBufferSizeInBytes);
                    Log.v("=======bufferData===","bufferData="+mBufferSizeInBytes);
                    for (int i = 0 ; i < bufferData ; i++){
                        outputStream.write(buffer[i]);
                    }
                }
                outputStream.close();
                bufferedOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                isRecording=false ;
            }
        }
    }


    /**
     * 停止录音
     */
     public void stopRecord() {
        isRecording = false;
        //停止录音，回收AudioRecord对象，释放内存
          if (audioRecord != null) {
              if (audioRecord.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING) {
                  audioRecord.stop();
              }
              if (audioRecord.getState() == AudioRecord.STATE_INITIALIZED) {
                  audioRecord.release();
              }
              recordingThread = null;
          }
    }




    /**
     *  录音播放
     *
     */
    public void startPlay(){
        if (!TextUtils.isEmpty(currentFilePath)){
            File file=new File(currentFilePath);
            try {
                byte[] bytes=new byte[(int) file.length()];
                FileInputStream fileInputStream=new FileInputStream(file);
                BufferedInputStream bufferedInputStream=new BufferedInputStream(fileInputStream);
                bufferedInputStream.read(bytes);

                // Set and push to audio track..
                int intSize = AudioTrack.getMinBufferSize(mSampleRateInHz, AudioFormat.CHANNEL_OUT_MONO,AudioFormat.ENCODING_PCM_16BIT);
                audioTrack=new AudioTrack(AudioManager.STREAM_MUSIC,mSampleRateInHz,AudioFormat.CHANNEL_OUT_MONO,AudioFormat.ENCODING_PCM_16BIT,intSize,AudioTrack.MODE_STREAM);
                if (audioTrack!=null){
                    audioTrack.play();
                    // Write the byte array to the track
                    audioTrack.write(bytes, 0, bytes.length);
                    audioTrack.stop();
                    audioTrack.release();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




    /**
     * 停止播放
     */
    private void stopPlay() {
        // stops the recording activity
        if (null != audioTrack) {
            audioTrack.stop();
            audioTrack.release();
            audioTrack = null;
        }
    }
}
