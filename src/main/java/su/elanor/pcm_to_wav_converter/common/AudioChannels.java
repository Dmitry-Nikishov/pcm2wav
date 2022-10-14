package su.elanor.pcm_to_wav_converter.common;

public enum AudioChannels {
    MONO("Моно", 1),
    STEREO("Стерео", 2);

    private String label;
    private int code;

    AudioChannels(String label, int code) {
        this.label = label;
        this.code = code;
    }

    public String getLabel() { return label; }

    public int getCode() { return code; }
}
