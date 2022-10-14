package su.elanor.pcm_to_wav_converter.controllers;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import su.elanor.pcm_to_wav_converter.common.*;
import su.elanor.pcm_to_wav_converter.utility.GeneratedFileNameCreator;
import su.elanor.pcm_to_wav_converter.utility.OutFileWriter;

import java.io.File;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

public class MainViewController implements Initializable {
    @FXML
    private Button convertBtn;

    @FXML
    private AnchorPane rootItem;

    @FXML
    private ComboBox<String> channelTypeCombo;

    @FXML
    private Label convertationStatusLabel;

    @FXML
    private TextField fileToConvertTextInput;

    @FXML
    private CheckBox isUsedSpecifiedSampleRate;

    @FXML
    private ComboBox<String> sampleRateCombo;

    @FXML
    private TextField userSampleRate;

    private ObservableList<String> availableSampleRates = FXCollections.observableArrayList(
            SampleRates.SAMPLE_RATE_8_KHZ.getLabel(),
            SampleRates.SAMPLE_RATE_16_KHZ.getLabel(),
            SampleRates.SAMPLE_RATE_44_KHZ.getLabel(),
            SampleRates.SAMPLE_RATE_48_KHZ.getLabel(),
            SampleRates.SAMPLE_RATE_96_KHZ.getLabel()
    );

    private ObservableList<String> availableChannelTypes = FXCollections.observableArrayList(
            AudioChannels.MONO.getLabel(),
            AudioChannels.STEREO.getLabel()
    );

    private void chooseFileNameToConvert()
    {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Выберете файл PCM для преобразования в WAV");
        chooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File filePathToTransfer = chooser.showOpenDialog(rootItem.getScene().getWindow());
        if (filePathToTransfer != null) {
            fileToConvertTextInput.setText(filePathToTransfer.getAbsolutePath());
        }
    }

    @FXML
    void convertHandler(MouseEvent event) {
        rootItem.setDisable(true);

        var inputFileName = fileToConvertTextInput.getText();
        int srate = 0;
        if (isUsedSpecifiedSampleRate.isSelected()) {
            try {
                srate = Integer.parseInt(userSampleRate.getText());
            }catch (NumberFormatException e) {
                e.printStackTrace();
                rootItem.setDisable(false);
                return;
            }
        } else {
            srate = SampleRateFromStringConverter.from(sampleRateCombo.getValue()).getSampleRate();
        }

        int finalSrate = srate;
        CompletableFuture.runAsync(() -> {
           try {
               var inputData = Files.readAllBytes(Paths.get(inputFileName));

               var wavHeader = WavHeaderBuilder.build(finalSrate,
                                        AudioChannelsFromStringConverter.from(channelTypeCombo.getValue()),
                                        inputData.length
               );

               var fileWriter = new OutFileWriter();
               fileWriter.openChannel(GeneratedFileNameCreator.get(inputFileName));

               fileWriter.writeToChannel(ByteBuffer.wrap(wavHeader));
               fileWriter.writeToChannel(ByteBuffer.wrap(inputData));

               fileWriter.closeChannel();

               Platform.runLater(() -> {
                   convertationStatusLabel.setText("Успех");
               });
           }catch (Exception e) {
               e.printStackTrace();

               Platform.runLater(() -> {
                   convertationStatusLabel.setText("Ошибка");
               });
           }
           finally {
               Platform.runLater(() -> {
                   rootItem.setDisable(false);
               });
           }
        });
    }

    @FXML
    void fileChooseInputClicked(MouseEvent event) {
        if (event.getClickCount() == 2) {
            chooseFileNameToConvert();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        convertBtn.disableProperty().bind(Bindings.when(fileToConvertTextInput.textProperty().isEmpty())
                .then(true)
                .otherwise(false));

        sampleRateCombo.setItems(availableSampleRates);
        sampleRateCombo.setValue(SampleRates.SAMPLE_RATE_8_KHZ.getLabel());

        channelTypeCombo.setItems(availableChannelTypes);
        channelTypeCombo.setValue(AudioChannels.MONO.getLabel());

        sampleRateCombo.disableProperty().bind(
                Bindings.when(isUsedSpecifiedSampleRate.selectedProperty())
                        .then(true)
                        .otherwise(false));

        userSampleRate.disableProperty().bind(
                Bindings.when(isUsedSpecifiedSampleRate.selectedProperty())
                        .then(false)
                        .otherwise(true)
        );

        userSampleRate.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    userSampleRate.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

    }
}
