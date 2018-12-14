package com.distributiongoods.jbh.melibrary.recording;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by zbsdata on 2018/9/11.
 */

public class MediaRecorderManager {


    private static MediaRecorderManager recorderManager;
    private MediaRecorder mediaRecorder;
    private String currentFilePath;
    private boolean isPrepare;
    private MediaPlayer  mediaPlayer;


    /**
     * 单例模式
     *
     * @return
     */
    public static MediaRecorderManager newinstance(){
        if (recorderManager==null){
            synchronized (MediaRecorderManager.class){
                if (recorderManager==null){
                    recorderManager = new MediaRecorderManager();
                }
            }
        }
        return recorderManager;
    }




    /**
     * 开始录音
     */
    public void startPrepare(){
        try {
            isPrepare = false;
            File file=new File(getFileDir());
            if (!file.exists())
                file.mkdirs();
            String fileName=getMediaFileName();
            File f=new File(file.getPath(),fileName);
            currentFilePath=f.getPath();
            mediaRecorder=new MediaRecorder();
            /*音频的来源于麦克风*/
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            /*音频输出格式*/
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
            /*音频输出编码*/
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
              /*音频输出文件*/
            mediaRecorder.setOutputFile(f.getAbsolutePath());
              /*音频准备状态*/
            mediaRecorder.prepare();
            mediaRecorder.start();
            /*准备结束*/
            isPrepare = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    /**
     * 随机生成名字
     *
     * @return
     */
    private String getMediaFileName() {
        return UUID.randomUUID().toString() + ".amr";
    }




    /**
     * 停止录音
     */
    public void reset(){
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder=null;
    }



    /**
     * 取消录音
     */
    public void cancel(){
        reset();
        if (!TextUtils.isEmpty(currentFilePath)){
            File file=new File(currentFilePath);
            file.delete();
            currentFilePath=null;
        }
    }


    /**
     * 获取文件目录
     *
     * @return
     */
    public String getFileDir(){
        String dir = null;
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            dir = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return dir + File.separator + "MediaDemo";
    }


    /**
     *
     * @param completionListener
     */
    public void playMediaRecorder(final OnCompletionListener completionListener){
        if (TextUtils.isEmpty(currentFilePath)){
            completionListener.onCompletion(null);
            return;
        }
        try {
            mediaPlayer = new MediaPlayer();
            /*播放源*/
            mediaPlayer.setDataSource(currentFilePath);
            /*准备就绪*/
            mediaPlayer.prepare();
            /*开始播放*/
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    completionListener.onCompletion(mp);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 停止播放
     */
    public void stopPlay(){
        if (mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
        }

        if (!TextUtils.isEmpty(currentFilePath)){
            File file=new File(currentFilePath);
            file.delete();
            currentFilePath=null;
        }
    }




    public interface OnCompletionListener{
        void  onCompletion(MediaPlayer mp);
    }



    private OnCompletionListener listener;
     void setCompletionListener(OnCompletionListener listener){
        this.listener=listener;
    }
}
