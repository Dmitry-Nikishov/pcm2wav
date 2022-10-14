module su.elanor.pcm_to_wav_converter {
    requires javafx.controls;
    requires javafx.fxml;

    opens su.elanor.pcm_to_wav_converter to javafx.fxml;
    opens su.elanor.pcm_to_wav_converter.controllers to javafx.fxml;
    exports su.elanor.pcm_to_wav_converter;
    exports su.elanor.pcm_to_wav_converter.controllers;
}