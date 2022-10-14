package su.elanor.pcm_to_wav_converter.common;

public class AudioChannelsFromStringConverter {
    public static AudioChannels from(String label) {
        var ret = AudioChannels.MONO;

        if (label.equals(AudioChannels.STEREO.getLabel())) {
            ret = AudioChannels.STEREO;
        }

        return ret;
    }
}
