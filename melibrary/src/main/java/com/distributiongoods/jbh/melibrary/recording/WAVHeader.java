package com.distributiongoods.jbh.melibrary.recording;


import java.io.IOException;
/**
 * Created by zbsdata on 2018/9/12.
 */

public class WAVHeader {
    private byte[] mHeader;          // the complete header.
    private int mSampleRate;         // sampling frequency in Hz (e.g. 44100).
    private int mChannels;           // number of channels.
    private int mNumSamples;         // total number of samples per channel.
    private int mNumBytesPerSample;  // number of bytes per sample, all channels included.

    public WAVHeader(int sampleRate, int numChannels, int numSamples) {
        mSampleRate = sampleRate;
        mChannels = numChannels;
        mNumSamples = numSamples;
        mNumBytesPerSample = 2 * mChannels;  // assuming 2 bytes per sample (for 1 channel)
        mHeader = null;
        setHeader();
    }

    public byte[] getWAVHeader() {
        return mHeader;
    }

    public static byte[] getWAVHeader(int sampleRate, int numChannels, int numSamples) {
        return new WAVHeader(sampleRate, numChannels, numSamples).mHeader;
    }

    public String toString() {
        String str = "";
        if (mHeader == null) {
            return str;
        }
        int num_32bits_per_lines = 8;
        int count = 0;
        for (byte b : mHeader) {
            boolean break_line = count > 0 && count % (num_32bits_per_lines * 4) == 0;
            boolean insert_space = count > 0 && count % 4 == 0 && !break_line;
            if (break_line) {
                str += '\n';
            }
            if (insert_space) {
                str += ' ';
            }
            str += String.format("%02X", b);
            count++;
        }

        return str;
    }

    private void setHeader() {
        byte[] header = new byte[46];
        int offset = 0;
        int size;

        // set the RIFF chunk
        System.arraycopy(new byte[]{'R', 'I', 'F', 'F'}, 0, header, offset, 4);
        offset += 4;
        size = 36 + mNumSamples * mNumBytesPerSample;
        header[offset++] = (byte) (size & 0xFF);
        header[offset++] = (byte) ((size >> 8) & 0xFF);
        header[offset++] = (byte) ((size >> 16) & 0xFF);
        header[offset++] = (byte) ((size >> 24) & 0xFF);
        System.arraycopy(new byte[]{'W', 'A', 'V', 'E'}, 0, header, offset, 4);
        offset += 4;

        // set the fmt chunk
        System.arraycopy(new byte[]{'f', 'm', 't', ' '}, 0, header, offset, 4);
        offset += 4;
        System.arraycopy(new byte[]{0x10, 0, 0, 0}, 0, header, offset, 4);  // chunk size = 16
        offset += 4;
        System.arraycopy(new byte[]{1, 0}, 0, header, offset, 2);  // format = 1 for PCM
        offset += 2;
        header[offset++] = (byte) (mChannels & 0xFF);
        header[offset++] = (byte) ((mChannels >> 8) & 0xFF);
        header[offset++] = (byte) (mSampleRate & 0xFF);
        header[offset++] = (byte) ((mSampleRate >> 8) & 0xFF);
        header[offset++] = (byte) ((mSampleRate >> 16) & 0xFF);
        header[offset++] = (byte) ((mSampleRate >> 24) & 0xFF);
        int byteRate = mSampleRate * mNumBytesPerSample;
        header[offset++] = (byte) (byteRate & 0xFF);
        header[offset++] = (byte) ((byteRate >> 8) & 0xFF);
        header[offset++] = (byte) ((byteRate >> 16) & 0xFF);
        header[offset++] = (byte) ((byteRate >> 24) & 0xFF);
        header[offset++] = (byte) (mNumBytesPerSample & 0xFF);
        header[offset++] = (byte) ((mNumBytesPerSample >> 8) & 0xFF);
        System.arraycopy(new byte[]{0x10, 0}, 0, header, offset, 2);
        offset += 2;

        // set the beginning of the data chunk
        System.arraycopy(new byte[]{'d', 'a', 't', 'a'}, 0, header, offset, 4);
        offset += 4;
        size = mNumSamples * mNumBytesPerSample;
        header[offset++] = (byte) (size & 0xFF);
        header[offset++] = (byte) ((size >> 8) & 0xFF);
        header[offset++] = (byte) ((size >> 16) & 0xFF);
        header[offset++] = (byte) ((size >> 24) & 0xFF);

        mHeader = header;
    }


    public static byte[] getHeader(  long totalAudioLen,
                                     long totalDataLen, long longSampleRate, int channels, long byteRate) throws IOException {


        byte[] header = new byte[44];
        header[0] = 'R'; // RIFF/WAVE header
        header[1] = 'I';
        header[2] = 'F';
        header[3] = 'F';
        header[4] = (byte) (totalDataLen & 0xff);
        header[5] = (byte) ((totalDataLen >> 8) & 0xff);
        header[6] = (byte) ((totalDataLen >> 16) & 0xff);
        header[7] = (byte) ((totalDataLen >> 24) & 0xff);
        header[8] = 'W';
        header[9] = 'A';
        header[10] = 'V';
        header[11] = 'E';
        header[12] = 'f'; // 'fmt ' chunk
        header[13] = 'm';
        header[14] = 't';
        header[15] = ' ';
        header[16] = 16; // 4 bytes: size of 'fmt ' chunk
        header[17] = 0;
        header[18] = 0;
        header[19] = 0;
        header[20] = 1; // format = 1
        header[21] = 0;
        header[22] = (byte) channels;
        header[23] = 0;
        header[24] = (byte) (longSampleRate & 0xff);
        header[25] = (byte) ((longSampleRate >> 8) & 0xff);
        header[26] = (byte) ((longSampleRate >> 16) & 0xff);
        header[27] = (byte) ((longSampleRate >> 24) & 0xff);
        header[28] = (byte) (byteRate & 0xff);
        header[29] = (byte) ((byteRate >> 8) & 0xff);
        header[30] = (byte) ((byteRate >> 16) & 0xff);
        header[31] = (byte) ((byteRate >> 24) & 0xff);
        header[32] = (byte) (2 * 16 / 8); // block align
        header[33] = 0;
        header[34] = 16; // bits per sample
        header[35] = 0;
        header[36] = 'd';
        header[37] = 'a';
        header[38] = 't';
        header[39] = 'a';
        header[40] = (byte) (totalAudioLen & 0xff);
        header[41] = (byte) ((totalAudioLen >> 8) & 0xff);
        header[42] = (byte) ((totalAudioLen >> 16) & 0xff);
        header[43] = (byte) ((totalAudioLen >> 24) & 0xff);



        return header;
    }
}
