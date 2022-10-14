package su.elanor.pcm_to_wav_converter.common;

public class SampleRateFromStringConverter {
    public static SampleRates from(String label) {
        var ret = SampleRates.SAMPLE_RATE_8_KHZ;

        if (label.equals(SampleRates.SAMPLE_RATE_16_KHZ.getLabel())) {
            ret = SampleRates.SAMPLE_RATE_16_KHZ;
        } else if (label.equals(SampleRates.SAMPLE_RATE_44_KHZ.getLabel())) {
            ret = SampleRates.SAMPLE_RATE_44_KHZ;
        } else if (label.equals(SampleRates.SAMPLE_RATE_48_KHZ.getLabel())) {
            ret = SampleRates.SAMPLE_RATE_48_KHZ;
        } else if (label.equals(SampleRates.SAMPLE_RATE_96_KHZ.getLabel())) {
            ret = SampleRates.SAMPLE_RATE_96_KHZ;
        }

        return ret;
    }
}
