package su.elanor.pcm_to_wav_converter.common;

public enum SampleRates {
    SAMPLE_RATE_8_KHZ("8 кГц", 8_000),

    SAMPLE_RATE_16_KHZ("16 кГц", 16_000),
    SAMPLE_RATE_44_KHZ("44.1 кГц", 44_100),
    SAMPLE_RATE_48_KHZ("48 кГц", 48_000),
    SAMPLE_RATE_96_KHZ("96 кГц", 96_000);

    private String label;
    private int sampleRate;

    SampleRates(String label, int sampleRate) {
        this.label = label;
        this.sampleRate = sampleRate;
    }

    public String getLabel() { return label; }
    public int getSampleRate() { return sampleRate; }
}

