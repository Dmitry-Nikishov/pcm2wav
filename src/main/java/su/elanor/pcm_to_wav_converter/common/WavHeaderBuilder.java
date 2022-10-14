package su.elanor.pcm_to_wav_converter.common;

//MONO/STEREO
//sample rate



//static std::vector<std::uint8_t> createWavHeader(int sampleRate,
//        int streamLength)
//        {
//        const int CHUNK_SIZE = 36;
//        const int BIT_RATE_16 = 16;
//        const int MONO = 1;
//        const int HEADER_SIZE = 44;
//
//        std::vector<std::uint8_t> header(HEADER_SIZE, 0);
//
//        int srate = sampleRate;
//        int channel = MONO;
//        int format = BIT_RATE_16;
//        int dataLength = streamLength;
//
//        int totalDataLen = dataLength + CHUNK_SIZE;
//        int bitrate = srate * channel * format;
//
//
//        return header;
//        }

public class WavHeaderBuilder {
    static private byte[] intToByteArray( int data ) {
        byte[] result = new byte[4];
        result[0] = (byte) ((data & 0xFF000000) >> 24);
        result[1] = (byte) ((data & 0x00FF0000) >> 16);
        result[2] = (byte) ((data & 0x0000FF00) >> 8);
        result[3] = (byte) ((data & 0x000000FF) >> 0);
        return result;
    }

    public static byte[] build(int srate,
                               AudioChannels channels,
                               int streamLength) {
        final var header = new byte[44];
        final int dataLength = streamLength;
        final int CHUNK_SIZE = 36;
        final byte BIT_RATE_16 = 16;
        final int totalDataLen = dataLength + CHUNK_SIZE;
        final var totalDataLenBytes = intToByteArray(totalDataLen);
        final var sRateBytes = intToByteArray(srate);
        final int bitrate = srate * channels.getCode() * BIT_RATE_16;
        final var bitRateBytes = intToByteArray(bitrate/8);
        final var dataLengthBytes = intToByteArray(streamLength);

        header[0] = 'R';
        header[1] = 'I';
        header[2] = 'F';
        header[3] = 'F';
        header[4] = totalDataLenBytes[3];
        header[5] = totalDataLenBytes[2];
        header[6] = totalDataLenBytes[1];
        header[7] = totalDataLenBytes[0];
        header[8] = 'W';
        header[9] = 'A';
        header[10] = 'V';
        header[11] = 'E';
        header[12] = 'f';
        header[13] = 'm';
        header[14] = 't';
        header[15] = ' ';
        header[16] = BIT_RATE_16;
        header[17] = 0;
        header[18] = 0;
        header[19] = 0;
        header[20] = 1;
        header[21] = 0;
        header[22] = (byte) channels.getCode();
        header[23] = 0;
        header[24] = sRateBytes[3];
        header[25] = sRateBytes[2];
        header[26] = sRateBytes[1];
        header[27] = sRateBytes[0];
        header[28] = bitRateBytes[3];
        header[29] = bitRateBytes[2];
        header[30] = bitRateBytes[1];
        header[31] = bitRateBytes[0];
        header[32] = (byte) ((channels.getCode() * BIT_RATE_16) / 8);
        header[33] = 0;
        header[34] = 16;
        header[35] = 0;
        header[36] = 'd';
        header[37] = 'a';
        header[38] = 't';
        header[39] = 'a';
        header[40] = dataLengthBytes[3];
        header[41] = dataLengthBytes[2];
        header[42] = dataLengthBytes[1];
        header[43] = dataLengthBytes[0];

        return header;
    }
}
